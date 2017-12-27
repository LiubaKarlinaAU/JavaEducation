package ru.spbau.karlina.test4;

import org.junit.Test;

import static org.junit.Assert.*;

public class PListTest {
    /**
     * Empty list
     */
    @Test
    public void test1() throws Exception {
        PList<String> list = new PList<>();
        assertEquals(null, list.peek());
    }

    /**
     * Add element
     */
    @Test
    public void test2Add() throws Exception {
        PList<String> list = new PList<>();
        list.add("str");
        assertEquals("str", list.peek());
    }

    /**
     * Add many elements
     */
    @Test
    public void test3Add() throws Exception {
        PList<String> list = new PList<>();
        list.add("bab");
        list.add("ab");

        assertEquals("bab", list.peek());
    }


}