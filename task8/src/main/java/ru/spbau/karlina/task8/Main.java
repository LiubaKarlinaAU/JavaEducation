package ru.spbau.karlina.task8;

import org.jetbrains.annotations.NotNull;

/**
 * Main class stored one method
 */
public class Main {
    /**
     * Calculates given in command line expression and writes expression result on screen.
     * Calculating uses reverse polish notation in Calculator class
     *
     * @param args should contain one string expression
     */
    public static void main(@NotNull String[] args) {
        if (args.length == 0) {
            System.out.println("Expected expression to be calculated.\n");
            return;
        }

        Calculator calculator = new Calculator(new Stack<Character>(), new Stack<Double>());
        String expression = args[0];

        String notation = calculator.makeReversePolishNotation(expression);
        double result = calculator.calculateNotation(notation);

        System.out.println("Result is " + result);
    }

}
