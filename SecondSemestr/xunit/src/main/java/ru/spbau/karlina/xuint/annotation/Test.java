package ru.spbau.karlina.xuint.annotation;

import org.jetbrains.annotations.NotNull;
import ru.spbau.karlina.xuint.exception.XUnitException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods with this annotation can be tested by test system.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    String EMPTY = "";

    /**
     * Used if you would like to ignore this test.
     *
     * @return a reason of ignoring.
     */
    @NotNull String ignore() default EMPTY;

    /**
     * Used if you expect this test should throws an exception.
     *
     * @return a type of expected exception.
     */
    @NotNull Class<? extends Throwable> expected() default XUnitException.class;
}
