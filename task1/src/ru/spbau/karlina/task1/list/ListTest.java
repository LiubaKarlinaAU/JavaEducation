package ru.spbau.karlina.task1.list;

import static org.junit.Assert.*;

public class ListTest {
    @org.junit.Test
    public void getInEmptyList() throws Exception {
        assertEquals(null, new List().get("key1"));
    }

    @org.junit.Test
    public void addAndGet() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals("value1", list.get("key1"));
    }

    @org.junit.Test
    public void addTwoAndGet() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.add("key2", "value2");
        assertEquals("value1", list.get("key1"));
    }

    @org.junit.Test
    public void addTwoEqualsAndGet() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.add("key1", "value2");
        assertEquals("value2", list.get("key1"));
    }

    @org.junit.Test
    public void getUsingVariablesAsKey() throws Exception {
        String key1 = "Key1";
        String key2 = "Key1";
        List list = new List();
        list.add(key1, "value");
        assertEquals("value", list.get(key2));
    }

    @org.junit.Test
    public void containsInEmptyList() throws Exception {
        assertEquals(false, new List().contains("key1"));
    }

    @org.junit.Test
    public void containsAfterAdding() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals(true, list.contains("key1"));
    }

    @org.junit.Test
    public void containsTwiceTimeAfterAdding() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals(true, list.contains("key1") == list.contains("key1"));
    }

    @org.junit.Test
    public void removeInEmptyList() throws Exception {
        assertEquals(null, new List().remove("key1"));
    }

    @org.junit.Test
    public void removeAfterAdding() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.remove("key1");
        assertEquals(false, list.contains("key1"));
    }

    @org.junit.Test
    public void removeStringChecking() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals("value1", list.remove("key1"));
    }

    @org.junit.Test
    public void removeAfterTwiceAddingStringChecking() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.add("key1", "value2");
        assertEquals("value2", list.remove("key1"));
    }
}