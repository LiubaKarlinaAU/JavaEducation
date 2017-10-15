package ru.spbau.karlina.task4;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;


public class UnzipArchivatorTest {
    private boolean isEqual(String firstFileName, String secondFileName) throws FileNotFoundException, IOException {
        FileReader file1 = new FileReader(firstFileName);
        FileReader file2 = new FileReader(secondFileName);
        int c1, c2;

        while ((c1 = file1.read()) != -1 && (c2 = file2.read()) != -1) {
            if (c1 != c2) {
                file1.close();
                file2.close();
                return false;
            }
        }

        if (file1.ready() != file2.ready()) {
            file1.close();
            file2.close();
            return false;
        }

        file1.close();
        file2.close();

        return true;
    }

    /** Test compare two files: unzip with archivator and original one */
    @Test
    public void unzipTest1() throws Exception {
        new UnzipArchivator().unzip("/home/liuba/Second_Year/JavaEducation/task4/for_testing/",
                ".*3");
        assertEquals(true, isEqual("/home/liuba/Second_Year/JavaEducation/task4/for_testing/out3",
                "/home/liuba/Second_Year/JavaEducation/task4/correct/out3"));
    }

    /** Test compare two files: unzip with archivator and original one */
    @Test
    public void unzipTest2() throws Exception {
        new UnzipArchivator().unzip("/home/liuba/Second_Year/JavaEducation/task4/for_testing/",
                ".*10");
        assertEquals(true, isEqual("/home/liuba/Second_Year/JavaEducation/task4/for_testing/out10",
                "/home/liuba/Second_Year/JavaEducation/task4/correct/out10"));
    }

}