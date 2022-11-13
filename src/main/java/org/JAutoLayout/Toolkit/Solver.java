package org.JAutoLayout.Toolkit;

import org.JAutoLayout.Toolkit.Exceptions.*;

import java.util.*;

public class Solver {
    private static record Tag(Symbol marker, Symbol other) {
    }

    private static record EditRecord(Tag tag, Constraint constraint, double constant) {
    }

    private Map<Constraint, Tag> cns = new LinkedHashMap<>();
    private Map<Symbol, Row> rows = new LinkedHashMap<>();
    private Map<Variable, Symbol> vars = new LinkedHashMap<>();
    private Map<Variable, EditRecord> edits = new LinkedHashMap<>();
    private List<Symbol> infeasibleRows = new ArrayList<>();
    private Row objective = new Row();
    private Row artificial;

    public void addConstraint(Constraint constraint) throws DuplicateConstraintException, UnsatisfiableConstraintException {
        if (cns.containsKey(constraint)) {
            throw new DuplicateConstraintException(constraint);
        }
        Tag tag = new Tag(Symbol.INVALID, Symbol.INVALID);
        Row row = createRow(constraint, tag);
        Symbol subject = chooseSubject(row, tag);

        if (subject == Symbol.INVALID && allDummies(row)) {
            if (!Utils.nearZero(row.getConstant()))
                throw new UnsatisfiableConstraintException(constraint);
            else
                subject = tag.marker();
        }

        if (subject == Symbol.INVALID) {
            if (!addWithArtificialVariables(row))
                throw new UnsatisfiableConstraintException(constraint);
        } else {
            row.solve(subject);
            substitute(subject, row);
            rows.put(subject, row);
        }
        cns.put(constraint, tag);
        optimize(objective);
    }

    public void removeConstraint(Constraint constraint) throws UnsatisfiableConstraintException {
        Tag tag = cns.get(constraint);
        if (tag == null)
            throw new UnsatisfiableConstraintException(constraint);

        cns.remove(constraint);
        removeConstraintEffects(constraint, tag);

        Row row = rows.get(tag.marker());
        if (row != null)
            rows.remove(tag.marker());
        else {
            row = getMarkerLeavingRow(tag.marker());
            if (row == null)
                throw new InternalSolverError("Internal error: unable to find leaving row for a marker variable");


            Symbol leaving = null;
            for(Symbol s: rows.keySet()){
                if(rows.get(s) == row){
                    leaving = s;
                }
            }
            if(leaving == null){
                throw new InternalSolverError("internal solver error");
            }

            rows.remove(leaving);
            row.solve(leaving, tag.marker());
            substitute(tag.marker(), row);
        }
        optimize(objective);
    }

    public boolean hasConstraint(Constraint constraint) { return cns.containsKey(constraint); }

    public void addEditVariable(Variable variable, double strength) throws DuplicateEditVariableException, RequiredFailureException {
        if (edits.containsKey(variable))
            throw new DuplicateEditVariableException();

        strength = Utils.clip(0.0, strength, Strength.REQUIRED.getValue());

        if (strength == Strength.REQUIRED.getValue())
            throw new RequiredFailureException();

        List<Term> terms = new ArrayList<>();
        terms.add(new Term(variable));
        Constraint cn = new Constraint(new Expression(terms), Relation.EQ, strength);

        try {
            addConstraint(cn);
        } catch (DuplicateConstraintException | UnsatisfiableConstraintException e) {
            e.printStackTrace();
        }

        EditRecord er = new EditRecord(cns.get(cn), cn, 0.0);
        edits.put(variable, er);
    }

