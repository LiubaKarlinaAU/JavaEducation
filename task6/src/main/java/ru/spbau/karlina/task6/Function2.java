package ru.spbau.karlina.task6;

import org.jetbrains.annotations.NotNull;

/**
 * Interface represents a two arguments function
 * Realisation includes templates
 */
public interface Function2<T1, T2, R> {
    /**
     * Apply function method
     *
     * @param T1 t1 - first function argument
     * @param T2 t2 - first function argument
     * @return R - function result with given arguments
     */
    R apply(@NotNull T1 t1, @NotNull T2 t2);

    /**
     * Compose of two functions
     *
     * @param @NotNull Function1<? super R, ? extends U> g - function to made composition
     * @return @NotNull <U> Function2<T1,T2 U> - function g(this(x))
     */
    default @NotNull <U> Function2<T1, T2, U> compose(@NotNull Function1<? super R, ? extends U> g) {
        return (argument1, argument2) -> g.apply(apply(argument1, argument2));
    }

    /**
     * Binds first argument
     *
     * @param T1 argument1 - value for binding
     * @return binded function
     */
    default @NotNull Function1<T2, R> bind1(T1 argument1) {
        return argument2 -> apply(argument1, argument2);
    }

    /**
     * Binds second argument
     *
     * @param T2 argument2 - value for binding
     * @return binded function
     */
    default @NotNull Function1<T1, R> bind2(T2 argument2) {
        return argument1 -> apply(argument1, argument2);
    }

    /**
     * Making currying
     *
     * @return curried function.
     */
    default @NotNull Function1<T1, Function1<T2, R>> curry() {
        return first -> (second -> apply(first, second));
    }
}
