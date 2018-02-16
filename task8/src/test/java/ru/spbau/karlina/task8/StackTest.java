package ru.spbau.karlina.task8;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class StackTest {

    /**
     * Testing on the empty stack
     */
    @Test
    public void emptyTest1() throws Exception {
        assertEquals(true, new Stack<String>().empty());
    }

    /**
     * Testing on the one element stack
     */
    @Test
    public void pushTest1() throws Exception {
        Stack<String> stack = new Stack<String>();
        stack.push("SuperStar");
        assertEquals(false, stack.empty());
    }

    /**
     * Testing peek method on the empty stack
     */
    @Test (expected = EmptyStackException.class)
    public void peekTest1() throws Exception {
        assertEquals("Stranger", new Stack<String>().peek());
    }

    /**
     * Testing peek method on the one element stack
     */
    @Test
    public void peekTest2() throws Exception {
        Stack<String> stack = new Stack<String>();
        String string = "SuperStar";
        stack.push(string);
        assertEquals(string, stack.peek());
    }

    /**
     * Testing peek method on the stack with some elements
     */
    @Test
    public void peekTest3() throws Exception {
        Stack<String> stack = new Stack<String>();
        stack.push("I ");
        stack.push("am ");
        stack.push("super ");
        String string = "Star!";
        stack.push(string);
        assertEquals(string, stack.peek());
    }


    /**
     * Testing pop method on the empty stack
     */
    @Test (expected = EmptyStackException.class)
    public void popTest1() throws Exception {
        assertEquals("Stranger", new Stack<String>().pop());
    }

    /**
     * Testing pop method on the one element stack
     */
    @Test
    public void popTest2() throws Exception {
        Stack<String> stack = new Stack<String>();
        String string = "SuperStar";
        stack.push(string);
        assertEquals(string, stack.pop());
        assertEquals(true, stack.empty());
    }

}