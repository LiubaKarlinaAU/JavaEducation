package ru.spbau.karlina.task2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class SpiralArrayTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanOutStream() {
        System.setOut(null);
    }

    @Test
    public void spiralOutputSmallArrayChecking() throws Exception {
        int[][] arr = {{9}};
        SpiralArray array = new SpiralArray(arr);
        array.spiralOutput();
        assertEquals("9", outContent.toString().trim());
    }

    @Test
    public void spiralOutputBigArrayChecking() throws Exception {
        int[][] arr = {{3, 1, 2}, {6, 4, 5}, {9, 7, 8}};
        SpiralArray array = new SpiralArray(arr);
        array.spiralOutput();
        assertEquals("4 3 6 9 7 8 5 2 1", outContent.toString().trim());
    }

    @Test
    public void simpleOutputChecking() throws Exception {
        int[][] arr = {{3, 1, 2}, {6, 4, 5}, {9, 7, 8}};
        SpiralArray array = new SpiralArray(arr);
        array.simpleOutput();
        assertEquals("3 1 2 \n" + "6 4 5 \n" + "9 7 8", outContent.toString().trim());
    }

    @Test
    public void simpleOutputCheckingSmallArray() throws Exception {
        int[][] arr = {{9}};
        SpiralArray array = new SpiralArray(arr);
        array.simpleOutput();
        assertEquals("9", outContent.toString().trim());
    }

    @Test
    public void sortedBigArrayChecking() throws Exception {
        int[][] arr = {{3, 1, 2}, {6, 4, 5}, {9, 7, 8}};
        SpiralArray array = new SpiralArray(arr);
        array.sortedByColum();
        array.simpleOutput();
        assertEquals("1 2 3 \n" + "4 5 6 \n" + "7 8 9", outContent.toString().trim());
    }

    @Test
    public void sortedSmallArrayChecking() throws Exception {
        int[][] arr = {{9}};
        SpiralArray array = new SpiralArray(arr);
        array.sortedByColum();
        array.simpleOutput();
        assertEquals("9", outContent.toString().trim());
    }
}