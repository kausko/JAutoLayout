package org.JAutoLayout.Toolkit;

import java.util.*;
import java.util.function.Supplier;


public class Solver {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CENTERX = "centerX";
    public static final String CENTERY = "centerY";

    private static class Tag {
        Symbol marker;
        Symbol other;

        public Tag() {
            marker = new Symbol();
            other = new Symbol();
        }
    }

    private static class EditInfo {
        Tag tag;
        Constraint constraint;
        double constant;

        public EditInfo(Constraint constraint, Tag tag, double constant) {
            this.constraint = constraint;
            this.tag = tag;
            this.constant = constant;
        }
    }

    private final Map<Constraint, Tag> cns = new LinkedHashMap<>();
    private final Map<Symbol, Row> rows = new LinkedHashMap<>();
    private final Map<Variable, Symbol> vars = new LinkedHashMap<>();
    private final Map<Variable, EditInfo> edits = new LinkedHashMap<>();
    private final List<Symbol> infeasibleRows = new ArrayList<>();
    private final Row objective = new Row();
    private Row artificial;


    public void addConstraint(Constraint constraint) throws ConstraintException, ConstraintException {

        if (cns.containsKey(constraint)) {
            throw new ConstraintException(constraint);
        }

        Tag tag = new Tag();
        Row row = createRow(constraint, tag);
        Symbol subject = chooseSubject(row, tag);

        if (subject.getType() == Symbol.Type.INVALID && allDummies(row)) {
            if (!Utils.nearZero(row.getConstant())) {
                throw new ConstraintException(constraint);
            } else {
                subject = tag.marker;
            }
        }

        if (subject.getType() == Symbol.Type.INVALID) {
            if (!addWithArtificialVariable(row)) {
                throw new ConstraintException(constraint);
            }
        } else {
            row.solve(subject);
            substitute(subject, row);
            this.rows.put(subject, row);
        }

        this.cns.put(constraint, tag);

        optimize(objective);
    }

    public void removeConstraint(Constraint constraint) throws ConstraintException, Error {
        Tag tag = cns.get(constraint);
        if (tag == null) {
            throw new ConstraintException(constraint);
        }

        cns.remove(constraint);
        removeConstraintEffects(constraint, tag);

        Row row = rows.get(tag.marker);
        if (row != null) {
            rows.remove(tag.marker);
        } else {
            row = getMarkerLeavingRow(tag.marker);
            if (row == null) {
                throw new Error("internal solver error");
            }

            Symbol leaving = null;
            for (Symbol s : rows.keySet()) {
                if (rows.get(s) == row) {
                    leaving = s;
                }
            }
            if (leaving == null) {
                throw new Error("internal solver error");
            }

            rows.remove(leaving);
            row.solve(leaving, tag.marker);
            substitute(tag.marker, row);
        }
        optimize(objective);
    }

    void removeConstraintEffects(Constraint constraint, Tag tag) {
        if (tag.marker.getType() == Symbol.Type.ERROR) {
            removeMarkerEffects(tag.marker, constraint.getStrength());
        } else if (tag.other.getType() == Symbol.Type.ERROR) {
            removeMarkerEffects(tag.other, constraint.getStrength());
        }
    }

    void removeMarkerEffects(Symbol marker, double strength) {
        Row row = rows.get(marker);
        if (row != null) {
            objective.insert(row, -strength);
        } else {
            objective.insert(marker, -strength);
        }
    }

    Row getMarkerLeavingRow(Symbol marker) {
        double dmax = Double.MAX_VALUE;
        double r1 = dmax;
        double r2 = dmax;

        Row first = null;
        Row second = null;
        Row third = null;

        for (Symbol s : rows.keySet()) {
            Row candidateRow = rows.get(s);
            double c = candidateRow.getCoefficient(marker);
            if (c == 0.0) {
                continue;
            }
            if (s.getType() == Symbol.Type.EXTERNAL) {
                third = candidateRow;
            } else if (c < 0.0) {
                double r = -candidateRow.getConstant() / c;
                if (r < r1) {
                    r1 = r;
                    first = candidateRow;
                }
            } else {
                double r = candidateRow.getConstant() / c;
                if (r < r2) {
                    r2 = r;
                    second = candidateRow;
                }
            }
        }

        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        return third;
    }

