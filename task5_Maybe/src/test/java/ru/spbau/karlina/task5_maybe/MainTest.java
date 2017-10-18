package ru.spbau.karlina.task5_maybe;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test for correct running Main class method main
 */
public class MainTest {
    @Test
    public void mainTestWithNumbers() throws Exception {
        String fileNames[] = {"first.txt", "second.txt"};
        File fileInput = new File(fileNames[0]);
        fileInput.createNewFile();

        FileWriter writer = new FileWriter(fileInput);
        writer.write("a\nb\n3\nc\nd\n4");
        writer.close();

        File fileOutput = new File(fileNames[1]);
        fileOutput.createNewFile();

        Main.main(fileNames);

        String stringArray[] = new String[6];
        int currentLine = 0;
        Scanner scanner = new Scanner(fileOutput);

        while (scanner.hasNextLine()) {
            stringArray[currentLine++] = scanner.nextLine();
        }

        assertArrayEquals(new String[]{"null", "null", "9", "null", "null", "16"}, stringArray);
        fileInput.delete();
        fileOutput.delete();
    }

    @Test
    public void mainTestWithoutNumbers() throws Exception {
        String fileNames[] = {"first.txt", "second.txt"};
        File fileInput = new File(fileNames[0]);
        fileInput.createNewFile();

        FileWriter writer = new FileWriter(fileInput);
        writer.write("a\nb\nc\nd");
        writer.close();

        File fileOutput = new File(fileNames[1]);
        fileOutput.createNewFile();

        Main.main(fileNames);

        String stringArray[] = new String[4];
        int currentLine = 0;
        Scanner scanner = new Scanner(fileOutput);

        while (scanner.hasNextLine()) {
            stringArray[currentLine++] = scanner.nextLine();
        }

        assertArrayEquals(new String[]{"null", "null", "null", "null"}, stringArray);
        fileInput.delete();
        fileOutput.delete();
    }
}