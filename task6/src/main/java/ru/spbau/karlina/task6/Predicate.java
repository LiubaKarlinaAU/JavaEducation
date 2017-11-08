package ru.spbau.karlina.task6;

import org.jetbrains.annotations.NotNull;

interface Predicate<T> extends Function1<T,Boolean> {
    /** This method generate predicate that is constant true
     * @return Predicate<T> always true predicate */
    default Predicate<T> ALWAYS_TRUE() {
        return argument -> true;
    }

    /** This method generate predicate that is constant false
     * @return Predicate<T> always false predicate */
    default @NotNull Predicate<T> ALWAYS_FALSE() {
        return argument -> false;
    }

    /**
     * Logical 'and' with given predicate.
     * @param predicate to make logical 'and' with
     * @return logical 'and' of two predicates
     */
    default @NotNull Predicate<T> and(@NotNull Predicate<? super T> predicate) {
        return argument -> apply(argument) && predicate.apply(argument);
    }

    /**
     * Logical 'or' with given predicate.
     * @param predicate to make logical 'or' with
     * @return logical or of two predicates
     */
    default @NotNull Predicate<T> or(@NotNull Predicate<? super T> predicate) {
        return argument -> apply(argument) || predicate.apply(argument);
    }

    /**
     * Logical 'not' of predicate.
     * @return predicate negation
     */
    default @NotNull Predicate<T> not() {
        return argument ->  !apply(argument);
    }
}