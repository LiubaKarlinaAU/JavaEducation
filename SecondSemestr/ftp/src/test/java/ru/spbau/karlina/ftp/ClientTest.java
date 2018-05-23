package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClientTest {
    private final static String LOCALHOST = "localhost";
    private final int PORT = 40444;
    private final String firstDirName = "./src/test/resources";
    private final String secondDirName = "./src/test/resources/firstDir";
    private final String firstFileName = "./src/test/resources/first.txt";
    private final String secondFileName = "./src/test/resources/firstDir/second.txt";
    private Client client;
    private Thread serverThread;

    @Before
    public void setOutStream() throws InterruptedException, IOException {
        serverThread = runServer();
        Thread.sleep(500);
        client = new Client(LOCALHOST, PORT);
    }

    @After
    public void cleanOutStream() throws Exception {
        client.close();
        serverThread.interrupt();
    }

    private Thread runServer() {
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            server.run();
        });
        serverThread.start();

        return serverThread;
    }

    /**
     * Simple test on Client RequestType.FILES_LIST request
     */
    @Test(timeout = 2000)
    public void getFileTest1() throws Exception {
        ArrayList<Pair<String, Boolean>> list = client.getDirectoryList(firstDirName);
        HashSet<Pair<String, Boolean>> expected = new HashSet<>();
        String first = "firstDir";
        String second = "first.txt";
        expected.add(new Pair(first, true));
        expected.add(new Pair<>(second, false));
        assertEquals(2, list.size());
        assertTrue(expected.contains(list.get(1)));
    }

    /**
     * Simple test on Client RequestType.FILES_LIST request
     */
    @Test(timeout = 2000)
    public void getFileTest2() throws Exception {
        ArrayList<Pair<String, Boolean>> list = client.getDirectoryList(secondDirName);
        HashSet<Pair<String, Boolean>> expected = new HashSet<>();
        String first = "second.txt";
        String second = "third.txt";
        expected.add(new Pair<>(first, false));
        expected.add(new Pair<>(second, false));
        assertEquals(2, list.size());
        assertTrue(expected.contains(list.get(0)));
        assertTrue(expected.contains(list.get(1)));
    }

    /**
     * Simple test on Client RequestType.FILE_CONTENT request
     */
    @Test(timeout = 2000)
    public void getFileContentTest1() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        client.getFileContent(firstFileName, outputStream);
        assertEquals(outputStream.toString().trim(), "1234");
    }

    /**
     * Simple test on Client RequestType.FILE_CONTENT request
     */
    @Test(timeout = 2000)
    public void getFileContentTest2() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        client.getFileContent(secondFileName, outputStream);
        assertEquals(outputStream.toString().trim(), "la la land");
    }
}