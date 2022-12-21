package org.JAutoLayout.SolverUtils;

import no.birkett.kiwi.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solver {
    int width, height;

    public Solver(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public HashMap<String, HashMap<String, Variable>> solve(List<String> constraints) throws Exception {

        var solver = new no.birkett.kiwi.Solver();
        HashMap<String, HashMap<String, Variable>> result = new HashMap<>();

        var variableResolver = new VariableResolver(solver, result);

        var allConstraints = new ArrayList<String>();
        allConstraints.addAll(Arrays.asList(
                "container.height == " + height,
                "container.width == " + width,
                "container.top == 0",
                "container.bottom == " + height,
                "container.left == 0",
                "container.right == " + width
        ));
        allConstraints.addAll(constraints);

        for (String constraint : allConstraints) {
            var con = ConstraintParser.parseConstraint(constraint, variableResolver);
            solver.addConstraint(con);
        }

        solver.updateVariables();
        return result;
    }
}
