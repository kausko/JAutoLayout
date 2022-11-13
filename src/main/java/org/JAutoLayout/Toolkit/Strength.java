package org.JAutoLayout.Toolkit;

public enum Strength {
    REQUIRED(1000.0, 1000.0, 1000.0),
    STRONG(1.0, 0.0, 0.0),
    MEDIUM(0.0, 1.0, 0.0),
    WEAK(0.0, 0.0, 1.0);

    private double value;

    Strength(double a, double b, double c, double w) {
        value = 0.0;
        value += Utils.clip(0.0, a * w, 1000.0) * 1000000.0;
        value += Utils.clip(0.0, b * w, 1000.0) * 1000.0;
        value += Utils.clip(0.0, c * w, 1000.0);
    }

    Strength(double a, double b, double c) {
        this(a, b, c, 1.0);
    }

    public double getValue() {
        return value;
    }

    public static double clip(double value) {
        return Utils.clip(0.0, value, REQUIRED.getValue());
    }
}
