package ru.spbau.karlina.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Server program representation that can do listing of directory and file content showing.
 */
public class Server {
    private Logger logger = Logger.getGlobal();
    private final int PORT = 4444;

    /**
     * Listens the port to make connection with client.
     */
    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server has started.");
            while (!Thread.interrupted()) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())
                ) {
                    RequestType type = RequestType.values()[dataInputStream.readInt()];
                    logger.info("server get " + type + " request");
                    int length = dataInputStream.readInt();
                    byte bytes[] = new byte[length];
                    dataInputStream.read(bytes);
                    String path = new String(bytes, "UTF-8");
                    if (type == RequestType.FILES_LIST) {
                        listDirectoryContent(path, dataOutputStream);
                    } else {
                        getFileContent(path, dataOutputStream);
                    }
                } catch (Exception e) {
                    logger.info("Problem after making conection.");
                }
            }
            logger.info("Server finished.");
        } catch (Exception e) {
            logger.info("Can't use " + PORT + " port.");
        }


    }

    /**
     * Made directory listing and write it to the socket DataOutputStream
     * If given name isn't a directory writes 0 to the output stream.
     */
    private void listDirectoryContent(@NotNull String path, @NotNull DataOutputStream out) throws IOException {
        File file = new File(path);

        if (file == null || !file.isDirectory()) {
            out.writeInt(0);
            return;
        }

        out.writeInt(file.listFiles().length);
        for (File subFile : file.listFiles()) {
            String fileName = subFile.getName();
            out.writeInt(fileName.getBytes().length);
            out.write(fileName.getBytes());
            out.writeBoolean(subFile.isDirectory());
        }
    }

    /**
     * Write file content to the socket DataOutputStream.
     * If there isn't file with such name writes 0 to the output stream.
     */
    private void getFileContent(@NotNull String fileName, @NotNull DataOutputStream dataOutputStream) throws IOException {
        File file = new File(fileName);

        if (file == null || file.isDirectory()) {
            dataOutputStream.writeInt(0);
            return;
        }

        dataOutputStream.writeLong(file.length());
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            dataOutputStream.write(scanner.nextLine().getBytes());
        }
        dataOutputStream.flush();
    }
}