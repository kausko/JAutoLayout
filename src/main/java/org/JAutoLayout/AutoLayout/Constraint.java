package org.JAutoLayout.AutoLayout;

import java.util.List;

public final class Constraint {
    private static List<String> userConstraint;

    public static List<String> getUserConstraint(){
        return userConstraint;
    }

    public static void setUserConstraint( List<String> constraint ){
        Constraint.userConstraint = constraint;
    }
}
