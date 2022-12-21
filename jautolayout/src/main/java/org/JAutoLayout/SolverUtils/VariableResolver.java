package org.JAutoLayout.SolverUtils;

import no.birkett.kiwi.*;
import no.birkett.kiwi.Solver;

import java.util.HashMap;

public class VariableResolver {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CENTERX = "centerX";
    public static final String CENTERY = "centerY";

    Solver solver;
    HashMap<String, HashMap<String, Variable>> result;

    public VariableResolver(Solver solver, HashMap<String, HashMap<String, Variable>> result) {
        this.solver = solver;
        this.result = result;
    }

    public Variable resolveVariable(String variableName) {
        String[] stringArray = variableName.split("\\.");
        if (stringArray.length == 2) {
            String nodeName = stringArray[0];
            String propertyName = stringArray[1];

            HashMap<String, Variable> node = getNode(nodeName);

            return getVariableFromNode(node, propertyName);

        } else {
            throw new RuntimeException("can't resolve variable");
        }
    }

    public Expression resolveConstant(String name) {
        try {
            return new Expression(Double.parseDouble(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Variable getVariableFromNode(HashMap<String, Variable> node, String propertyName) {
        try {
            if (node.containsKey(propertyName)) {
                return node.get(propertyName);
            } else {
                Variable variable = new Variable(propertyName);
                node.put(propertyName, variable);
                if (RIGHT.equals(propertyName)) {
                    solver.addConstraint(Symbolics.equals(variable, Symbolics.add(getVariableFromNode(node, LEFT), getVariableFromNode(node, WIDTH))));
                } else if (BOTTOM.equals(propertyName)) {
                    solver.addConstraint(Symbolics.equals(variable, Symbolics.add(getVariableFromNode(node, TOP), getVariableFromNode(node, HEIGHT))));
                }
                return variable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, Variable> getNode(String nodeName) {
        HashMap<String, Variable> node;
        if (result.containsKey(nodeName)) {
            node = result.get(nodeName);
        } else {
            node = new HashMap<>();
            result.put(nodeName, node);
        }
        return node;
    }
}
