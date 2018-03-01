import java.util.function.Supplier;

/** Representation of Multi thread lazy idea */
public class MultiThreadLazy<T> implements LazyInterface<T> {
    private Supplier<T> supplier;
    private T result;

    /**
     * Constructor saved given supplier
     *
     * @param supplier - which will be saved
     */
    public MultiThreadLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Method that call supplier's get method one time during all calls
     * Return a result of stored supplier get method
     *
     * @return T result of supplier
     */
    public T get() {
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }

        return result;
    }
}
