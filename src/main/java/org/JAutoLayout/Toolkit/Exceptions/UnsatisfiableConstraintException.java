package org.JAutoLayout.Toolkit.Exceptions;

import org.JAutoLayout.Toolkit.Constraint;


public class UnsatisfiableConstraintException extends KiwiException {

    private Constraint constraint;
    public UnsatisfiableConstraintException(Constraint constraint) {
        super(constraint.toString());
        this.constraint = constraint;
    }
}
