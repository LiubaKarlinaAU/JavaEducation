package ru.spbau.karlina.task6;

import com.sun.istack.internal.NotNull;

/** Interface for one argument function */
public interface Function1<T, R> {
    /** Apply function method
     * @param T t - function argument
     * @return R - function result with given argument */
    R apply(@NotNull T t);

    /** Composition of two functions
     * @NotNull Function1<? super R,? exstends U> g - function to made composition
     * @return @NotNull <U> Function1<T, U> - function g(this(x)) */
    default @NotNull <U> Function1<T, U> compose(@NotNull Function1<? super R, ? extends U> g) {
        return argument -> g.apply(apply(argument));
    }
}
