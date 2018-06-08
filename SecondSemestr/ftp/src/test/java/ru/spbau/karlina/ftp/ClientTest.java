package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;
import static ru.spbau.karlina.ftp.CommonConstants.*;
import static ru.spbau.karlina.ftp.CommonStringConstant.*;

public class ClientTest {
    private Thread serverThread;
    private Client client;

    @Before
    public void setOutStream() throws InterruptedException, IOException {
        serverThread = runServer();
        Thread.sleep(500);
        client = new Client(LOCAL_HOST, PORT);
    }

    @After
    public void cleanOutStream() throws Exception {
        client.close();
        serverThread.interrupt();
    }

    @Test
    public void getDirectoryListTest() throws Exception {
        ArrayList<Pair<String, Boolean>> list = client.getDirectoryList(firstDirName);
        HashSet<Pair<String, Boolean>> expected = new HashSet<>();
        String first = "firstDir";
        String second = "first.txt";
        expected.add(new Pair<>(first, true));
        expected.add(new Pair<>(second, false));
        assertEquals(2, list.size());
        assertTrue(expected.contains(list.get(1)));

    }

    @Test
    public void getFileContentTest() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        client.getFileContent(firstFileName, outputStream);
        assertEquals(outputStream.toString().trim(), "1234");

    }


    private Thread runServer() {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                serverSocket.setSoTimeout(1500);
                while (!Thread.interrupted()) {
                    try (Socket clientSocket = serverSocket.accept();
                         DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                         DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {
                        RequestType type = RequestType.values()[dataInputStream.readInt()];
                        String path = dataInputStream.readUTF();
                        if (type == RequestType.FILES_LIST) {
                            listDirectoryContent(path, dataOutputStream);
                        } else {
                            getFileContent(path, dataOutputStream);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        });

        serverThread.start();

        return serverThread;
    }

    private void listDirectoryContent(@NotNull String path, @NotNull DataOutputStream out) throws IOException {
        File file = new File(path);

        if (!file.exists() || !file.isDirectory()) {
            out.writeInt(0);
            return;
        }

        out.writeInt(file.listFiles().length);
        for (File subFile : file.listFiles()) {
            String fileName = subFile.getName();
            out.writeUTF(fileName);
            out.writeBoolean(subFile.isDirectory());
        }
        out.flush();
    }

    private void getFileContent(@NotNull String fileName, @NotNull DataOutputStream dataOutputStream) throws IOException {
        File file = new File(fileName);

        if (!file.exists() || file.isDirectory()) {
            dataOutputStream.writeInt(0);
            return;
        }

        dataOutputStream.writeLong(file.length());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[BUFFER_SIZE];

        int bufferLen = fileInputStream.read(buffer);
        while (bufferLen > 0) {
            dataOutputStream.write(buffer, 0, bufferLen);
            bufferLen = fileInputStream.read(buffer);
        }

        dataOutputStream.flush();
    }
}