package org.JAutoLayout.Toolkit;

public class ConstraintException extends Exception{
    public ConstraintException(Constraint constraint) {
        super(constraint.toString());
    }
}
