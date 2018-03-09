package ru.spbau.karlina.pool;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Pool of thread which execute task in stored queue
 */
public class ThreadPool {
    private Thread[] threadArray;
    private final LinkedList<Task> queue = new LinkedList<>();
    volatile static Exception exception = new Exception();

    /**
     * Class constructor makes initialization
     *
     * @param count - count of threads
     */
    public ThreadPool(int count) {
        threadArray = new Thread[count];

        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        Task task = null;
                        synchronized (ThreadPool.this) {
                            while (queue.isEmpty()) {
                                wait();
                                task = queue.poll();
                            }

                            synchronized (task) {
                                task.get();
                                task.notifyAll();
                            }

                            synchronized (ThreadPool.this) {
                                notify();
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
            );

            threadArray[i].setDaemon(true);
            threadArray[i].start();
        }
    }

    /**
     * Add new task with given supplier inside to the tak queue
     *
     * @param supplier - to make task
     * @return added task that represents like interface
     */
    public <T> LightFuture<T> addTask(@NotNull Supplier<T> supplier) {
        synchronized (this) {
            Task task = new Task(supplier);
            queue.add(task);
            notify();
            return task;
        }
    }

    /**
     * Interrupts all the threads
     */
    public void shutdown() {
        for (Thread thread : threadArray) {
            thread.interrupt();
        }
    }

    /**
     * Representation of task for thread pool class
     */
    private class Task<T> implements LightFuture<T> {
        volatile private T result;
        volatile private Supplier<T> supplier;
        private LinkedList<Task> dependsTasks = new LinkedList<>();

        /**
         * Class constructor saves supplier
         *
         * @param taskSupplier - to be saved
         */
        public Task(@NotNull Supplier<T> taskSupplier) {
            supplier = taskSupplier;
        }

        /**
         * Checker if task is done
         *
         * @return result of checking
         */
        @Override
        @NotNull
        public synchronized boolean isReady() {
            return supplier == null;
        }

        /**
         * Calculate task's computation only once
         */
        @Override
        public synchronized T get() throws LightExecutionException {
            if (supplier != null) {
                try {
                    result = supplier.get();
                    supplier = null;

                } catch (Exception e) {
                    throw new LightExecutionException(e.getMessage());
                } finally {
                    for (Task task : dependsTasks) {
                        synchronized (ThreadPool.this) {
                            queue.add(task);
                            notify();
                        }
                    }
                }
            }
            return result;
        }

        /**
         * Makes new task to execute in thread pool using result of this task
         * The new one will be executed after executing this one
         *
         * @param function - to make new task
         * @return result in LightFuture representation
         */
        @Override
        @NotNull
        public synchronized <U> LightFuture<U> thenApply(Function<T, U> function) {
            if (supplier == null) {
                return ThreadPool.this.addTask(() -> function.apply(result));
            } else {
                Task<U> task = new Task<>(() -> function.apply(result));
                dependsTasks.add(task);
                return task;
            }
        }

    }

}
