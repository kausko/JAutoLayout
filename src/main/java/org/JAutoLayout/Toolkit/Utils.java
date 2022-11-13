package org.JAutoLayout.Toolkit;

public class Utils {

    private static final double tolerance = 1.0e-8;
    public static double clip(double lower, double value, double upper) {
        return Math.max(lower, Math.min(value, upper));
    }

    public static boolean nearZero(double value) {
        return value < 0.0 ? -value < tolerance : value < tolerance;
    }
}
