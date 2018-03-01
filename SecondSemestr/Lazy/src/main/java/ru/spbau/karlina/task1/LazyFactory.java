package ru.spbau.karlina.task1;

import com.sun.istack.internal.NotNull;

import java.util.function.Supplier;

/**
 * Class contains two method which creates different lazy realisation
 */
class LazyFactory {
    /**
     * Creates exemplar of multi thread Lazy
     *
     * @param supplier - for creating lazy
     * @return lazy object for multi thread case
     */
    @NotNull
    public static <T> MultiThreadLazy<T> createMultiThreadLazy(@NotNull Supplier<T> supplier) {
        return new MultiThreadLazy<>(supplier);
    }

    /**
     * Creates exemplar of single thread lazy object
     *
     * @param supplier - for creating lazy
     * @return lazy object for single thread case
     */
    @NotNull
    public static <T> SingleThreadLazy<T> createSingleThreadLazy(@NotNull Supplier<T> supplier) {
        return new SingleThreadLazy<>(supplier);
    }
}
