package ru.spbau.karlina.ftp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ClientTest {
    private final static String LOCALHOST = "localhost";
    private final int PORT = 40444;
    private Thread serverThread;
    private final String firstDirName = "./src/test/resources";
    private final String secondDirName = "./src/test/resources/firstDir";
    private final String firstFileName = "./src/test/resources/first.txt";
    private final String secondFileName = "./src/test/resources/firstDir/second.txt";

    @Before
    public void setOutStream() throws InterruptedException {
        serverThread = runServer();
        Thread.sleep(1500);
    }

    @After
    public void cleanOutStream() throws InterruptedException {
        serverThread.interrupt();
        serverThread.join();
        Thread.sleep(500);
    }

    private Thread runServer() {
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        return serverThread;
    }

    /**
     * Simple test on Client RequestType.FILES_LIST request
     */
    @Test
    public void getFileTest1() throws Exception {
        Client client = new Client(LOCALHOST, PORT);

        ArrayList<String> list = client.getDirectoryList(firstDirName);
        String expected0 = "firstDir (directory)";
        String expected1 = "first.txt (file)";
        assertEquals(expected0, list.get(0));
        assertEquals(expected1, list.get(1));

    }

    /**
     * Simple test on Client RequestType.FILES_LIST request
     */
    @Test
    public void getFileTest2() throws Exception {
        Client client = new Client(LOCALHOST, PORT);

        ArrayList<String> list = client.getDirectoryList(secondDirName);
        String expected0 = "second.txt (file)";
        String expected1 = "third.txt (file)";
        assertEquals(2, list.size());
        assertEquals(expected0, list.get(0));
        assertEquals(expected1, list.get(1));

    }

    /**
     * Simple test on Client RequestType.FILE_CONTENT request
     */
    @Test
    public void getFileContentTest1() throws Exception {
        Client client = new Client(LOCALHOST, PORT);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        long size = client.getFileContent(firstFileName, outputStream);
        assertEquals(4, size);
        assertEquals(outputStream.toString().trim(), "1234");
    }

    /**
     * Simple test on Client RequestType.FILE_CONTENT request
     */
    @Test
    public void getFileContentTest2() throws Exception {
        Client client = new Client(LOCALHOST, PORT);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        long size = client.getFileContent(secondFileName, outputStream);
        assertEquals(10, size);
        assertEquals(outputStream.toString().trim(), "la la land");
    }


}