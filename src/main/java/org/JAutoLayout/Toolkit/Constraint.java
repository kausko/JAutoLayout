package org.JAutoLayout.Toolkit;

import java.util.*;


public class Constraint {

    private Expression expression;
    private double strength;
    private Relation op;

    public Constraint(){
    }

    public Constraint(Expression expr, Relation op) {
        this(expr, op, Strength.REQUIRED);
    }

    public Constraint(Expression expr, Relation op, double strength) {
        this.expression = reduce(expr);
        this.op = op;
        this.strength = Strength.clip(strength);
    }

    public Constraint(Constraint other, double strength) {
        this(other.expression, other.op, strength);
    }

    private static Expression reduce(Expression expr){

        return new Expression(
                expr
                        .getTerms()
                        .stream()
                        .collect(
                                LinkedHashMap<Variable, Double>::new,
                                (m, t) -> m.put(t.getVariable(), m.getOrDefault(t.getVariable(), 0.0) + t.getCoefficient()),
                                LinkedHashMap::putAll
                        )
                        .entrySet()
                        .stream()
                        .map(entry -> new Term(entry.getKey(), entry.getValue()))
                        .toList(),
                expr.getConstant()
        );
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public double getStrength() {
        return strength;
    }

    public Constraint setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    public Relation getOp() {
        return op;
    }

    public void setOp(Relation op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "expression: (" + expression + ") strength: " + strength + " operator: " + op;
    }

}
