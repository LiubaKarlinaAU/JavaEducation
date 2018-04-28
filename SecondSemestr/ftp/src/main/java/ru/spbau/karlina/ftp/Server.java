package ru.spbau.karlina.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {
    private Logger logger = Logger.getGlobal();
    private final int PORT = 4444;
    private boolean hasStarted = false;

    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            logger.info("Server has started.");
            hasStarted = true;
            while (!Thread.interrupted()) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())
                ) {
                    RequestType type = RequestType.values()[dataInputStream.readInt()];
                    logger.info(type + " read");
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

    private void listDirectoryContent(String path, DataOutputStream out) throws IOException {
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

    private void getFileContent(String path, DataOutputStream dataOutputStream) throws IOException {
        File file = new File(path);

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
