package ru.spbau.karlina.task6;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    /** Test notNull predicate apply to make correct testing hierarchy */
    @Test
    public void applyTest1() throws Exception {
        Predicate<Integer> notNull = integer -> integer != 0;

        assertEquals(true, notNull.apply(new Integer(3)));
    }

    /** Test isNull predicate apply to make correct testing hierarchy */
    @Test
    public void applyTest2() throws Exception {
        Predicate<Integer> isNull = integer -> integer == 0;

        assertEquals(false, isNull.apply(new Integer(3)));
    }

    /** Test and method on one function that was tested before */
    @Test
    public void andTest1() throws Exception {
        Predicate<Integer> notNull = integer -> integer != 0;

        Predicate<Integer> notNullDouble = notNull.and(notNull);

        assertEquals(true, notNullDouble.apply(4));
    }

    /** Test and method on two functions that was tested before */
    @Test
    public void andTest2() throws Exception {
        Predicate<Integer> notNull = integer -> integer != 0;
        Predicate<Integer> isNull = integer -> integer == 0;

        Predicate<Integer> falseComposition = notNull.and(isNull);

        assertEquals(false, falseComposition.apply(4));
    }

    /** Test and method on function that was tested before and always false*/
    @Test
    public void andTest3() throws Exception {
        Predicate<Integer> notNull = integer -> integer != 0;

        Predicate<Integer> falseComposition = notNull.and(notNull.ALWAYS_FALSE());

        assertEquals(false, falseComposition.apply(4));
    }

    /** Test or method on one function that was tested before */
    @Test
    public void orTest1() throws Exception {
        Predicate<Integer> isNull = integer -> integer == 0;

        Predicate<Integer> isNullDouble = isNull.or(isNull);

        assertEquals(false, isNullDouble.apply(4));
    }

    /** Test or method on two functions that was tested before */
    @Test
    public void orTest2() throws Exception {
        Predicate<Integer> notNull = integer -> integer != 0;
        Predicate<Integer> isNull = integer -> integer == 0;

        Predicate<Integer> trueComposition = notNull.or(isNull);

        assertEquals(true, trueComposition.apply(4));
    }

    /** Test or method on function that was tested before and always true*/
    @Test
    public void orTest3() throws Exception {
        Predicate<Integer> isNull = integer -> integer == 0;

        Predicate<Integer> trueComposition = isNull.or(isNull.ALWAYS_TRUE());

        assertEquals(true, trueComposition.apply(4));
    }

    /** Test not method on simple predicate */
    @Test
    public void notTest1() throws Exception {
        Predicate<String> predicate = string -> string.isEmpty();
        assertEquals(true, predicate.not().apply("str"));
    }

    /** Test not method on another simple predicate */
    @Test
    public void notTest2() throws Exception {
        Predicate<String> predicate = string -> !string.isEmpty();
        assertEquals(false, predicate.not().apply("str"));
    }

    /** Test not method on always false */
    @Test
    public void notTest3() throws Exception {
        Predicate<String> predicate = string -> string.isEmpty();
        assertEquals(true, predicate.ALWAYS_FALSE().not().apply("str"));
    }

    /** Test not method on always true */
    @Test
    public void notTest4() throws Exception {
        Predicate<String> predicate = string -> string.isEmpty();
        assertEquals(false, predicate.ALWAYS_TRUE().not().apply("str"));
    }

}