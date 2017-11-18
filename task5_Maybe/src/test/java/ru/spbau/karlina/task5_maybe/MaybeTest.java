package ru.spbau.karlina.task5_maybe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaybeTest {
    @Test
    public void justAndGetStringTest() throws Exception {
        Maybe<String> maybe = Maybe.just("Abr");
        assertEquals("Abr", maybe.get());
    }

    @Test(expected = ValueNotFoundException.class)
    public void nothingAndGetStringTest() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        maybe.get();
    }

    @Test
    public void nothingAndIsPresentStringTest() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        assertEquals(false, maybe.isPresent());

    }

    @Test
    public void justAndIsPresentStringTest() throws Exception {
        Maybe<String> maybe = Maybe.just("Abr");
        assertEquals(true, maybe.isPresent());
    }

    @Test
    public void justAndGetIntegerTest() throws Exception {
        Maybe<Integer> maybe = Maybe.just(30);
        assertEquals(new Integer(30), maybe.get());
    }

    @Test(expected = ValueNotFoundException.class)
    public void nothingAndGetIntegerTest() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        maybe.get();
    }

    @Test
    public void nothingAndIsPresentIntegerTest() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        assertEquals(false, maybe.isPresent());

    }

    @Test
    public void justAndIsPresentIntegerTest() throws Exception {
        Maybe<Integer> maybe = Maybe.just(30);
        assertEquals(true, maybe.isPresent());
    }

    @Test
    public void mapStringLengthTest() throws Exception {
        Maybe<String> maybe = Maybe.just("string");
        assertEquals(new Integer(6), maybe.map(String::length).get());
    }

    @Test
    public void mapWithNothingTest() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        assertEquals(false, maybe.map(s -> s).isPresent());
    }
}