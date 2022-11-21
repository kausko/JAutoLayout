package org.JAutoLayout.Toolkit;

public interface VariableResolver {
    Variable resolveVariable(String variableName);

    Expression resolveConstant(String name);
}
