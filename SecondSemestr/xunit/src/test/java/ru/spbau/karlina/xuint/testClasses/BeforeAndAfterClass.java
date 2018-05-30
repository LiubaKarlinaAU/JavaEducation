package ru.spbau.karlina.xuint.testClasses;

import ru.spbau.karlina.xuint.annotation.After;
import ru.spbau.karlina.xuint.annotation.Before;
import ru.spbau.karlina.xuint.annotation.Test;

public class BeforeAndAfterClass {
    @Before
    void before() {

    }

    @After
    void after() {

    }

    @Test
    void test(){
        System.out.println("Summer.");
    }
}
