package org.JAutoLayout.Toolkit;


public class Utils {

    public static boolean nearZero(double value ) {
        double EPS = 1.0e-8;
        return value < 0.0 ? -value < EPS : value < EPS;
    }
}
