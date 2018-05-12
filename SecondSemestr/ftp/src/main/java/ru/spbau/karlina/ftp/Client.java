package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Client representation that can send 2 type of request to server.
 */
public class Client {
    private String host;
    private int port;
    private final int BUFFER_SIZE = 2048;

    /**
     * Set up settings to make connection with server.
     *
     * @param host - server IP address.
     * @param port - server port.
     */
    public Client(@NotNull String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends RequestType.FILES_LIST request to server handles server answer.
     *
     * @param dirName - path to directory.
     * @return list of pairs: name of content in given catalog and
     *         true if it directory and false if it file
     */
    @NotNull
    public ArrayList<Pair<String, Boolean>> getDirectoryList(@NotNull String dirName) {
        ArrayList<Pair<String, Boolean>> list = new ArrayList<>();
        try (Socket socket = new Socket(host, port);
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            dataOutputStream.writeInt(RequestType.FILES_LIST.ordinal());
            dataOutputStream.writeUTF(dirName);
            dataOutputStream.flush();

            int size = dataInputStream.readInt();

            for (int i = 0; i < size; ++i) {
                String string = dataInputStream.readUTF();
                boolean is_dir = dataInputStream.readBoolean();
                list.add(new Pair (string, is_dir));
            }

        } catch (IOException e) {
            System.out.println("In getDirectoryList is a problem: " + e.getMessage());
        }
        return list;
    }

    /**
     * Sends RequestType.FILE_CONTENT request to server and handles answer.
     *
     * @param fileName - name of file for loading.
     * @param output   - stream to save file
     * @return size of file
     */
    public long getFileContent(@NotNull String fileName, @NotNull OutputStream output) {
        long size = 0;
        try (Socket socket = new Socket(host, port);
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            dataOutputStream.writeInt(RequestType.FILE_CONTENT.ordinal());
            dataOutputStream.writeUTF(fileName);
            dataOutputStream.flush();

            size = dataInputStream.readLong();
            byte buffer[] = new byte[BUFFER_SIZE];

            while (dataInputStream.read(buffer) != -1) {
                output.write(buffer);
            }

        } catch (IOException e) {
            System.out.println("In getFileContent is a problem: " + e.getMessage());
        }

        return size;
    }

}