    void removeConstraintEffects(Constraint constraint, Tag tag) {
        if (tag.marker() == Symbol.ERROR) {
            removeMarkerEffects(tag.marker, constraint.getStrength());
        } else if (tag.other() == Symbol.ERROR) {
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
            if (s == Symbol.EXTERNAL) {
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

    Row createRow(Constraint constraint, Tag tag) {
        Expression expr = constraint.getExpression();
        Row row = new Row(expr.getConstant());

        expr.getTerms().forEach(term -> {
            if (Utils.nearZero(term.getCoefficient())) return;

            Symbol sym = Optional
                    .ofNullable(vars.putIfAbsent(term.getVariable(), Symbol.EXTERNAL))
                    .orElse(Symbol.EXTERNAL);

            Row symRow = rows.get(sym);
            if (symRow == null)
                row.insert(sym, term.getCoefficient());
            else
                row.insert(symRow, term.getCoefficient());
        });

        switch (constraint.getRelation()) {
            case LTE, GTE -> {
                var coeff = constraint.getRelation().equals(Relation.LTE) ? 1.0 : -1.0;
                tag = new Tag(Symbol.SLACK, tag.other());
                row.insert(Symbol.SLACK, coeff);
                if (constraint.getStrength() < Strength.REQUIRED.getValue()) {
                    tag = new Tag(Symbol.ERROR, tag.other());
                    row.insert(Symbol.ERROR, -coeff);
                    this.objective.insert(Symbol.ERROR, constraint.getStrength());
                }
            }
            case EQ -> {
                if (constraint.getStrength() < Strength.REQUIRED.getValue()) {
                    row.insert(Symbol.ERROR, -1.0);
                    row.insert(Symbol.ERROR, 1.0);
                    this.objective.insert(Symbol.ERROR, constraint.getStrength());
                    this.objective.insert(Symbol.ERROR, constraint.getStrength());
                } else {
                    tag = new Tag(Symbol.DUMMY, tag.other());
                    row.insert(Symbol.DUMMY);
                }
            }
        }

        if (row.getConstant() < 0.0)
            row.invertSign();
        return row;
    }

    private static Symbol chooseSubject(Row row, Tag tag) {
        for (var entry : row.getCells().entrySet())
            if (entry.getKey() == Symbol.EXTERNAL)
                return entry.getKey();

        if (tag.marker() == Symbol.SLACK || tag.marker() == Symbol.ERROR)
            if (row.getCoefficient(tag.marker()) < 0.0)
                return tag.marker();

        if (tag.other() != null && tag.other() == Symbol.SLACK || tag.other() == Symbol.ERROR)
            if (row.getCoefficient(tag.other()) < 0.0)
                return tag.other();

        return Symbol.INVALID;
    }

    private static boolean allDummies(Row row) {
        return row
                .getCells()
                .keySet()
                .stream()
                .allMatch(sym -> sym == Symbol.DUMMY);
    }

    private boolean addWithArtificialVariables(Row row) {
        var artificialSymbol = Symbol.SLACK;
        rows.put(artificialSymbol, new Row(row));
        this.artificial = new Row(row);
        optimize(this.artificial);

        var success = Utils.nearZero(this.artificial.getConstant());
        artificial = null;

        Row artificialRow = rows.get(artificialSymbol);
        if (artificialRow != null) {
            LinkedList<Symbol> toRemove = rows
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() == artificialRow)
                    .map(Map.Entry::getKey)
                    .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

            toRemove.forEach(rows::remove);
            toRemove.clear();

            if (artificialRow.getCells().isEmpty())
                return success;

            Symbol entry = findPivotableSymbol(artificialRow);
            if (entry == Symbol.INVALID)
                return false;

            artificialRow.solve(artificialSymbol, entry);
            substitute(entry, artificialRow);
            this.rows.put(entry, artificialRow);
        }

        rows.forEach((key, value) -> value.remove(artificialSymbol));
        objective.remove(artificialSymbol);
        return success;
    }

    void optimize(Row row) {
        while (true) {
            Symbol entering = getEnteringSymbol(objective);
            if (entering == Symbol.INVALID)
                return;

            Row entry = getLeavingRow(entering);
            if (entry == null)
                throw new InternalSolverError("Objective function is unbounded");

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

    private static Symbol getEnteringSymbol(Row objective) {
        return objective
                .getCells()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != Symbol.DUMMY && entry.getValue() < 0.0)
                .findFirst()
                .orElse(Map.entry(Symbol.INVALID, 0.0))
                .getKey();
    }

    private Row getLeavingRow(Symbol entering) {
        double minRatio = Double.MAX_VALUE;
        Row minRow = null;
        for (var entry : rows.entrySet()) {
            if (entry.getKey() == Symbol.EXTERNAL) continue;
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

    void substitute(Symbol symbol, Row row) {
        rows.forEach((key, value) -> {
            value.substitute(symbol, row);
            if (key != Symbol.EXTERNAL && value.getConstant() < 0.0)
                infeasibleRows.add(key);
        });
        objective.substitute(symbol, row);
        if (artificial != null)
            artificial.substitute(symbol, row);
    }

    private Symbol findPivotableSymbol(Row row) {
        return row
                .getCells()
                .keySet()
                .stream()
                .filter(sym -> sym == Symbol.SLACK || sym == Symbol.ERROR)
                .findFirst()
                .orElse(Symbol.INVALID);
    }
}