    public boolean hasConstraint(Constraint constraint) {
        return cns.containsKey(constraint);
    }

    public void addEditVariable(Variable variable, double strength) throws Exception {
        if (edits.containsKey(variable)) {
            throw new Exception("Duplicate edit variable");
        }

        strength = Strength.clip(strength);

        if (strength == Strength.REQUIRED) {
            throw new Exception("An edit variable cannot be required");
        }

        List<Term> terms = new ArrayList<>();
        terms.add(new Term(variable));
        Constraint constraint = new Constraint(new Expression(terms), Relation.EQ, strength);

        try {
            addConstraint(constraint);
        } catch (ConstraintException e) {
            e.printStackTrace();
        }


        EditInfo info = new EditInfo(constraint, cns.get(constraint), 0.0);
        edits.put(variable, info);
    }

    public void removeEditVariable(Variable variable) throws Exception {
        EditInfo edit = edits.get(variable);
        if (edit == null) {
            throw new Exception("Unknown edit variable");
        }

        try {
            removeConstraint(edit.constraint);
        } catch (ConstraintException e) {
            e.printStackTrace();
        }

        edits.remove(variable);
    }

    public boolean hasEditVariable(Variable variable) {
        return edits.containsKey(variable);
    }

    public void suggestValue(Variable variable, double value) throws Exception {
        EditInfo info = edits.get(variable);
        if (info == null) {
            throw new Exception("Unknown edit variable");
        }

        double delta = value - info.constant;
        info.constant = value;

        Row row = rows.get(info.tag.marker);
        if (row != null) {
            if (row.add(-delta) < 0.0) {
                infeasibleRows.add(info.tag.marker);
            }
            dualOptimize();
            return;
        }

        row = rows.get(info.tag.other);
        if (row != null) {
            if (row.add(delta) < 0.0) {
                infeasibleRows.add(info.tag.other);
            }
            dualOptimize();
            return;
        }

        rows.forEach((k, v) -> {
            var coeff = v.getCoefficient(info.tag.marker);
            if (coeff != 0.0 && v.add(delta * coeff) < 0.0 && k.getType() != Symbol.Type.EXTERNAL)
                infeasibleRows.add(k);
        });

        dualOptimize();
    }

    public void updateVariables() {
        vars.forEach((k, v) -> {
            var row = this.rows.get(v);
            k.setValue(row != null ? row.getConstant() : 0);
        });
    }

    Row createRow(Constraint constraint, Tag tag) {
        Expression expression = constraint.getExpression();
        Row row = new Row(expression.getConstant());

        expression.getTerms().forEach(term -> {
            if (Utils.nearZero(term.getCoefficient())) return;

            Symbol sym = getVarSymbol(term.getVariable());

            Row symRow = rows.get(sym);
            if (symRow == null)
                row.insert(sym, term.getCoefficient());
            else
                row.insert(symRow, term.getCoefficient());
        });

        switch (constraint.getOp()) {
            case LEQ, GEQ -> {
                double coeff = constraint.getOp() == Relation.LEQ ? 1.0 : -1.0;
                Symbol slack = new Symbol(Symbol.Type.SLACK);
                tag.marker = slack;
                row.insert(slack, coeff);
                if (constraint.getStrength() < Strength.REQUIRED) {
                    Symbol error = new Symbol(Symbol.Type.ERROR);
                    tag.other = error;
                    row.insert(error, -coeff);
                    this.objective.insert(error, constraint.getStrength());
                }
            }
            case EQ -> {
                if (constraint.getStrength() < Strength.REQUIRED) {
                    Symbol errplus = new Symbol(Symbol.Type.ERROR);
                    Symbol errminus = new Symbol(Symbol.Type.ERROR);
                    tag.marker = errplus;
                    tag.other = errminus;
                    row.insert(errplus, -1.0); // v = eplus - eminus
                    row.insert(errminus, 1.0); // v - eplus + eminus = 0
                    this.objective.insert(errplus, constraint.getStrength());
                    this.objective.insert(errminus, constraint.getStrength());
                } else {
                    Symbol dummy = new Symbol(Symbol.Type.DUMMY);
                    tag.marker = dummy;
                    row.insert(dummy);
                }
            }
        }

        if (row.getConstant() < 0.0)
            row.invertSign();
        return row;
    }

