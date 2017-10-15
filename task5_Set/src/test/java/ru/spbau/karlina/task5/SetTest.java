package ru.spbau.karlina.task5;

import static org.junit.Assert.*;

public class SetTest {
    @org.junit.Test
    public void sizeInEmptySet() throws Exception {
        Set<Integer> set = new Set<Integer>();
        assertEquals(0, set.size());
    }

    @org.junit.Test
    public void containsInEmptySet() throws Exception {
        Set<Integer> set = new Set<Integer>();
        assertEquals(false, set.contains(4));
    }

    @org.junit.Test
    public void addOneElement() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(3);
        assertEquals(1, set.size());
    }

    @org.junit.Test
    public void addTwoEqualElements() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(3);
        set.add(3);
        assertEquals(1, set.size());
    }

    @org.junit.Test
    public void sizeAfterAddingTwoDifferentElements() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(3);
        set.add(4);
        assertEquals(2, set.size());
    }

    @org.junit.Test
    public void containsAfterAddingTwoDifferentElements() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(3);
        set.add(4);
        assertEquals(true, set.contains(3));
    }

    @org.junit.Test
    public void containsAfterAddingSomeElements() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(3);
        set.add(4);
        set.add(567);
        set.add(4);
        assertEquals(true, set.contains(567));
    }
}