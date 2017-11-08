package ru.spbau.karlina.task6;

import static org.junit.Assert.*;

public class Function1Test {
    /**
     * Test apply method on simple function
     */
    @org.junit.Test
    public void applyTest1() throws Exception {
        Function1<String, Integer> function1 = s -> s.length();

        assertEquals(new Integer(6), function1.apply("string"));
    }

    /**
     * Test apply method on another simple function
     */
    @org.junit.Test
    public void applyTest2() throws Exception {
        Function1<Integer, Integer> function1 = integer -> new Integer(integer * 3);

        assertEquals(new Integer(9), function1.apply(new Integer(3)));
    }

    /**
     * Test compose method on one simple functions that was testing before
     */
    @org.junit.Test
    public void composeTest1() throws Exception {
        Function1<Integer, Integer> function1 = integer -> new Integer(integer * 3);

        Function1<Integer, Integer> function2 = function1.compose(function1);
        assertEquals(new Integer(9), function2.apply(new Integer(1)));
    }

    /**
     * Test compose method on two simple functions that was testing before
     */
    @org.junit.Test
    public void composeTest2() throws Exception {
        Function1<String, Integer> function1 = s -> s.length();
        Function1<Integer, Integer> function2 = integer -> new Integer(integer * 3);

        Function1<String, Integer> function3 = function1.compose(function2);
        assertEquals(new Integer(9), function3.apply("dog"));
    }
}