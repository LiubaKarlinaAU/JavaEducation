package ru.spbau.karlina.task5_maybe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class Main with one public method
 */

public class Main {
    /**
     * Receive two filenames (first and second)
     * Read line from first file:
     * if line is integer function write square of integer to second file
     * otherwise function write "null" to second file
     * All strings end on '\n'
     */
    public static void main(String args[]) {
        Scanner scanner;
        FileWriter fileWriter;

        try {
            scanner = new Scanner(new File(args[0]));
            fileWriter = new FileWriter(new File(args[1]));

            while (scanner.hasNext()) {
                Maybe<Integer> maybe;
                if (scanner.hasNextInt()) {
                    maybe = Maybe.just(scanner.nextInt());
                } else {
                    scanner.next();
                    maybe = Maybe.nothing();
                }

                if (maybe.isPresent()) {
                    fileWriter.write(maybe.map(x -> x * x).get().toString() + '\n');
                } else {
                    fileWriter.write("null\n");
                }
            }
            fileWriter.flush();
        } catch (ValueNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}