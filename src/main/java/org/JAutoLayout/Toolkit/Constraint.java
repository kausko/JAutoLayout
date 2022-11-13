package org.JAutoLayout.Toolkit;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constraint {
    private Expression expression;
    private Relation relation;
    private double strength;

    public Constraint(Expression expression, Relation relation) {
        this(expression, relation, Strength.REQUIRED.getValue());
    }

    public Constraint(Expression expression, Relation relation, double strength) {
        this.expression = expression;
        this.relation = relation;
        this.strength = Strength.clip(strength);
    }

    public Constraint(Constraint c, double strength) {
        this(c.expression, c.relation, strength);
    }

    public static Expression reduce(Expression e) {
        return new Expression(
                e
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
                e.getConstant()
        );
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public double getStrength() {
        return strength;
    }

    public Constraint setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    @Override
    public String toString() {
        return "expression: (" + expression + ") strength: " + strength + " relation: " + relation;
    }
}
