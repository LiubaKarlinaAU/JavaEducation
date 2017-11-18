package ru.spbau.karlina.task5_maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Maybe pattern realization
 */
public class Maybe<T> {

    private T data = null;
    /**
     * Set value
     */
    public static @NotNull <T> Maybe<T> just(@NotNull T t) {
        return new Maybe<>(t);
    }

    /**
     * Made Maybe object with null inside
     */
    public static @NotNull <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * Give object or throw ValueNotFoundException if object was null
     * @throws ValueNotFoundException if storing data is null
     */
    public @NotNull T get() throws ValueNotFoundException {
        if (data == null) {
            throw new ValueNotFoundException("Nothing to get in Maybe object.");
        }

        return data;
    }

    /**
     * Give information about object inside
     *
     * @return false if data is null and true otherwise
     */
    public @NotNull boolean isPresent() {
        return data != null;
    }

    /**
     * Applies a function to data object
     *
     * @return Maybe(null) if null was inside before or Maybe(result of function) otherwise
     */
    public @NotNull <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (isPresent()) {
            return new Maybe<>(mapper.apply(data));
        }

        return nothing();
    }

    private Maybe(T t) {
        data = t;
    }
}
