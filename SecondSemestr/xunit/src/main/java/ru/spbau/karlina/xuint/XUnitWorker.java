package ru.spbau.karlina.xuint;

import ru.spbau.karlina.xuint.annotation.*;
import ru.spbau.karlina.xuint.exception.TwoSameAnnotationException;
import ru.spbau.karlina.xuint.exception.XUnitException;
import ru.spbau.karlina.xuint.recording.Statistic;
import ru.spbau.karlina.xuint.recording.TestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class XUnitWorker {
    private static Statistic statistic = new Statistic(System.out);
    private static Method before;
    private static Method beforeClass;
    private static Method after;
    private static Method afterClass;
    private static ArrayList<Method> tests = new ArrayList<Method>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Write only one class name!");
            return;
        }

        Class testClass;

        try {
            testClass = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.out.println("Didn't found the class!");
            return;
        }

        runTests(testClass);
    }

    private static void runTests(Class testClass) {
        tests = new ArrayList<>();
        before = null;
        beforeClass = null;
        after = null;
        afterClass = null;

        try {
            addTests(testClass);
        } catch (XUnitException | TwoSameAnnotationException e) {
            System.out.println(e.getMessage());
        }

        try {
            invokeTests(testClass);
        } catch (XUnitException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    private static void invokeTests(Class testClass) throws XUnitException {
        Object instance;
        try {
            instance = testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new XUnitException("Problem with creating instance of " + testClass.getName());
        }

        if (beforeClass != null) {
            try {
                beforeClass.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new XUnitException("Problem with " + beforeClass.getName());
            }
        }

        for (Method test : tests) {
            runTest(instance, test);
        }

        if (afterClass != null) {
            try {
                afterClass.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new XUnitException("Problem with " + beforeClass.getName());
            }
        }
    }

    private static void runTest(Object instance, Method test) {
        long startTime = System.currentTimeMillis();

        try {
            if (before != null) {
                before.invoke(instance);
            }

            test.invoke(instance);

            if (after != null) {
                after.invoke(instance);
            }

        } catch (InvocationTargetException error) {
            long time = System.currentTimeMillis() - startTime;
            Test testAnnotation = test.getAnnotation(Test.class);
            if (!testAnnotation.expected().equals(error.getClass())) {
                statistic.addRecord(test.getName(), time, TestResult.FAILED,
                        "unexpected exception " + error.getCause().getClass().getName());
            } else {
                statistic.addRecord(test.getName(), time, TestResult.PASSED,
                        "expected exception " + error.getCause().getClass().getName());
            }
        } catch (IllegalAccessException error) {
            long time = System.currentTimeMillis() - startTime;
            statistic.addRecord(test.getName(), time, TestResult.FAILED,
                    "unexpected exception " + error.getCause().getClass().getName());
        }

        long time = System.currentTimeMillis() - startTime;

        statistic.addRecord(test.getName(), time, TestResult.PASSED, "");
    }

    private static void addTests(Class testClass) throws XUnitException, TwoSameAnnotationException {
        for (Method current : testClass.getDeclaredMethods()) {
            int annotationCount = 0;
            if (current.getAnnotation(Test.class) != null) {
                tests.add(current);
                annotationCount++;
            }

            if (current.getAnnotation(Before.class) != null) {
                if (before != null) {
                    throw new TwoSameAnnotationException("Before");
                }
                before = current;
                annotationCount++;
            }

            if (current.getAnnotation(After.class) != null) {
                if (after != null) {
                    throw new TwoSameAnnotationException("After");
                }
                after = current;
                annotationCount++;
            }

            if (current.getAnnotation(BeforeClass.class) != null) {
                if (beforeClass != null) {
                    throw new TwoSameAnnotationException("BeforeClass");
                }
                beforeClass = current;
                annotationCount++;
            }

            if (current.getAnnotation(AfterClass.class) != null) {
                if (afterClass != null) {
                    throw new TwoSameAnnotationException("AfterClass");
                }
                afterClass = current;
                annotationCount++;
            }

            if (annotationCount > 1) {
                throw new XUnitException("Too many annotations!\n");
            }
        }
    }


}
