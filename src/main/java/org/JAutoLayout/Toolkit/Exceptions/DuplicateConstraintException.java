package org.JAutoLayout.Toolkit.Exceptions;

import org.JAutoLayout.Toolkit.Constraint;

public class DuplicateConstraintException extends Exception {
    private Constraint constraint;

    public DuplicateConstraintException(Constraint constraint) {
        this.constraint = constraint;
    }
}
