package ru.spbau.karlina.pool;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/** Task interface representation */
public interface LightFuture<T> {
    /**
     * Checks if task is ready.
     *
     * @return true if task is done and false otherwise
     */
    boolean isReady();

    /**
     * Makes calculation
     *
     * @return result of calculation
     * @throws LightExecutionException if there is any problem in supplier get
     */
    @NotNull
    T get() throws LightExecutionException;

    /**
     * Creates a new task that use this task result
     * The task will be done only after complete this task
     *
     * @param function - function to create new task
     * @return new task
     */
    @NotNull
    <U> LightFuture<U> thenApply(Function<T, U> function);

    /** Special exception for LightFuture interface */
    class LightExecutionException extends Exception {
        public LightExecutionException(String message) {
            super(message);
        }
    }
}