    private static Symbol chooseSubject(Row row, Tag tag) {
        return row
                .getCells()
                .keySet()
                .stream()
                .filter(k -> k.getType() == Symbol.Type.EXTERNAL)
                .findFirst()
                .orElse(
                        ((Supplier<Symbol>) () -> {
                            if (tag.marker.getType() == Symbol.Type.SLACK || tag.marker.getType() == Symbol.Type.ERROR) {
                                if (row.getCoefficient(tag.marker) < 0.0)
                                    return tag.marker;
                            }
                            if (tag.other != null && (tag.other.getType() == Symbol.Type.SLACK || tag.other.getType() == Symbol.Type.ERROR)) {
                                if (row.getCoefficient(tag.other) < 0.0)
                                    return tag.other;
                            }
                            return new Symbol();
                        }).get()
                );
    }

    private boolean addWithArtificialVariable(Row row) {

        Symbol art = new Symbol(Symbol.Type.SLACK);
        rows.put(art, new Row(row));
        this.artificial = new Row(row);
        optimize(this.artificial);

        boolean success = Utils.nearZero(artificial.getConstant());
        artificial = null;

        Row rowptr = this.rows.get(art);
        if (rowptr != null) {
            LinkedList<Symbol> toRemove = rows
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() == rowptr)
                    .map(Map.Entry::getKey)
                    .collect(
                      LinkedList::new,
                      LinkedList::add,
                      LinkedList::addAll
                    );
            toRemove.forEach(rows::remove);
            toRemove.clear();

            if (rowptr.getCells().isEmpty()) {
                return success;
            }

            Symbol entering = anyPivotableSymbol(rowptr);
            if (entering.getType() == Symbol.Type.INVALID) {
                return false;
            }
            rowptr.solve(art, entering);
            substitute(entering, rowptr);
            this.rows.put(entering, rowptr);
        }

        // Remove the artificial variable from the tableau.
        for (Map.Entry<Symbol, Row> rowEntry : rows.entrySet()) {
            rowEntry.getValue().remove(art);
        }

        objective.remove(art);

