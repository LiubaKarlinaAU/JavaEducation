package ru.spbau.karlina.task1;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    @Test
    public void sizeInEmptyHashTable() throws Exception {
        assertEquals(0, new HashTable().size());
    }

    @Test
    public void removeInEmptyHashTable() throws Exception {
        assertEquals(null, new HashTable().remove("key1"));
    }

    @Test
    public void getInEmptyHashTable() throws Exception {
        assertEquals(null, new HashTable().get("key1"));
    }

    @Test
    public void containsInEmptyTamle() throws Exception {
        assertEquals(false, new HashTable().contains("key"));
    }

    @Test
    public void putAndCheckSize() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        assertEquals(1, table.size());
    }

    @Test
    public void putTwoAndCheckSize() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        table.put("key2", "value");
        assertEquals(2, table.size());
    }

    @Test
    public void putTwoEqualsAndCheckSize() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        table.put("key1", "value1");
        assertEquals(1, table.size());
    }

    @Test
    public void putAndCheckContain() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        assertEquals(true, table.contains("key1"));
    }

    @Test
    public void putAndCheckStringValue() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        assertEquals("value", table.get("key1"));
    }

    @Test
    public void putTwoEqualsKeyAndCheckStringValue() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value1");
        table.put("key1", "value2");
        assertEquals("value2", table.get("key1"));
    }

    @Test
    public void putAndRemoveSizeChecking() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        table.remove("key1");
        assertEquals(0, table.size());
    }

    @Test
    public void putAndRemoveStringValueChecking() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        assertEquals("value", table.remove("key1"));
    }

    @Test
    public void cleanAfterPutValue() throws Exception {
        HashTable table = new HashTable();
        table.put("key1", "value");
        table.clean();
        assertEquals(false, table.contains("key1"));
    }

    @Test
    public void cleanAfterPutValues() throws Exception {
        HashTable table = new HashTable();

        table.put("key1", "value");
        table.put("key2", "value");
        table.put("key3", "value");
        table.put("key4", "value");
        table.clean();

        assertEquals(false, table.contains("key1"));
    }

    @Test
    public void cleanAfterPutValuesAndRemove() throws Exception {
        HashTable table = new HashTable();

        table.put("key1", "value");
        table.put("key2", "value");
        table.put("key3", "value");
        table.put("key4", "value");
        table.remove("key3");
        table.clean();

        assertEquals(0, table.size());
    }

    @Test
    public void addManyDataAndRebuildChecking() throws Exception {
        HashTable table = new HashTable();

        table.put("key1", "value1");
        table.put("key2", "value2");
        table.put("key3", "value3");
        table.put("key4", "value4");
        table.put("key5", "value5");
        table.put("key6", "value6");
        table.put("key7", "value7");
        table.put("key8", "value8");

        assertEquals(8, table.size());
    }

    @Test
    public void rebuildAndRemove() throws Exception {
        HashTable table = new HashTable();

        table.put("key1", "value1");
        table.put("key2", "value2");
        table.put("key3", "value3");
        table.put("key4", "value4");
        table.put("key5", "value5");
        table.put("key6", "value6");
        table.put("key7", "value7");
        table.put("key8", "value8");

        assertEquals(table.remove("key1"), "value1");
    }

    @Test
    public void rebuildAndClean() throws Exception {
        HashTable table = new HashTable();

        table.put("key1", "value1");
        table.put("key2", "value2");
        table.put("key3", "value3");
        table.put("key4", "value4");
        table.put("key5", "value5");
        table.put("key6", "value6");
        table.put("key7", "value7");
        table.put("key8", "value8");

        table.clean();
        table.put("key1", "value1");
        assertEquals(1, table.size());
    }

    @Test
    public void equalsHashCheckingFirst() throws Exception {
        HashTable table = new HashTable();

        table.put("ke", "value1");
        table.put("ek", "value2");

        assertEquals("value1", table.get("ke"));
    }

    @Test
    public void equalsHashCheckingSecond() throws Exception {
        HashTable table = new HashTable();
        table.put("ke", "value1");
        table.put("ek", "value2");
        assertEquals("value2", table.get("ek"));
    }

    @Test
    public void equalsHashCheckingRemove() throws Exception {
        HashTable table = new HashTable();
        table.put("ke", "value1");
        table.put("ek", "value2");
        table.remove("ke");
        assertEquals("value2", table.get("ek"));
    }
}