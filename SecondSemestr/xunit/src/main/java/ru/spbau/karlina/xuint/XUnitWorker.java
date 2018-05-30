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

/**
 * Class for running test with annotations.
 */
public class XUnitWorker {
    private Statistic statistic = new Statistic(System.out);
    private Method before;
    private Method beforeClass;
    private Method after;
    private Method afterClass;
    private ArrayList<Method> tests = new ArrayList<>();

    /**
     * Run tests from given class name.
     *
     * @param args - should contain one string - name of class to be load.
     */
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

        XUnitWorker worker = new XUnitWorker();
        worker.runTests(testClass);
    }


    /**
     * Run tests from given class.
     *
     * @param testClass - class with tests to be run.
     */
    public void runTests(Class testClass) {
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

    private void invokeTests(Class testClass) throws XUnitException {
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

    private void runTest(Object instance, Method test) throws XUnitException {
        Test testAnnotation = test.getAnnotation(Test.class);
        if (!testAnnotation.ignore().equals(Test.EMPTY)) {
            statistic.addNote("Test " + test.getName() + " ignored. Reason: " + testAnnotation.ignore());
            return;
        }

        if (before != null) {
            try {
                before.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new XUnitException("Problem with " + before.getName() + " method.");
            }
            statistic.addNote("Method " + before.getName() + " finished.");
        }

        long startTime = System.currentTimeMillis(), time;
        try {
            test.invoke(instance);
        } catch (InvocationTargetException error) {
            time = System.currentTimeMillis() - startTime;
            if (!testAnnotation.expected().equals(error.getClass())) {
                statistic.addRecord(test.getName(), time, TestResult.FAILED,
                        "unexpected exception " + error.getCause().getClass().getName());
            } else {
                statistic.addRecord(test.getName(), time, TestResult.PASSED,
                        "expected exception " + error.getCause().getClass().getName());
            }
        } catch (IllegalAccessException error) {
            time = System.currentTimeMillis() - startTime;
            statistic.addRecord(test.getName(), time, TestResult.FAILED,
                    "unexpected exception " + error.getCause().getClass().getName());
        }

        time = System.currentTimeMillis() - startTime;

        statistic.addRecord(test.getName(), time, TestResult.PASSED, "");

        if (after != null) {
            try {
                after.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new XUnitException("Problem with " + after.getName() + " method.");
            }
            statistic.addNote("Method " + after.getName() + " finished.");

        }
    }

    private void addTests(Class testClass) throws XUnitException, TwoSameAnnotationException {
        for (Method current : testClass.getDeclaredMethods()) {
            current.setAccessible(true);
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