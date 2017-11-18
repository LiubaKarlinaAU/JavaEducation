package ru.spbau.karlina.task6;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class contains realisation of some methods with iterable containers
 * Realisation includes templates
 */
public class Collections {
    /**
     * Make arrayList with result of applying function to container's elements
     *
     * @param @NotNull Function1<? super T,? extends R> func - function for applying on elements
     * @param @NotNull Iterable<T> a iterable container
     * @return ArrayList<R> list with result
     */
    public static <R, T> ArrayList<R> map(@NotNull Function1<? super T, ? extends R> func, @NotNull Iterable<T> a) {
        ArrayList<R> resultList = new ArrayList<>();

        for (T value : a) {
            resultList.add(func.apply(value));
        }

        return resultList;
    }

    /**
     * Make arrayList with container's elements that make given predicate true
     *
     * @param @NotNull Predicate<? super T> pred - predicate for applying on elements
     * @param @NotNull Iterable<T> a iterable container
     * @return ArrayList<T> list with result
     */
    public static <T> ArrayList<T> filter(@NotNull Predicate<? super T> pred, @NotNull Iterable<T> a) {
        ArrayList<T> resultList = new ArrayList<>();

        for (Iterator<T> iter = a.iterator(); iter.hasNext(); ) {
            T tmp = iter.next();

            if (pred.apply(tmp)) {
                resultList.add(tmp);
            }
        }

        return resultList;
    }

    /**
     * Make arrayList with container's elements that make given predicate true
     * Stopped at first element that makes given predicate false or at the end of the container
     *
     * @param @NotNull Predicate<? super T> pred - predicate for applying on elements
     * @param @NotNull Iterable<T> a iterable container
     * @return ArrayList<T> list with result
     */
    public static <T> ArrayList<T> takeWhile(@NotNull Predicate<? super T> pred, @NotNull Iterable<T> a) {
        ArrayList<T> resultList = new ArrayList<>();

        for (Iterator<T> iter = a.iterator(); iter.hasNext(); ) {
            T tmp = iter.next();

            if (!pred.apply(tmp)) {
                return resultList;
            }

            resultList.add(tmp);
        }

        return resultList;
    }

    /**
     * Make arrayList with container's elements that make given predicate false
     * Stopped at first element that makes given predicate true or at the end of the container
     *
     * @param @NotNull Predicate<? super T> pred - predicate for applying on elements
     * @param @NotNull Iterable<T> a iterable container
     * @return ArrayList<T> list with result
     */
    public static <T> ArrayList<T> takeUnless(@NotNull Predicate<? super T> pred, @NotNull Iterable<T> a) {
        return takeWhile(pred.not(), a);
    }

    /**
     * Represents a convolution using foldr ideology.
     * Convolution start from beggining
     *
     * @param @NotNull Function2<? super R, ? super T, ? extends R> func - function for convolution
     * @param R        mempty - neutral element for convolution
     * @param @NotNull Iterable<T> a iterable container
     * @return R result of convolution
     */
    public static <T, R> R foldr(@NotNull Function2<? super T, ? super R, ? extends R> func,
                                 R mempty, @NotNull Iterable<T> a) {
        return recursiveFoldr(func, mempty, a.iterator());
    }


    /**
     * Represents a convolution using foldl ideology.
     * Convolution start from end
     *
     * @param @NotNull Function2<? super R, ? super T, ? extends R> func - function for convolution
     * @param R        mempty - neutral element for convolution
     * @param @NotNull Iterable<T> a iterable container
     * @return R result of convolution
     */
    public static <T, R> R foldl(@NotNull Function2<? super R, ? super T, ? extends R> func,
                                 R mempty, @NotNull Iterable<T> a) {
        return recursiveFoldl(func, mempty, a.iterator());
    }

    private static <T, R> R recursiveFoldl(@NotNull Function2<? super R, ? super T, ? extends R> func,
                                           R mempty, @NotNull Iterator<T> it) {

        if (it.hasNext()) {
            return recursiveFoldl(func, func.apply(mempty, it.next()), it);
        }

        return mempty;
    }

    private static <T, R> R recursiveFoldr(@NotNull Function2<? super T, ? super R, ? extends R> func,
                                           R mempty, @NotNull Iterator<T> it) {

        if (it.hasNext()) {
            return func.apply(it.next(), recursiveFoldr(func, mempty, it));
        }

        return mempty;
    }
}
