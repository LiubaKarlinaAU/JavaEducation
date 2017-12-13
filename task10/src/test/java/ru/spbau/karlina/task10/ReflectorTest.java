package ru.spbau.karlina.task10;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ReflectorTest {
    @Test
    public void test() throws FileNotFoundException {
        Reflector ref = new Reflector();
        ref.printStructure(Integer.class);
    }
}