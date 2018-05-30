package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Client representation that can send 2 type of request to server.
 */
public class Client implements AutoCloseable {
    private final static int PORT = 40444;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final int BUFFER_SIZE = 2048;


    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", PORT);
        } catch (IOException e) {
            System.out.println("Problem with socket on server side: " + e.getMessage());
        }
    }

    /**
     * Create socket to make connection with server.
     *
     * @param host - server IP address.
     * @param port - server port.
     * @throws IOException - if there is problem with creating streams.
     */
    public Client(@NotNull String host, int port) throws IOException {
        socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Sends RequestType.FILES_LIST request to server handles server answer.
     *
     * @param dirName - path to directory.
     * @return list of pairs: name of content in given catalog and
     * true if it directory and false if it file
     * @throws IOException - if there is problem with reading from stream or writing to stream.
     */
    @NotNull
    public ArrayList<Pair<String, Boolean>> getDirectoryList(@NotNull String dirName) throws IOException {
        ArrayList<Pair<String, Boolean>> list = new ArrayList<>();

        dataOutputStream.writeInt(RequestType.FILES_LIST.ordinal());
        dataOutputStream.writeUTF(dirName);
        dataOutputStream.flush();

        int size = dataInputStream.readInt();

        for (int i = 0; i < size; ++i) {
            String string = dataInputStream.readUTF();
            boolean is_dir = dataInputStream.readBoolean();
            list.add(new Pair(string, is_dir));
        }

        return list;
    }

    /**
     * Sends RequestType.FILE_CONTENT request to server and handles answer.
     *
     * @param fileName - name of file for loading.
     * @param output   - stream to save file.
     * @throws IOException - if there is problem with reading from stream or writing to stream.
     */
    public void getFileContent(@NotNull String fileName, @NotNull OutputStream output) throws IOException {
        long size;

        dataOutputStream.writeInt(RequestType.FILE_CONTENT.ordinal());
        dataOutputStream.writeUTF(fileName);
        dataOutputStream.flush();

        size = dataInputStream.readLong();
        byte buffer[] = new byte[BUFFER_SIZE];
        int readedSize;

        do {
            readedSize = dataInputStream.read(buffer, 0, (int) Long.min(size, BUFFER_SIZE));
            size -= readedSize;
            output.write(buffer, 0, readedSize);
        } while (size > 0);

    }

    /**
     * Close socket connection.
     *
     * @throws IOException - if there is problem with closing streams or socket
     */
    @Override
    public void close() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}