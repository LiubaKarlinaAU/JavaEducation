package ru.spbau.karlina.task5_maybe;

/** Class for identification exception in Maybe class */
public class ValueNotFoundException extends Exception {
    public ValueNotFoundException(String message) {
        super(message);
    }
}