package org.JAutoLayout.Toolkit.inequality;

import org.JAutoLayout.Toolkit.ConstraintException;
import org.junit.Test;

import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.Toolkit.Operations;
import org.JAutoLayout.Toolkit.Variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alex on 31/01/16.
 */
public class VariableVariableTest {

    private static double EPSILON = 1.0e-8;

    @Test
    public void lessThanEqualTo() throws Exception {
        Solver solver = new Solver();

        Variable x = new Variable("x");
        Variable y = new Variable("y");

        solver.addConstraint(Operations.equals(y, 100));
        solver.addConstraint(Operations.lessThanOrEqualTo(x, y));

        solver.updateVariables();
        assertTrue(x.getValue() <= 100);
        solver.addConstraint(Operations.equals(x, 90));
        solver.updateVariables();
        assertEquals(x.getValue(), 90, EPSILON);
    }

    @Test(expected = ConstraintException.class)
    public void lessThanEqualToUnsatisfiable() throws Exception {
        Solver solver = new Solver();

        Variable x = new Variable("x");
        Variable y = new Variable("y");

        solver.addConstraint(Operations.equals(y, 100));
        solver.addConstraint(Operations.lessThanOrEqualTo(x, y));

        solver.updateVariables();
        assertTrue(x.getValue() <= 100);
        solver.addConstraint(Operations.equals(x, 110));
        solver.updateVariables();
    }

    @Test
    public void greaterThanEqualTo() throws Exception {
        Solver solver = new Solver();

        Variable x = new Variable("x");
        Variable y = new Variable("y");

        solver.addConstraint(Operations.equals(y, 100));
        solver.addConstraint(Operations.greaterThanOrEqualTo(x, y));

        solver.updateVariables();
        assertTrue(x.getValue() >= 100);
        solver.addConstraint(Operations.equals(x, 110));
        solver.updateVariables();
        assertEquals(x.getValue(), 110, EPSILON);
    }

    @Test(expected = ConstraintException.class)
    public void greaterThanEqualToUnsatisfiable() throws Exception {

        Solver solver = new Solver();

        Variable x = new Variable("x");
        Variable y = new Variable("y");

        solver.addConstraint(Operations.equals(y, 100));

        solver.addConstraint(Operations.greaterThanOrEqualTo(x, y));
        solver.updateVariables();
        assertTrue(x.getValue() >= 100);
        solver.addConstraint(Operations.equals(x, 90));
        solver.updateVariables();
    }
}
