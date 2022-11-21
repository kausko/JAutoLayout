package org.JAutoLayout.Toolkit;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstraintParser {

    private static final Pattern pattern = Pattern.compile("\\s*(.*?)\\s*(<=|==|>=|[GL]?EQ)\\s*(.*?)\\s*(@[0-9]+)?");

    final static String OPS = "-+/*^";

    public static Constraint parseConstraint(String constraintString, VariableResolver variableResolver) throws Exception {

        Matcher matcher = pattern.matcher(constraintString);
        if (matcher.matches()) {
            Variable variable = variableResolver.resolveVariable(matcher.group(1));
            Relation operator = parseOperator(matcher.group(2));
            Expression expression = resolveExpression(matcher.group(3), variableResolver);
            double strength = parseStrength(matcher.group(4));
            return new Constraint(Operations.subtract(variable, expression), operator, strength);
        } else {
            throw new RuntimeException("could not parse " +   constraintString);
        }
    }

    private static Relation parseOperator(String operatorString) {
        Relation operator = null;
        if ("EQ".equals(operatorString) || "==".equals(operatorString)) {
            operator = Relation.EQ;
        } else if ("GEQ".equals(operatorString) || ">=".equals(operatorString)) {
            operator = Relation.GEQ;
        } else if ("LEQ".equals(operatorString) || "<=".equals(operatorString)) {
            operator = Relation.LEQ;
        }
        return operator;
    }

    private static double parseStrength(String strengthString) {

        double strength =  Strength.REQUIRED;
        if (strengthString != null) {
            strength = Strength.clip(
                    Double.parseDouble(
                            strengthString.substring(1)
                    )
            );
        }
        return strength;
    }

    public static Expression resolveExpression(String expressionString, VariableResolver variableResolver) throws Exception {

        List<String> postFixExpression = infixToPostfix(tokenizeExpression(expressionString));

        Stack<Expression> expressionStack = new Stack<>();

        for (String expression : postFixExpression) {
            if ("+".equals(expression)) {
                expressionStack.push(Operations.add(expressionStack.pop(), (expressionStack.pop())));
            } else if ("-".equals(expression)) {
                Expression a = expressionStack.pop();
                Expression b = expressionStack.pop();
                expressionStack.push(Operations.subtract(b, a));
            } else if ("/".equals(expression)) {
                Expression denominator = expressionStack.pop();
                Expression numerator = expressionStack.pop();
                expressionStack.push(Operations.divide(numerator, denominator));
            } else if ("*".equals(expression)) {
                expressionStack.push(Operations.multiply(expressionStack.pop(), (expressionStack.pop())));
            } else {
                Expression linearExpression =  variableResolver.resolveConstant(expression);
                if (linearExpression == null) {
                    linearExpression = new Expression(new Term(variableResolver.resolveVariable(expression)));
                }
                expressionStack.push(linearExpression);
            }
        }
        return expressionStack.pop();
    }

    public static List<String> infixToPostfix(List<String> tokenList) {

        Stack<Integer> s = new Stack<>();

        List<String> postFix = new ArrayList<>();
        for (String token : tokenList) {
            char c = token.charAt(0);
            int idx = OPS.indexOf(c);
            if (idx != -1 && token.length() == 1) {
                if (s.isEmpty())
                    s.push(idx);
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            postFix.add(Character.toString(OPS.charAt(s.pop())));
                        else break;
                    }
                    s.push(idx);
                }
            } else if (c == '(') {
                s.push(-2);
            } else if (c == ')') {
                while (s.peek() != -2)
                    postFix.add(Character.toString(OPS.charAt(s.pop())));
                s.pop();
            } else {
                postFix.add(token);
            }
        }
        while (!s.isEmpty())
            postFix.add(Character.toString(OPS.charAt(s.pop())));
        return postFix;
    }

    public static List<String> tokenizeExpression(String expressionString) {
        ArrayList<String> tokenList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        int i;
        for (i = 0; i < expressionString.length(); i++) {
            char c = expressionString.charAt(i);
            switch (c) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')':
                    if (stringBuilder.length() > 0) {
                        tokenList.add(stringBuilder.toString());
                        stringBuilder.setLength(0);
                    }
                    tokenList.add(Character.toString(c));
                    break;
                case ' ':
                    break;
                default:
                    stringBuilder.append(c);
            }

        }
        if (stringBuilder.length() > 0) {
            tokenList.add(stringBuilder.toString());
        }

        return tokenList;
    }

}
