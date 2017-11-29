package ru.spbau.karlina.task8;

import org.jetbrains.annotations.NotNull;

import static java.lang.Character.isDigit;

public class Calculator {
    final private Stack<Character> operators;
    final private Stack<Double> digits;

    /**
     * Constructor make stacks initialization
     *
     * @param @NotNull Stack<Character> operators stack for storing operators
     * @param @NotNull Stack<Character> digits stack for storing variables
     */
    public Calculator(@NotNull Stack<Character> operators, @NotNull Stack<Double> digits) {
        this.operators = operators;
        this.digits = digits;
    }

    /**
     * Make reverse polish notation of input expression
     *
     * @param @NotNull String expression expression to be make reverse polish notation
     * @return @NotNull String result notation
     */
    public @NotNull String makeReversePolishNotation(@NotNull String expression) {
        StringBuilder notation = new StringBuilder("");

        expression = '(' + expression + ')';

        for (int i = 0; i < expression.length(); ++i) {
            Character current = expression.charAt(i);

            if (isDigit(current)) {
                notation.append(current);
            } else if (current == '(') {
                operators.push(current);
            } else if (current == ')') {
                char top = operators.pop();
                while (top != '(') {
                    notation.append(top);
                    top = operators.pop();
                }
            } else {
                int priority = getPriority(current);

                while (!operators.empty() && getPriority(operators.peek()) >= priority) {
                    notation.append(operators.pop());
                }

                operators.push(current);
            }
        }

        return notation.toString();
    }

    /**
     * Calculate given reverse polish notation
     *
     * @param @NotNull String notation expression to be calculate
     * @return notation result
     */
    public double calculateNotation(@NotNull String notation) {
        for (int i = 0; i < notation.length(); ++i) {
            char current = notation.charAt(i);

            if (isDigit(current)) {
                digits.push((double) (current - '0'));
            } else {
                double b = digits.pop();
                double a = digits.pop();

                digits.push(applyOperation(current, a, b));
            }
        }
        return digits.pop();
    }

    private int getPriority(char c) {
        switch (c) {
            case '(':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                throw new IllegalArgumentException("Operator" + c + "isn't in scope.");
        }
    }

    private double applyOperation(char c, double a, double b) {
        switch (c) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new IllegalArgumentException("Operator" + c + "isn't in scope.");
        }
    }
}
