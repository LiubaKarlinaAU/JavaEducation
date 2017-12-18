package ru.spbau.karlina.test2.exceptions;

public class InjectionCycleException extends Exception {
    public InjectionCycleException(String msg) {
        super(msg);
    }
}
