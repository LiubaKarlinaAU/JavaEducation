package ru.spbau.karlina.xuint.testClasses;

import ru.spbau.karlina.xuint.annotation.Test;

public class TestWithIgnore {
    @Test(ignore = "not ready")
    void testIgnored(){
        System.out.println("Shouldn't be there.");
    }
}
