package ru.spbau.karlina.xuint.recording;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

/**
 * Class for make records about test passing.
 */
public class Statistic {
    private PrintStream output;

    /**
     * Set output stream - where to write statistic.
     *
     * @param stream - to be set.
     */
    public Statistic(@NotNull PrintStream stream) {
        output = stream;
        output.println("Beginning of statistic: ");
    }

    /**
     * Make record about one test passing.
     *
     * @param testName - name of the test.
     * @param result   - result of test passing.
     * @param message  - additional information about passing.
     */
    public void addRecord(@NotNull String testName, long time, @NotNull TestResult result, @NotNull String message) {
        output.println("Test " + testName + " has finished after " + time + "mls.");
        output.println("Result is : " + result + message);
    }

    /**
     * Write given string.
     *
     * @param note - string to write.
     */
    public void addNote(@NotNull String note) {
        output.println(note);
    }
}
