package ru.spbau.karlina.task6;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollectionsTest {
    /**
     * Tests map on empty list
     */
    @Test
    public void mapTest1() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> resultList = new ArrayList<>();

        assertEquals(resultList, Collections.map(arg -> arg * 2, list));
    }

    /**
     * Test map on not empty list
     */
    @Test
    public void mapTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ArrayList<Integer> resultList = new ArrayList<>();
        resultList.add(2);
        resultList.add(4);
        resultList.add(6);

        assertEquals(resultList, Collections.map(arg -> arg * 2, list));
    }

    /**
     * Tests filter on empty list
     */
    @Test
    public void filterTest1() throws Exception {
        assertEquals(new ArrayList<Integer>(), Collections.filter(Predicate.ALWAYS_FALSE(), new ArrayList<Integer>()));
    }

    /**
     * Tests filter on filled list with always false predicate
     */
    @Test
    public void filterTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(new ArrayList<Integer>(), Collections.filter(Predicate.ALWAYS_FALSE(), list));
    }

    /**
     * Tests filter on filled list with always true predicate
     */
    @Test
    public void filterTest3() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(list, Collections.filter(Predicate.ALWAYS_TRUE(), list));
    }

    /**
     * Tests filter on filled list with inconstant predicate
     */
    @Test
    public void filterTest4() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ArrayList<Integer> resultList = new ArrayList<>();
        resultList.add(1);
        resultList.add(3);

        assertEquals(resultList, Collections.filter(arg -> arg % 2 == 1, list));
    }

    /**
     * Tests takeWhile method on empty list
     */
    @Test
    public void takeWhileTest1() throws Exception {
        assertEquals(new ArrayList<Integer>(), Collections.takeWhile(Predicate.ALWAYS_FALSE(), new ArrayList<Integer>()));
    }

    /**
     * Tests takeWhile method on filled list with always false predicate
     */
    @Test
    public void takeWhileTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(new ArrayList<Integer>(), Collections.takeWhile(Predicate.ALWAYS_FALSE(), list));
    }

    /**
     * Tests takeWhile method on filled list with always true predicate
     */
    @Test
    public void takeWhileTest3() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(list, Collections.takeWhile(Predicate.ALWAYS_TRUE(), list));
    }

    /**
     * Tests filter on filled list with inconstant predicate
     */
    @Test
    public void takeWhileTest4() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ArrayList<Integer> resultList = new ArrayList<>();
        resultList.add(1);

        assertEquals(resultList, Collections.takeWhile(arg -> arg % 2 == 1, list));
    }

    /**
     * Tests takeUnless method on empty list
     */
    @Test
    public void takeUnlessTest1() throws Exception {
        assertEquals(new ArrayList<Integer>(), Collections.takeUnless(Predicate.ALWAYS_FALSE(), new ArrayList<Integer>()));
    }

    /**
     * Tests takeUnless method on filled list with always true predicate
     */
    @Test
    public void takeUnlessTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(new ArrayList<Integer>(), Collections.takeUnless(Predicate.ALWAYS_TRUE(), list));
    }

    /**
     * Tests takeUnless method on filled list with always false predicate
     */
    @Test
    public void takeUnlessTest3() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(list, Collections.takeUnless(Predicate.ALWAYS_FALSE(), list));
    }

    /**
     * Tests filter on filled list with inconstant predicate
     */
    @Test
    public void takeUnlessTest4() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ArrayList<Integer> resultList = new ArrayList<>();
        resultList.add(1);

        assertEquals(resultList, Collections.takeUnless(arg -> arg % 2 == 0, list));
    }

    /**
     * Tests foldr method on empty container
     */
    @Test
    public void foldrTest1() throws Exception {
        assertEquals(new Integer(1), Collections.foldr((t, r) -> t * r, 1, new ArrayList<Integer>()));
    }

    /**
     * Tests foldr method on filled container
     */
    @Test
    public void foldrTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(new Integer(6), Collections.foldr((t, r) -> t * r, 1, list));
    }


    /**
     * Test foldl method on empty container
     */
    @Test
    public void foldlTest1() throws Exception {
        assertEquals(new Integer(1), Collections.foldr((t, r) -> t * r, 1, new ArrayList<Integer>()));
    }

    /**
     * Tests foldr method on filled container
     */
    @Test
    public void foldlTest2() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(new Integer(0), Collections.foldr((t, r) -> t * r, 0, list));
    }
}