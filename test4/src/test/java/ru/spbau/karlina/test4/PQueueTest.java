package ru.spbau.karlina.test4;

import org.junit.Test;

import static org.junit.Assert.*;

public class PQueueTest {

    @Test
    public void add() throws Exception {
        PQueue<String> queue = new PQueue<>();
        assertEquals(0,queue.size());
    }

    @Test
    public void size() throws Exception {
        PQueue<String> queue = new PQueue<>();
        assertEquals(0,queue.size());
    }

    @Test
    public void peek() throws Exception {
        PQueue<String> queue = new PQueue<>();
        assertEquals(null,queue.peek());
    }

    @Test
    public void clear() throws Exception {
    PQueue<String> queue = new PQueue<>();
    queue.offer("a");
    queue.offer("v");
    queue.offer("c");
    queue.clear();
    assertEquals(0,queue.size());

    }
}