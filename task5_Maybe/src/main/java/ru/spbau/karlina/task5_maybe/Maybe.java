package ru.spbau.karlina.task5_maybe;

import java.util.function.Function;

/**
 * Maybe pattern realization
 */
@SuppressWarnings("WeakerAccess")
public class Maybe<T> {
    /**
     * Set value
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Made Maybe object with null inside
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * Give object or throw MaybeException if object was null
     * @throws MaybeException if storing data is null
     */
    public T get() throws MaybeException {
        if (data == null) {
            throw new MaybeException("Nothing to get in Maybe object.");
        }

        return data;
    }

    /**
     * Give information about object inside
     *
     * @return false if data is null and true otherwise
     */
    public boolean isPresent() {
        return data != null;
    }

    /**
     * Applies a function to data object
     *
     * @return Maybe(null) if null was inside before or Maybe(result of function) otherwise
     */
    public <U> Maybe<U> map(Function<T, U> mapper) {
        if (isPresent()) {
            return new Maybe<>(mapper.apply(data));
        }

        return new Maybe<>(null);
    }

    private T data = null;

    private Maybe(T t) {
        data = t;
    }
}