        return success;
    }

    void substitute(Symbol symbol, Row row) {
        rows.forEach((k,v) -> {
            v.substitute(symbol, row);
            if (k.getType() != Symbol.Type.EXTERNAL && v.getConstant() < 0.0)
                infeasibleRows.add(k);
        });

        objective.substitute(symbol, row);
        if (artificial != null) {
            artificial.substitute(symbol, row);
        }
    }
    void optimize(Row objective) {
        while (true) {
            Symbol entering = getEnteringSymbol(objective);
            if (entering.getType() == Symbol.Type.INVALID) {
                return;
            }

            Row entry = getLeavingRow(entering);
            if (entry == null) {
                throw new Error("The objective is unbounded.");
            }
            Symbol leaving = rows
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() == entry)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            rows.remove(leaving);
            entry.solve(leaving, entering);
            substitute(entering, entry);
            rows.put(entering, entry);
        }
    }

    void dualOptimize() throws Error {
        while (!infeasibleRows.isEmpty()) {
            Symbol leaving = infeasibleRows.remove(infeasibleRows.size() - 1);
            Row row = rows.get(leaving);
            if (row != null && row.getConstant() < 0.0) {
                Symbol entering = getDualEnteringSymbol(row);
                if (entering.getType() == Symbol.Type.INVALID) {
                    throw new Error("internal solver error");
                }
                rows.remove(leaving);
                row.solve(leaving, entering);
                substitute(entering, row);
                rows.put(entering, row);
            }
        }
    }

    private static Symbol getEnteringSymbol(Row objective) {
        return objective
                .getCells()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().getType() != Symbol.Type.DUMMY && e.getValue() < 0.0)
                .findFirst()
                .orElse(Map.entry(new Symbol(), 0.0))
                .getKey();
    }

    private Symbol getDualEnteringSymbol(Row row) {
        Symbol entering = new Symbol();
        double ratio = Double.MAX_VALUE;
        for (Symbol s : row.getCells().keySet()) {
            if (s.getType() != Symbol.Type.DUMMY) {
                double currentCell = row.getCells().get(s);
                if (currentCell > 0.0) {
                    double coefficient = objective.getCoefficient(s);
                    double r = coefficient / currentCell;
                    if (r < ratio) {
                        ratio = r;
                        entering = s;
                    }
                }
            }
        }
        return entering;
    }

    private Symbol anyPivotableSymbol(Row row) {
        return row
                .getCells()
                .keySet()
                .stream()
                .filter(s -> s.getType() == Symbol.Type.SLACK || s.getType() == Symbol.Type.ERROR)
                .findFirst()
                .orElse(new Symbol());
    }

    private Row getLeavingRow(Symbol entering) {
        double minRatio = Double.MAX_VALUE;
        Row minRow = null;
        for (var entry : rows.entrySet()) {
            if (entry.getKey().getType() == Symbol.Type.EXTERNAL) continue;
            double coeff = entry.getValue().getCoefficient(entering);
            if (coeff >= 0.0) continue;
            double ratio = (-entry.getValue().getConstant() / coeff);
            if (ratio < minRatio) {
                minRatio = ratio;
                minRow = entry.getValue();
            }
        }
        return minRow;
    }

    private Symbol getVarSymbol(Variable variable) {
        Symbol symbol;
        if (vars.containsKey(variable)) {
            symbol = vars.get(variable);
        } else {
            symbol = new Symbol(Symbol.Type.EXTERNAL);
            vars.put(variable, symbol);
        }
        return symbol;
    }

    private static boolean allDummies(Row row) {
        return row
                .getCells()
                .keySet()
                .parallelStream()
                .allMatch(s -> s.getType() == Symbol.Type.DUMMY);
    }

    public HashMap<String, HashMap<String, Variable>> solve(List<String> constraints, Integer height, Integer width) throws Exception {
        HashMap<String, HashMap<String, Variable>> result = new HashMap<>();
        var variableSolver = new VariableResolver() {
            private Variable getVariableFromNode(HashMap<String, Variable> node, String propertyName) {

                try {
                    if (node.containsKey(propertyName)) {
                        return node.get(propertyName);
                    } else {
                        Variable variable = new Variable(propertyName);
                        node.put(propertyName, variable);
                        if (RIGHT.equals(propertyName)) {
                            addConstraint(Operations.equals(variable, Operations.add(getVariableFromNode(node, LEFT), getVariableFromNode(node, WIDTH))));
                        } else if (BOTTOM.equals(propertyName)) {
                            addConstraint(Operations.equals(variable, Operations.add(getVariableFromNode(node, TOP), getVariableFromNode(node, HEIGHT))));
                        }
                        return variable;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

                return null;

            }

            private HashMap<String, Variable> getNode(String nodeName) {
                HashMap<String, Variable> node;
                if (result.containsKey(nodeName)) {
                    node = result.get(nodeName);
                } else {
                    node = new HashMap<>();
                    result.put(nodeName, node);
                }
                return node;
            }

            @Override
            public Variable resolveVariable(String variableName) {

                String[] stringArray = variableName.split("\\.");
                if (stringArray.length == 2) {
                    String nodeName = stringArray[0];
                    String propertyName = stringArray[1];

                    HashMap<String, Variable> node = getNode(nodeName);

                    return getVariableFromNode(node, propertyName);

                } else {
                    throw new RuntimeException("can't resolve variable");
                }
            }

            @Override
            public Expression resolveConstant(String name) {
                try {
                    return new Expression(Double.parseDouble(name));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        };

        var defaultConstraints = Arrays.asList(
                "container.height == " + height,
                "container.width == " + width,
                "container.top == 0",
                "container.bottom == " + height,
                "container.left == 0",
                "container.right == " + width
        );

        for (String constraint : defaultConstraints) {
            var con = ConstraintParser.parseConstraint(constraint, variableSolver);
            addConstraint(con);
        }

        for (String constraint : constraints) {
            var con = ConstraintParser.parseConstraint(constraint, variableSolver);
            addConstraint(con);
        }

        updateVariables();
        return result;
    }
}
