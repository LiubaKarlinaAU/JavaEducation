package ru.spbau.karlina.ftp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClientTest {
    private final static String LOCALHOST = "localhost";
    private final String dirName = "/home/liuba/Second_Year/JavaEducation/SecondSemestr/ftp/src/test/resources";
    private final String fileName = "/home/liuba/Second_Year/JavaEducation/SecondSemestr/ftp/src/test/resources/first.txt";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanOutStream() {
        System.setOut(null);
    }


    //@BeforeEach
    public Thread runServer(){
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


    @Test
    public void getFileTest1() throws Exception {
        Thread server = runServer();
        Client client = new Client("localhost", 4444);

        client.request(RequestType.FILES_LIST,dirName);
        server.interrupt();
        String expected = "firstDir (directory)\n" + "first.txt (file)";
        assertEquals(expected, outContent.toString().trim());
    }

    @Test
    public void getSocketPort() throws Exception {
        Thread server = runServer();
        Client client = new Client("localhost", 4444);

        client.request(RequestType.FILES_CONTENT, fileName);
        server.interrupt();
        assertTrue(outContent.toString().trim().startsWith("size of file is 14\n"));

    }
}