package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;
import static ru.spbau.karlina.ftp.CommonConstants.*;
import static ru.spbau.karlina.ftp.CommonStringConstant.*;


public class ServerTest {
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private DataInputStream dataInputStream;

    @Before
    public void initialize() throws IOException {
        socket = new Socket(LOCAL_HOST, PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @After
    public void close() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }

    @Test
    public void getDirectoryListTest() throws Exception {
        ArrayList<Pair<String, Boolean>> list = new ArrayList<>();

        dataOutputStream.writeInt(RequestType.FILES_LIST.ordinal());
        dataOutputStream.writeUTF(firstDirName);
        dataOutputStream.flush();

        int size = dataInputStream.readInt();

        for (int i = 0; i < size; ++i) {
            String string = dataInputStream.readUTF();
            boolean is_dir = dataInputStream.readBoolean();
            list.add(new Pair(string, is_dir));
        }

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
        long size;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        dataOutputStream.writeInt(RequestType.FILE_CONTENT.ordinal());
        dataOutputStream.writeUTF(firstFileName);
        dataOutputStream.flush();

        size = dataInputStream.readLong();
        byte buffer[] = new byte[BUFFER_SIZE];
        int readedSize;

        do {
            readedSize = dataInputStream.read(buffer, 0, (int) Long.min(size, BUFFER_SIZE));
            size -= readedSize;
            outputStream.write(buffer, 0, readedSize);
        } while (size > 0);

        assertEquals(outputStream.toString().trim(), "1234");
    }
}