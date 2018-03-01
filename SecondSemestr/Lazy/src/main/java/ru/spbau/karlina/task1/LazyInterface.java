package ru.spbau.karlina.task1;

/**
 * An interface for lazy calculations
 */
public interface LazyInterface<T> {
    /**
     * Method returns result of calculation that happen only once
     *
     * @return result of calculation
     */
    T get();
}
