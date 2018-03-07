package ru.spbau.karlina.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LazyFactoryTest {
    /** Simple functional test which uses single thread lazy class in one thread */
    @org.junit.Test
    public void singleThreadTest1() throws Exception {
        assertEquals(new Integer(5), LazyFactory.createSingleThreadLazy(() -> 1 + 4).get());
    }


    /** Simple functional test which uses multi thread lazy class in one thread */
    @org.junit.Test
    public void multiThreadTest1() throws Exception {
        assertEquals(new Integer(5), LazyFactory.createMultiThreadLazy(() -> 1 + 4).get());
    }

    /** Test uses single thread lazy class in one thread
     *  Supplier in test return null */
    @org.junit.Test
    public void singleThreadTest2() throws Exception {
        assertNull(LazyFactory.createSingleThreadLazy(()-> null).get());
    }


    /** Test uses multi thread lazy class in one thread
     *  Supplier in test return null */
    @org.junit.Test
    public void multiThreadTest2() throws Exception {
        assertNull(LazyFactory.createMultiThreadLazy(() -> null).get());
    }

    /** Test uses single thread lazy class in one thread
     *  Supplier in test stores counter of calling get */
    @org.junit.Test
    public void singleThreadTest3() throws Exception {
        int[] counter = new int[1];
        counter[0] = 0;

        Supplier<String> supplier = () -> {
            counter[0]++;
            return "abc" + "def";
        };

        SingleThreadLazy<String> lazy = LazyFactory.createSingleThreadLazy(supplier);

        assertEquals("abcdef", lazy.get());
        assertEquals("abcdef", lazy.get());
        assertEquals("abcdef", lazy.get());
        assertEquals("abcdef", lazy.get());
        assertEquals(1, counter[0]);
    }


    /** Test uses multi thread lazy class in one thread
     *  Supplier in test return null */
    @org.junit.Test
    public void multiThreadTest3() throws Exception {
        Supplier<Integer> supplier = () -> null;
        assertEquals(null, LazyFactory.createMultiThreadLazy(supplier).get());
    }

    /** Test on race condition uses multi threads lazy with a lot of threads */
    @org.junit.Test
    public void multiThreadTest4() throws Exception {
        int count = 100;
        MultiThreadLazy<Integer> lazy = LazyFactory.createMultiThreadLazy(() -> 1 + 41);;
        Thread[] threadArray = new Thread[count];
        final int[] result = new int[count];
        Arrays.fill(result, 0);

        for (int i = 0; i < count; ++i) {
            int finalI = i;
            threadArray[i] = new Thread(() -> result[finalI] = lazy.get());
        }

        for (int i = 0; i < count; ++i) {
            threadArray[i].start();
        }


        for (int i = 0; i < count; ++i) {
            threadArray[i].join();
            assertEquals(42, result[i]);
        }

    }

    /** Test uses multi threads lazy with a lot of threads
     *  Test checks that the evalution call called only once*/
    @org.junit.Test
    public void multiThreadTest5() throws Exception {
        int count = 100;
        int[] counter = new int[1];
        counter[0] = 0;

        Supplier<String> supplier = () -> {
            counter[0]++;
            return "abc" + "def";
        };

        SingleThreadLazy<String> lazy = LazyFactory.createSingleThreadLazy(supplier);
        Thread[] threadArray = new Thread[count];

        final String[] result = new String[count];

        for (int i = 0; i < count; ++i) {
            int finalI = i;
            threadArray[i] = new Thread(() -> result[finalI] = lazy.get());
        }

        for (int i = 0; i < count; ++i) {
            threadArray[i].start();
        }


        for (int i = 0; i < count; ++i) {
            threadArray[i].join();
            assertEquals("abcdef", result[i]);
            assertEquals(1, counter[0]);
        }

    }

    /** Test on race condition uses multi threads lazy with a lot of threads */
    @org.junit.Test
    public void multiThreadTest6() throws Exception {
        int count = 100;
        final int[] result = new int[count];
        Arrays.fill(result, 0);

        MultiThreadLazy<Integer> lazy = LazyFactory.createMultiThreadLazy(() -> result[0] += 1);;
        Thread[] threadArray = new Thread[count];

        for (int i = 0; i < count; ++i) {
            int finalI = i;
            threadArray[i] = new Thread(() -> result[finalI] = lazy.get());
        }

        for (int i = 0; i < count; ++i) {
            threadArray[i].start();
        }


        for (int i = 0; i < count; ++i) {
            threadArray[i].join();
            assertEquals(1, result[i]);
        }

    }
}