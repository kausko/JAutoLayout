package org.JAutoLayout.Toolkit;

import java.util.ArrayList;
import java.util.List;


public class Expression {

    private List<Term> terms;

    private double constant;

    public Expression() {
        this(0);
    }

    public Expression(double constant) {
        this.constant = constant;
        this.terms = new ArrayList<>();
    }

    public Expression(Term term, double constant) {
        this.terms = new ArrayList<>();
        terms.add(term);
        this.constant = constant;
    }

    public Expression(Term term) {
        this (term, 0.0);
    }

    public Expression(List<Term> terms, double constant) {
        this.terms = terms;
        this.constant = constant;
    }

    public Expression(List<Term> terms) {
        this(terms, 0);
    }

    public double getConstant() {
        return constant;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public double getValue() {
        return terms
                .parallelStream()
                .mapToDouble(Term::getValue)
                .sum() + constant;
    }

    public final boolean isConstant() {
        return terms.size() == 0;
    }

    @Override
    public String toString() {
        return "constant: " + constant + terms
                .stream()
                .map(Term::toString)
                .map(s -> "(" + s + ")")
                .reduce("terms: ", (s1, s2) -> s1 + s2);
    }

}

