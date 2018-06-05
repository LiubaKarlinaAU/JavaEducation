package ru.spbau.karlina.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static ru.spbau.karlina.ftp.CommonConstants.*;

/**
 * Server program representation that can do listing of directory and file content showing.
 */
public class Server {
    private Logger logger = Logger.getGlobal();
    private ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new Server().run();
    }

    /**
     * Listens the port to make connection with client.
     * For each socket make new thread
     */
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server has started.");
            while (!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(makeTask(clientSocket));
            }
            logger.info("Server finished.");
        } catch (IOException e) {
            logger.info("Problem with socket on server side: " + e.getMessage());
        }
    }

    /**
     * Made directory listing and write it to the socket DataOutputStream
     * If given name isn't a directory writes 0 to the output stream.
     */
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

    /**
     * Write file content to the socket DataOutputStream.
     * If there isn't file with such name writes 0 to the output stream.
     */
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

    private Runnable makeTask(Socket socket) {
        return () -> {
            try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                 DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                while (socket.isConnected()) {
                    int tmp = dataInputStream.readInt();
                    RequestType type = RequestType.values()[tmp];
                    String path = dataInputStream.readUTF();

                    if (type == RequestType.FILES_LIST) {
                        listDirectoryContent(path, dataOutputStream);
                    } else {
                        getFileContent(path, dataOutputStream);
                    }
                }
            } catch (EOFException e) {
                logger.info("close connection");
            } catch (IOException e) {
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        };
    }
}
