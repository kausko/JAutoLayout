package org.JAutoLayout.Toolkit;

import java.util.ArrayList;
import java.util.List;

public class Operations {

    private Operations() {
    }

    public static <T, U, V> T multiply(U u, V v) throws Exception {

        if (u instanceof Double u1) {
            if (v instanceof Double v1)
                return (T) Double.valueOf(u1 * v1);

            return multiply(v, u1);
        }

        if (v instanceof Expression e2) {
            Expression e1 = (Expression) u;
            if (e1.isConstant())
                return multiply(e1.getConstant(), e2);
            if (e2.isConstant())
                return multiply(e1, e2.getConstant());
            throw new Exception("Nonlinear expression");
        }

        Double coefficient = (Double) v;

        if (u instanceof Variable variable)
            return (T) new Term(variable, coefficient);

        if (u instanceof Term term)
            return (T) new Term(term.getVariable(), term.getCoefficient() * coefficient);

        Expression expression = (Expression) u;
        List<Term> terms = expression
                .getTerms()
                .stream()
                .map(t -> new Term(t.getVariable(), t.getCoefficient() * coefficient))
                .toList();

        return (T) new Expression(terms, expression.getConstant() * coefficient);
    }

    public static <T, U, V> T divide(U u, V v) throws Exception {
        if (v instanceof Expression e2) {
            if (e2.isConstant())
                return divide(u, e2.getConstant());
            throw new Exception("Nonlinear expression");
        }
        return multiply(u, 1 / (Double) v);
    }

    public static <T, U> T negate(U u) throws Exception {
        return multiply(u, -1.0);
    }

    public static <U, V> Expression add(U u, V v) throws Exception {
        if (u instanceof Double)
            return add(v, u);

        if (u instanceof Expression e1) {
            if (v instanceof Double constant)
                return new Expression(e1.getTerms(), e1.getConstant() + constant);
            if (v instanceof Variable variable)
                return add(e1, new Term(variable));
            Expression e2;
            if (v instanceof Term term)
                e2 = new Expression(term);
            else
                e2 = (Expression) v;
            List<Term> terms = new ArrayList<>(e1.getTerms().size() + e2.getTerms().size());
            terms.addAll(e1.getTerms());
            terms.addAll(e2.getTerms());
            return new Expression(terms, e1.getConstant() + e2.getConstant());
        }

        if (u instanceof Term t) {
            if (v instanceof Expression e)
                return add(e, t);
            if (v instanceof Term t2)
                return add(new Expression(t), t2);
            if (v instanceof Variable variable)
                return add(t, new Term(variable));
            return new Expression(t, (Double) v);
        }

        Variable variable = (Variable) u;
        if (v instanceof Expression e)
            return add(e, variable);
        if (v instanceof Term t)
            return add(t, variable);
        if (v instanceof Variable v2)
            return add(new Term(variable), v2);
        return new Expression(new Term(variable), (Double) v);
    }

    public static <U, V> Expression subtract(U u, V v) throws Exception {
        return add(u, negate(v));
    }

    public static <U, V> Constraint equals(U u, V v) throws Exception {
        if (u instanceof Double)
            return equals(v, u);

        if (u instanceof Variable variable) {
            if (v instanceof Expression || v instanceof Term)
                return equals(v, variable);
            return equals(new Term(variable), v);
        }

        if (u instanceof Term term) {
            if (v instanceof Expression)
                return equals(v, term);
            return equals(new Expression(term), v);
        }

        Expression e1 = (Expression) u;
        if (v instanceof Expression e2)
            return new Constraint(subtract(e1, e2), Relation.EQ);
        if (v instanceof Variable variable)
            return equals(e1, new Term(variable));
        if (v instanceof Term t)
            return equals(e1, new Expression(t));
        Double constant = (Double) v;
        return equals(e1, new Expression(constant));
    }

    public static <U, V> Constraint lessThanOrEqualTo(U u, V v) throws Exception {
        if (u instanceof Double)
            return lessThanOrEqualTo(v, u);

        if (u instanceof Variable variable)
            return lessThanOrEqualTo(new Term(variable), v);

        if (u instanceof Term term)
            return lessThanOrEqualTo(new Expression(term), v);

        Expression e1 = (Expression) u;
        if (v instanceof Expression e2)
            return new Constraint(subtract(e1, e2), Relation.LEQ);
        if (v instanceof Variable variable)
            return lessThanOrEqualTo(e1, new Term(variable));
        if (v instanceof Term t)
            return lessThanOrEqualTo(e1, new Expression(t));
        Double constant = (Double) v;
        return lessThanOrEqualTo(e1, new Expression(constant));
    }

    public static <U, V> Constraint greaterThanOrEqualTo(U u, V v) throws Exception {
        if (u instanceof Double)
            return greaterThanOrEqualTo(v, u);

        if (u instanceof Variable variable)
            return greaterThanOrEqualTo(new Term(variable), v);

        if (u instanceof Term term)
            return greaterThanOrEqualTo(new Expression(term), v);

        Expression e1 = (Expression) u;
        if (v instanceof Expression e2)
            return new Constraint(subtract(e1, e2), Relation.GEQ);
        if (v instanceof Variable variable)
            return greaterThanOrEqualTo(e1, new Term(variable));
        if (v instanceof Term t)
            return greaterThanOrEqualTo(e1, new Expression(t));
        Double constant = (Double) v;
        return greaterThanOrEqualTo(e1, new Expression(constant));
    }

    // Constraint strength modifier
    public static Constraint modifyStrength(Constraint constraint, double strength) {
        return new Constraint(constraint, strength);
    }

    public static Constraint modifyStrength(double strength, Constraint constraint) {
        return modifyStrength(constraint, strength);
    }

}
