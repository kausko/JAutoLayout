package org.JAutoLayout.Toolkit;

import org.JAutoLayout.Toolkit.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Tests {

    private static double EPSILON = 1.0e-8;

    @Test()
    public void simpleNew() throws Exception {
        Solver solver = new Solver();
        Variable x = new Variable("x");


        solver.addConstraint(Operations.equals(Operations.add(x, 2.0), 20.0));

        solver.updateVariables();

        assertEquals(x.getValue(), 18, EPSILON);
    }

    @Test
    public void simple0() throws Exception {
        Solver solver = new Solver();
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        solver.addConstraint(Operations.equals(x, 20.0));

        solver.addConstraint(Operations.equals(Operations.add(x, 2.0), Operations.add(y, 10.0)));

        solver.updateVariables();

        System.out.println("x " + x.getValue() + " y " + y.getValue());

        assertEquals(y.getValue(), 12, EPSILON);
        assertEquals(x.getValue(), 20, EPSILON);
    }

    @Test
    public void simple1() throws Exception {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Solver solver = new Solver();
        solver.addConstraint(Operations.equals(x, y));
        solver.updateVariables();
        assertEquals(x.getValue(), y.getValue(), EPSILON);
    }

    @Test
    public void casso1() throws Exception {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Solver solver = new Solver();

        solver.addConstraint(Operations.lessThanOrEqualTo(x, y));
        solver.addConstraint(Operations.equals(y, Operations.add(x, 3.0)));
        solver.addConstraint(Operations.equals(x, 10.0).setStrength(Strength.WEAK));
        solver.addConstraint(Operations.equals(y, 10.0).setStrength(Strength.WEAK));

        solver.updateVariables();

        if (Math.abs(x.getValue() - 10.0) < EPSILON) {
            assertEquals(10, x.getValue(), EPSILON);
            assertEquals(13, y.getValue(), EPSILON);
        } else {
            assertEquals(7, x.getValue(), EPSILON);
            assertEquals(10, y.getValue(), EPSILON);
        }
    }

    @Test
    public void addDelete1() throws Exception {
        Variable x = new Variable("x");
        Solver solver = new Solver();

        solver.addConstraint(Operations.lessThanOrEqualTo(x, 100.0).setStrength(Strength.WEAK));

        solver.updateVariables();
        assertEquals(100, x.getValue(), EPSILON);

        Constraint c10 = Operations.lessThanOrEqualTo(x, 10.0);
        Constraint c20 = Operations.lessThanOrEqualTo(x, 20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);

        solver.updateVariables();

        assertEquals(10, x.getValue(), EPSILON);

        solver.removeConstraint(c10);

        solver.updateVariables();

        assertEquals(20, x.getValue(), EPSILON);

        solver.removeConstraint(c20);
        solver.updateVariables();

        assertEquals(100, x.getValue(), EPSILON);

        Constraint c10again = Operations.lessThanOrEqualTo(x, 10.0);

        solver.addConstraint(c10again);
        solver.addConstraint(c10);
        solver.updateVariables();

        assertEquals(10, x.getValue(), EPSILON);

        solver.removeConstraint(c10);
        solver.updateVariables();
        assertEquals(10, x.getValue(), EPSILON);

        solver.removeConstraint(c10again);
        solver.updateVariables();
        assertEquals(100, x.getValue(), EPSILON);
    }

    @Test
    public void addDelete2() throws Exception {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Solver solver = new Solver();

        solver.addConstraint(Operations.equals(x, 100.0).setStrength(Strength.WEAK));
        solver.addConstraint(Operations.equals(y, 120.0).setStrength(Strength.STRONG));

        Constraint c10 = Operations.lessThanOrEqualTo(x, 10.0);
        Constraint c20 = Operations.lessThanOrEqualTo(x, 20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);
        solver.updateVariables();

        assertEquals(10, x.getValue(), EPSILON);
        assertEquals(120, y.getValue(), EPSILON);

        solver.removeConstraint(c10);
        solver.updateVariables();

        assertEquals(20, x.getValue(), EPSILON);
        assertEquals(120, y.getValue(), EPSILON);

        Constraint cxy = Operations.equals(Operations.multiply(x, 2.0), y);
        solver.addConstraint(cxy);
        solver.updateVariables();

        assertEquals(20, x.getValue(), EPSILON);
        assertEquals(40, y.getValue(), EPSILON);

        solver.removeConstraint(c20);
        solver.updateVariables();

        assertEquals(60, x.getValue(), EPSILON);
        assertEquals(120, y.getValue(), EPSILON);

        solver.removeConstraint(cxy);
        solver.updateVariables();

        assertEquals(100, x.getValue(), EPSILON);
        assertEquals(120, y.getValue(), EPSILON);
    }

    @Test(expected = ConstraintException.class)
    public void inconsistent1() throws InternalError, Exception {
        Variable x = new Variable("x");
        Solver solver = new Solver();

        solver.addConstraint(Operations.equals(x, 10.0));
        solver.addConstraint(Operations.equals(x, 5.0));

        solver.updateVariables();
    }

    @Test(expected = ConstraintException.class)
    public void inconsistent2() throws Exception {
        Variable x = new Variable("x");
        Solver solver = new Solver();

        solver.addConstraint(Operations.greaterThanOrEqualTo(x, 10.0));
        solver.addConstraint(Operations.lessThanOrEqualTo(x, 5.0));
        solver.updateVariables();
    }

    @Test(expected = ConstraintException.class)
    public void inconsistent3() throws Exception {

        Variable w = new Variable("w");
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        Solver solver = new Solver();

        solver.addConstraint(Operations.greaterThanOrEqualTo(w, 10.0));
        solver.addConstraint(Operations.greaterThanOrEqualTo(x, w));
        solver.addConstraint(Operations.greaterThanOrEqualTo(y, x));
        solver.addConstraint(Operations.greaterThanOrEqualTo(z, y));
        solver.addConstraint(Operations.greaterThanOrEqualTo(z, 8.0));
        solver.addConstraint(Operations.lessThanOrEqualTo(z, 4.0));
        solver.updateVariables();
    }

}
