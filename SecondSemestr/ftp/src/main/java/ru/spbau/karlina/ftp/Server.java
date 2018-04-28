package ru.spbau.karlina.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
                        list(path, dataOutputStream);
                    } else {
                        get(path, dataOutputStream);
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
/*
    public Server() throws IOException {
        serverSocket = new ServerSocket(4444);
    }*/
/*
    public void run() {
        try (Socket socket = serverSocket.accept()) {
            InputStream is = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(is);
            int id = dataInputStream.readInt();
            int length = dataInputStream.readInt();
            byte bytes[] = new byte[length];
            dataInputStream.read(bytes);
            String path = new String(bytes, "UTF-8");
            if (id == 1) {
                list(path);
            } else {
                get(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    private void list(String path, DataOutputStream out) throws IOException {
        StringBuilder builder = new StringBuilder();
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

    private void findAllFiles(File current, StringBuilder prefix, StringBuilder builder) {
        if (current.isDirectory()) {
            for (File subFile : current.listFiles()) {
                prefix.append('/' + subFile.getName());
                findAllFiles(subFile, prefix, builder);
                prefix.setLength(prefix.lastIndexOf("/"));
            }
        } else {
            builder.append(prefix.toString() + "\n");
        }
    }

    private void get(String path, DataOutputStream out) throws IOException {
        String str = "Hello.This is get method";
        logger.info(str);
        out.writeInt(str.getBytes().length);
        out.write(str.getBytes());
        out.flush();
    }
}
