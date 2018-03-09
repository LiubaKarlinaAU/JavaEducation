package ru.spbau.karlina.pool;

import org.junit.Test;

import java.util.LinkedHashSet;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    @Test
    public void readyWithManyTasksAndManyThreadTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(1);
        LightFuture<Integer> task = pool.addTask(() -> 1 + 3);

        assertFalse(task.isReady());
        assertEquals(new Integer(4), task.get());
        assertTrue(task.isReady());
    }

    @Test
    public void addOneTaskAndSleepTest() throws LightFuture.LightExecutionException, InterruptedException {
        ThreadPool pool = new ThreadPool(1);
        LightFuture<Integer> task = pool.addTask(() -> 1 + 3);

        sleep(1000);
        assertFalse(task.isReady());
    }

    @Test
    public void addTaskWithReturnNullSupplierTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(1);
        LightFuture<Integer> task = pool.addTask(() -> null);

        assertFalse(task.isReady());
        assertNull(task.get());
        assertTrue(task.isReady());
    }

    @Test
    public void readyWithManyTasksAndOneThreadTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(1);

        LightFuture<Integer> task1 = pool.addTask(() -> 1 + 3);
        LightFuture<LinkedHashSet<Object>> task2 = pool.addTask(() -> null);
        LightFuture<String> task3 = pool.addTask(() -> "abs" + "vbm");

        assertFalse(task1.isReady());
        assertEquals(new Integer(4), task1.get());
        assertTrue(task1.isReady());

        assertFalse(task2.isReady());
        assertNull(task2.get());
        assertTrue(task2.isReady());

        assertFalse(task3.isReady());
        assertEquals("absvbm", task3.get());
        assertTrue(task3.isReady());
    }

    @Test
    public void readyWithManyTasksAndManyThreadsTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(5);

        LightFuture<Integer> task1 = pool.addTask(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
            return 1 + 3;
        });
        LightFuture<LinkedHashSet<Object>> task2 = pool.addTask(() -> null);
        LightFuture<String> task3 = pool.addTask(() -> "abs" + "vbm");

        assertFalse(task1.isReady());
        assertEquals(new Integer(4), task1.get());
        assertTrue(task1.isReady());

        assertNull(task2.get());
        assertTrue(task2.isReady());

        assertEquals("absvbm", task3.get());
        assertTrue(task3.isReady());
    }


    @Test
    public void thenApplyWithOneThreadTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(5);

        LightFuture<Integer> task1 = pool.addTask(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
            return 1 + 3;
        });
        LightFuture<Integer> task2 = task1.thenApply((a) -> a * 2);

        assertFalse(task1.isReady());
        assertFalse(task2.isReady());
        assertEquals(new Integer(4), task1.get());
        assertEquals(new Integer(8), task2.get());

        assertTrue(task2.isReady());
        assertTrue(task1.isReady());

    }

    @Test(expected = LightFuture.LightExecutionException.class)
    public void throwExpectedExceptionTest() throws LightFuture.LightExecutionException {
        ThreadPool pool = new ThreadPool(5);

        LightFuture<Integer> task1 = pool.addTask(() -> null);
        LightFuture<Integer> task2 = task1.thenApply((a) -> a * 2);

        assertFalse(task1.isReady());
        assertFalse(task2.isReady());
        assertEquals(null, task1.get());
        task2.get();
    }

    @Test
    public void shutdownTest() throws Exception {
        ThreadPool pool = new ThreadPool(20);

        LightFuture<Integer> task1 = pool.addTask(() -> 1 + 3);
        LightFuture<LinkedHashSet<Object>> task2 = pool.addTask(() -> null);
        LightFuture<String> task3 = pool.addTask(() -> "abs" + "vbm");

        pool.shutdown();

        assertFalse(task1.isReady() || task2.isReady() || task3.isReady());
    }

    @Test
    public void shutdownAndSleepTest() throws Exception {
        ThreadPool pool = new ThreadPool(20);

        LightFuture<Integer> task1 = pool.addTask(() -> 1 + 3);
        LightFuture<LinkedHashSet<Object>> task2 = pool.addTask(() -> null);
        LightFuture<String> task3 = pool.addTask(() -> "abs" + "vbm");

        pool.shutdown();
        Thread.sleep(800);

        assertFalse(task1.isReady() || task2.isReady() || task3.isReady());
    }

}