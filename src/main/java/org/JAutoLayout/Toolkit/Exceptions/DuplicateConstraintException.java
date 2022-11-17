package org.JAutoLayout.Toolkit.Exceptions;

import org.JAutoLayout.Toolkit.Constraint;


public class DuplicateConstraintException extends KiwiException {

    private Constraint constraint;

    public DuplicateConstraintException(Constraint constraint) {
        this.constraint = constraint;
    }
}
