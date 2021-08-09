package uk.org.websolution.trader_org_tool.calculator;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Calculator {
    List<String> expression;
    private DecimalFormat decimalFormat = new DecimalFormat("#.###");

    public String calc() {
        for (String ss : expression) {
            if (ss.equals("*") || ss.equals("/")) {
                for (int i = 0; i < expression.size(); i++) {
                    Double number;
                    if (expression.get(i).equals("*")) {
                        number = mult(Double.parseDouble(expression.get(i - 1)), Double.parseDouble(expression.get(i + 1)));
                        expression.set(i, number.toString());
                        expression.remove(i + 1);
                        expression.remove(i - 1);
                    } else if (expression.get(i).equals("/")) {
                        number = div(Double.parseDouble(expression.get(i - 1)), Double.parseDouble(expression.get(i + 1)));
                        expression.set(i, number.toString());
                        expression.remove(i + 1);
                        expression.remove(i - 1);
                    }
                }
            }
        }
        for (String ss : expression) {
            if (ss.equals("+") || ss.equals("-")) {
                for (int i = 0; i < expression.size(); i++) {
                    Double number;
                    if (expression.get(i).equals("+")) {
                        number = plus(Double.parseDouble(expression.get(i - 1)), Double.parseDouble(expression.get(i + 1)));
                        expression.set(i, number.toString());
                        expression.remove(i + 1);
                        expression.remove(i - 1);
                    } else if (expression.get(i).equals("-")) {
                        number = minus(Double.parseDouble(expression.get(i - 1)), Double.parseDouble(expression.get(i + 1)));
                        expression.set(i, number.toString());
                        expression.remove(i + 1);
                        expression.remove(i - 1);
                    }
                }
            }
        }

        double x = Double.parseDouble(expression.get(0));
        if (Double.isInfinite(x)) throw new ArithmeticException();

        return decimalFormat.format(x);
    }

    double plus(double a, double b) {
        return a + b;
    }

    double minus(double a, double b) {
        return a - b;
    }

    double mult(double a, double b) {
        return a * b;
    }

    double div(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divided by zero!");
        return a / b;
    }

    public String equally(String strExpression) {
        expression = new CopyOnWriteArrayList<>(Arrays.asList(strExpression.split(
                "((?<=[+-])|(?=[+-]))" +
                        "|((?<=\\s[+-]\\s))" +
                        "|((?<=[*/])|(?=[*/]))" +
                        "|((?<=\\s[*/]\\s))")));

        return calc();
    }

    public boolean checkSymbol(String expression) {
        if (expression.length() == 0) return false;
        char lastSymbol = expression.charAt(expression.length() - 1);
        return lastSymbol != '+' && lastSymbol != '-' && lastSymbol != '*' && lastSymbol != '/' && lastSymbol != '.';
    }


    public String clearSymbol(String expression) {
        if (expression.length() > 0) {
            return expression.substring(0, expression.length() - 1);
        } else return "";
    }

    public String clearAll() {
        return "";
    }
}
