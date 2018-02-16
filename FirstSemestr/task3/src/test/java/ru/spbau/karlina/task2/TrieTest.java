package ru.spbau.karlina.task2;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void addOneElement() throws Exception {
        assertEquals(true, new Trie().add("f"));
    }

    /**
     * Add some elements with equal prefix
     */
    @Test
    public void addTest1() throws Exception {
        Trie trie = new Trie();
        trie.add("s");
        trie.add("str");
        trie.add("string length");
        trie.remove("sa");
        assertEquals(false, trie.contains("sa"));
    }

    /**
     * Testing size on empty trie
     */
    @Test
    public void sizeTest1() throws Exception {
        assertEquals(0, new Trie().size());
    }

    /**
     * Testing after adding one element
     */
    @Test
    public void sizeTest2() throws Exception {
        Trie trie = new Trie();
        trie.add("s");
        assertEquals(1, trie.size());
    }

    /**
     * Testing after adding two different  elements
     */
    @Test
    public void sizeTest3() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("tr");
        assertEquals(2, trie.size());
    }

    /**
     * Testing after adding two equal elements
     */
    @Test
    public void sizeTest4() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sa");
        assertEquals(1, trie.size());
    }

    /**
     * Testing after adding two element with equal prefix
     */
    @Test
    public void sizeTest5() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        assertEquals(2, trie.size());
    }

    /**
     * Testing on empty trie
     */
    @Test
    public void containsTest1() throws Exception {
        assertEquals(false, new Trie().contains("s"));
    }

    /**
     * Testing after adding one element
     */
    @Test
    public void containsTest2() throws Exception {
        Trie trie = new Trie();
        trie.add("s");
        assertEquals(true, trie.contains("s"));
    }

    /**
     * Testing after adding two elements with equal prefix
     */
    @Test
    public void containsTest3() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        assertEquals(true, trie.contains("sa"));
    }

    /**
     * Testing in empty trie
     */
    @Test
    public void removeTest1() throws Exception {
        assertEquals(false, new Trie().remove("sa"));
    }

    /**
     * Testing after adding one element
     */
    @Test
    public void removeTest2() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        assertEquals(true, trie.remove("sa"));
    }

    /**
     * Remove element after adding two elements with Equal Prefix
     */
    @Test
    public void removeTest3() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        assertEquals(true, trie.remove("sa"));
    }

    /**
     * Testing after added and removed two elements
     */
    @Test
    public void removeTest4() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        trie.remove("sa");
        trie.remove("sr");
        assertEquals(false, trie.remove("sr"));
    }

    @Test
    public void sizeAfterRemove() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.remove("sa");
        assertEquals(0, trie.size());
    }

    @Test
    public void addAfterRemove() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        assertEquals(true, trie.remove("sa"));
    }

    @Test
    public void containAfterRemove() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        trie.remove("sa");
        assertEquals(false, trie.contains("sa"));
    }

    /**
     * Testing serialize on empty Trie
     */
    @Test
    public void serializeTest1() throws Exception {
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        new Trie().serialize(fos);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write(0);
        expected.write(0);
        expected.write(0);
        expected.close();

        assertArrayEquals(expected.toByteArray(), fos.toByteArray());
    }

    /**
     * Testing serialize on not empty Trie
     */
    @Test
    public void serializeTest2() throws Exception {
        Trie trie = new Trie();
        trie.add("cats");
        trie.add("car");

        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        trie.serialize(fos);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write(0);
        expected.write(0);
        expected.write(1);

        expected.write('c');

        expected.write(1);
        expected.write(0);
        expected.write(1);

        expected.write('a');

        expected.write(2);
        expected.write(0);
        expected.write(2);

        expected.write('t');

        expected.write(3);
        expected.write(0);
        expected.write(1);

        expected.write('s');

        expected.write(4);
        expected.write(1);
        expected.write(0);

        expected.write('r');

        expected.write(3);
        expected.write(1);
        expected.write(0);

        expected.close();

        assertArrayEquals(expected.toByteArray(), fos.toByteArray());
    }

    /**
     * Testing deserialize on empty Trie
     */
    @Test
    public void deserializeTest1() throws Exception {
        byte[] serializeTrie = {0, 0, 0};
        ByteArrayInputStream in = new ByteArrayInputStream(serializeTrie);
        Trie trie = new Trie();
        trie.deserialize(in);

        assertEquals(0, trie.size());
    }

    /**
     * Testing deserialize on not empty Trie
     */
    @Test
    public void deserializeTest2() throws Exception {
        byte[] serializeTrie = {0, 0, 1, 'c',
                1, 0, 1, 'a',
                2, 0, 2, 't',
                3, 0, 1, 's',
                4, 1, 0, 'r',
                3, 1, 0};
        ByteArrayInputStream in = new ByteArrayInputStream(serializeTrie);
        Trie trie = new Trie();
        trie.deserialize(in);

        assertEquals(true, trie.contains("cats"));
        assertEquals(false, trie.contains("ca"));
        assertEquals(true, trie.contains("car"));
        assertEquals(2, trie.size());
    }

    /**
     * Testing on empty Trie
     */
    @Test
    public void serializeDeserializeTest1() throws Exception {
        Trie trie = new Trie();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        trie.serialize(fos);
        ByteArrayInputStream fis = new ByteArrayInputStream(fos.toByteArray());
        trie.deserialize(fis);
        assertEquals(0, trie.size());
    }

    /**
     * Testing on one element Trie
     */
    @Test
    public void serializeDeserializeTest2() throws Exception {
        Trie firstTrie = new Trie();
        Trie secondTrie = new Trie();
        firstTrie.add("s");
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        firstTrie.serialize(fos);
        ByteArrayInputStream fis = new ByteArrayInputStream(fos.toByteArray());
        secondTrie.deserialize(fis);
        assertEquals(true, secondTrie.contains("s"));
    }

    /**
     * Testing on Trie with some elements
     */
    @Test
    public void serializeDeserializeTest3() throws Exception {
        Trie firstTrie = new Trie();
        Trie secondTrie = new Trie();
        firstTrie.add("s");
        firstTrie.add("str");
        firstTrie.add("string");
        firstTrie.add("ty");
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        firstTrie.serialize(fos);
        ByteArrayInputStream fis = new ByteArrayInputStream(fos.toByteArray());
        secondTrie.deserialize(fis);
        assertEquals(2, secondTrie.howManyStartsWithPrefix("st"));
    }

    /**
     * Testing existence on trie with some elements
     */
    @Test
    public void serializeDeserializeTest4() throws Exception {
        Trie firstTrie = new Trie();
        Trie secondTrie = new Trie();
        firstTrie.add("s");
        firstTrie.add("str");
        firstTrie.add("string");
        firstTrie.add("ty");
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        firstTrie.serialize(fos);
        ByteArrayInputStream fis = new ByteArrayInputStream(fos.toByteArray());
        secondTrie.deserialize(fis);
        assertEquals(true, secondTrie.contains("ty"));
    }

    /**
     * Testing in empty trie
     */
    @Test
    public void howManyStartsWithTest1() throws Exception {
        assertEquals(0, new Trie().howManyStartsWithPrefix("asd"));
    }

    /**
     * Testing in one element trie
     */
    @Test
    public void howManyStartsWithTest2() throws Exception {
        Trie trie = new Trie();
        trie.add("s");
        assertEquals(1, trie.howManyStartsWithPrefix("s"));
    }

    /**
     * Testing after adding two different elements
     */
    @Test
    public void howManyStartsWithTest3() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("tr");
        assertEquals(1, trie.howManyStartsWithPrefix("s"));
    }

    /**
     * Testing after adding two different elements with equal prefix
     */
    @Test
    public void howManyStartsWithTest4() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        assertEquals(2, trie.howManyStartsWithPrefix("s"));
    }

    /**
     * Testing on trie without such prefix
     */
    @Test
    public void howManyStartsWithTest5() throws Exception {
        Trie trie = new Trie();
        trie.add("sa");
        trie.add("sr");
        assertEquals(0, trie.howManyStartsWithPrefix("r"));
    }
}