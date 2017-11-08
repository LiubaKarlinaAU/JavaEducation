package ru.spbau.karlina.task6;

import org.jetbrains.annotations.NotNull;

interface Predicate<T> extends Function1<T,Boolean> {
    /**
     * This predicate represents constant true.
     */
    Predicate<Object> ALWAYS_TRUE = t -> true;

    /**
     * This predicate represents constant false.
     */
    Predicate<Object> ALWAYS_FALSE = t -> false;

    /**
     * Logical and with another predicate.
     * @param p predicate to make logical and with
     * @return logical and of two predicates
     */
    default @NotNull Predicate<T> and(@NotNull Predicate<? super T> p) {
        return t ->  apply(t) && p.apply(t);
    }

    /**
     * Logical or with another predicate.
     * @param p predicate to make logical or with
     * @return logical or of two predicates
     */
    default @NotNull Predicate<T> or(@NotNull Predicate<? super T> p) {
        return t ->  apply(t) || p.apply(t);
    }

    /**
     * Logical not of predicate.
     * @return predicate-negation
     */
    default @NotNull Predicate<T> not() {
        return t ->  !apply(t);
    }
}
