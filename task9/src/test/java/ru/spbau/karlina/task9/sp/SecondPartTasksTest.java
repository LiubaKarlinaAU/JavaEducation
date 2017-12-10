package ru.spbau.karlina.task9.sp;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static ru.spbau.karlina.task9.sp.SecondPartTasks.findQuotes;
import static ru.spbau.karlina.task9.sp.SecondPartTasks.piDividedBy4;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        ArrayList<String> fileNames = new ArrayList<>();

        for (int i = 0; i < 5; ++i)
            fileNames.add("src/test/resources/temp" + i);

        String[] expected = {"He'll sit one day, the lights are down", "And when they let you down"};
        assertArrayEquals( expected, findQuotes(fileNames, "down").toArray());

    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, piDividedBy4(), 1e-2);
    }

    @Test
    public void testFindPrinter() {
        fail();
    }

    @Test
    public void testCalculateGlobalOrder() {
        fail();
    }
}