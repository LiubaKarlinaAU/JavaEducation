import java.util.function.Supplier;

public class LazyFactory {
    public static <T> MultiThreadLazy<T> createMultiThreadLazy(Supplier<T> supplier) {
       return new MultiThreadLazy<>(supplier);
    }

    public static <T> SingleThreadLazy<T> createSingleThreadLazy(Supplier<T> supplier) {
        return new SingleThreadLazy<>(supplier);
    }
}
