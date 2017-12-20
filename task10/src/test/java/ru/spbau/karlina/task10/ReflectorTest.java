package ru.spbau.karlina.task10;

import org.junit.Test;

import java.io.IOException;

public class ReflectorTest {
    @Test
    public void test() throws IOException, NoSuchMethodException {
        Reflector ref = new Reflector();
        ref.printStructure(Integer.class);
    }
}