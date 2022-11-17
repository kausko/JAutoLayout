package org.JAutoLayout.Toolkit;

import java.util.LinkedHashMap;
import java.util.Map;


public class Row {

    private double constant;

    private Map<Symbol, Double> cells = new LinkedHashMap<>();

    public Row() {
        this(0);
    }

    public Row(double constant) {
        this.constant = constant;
    }

    public Row(Row other) {
        this.cells = new LinkedHashMap<>(other.cells);
        this.constant = other.constant;
    }

    public double getConstant() {
        return constant;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    public Map<Symbol, Double> getCells() {
        return cells;
    }

    public void setCells(Map<Symbol, Double> cells) {
        this.cells = cells;
    }

    double add(double value) {
        return this.constant += value;
    }

    void insert(Symbol symbol, double coefficient) {
        var addedCoefficient = coefficient + cells.getOrDefault(symbol, 0.0);
        if (Utils.nearZero(addedCoefficient)) {
            cells.remove(symbol);
        } else {
            cells.put(symbol, addedCoefficient);
        }
    }
    void insert(Symbol symbol) {
        insert(symbol, 1.0);
    }

    void insert(Row other, double coefficient) {

        this.constant += other.constant * coefficient;
        for (Symbol s : other.cells.keySet()) {
            var coeff = other.cells.get(s) * coefficient;
            this.cells.putIfAbsent(s, 0.0);
            var temp = this.cells.get(s) + coeff;
            this.cells.put(s, temp);
            if (Utils.nearZero(temp)) {
                this.cells.remove(s);
            }
        }
    }

    void insert(Row other) {
        insert(other, 1.0);
    }

    void remove(Symbol symbol) { cells.remove(symbol); }

    void invertSign() {
        constant = -constant;
        cells.replaceAll((k, v) -> -v);
    }

    void solve(Symbol symbol) {
        var coefficient = -1.0 / cells.get(symbol);
        cells.remove(symbol);
        this.constant *= coefficient;
        cells.replaceAll((k, v) -> v * coefficient);
    }

    void solve(Symbol lhs, Symbol rhs) {
        insert(lhs, -1.0);
        solve(rhs);
    }
    double getCoefficient(Symbol symbol) { return cells.getOrDefault(symbol, 0.0);}

    void substitute(Symbol symbol, Row row) {
        if (cells.containsKey(symbol)) {
            insert(row, cells.remove(symbol));
        }
    }

}
