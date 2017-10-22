package ru.spbau.karlina.task1.list;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {
    @Test
    public void getHeadKeyInEmptyList() throws Exception {
      assertEquals(null, new List().getHeadKey());
    }

    @Test
    public void getHeadKeyAfterAddingOneElement() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals("key1", list.getHeadKey());
    }

    @Test
    public void getHeadKeyAfterAddingTwoElements() throws Exception {
        List list = new List();
        list.add("key2", "value2");
        list.add("key1", "value1");
        assertEquals("key1", list.getHeadKey());
    }

    @Test
    public void getHeadValueInEmptyList() throws Exception {
        assertEquals(null, new List().getHeadValue());
    }

    @Test
    public void getHeadValueAfterAddingOneElement() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        assertEquals("value1", list.getHeadValue());
    }

    @Test
    public void getHeadValueAfterAddingTwoElements() throws Exception {
        List list = new List();
        list.add("key2", "value2");
        list.add("key1", "value1");
        assertEquals("value1", list.getHeadValue());
    }

    @Test
    public void popAfterAddingElement() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.pop();
        assertEquals(null, list.getHeadValue());
    }

    @Test
    public void popAfterAddingTwoElements() throws Exception {
        List list = new List();
        list.add("key1", "value1");
        list.add("key2", "value2");
        list.pop();
        assertEquals("value1", list.getHeadValue());
    }

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