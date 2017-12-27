package ru.spbau.karlina.task10;

import org.junit.Test;
import ru.spbau.karlina.task10.resources.testClasses.*;

import java.io.*;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

public class ReflectorTest {
    private final String generatedClassPath = "src/test/java/ru/spbau/karlina/task10/generatedClasses/";
    private final String packageName = "ru.spbau.karlina.task10.generatedClasses";
    private final String classPath = "src/test/java/ru/spbau/karlina/task10/resources/testClasses/";

    /**
     * Test on empty class
     */
    @Test
    public void test1PrintStructure() throws IOException, NoSuchMethodException {
        Reflector.printStructure(Example.class, generatedClassPath, packageName);
        assertEquals(true, isEqualExceptPackage(generatedClassPath + "/Example.java",
                classPath + "/Example.java"));
    }

    /**
     * Test on class with two constructors
     */
    @Test
    public void test2PrintStructure() throws IOException, NoSuchMethodException {
        Reflector.printStructure(ClassWithTwoConstructors.class, generatedClassPath, packageName);
        assertEquals(true, isEqualExceptPackage(generatedClassPath + "ClassWithTwoConstructors.java",
                classPath + "/ClassWithTwoConstructors.java"));
    }

    /**
     * Test on generic class with two constructors
     */
    @Test
    public void test3PrintStructure() throws IOException, NoSuchMethodException {
        Reflector.printStructure(GenericClass.class, generatedClassPath, packageName);
        assertEquals(true, isEqualExceptPackage(generatedClassPath + "GenericClass.java",
                classPath + "GenericClass.java"));
    }

    /**
     * Test on generic class contains method with not void return value
     */
    @Test
    public void test4PrintStructure() throws IOException, NoSuchMethodException {
        Reflector.printStructure(ClassAWithMethod.class, generatedClassPath, packageName);
        assertEquals(true, isEqualExceptPackage(generatedClassPath + "ClassAWithMethod.java",
                classPath + "ClassAWithMethod.java"));
    }


    /**
     * Test on two equal classes
     */
    @Test
    public void test1DiffClasses() throws IOException, NoSuchMethodException {
        assertEquals(false, Reflector.diffClasses(Integer.class, Integer.class, System.out));
    }

    /**
     * Test on two different classes
     */
    @Test
    public void test2DiffClasses() throws IOException, NoSuchMethodException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertEquals(true, Reflector.diffClasses(Integer.class, Long.class, System.out));
        System.setOut(null);
    }

    /**
     * Test on classes with one different method
     */
    @Test
    public void test3DiffClasses() throws IOException, NoSuchMethodException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertEquals(true, Reflector.diffClasses(ClassA.class, ClassAWithMethod.class, System.out));
        assertEquals("public int getJ()", outContent.toString().trim());
        System.setOut(null);
    }

    /**
     * Print class to file and load it and try to find difference between them
     */
    @Test
    public void test4DiffClassesWithPrintClass() throws IOException, NoSuchMethodException, ClassNotFoundException {
        Reflector.printStructure(ClassA.class, generatedClassPath, packageName);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        classLoader.loadClass(packageName + ".ClassA");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertEquals(false, Reflector
                .diffClasses(ru.spbau.karlina.task10.generatedClasses.ClassA.class,
                        ru.spbau.karlina.task10.resources.testClasses.ClassA.class, System.out));
        assertEquals("", outContent.toString().trim());

        System.setOut(null);

    }

    private boolean isEqualExceptPackage(String firstFileName, String secondFileName) throws FileNotFoundException, IOException {
        Scanner first = new Scanner(new File(firstFileName));
        Scanner second = new Scanner(new File(secondFileName));

        if (!first.hasNext() || !second.hasNext()) {
            return false;
        }

        first.nextLine();
        second.nextLine();

        String fromFirst = "", fromSecond = "";
        while (first.hasNext() && second.hasNext()) {
            while (first.hasNext()) {
                fromFirst = first.nextLine().replace(" ", "");
                if (fromFirst.length() != 0) {
                    break;
                }
            }

            while (second.hasNext()) {
                fromSecond = second.nextLine().replace(" ", "");
                if (fromSecond.length() != 0) {
                    break;
                }
            }
            if (fromFirst.length() != 0) {
                if (fromSecond.length() != 0) {
                    if (!fromFirst.equals(fromSecond)) {
                        return false;
                    }
                } else if (fromSecond.length() != 0) {
                    return false;
                }
            }
        }

        if (first.hasNext() != second.hasNext()) {
            return false;
        }

        return true;
    }
}