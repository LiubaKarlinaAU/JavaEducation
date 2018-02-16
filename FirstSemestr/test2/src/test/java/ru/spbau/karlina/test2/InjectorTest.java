package ru.spbau.karlina.test2;

import org.junit.Test;
import ru.spbau.karlina.test2.exceptions.test.ClassWithOneClassDependency;
import ru.spbau.karlina.test2.exceptions.test.ClassWithOneInterfaceDependency;
import ru.spbau.karlina.test2.exceptions.test.ClassWithoutDependencies;
import ru.spbau.karlina.test2.exceptions.test.InterfaceImpl;

import java.util.Collections;

import static org.junit.Assert.*;

public class InjectorTest {
    private String infix = "ru.spbau.karlina.test2.";

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize(infix + "testClasses.ClassWithoutDependencies", Collections.<String>emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                infix + "testClasses.ClassWithOneClassDependency",
                Collections.singletonList(infix + "testClasses.ClassWithoutDependencies")
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                infix + "testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList(infix + "testClasses.InterfaceImpl")
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }
}