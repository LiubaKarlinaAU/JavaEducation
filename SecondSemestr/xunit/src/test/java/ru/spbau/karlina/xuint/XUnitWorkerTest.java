package ru.spbau.karlina.xuint;

import org.junit.After;
import org.junit.Before;
import ru.spbau.karlina.xuint.testClasses.BeforeAndAfterClass;
import ru.spbau.karlina.xuint.testClasses.Empty;
import ru.spbau.karlina.xuint.testClasses.SimpleTest;
import ru.spbau.karlina.xuint.testClasses.TestWithIgnore;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class XUnitWorkerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanOutStream() {
        System.setOut(null);
    }

    @org.junit.Test
    public void testEmptyClass() throws Exception {
        XUnitWorker.runTests(Empty.class);
        assertEquals("", outContent.toString().trim());
    }

    @org.junit.Test
    public void testWithIgnore() throws Exception {
        XUnitWorker.runTests(TestWithIgnore.class);
        assertEquals("Test testIgnored ignored. Reason: not ready",
                outContent.toString().trim());
    }

    @org.junit.Test
    public void testSimpleTest() throws Exception {
        XUnitWorker.runTests(SimpleTest.class);
        assertEquals("Test simple has finished after 0 mls.\n" +
                "Result is : PASSED", outContent.toString().trim());
    }

    @org.junit.Test
    public void testBeforeAndAfterTest() throws Exception {
        XUnitWorker.runTests(BeforeAndAfterClass.class);
        assertEquals("Method before finished.\n" +
                "\n" +
                "Summer.\n" +
                "Test test has finished after 0 mls.\n" +
                "Result is : PASSED\n" +
                "\n" +
                "Method after finished.", outContent.toString().trim());
    }
}