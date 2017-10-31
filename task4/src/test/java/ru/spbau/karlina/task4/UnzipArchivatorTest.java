package ru.spbau.karlina.task4;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;


public class UnzipArchivatorTest {
    /** Test with nothing to unzip */
    @Test
    public void unzipTest0() throws Exception {
        File directory = new File("src/test/resources/");
        new UnzipArchivator().unzip("src/test/resources/",
                " ");
        assertEquals(2, directory.listFiles().length);
    }


    /** Test compare two files: unzip with archivator and original one */
    @Test
    public void unzipTest1() throws Exception {
        new UnzipArchivator().unzip("src/test/resources/",
                ".*3");
        assertEquals(true, isEqual("src/test/resources/out3",
                "src/test/resources/expected/out3"));

        File file = new File("src/test/resources/out3");
        file.delete();
    }

    /** Test compare two files: unzip with archivator and original one */
    @Test
    public void unzipTest2() throws Exception {
        new UnzipArchivator().unzip("src/test/resources/",
                ".*10");
        assertEquals(true, isEqual("src/test/resources/out10",
                "src/test/resources/expected/out10"));

        File file = new File("src/test/resources/out10");
        file.delete();
    }

    /** Test with incorrect directory path */
    @Test
    public void unzipTest3() throws Exception {
        System.setOut(new PrintStream(outContent));
        new UnzipArchivator().unzip("src/test/res/",
                ".*10");
        System.setOut(null);
        assertEquals("File 'src/test/res/' doesn't exist.", outContent.toString().trim());
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
}