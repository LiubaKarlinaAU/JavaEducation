package ru.spbau.karlina.xuint.exception;

/**
 * Exception for more than one same annotation existing.
 */
public class TwoSameAnnotationException extends Exception {
    public TwoSameAnnotationException(String annotationName) {
        super("Two " + annotationName + " annotation!");
    }
}
