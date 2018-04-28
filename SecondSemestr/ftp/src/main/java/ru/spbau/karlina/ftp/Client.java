package ru.spbau.karlina.ftp;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private Socket socket;
    private final int BUFFER_SIZE = 2048;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void request(RequestType type, String fileName) {
        try (DataOutputStream os = new DataOutputStream(socket.getOutputStream())) {
            os.writeInt(type.ordinal());
            os.writeInt(fileName.getBytes().length);
            os.write(fileName.getBytes());
            os.flush();

            if (type == RequestType.FILES_LIST) {
                getDirectoryList();
            } else if (type == RequestType.FILES_CONTENT) {
                getFileContent(fileName);
            } else {
                System.out.println("Incorrect request type.");
            }
        } catch (IOException e) {
            /**Note somehow */
        }
    }

    private void getDirectoryList() {
        try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())
        ) {
            int size = dataInputStream.readInt();
            byte buffer[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < size; ++i) {
                int length = dataInputStream.readInt();
                int count;
                StringBuilder builder = new StringBuilder();
                while (length > 0) {
                    if (length > BUFFER_SIZE) {
                        count = dataInputStream.read(buffer);
                    } else {
                        count = dataInputStream.read(buffer, 0, length);
                    }
                    builder.append(new String(buffer, 0, count, "UTF-8"));
                    length -= count;

                }
                boolean is_dir = dataInputStream.readBoolean();
                System.out.println(builder.toString() + " (" + (is_dir ? "directory" : "file") + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getFileContent(String fileName) {
        try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())
        ) {
            long size = dataInputStream.readLong();
            byte buffer[] = new byte[BUFFER_SIZE];
            if (size > 0)
                System.out.println("size of file is " + size);

            while (dataInputStream.read(buffer) != -1) {
                System.out.print(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}