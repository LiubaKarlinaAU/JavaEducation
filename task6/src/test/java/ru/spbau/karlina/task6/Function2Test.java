package ru.spbau.karlina.task6;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {
    /**
     * Test apply method on simple function
     */
    @org.junit.Test
    public void applyTest1() throws Exception {
        Function2<String, String, Integer> function1 = (s1, s2) -> s1.length() + s2.length();

        assertEquals(new Integer(6), function1.apply("ne", "stle"));
    }

    /**
     * Test apply method on another simple function
     */
    @org.junit.Test
    public void applyTest2() throws Exception {
        Function2<Integer, Integer, Integer> function1 = (integer1, integer2) -> new Integer(integer1 * 3 + integer2);

        assertEquals(new Integer(6), function1.apply(new Integer(1), new Integer(3)));
    }

    /**
     * Test compose method on two simple functions that was testing before
     */
    @org.junit.Test
    public void composeTest1() throws Exception {
        Function2<String, String, Integer> function1 = (s1, s2) -> s1.length() + s2.length();
        Function1<Integer, Integer> function2 = integer -> new Integer(integer * 3);

        Function2<String, String, Integer> function3 = function1.compose(function2);
        assertEquals(new Integer(18), function3.apply("dog", "cat"));
    }

    /**
     * Test bind1 method on function ussing apply that was tested before
     */
    @Test
    public void bind1Test() throws Exception {
        Function2<String, String, Integer> function1 = (s1, s2) -> s1.length() + 2 * s2.length();
        Function1<String, Integer> function2 = function1.bind1("olya");

        assertEquals(new Integer(14), function2.apply("liuba"));
    }

    /**
     * Test bind2 method on function ussing apply that was tested before
     */
    @Test
    public void bind2Test() throws Exception {
        Function2<String, String, Integer> function1 = (s1, s2) -> s1.length() + 2 * s2.length();
        Function1<String, Integer> function2 = function1.bind2("olya");

        assertEquals(new Integer(13), function2.apply("liuba"));
    }

    /**
     * Test bind2 method on function ussing apply that was tested before
     */
    @Test
    public void curry() throws Exception {
        Function2<String, String, Integer> function1 = (s1, s2) -> s1.length() + 3 * s2.length();
        Function1<String, Function1<String, Integer>> function2 = function1.curry();

        assertEquals(new Integer(17), function2.apply("liuba").apply("olya"));
    }

}