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

    public Row(Row row) {
        this.cells = new LinkedHashMap<>(row.cells);
        this.constant = row.constant;
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

    double add(double value) { return this.constant += value; }

    void insert(Symbol sym, double coefficient) {
        var addedCoefficient = coefficient + cells.getOrDefault(sym, 0.0);
        if (Utils.nearZero(addedCoefficient)) {
            cells.remove(sym);
        } else {
            cells.put(sym, addedCoefficient);
        }
    }

    void insert(Symbol sym) { insert(sym, 1.0); }

    void insert(Row row, double coefficient) {
        this.constant += row.constant * coefficient;
        for (Symbol s : row.cells.keySet()) {
            var coeff = row.cells.get(s) * coefficient;
            this.cells.putIfAbsent(s, 0.0);
            var temp = this.cells.get(s) + coeff;
            this.cells.put(s, temp);
            if (Utils.nearZero(temp)) {
                this.cells.remove(s);
            }
        }
    }

    void insert(Row row) { insert(row, 1.0); }

    void remove(Symbol sym) { cells.remove(sym); }

    void invertSign() {
        constant = -constant;
        cells.replaceAll((k, v) -> -v);
    }

    void solve(Symbol sym) {
        var coefficient = -1.0 / cells.remove(sym);
        this.constant *= coefficient;
        cells.replaceAll((k, v) -> v * coefficient);
    }

    void solve(Symbol lhs, Symbol rhs) {
        insert(lhs, -1.0);
        solve(rhs);
    }

    double getCoefficient(Symbol sym) { return cells.getOrDefault(sym, 0.0); }

    void substitute(Symbol sym, Row row) {
        if (cells.containsKey(sym)) {
            insert(row, cells.remove(sym));
        }
    }
}
