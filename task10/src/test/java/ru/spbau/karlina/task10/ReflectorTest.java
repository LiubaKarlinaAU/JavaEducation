package ru.spbau.karlina.task10;

import org.junit.Test;
import ru.spbau.karlina.task10.resources.testClasses.*;
import ru.spbau.karlina.task10.resources.testClasses.Example;

import java.io.*;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

public class ReflectorTest {

    /**
     * Test on empty class
     */
    @Test
    public void test1PrintStructure() throws IOException, NoSuchMethodException {
        new Reflector().printStructure(Example.class);
        // assertEquals(true, isEqualExceptPackage("src/main/java/ru/spbau/karlina/task10/Example.java",
        //        "src/test/java/ru/spbau/karlina/task10/resources/testClasses/Example.java"));
    }

    /**
     * Test on class with two constructors
     */
    @Test
    public void test2PrintStructure() throws IOException, NoSuchMethodException {
        new Reflector().printStructure(ClassWithTwoConstructors.class);
        // assertEquals(true, isEqual());
    }

    /**
     * Test on generic class with two constructors
     */
    @Test
    public void test3PrintStructure() throws IOException, NoSuchMethodException {
        new Reflector().printStructure(GenericClass.class);
        // assertEquals(true, isEqual());
    }

    /**
     * Test on two equal classes
     */
    @Test
    public void test1DiffClasses() throws IOException, NoSuchMethodException {
        assertEquals(false, new Reflector().diffClasses(Integer.class, Integer.class, System.out));
    }

    /**
     * Test on two different classes
     */
    @Test
    public void test2DiffClasses() throws IOException, NoSuchMethodException {
        assertEquals(true, new Reflector().diffClasses(Integer.class, Long.class, System.out));
    }

    /**
     * Test on classes with one different method
     */
    @Test
    public void test3DiffClasses() throws IOException, NoSuchMethodException {
        assertEquals(true, new Reflector().diffClasses(ClassA.class, ClassAWithMethod.class, System.out));
    }

    private boolean isEqualExceptPackage(String firstFileName, String secondFileName) throws FileNotFoundException, IOException {
        Scanner first = new Scanner(new File(firstFileName));
        Scanner second = new Scanner(new File(secondFileName));

        if (!first.hasNext() || !second.hasNext()) {
            return false;
        }

        first.nextLine();
        second.nextLine();

        while (first.hasNext() && second.hasNext()) {
            if (!first.nextLine().toString().equals(second.nextLine().toString())) {
                return false;
            }
        }

        if (first.hasNext() != second.hasNext()) {
            return false;
        }

        return true;
    }
}