package ru.spbau.karlina.task9.sp;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static ru.spbau.karlina.task9.sp.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        ArrayList<String> fileNames = new ArrayList<>();

        for (int i = 0; i < 5; ++i)
            fileNames.add("src/test/resources/temp" + i);

        String[] expected = {"He'll sit one day, the lights are down", "And when they let you down"};
        assertArrayEquals(expected, findQuotes(fileNames, "down").toArray());

    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, piDividedBy4(), 1e-2);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("first", Arrays.asList("small", "text")); // 9
        map.put("second", Arrays.asList("some", "text", "!!!!")); // 12
        map.put("king", Arrays.asList("big", "big", "big", "text")); // 13

        assertEquals("king", findPrinter(map));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("potato", 3);
        map1.put("tomato", 1);
        map1.put("apple", 4);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("tomato", 0);
        map2.put("apple", 1);

        Map<String, Integer> map3 = new HashMap<>();
        map3.put("orange", 78);
        map3.put("tomato", 3);
        map3.put("apple", 1);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("orange", 78);
        expected.put("tomato", 4);
        expected.put("apple", 6);
        expected.put("potato", 3);

        assertArrayEquals(expected.entrySet().toArray(),
                calculateGlobalOrder(Arrays.asList(map1, map2, map3)).entrySet().toArray());
    }
}