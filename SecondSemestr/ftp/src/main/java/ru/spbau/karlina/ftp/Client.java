package ru.spbau.karlina.ftp;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client representation that can send 2 type of request to server.
 */
public class Client implements AutoCloseable {
    private final static int PORT = 40444;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final int BUFFER_SIZE = 2048;


    /**
     * Main method to create client and start sending requests.
     *
     * @param args - ignored
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong input. Correct is: <type> <path>");
            return;
        }
        System.out.println("Correct request is <type> <path>");
        System.out.println("type(number): 0 - directory listing, 1 - file saving.");
        System.out.println("path(string) to directory or file.");
        System.out.println("write 'exit' if you would like to shut down application.");

        try {
            Client client = new Client("localhost", PORT);
            Scanner reader = new Scanner(System.in);
            client.run(reader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Allows to send request to server two types.
     * <1: Int> <path: String>  get directory list
     * <2: Int> <path: String>  get file content
     * @throws IOException in case getDirectoryList or getFileContent threw them
     */
    public void run(Scanner reader) throws IOException {
        while (reader.hasNext()) {
            String[] arguments = reader.nextLine().split(" ");

            if (arguments.length != 2) {
                if (arguments.length == 1 && arguments[0].equals("exit")) {
                    System.exit(0);
                } else {
                    System.out.println("Wrong input. Correct is: <type> <path>");
                }

            } else {
                if (arguments[0].equals("0")) {
                    ArrayList<Pair<String, Boolean>> list = getDirectoryList(arguments[1]);
                    for (Pair<String, Boolean> pair : list) {
                        System.out.println(pair.getKey() + " " + (pair.getValue() ? "(directory)" : "(file)"));
                    }
                } else if (arguments[0].equals("1")) {
                    try {
                        File outputFile = takeOutputFile(reader);
                        FileOutputStream stream = new FileOutputStream(outputFile);
                        getFileContent(arguments[1], stream);
                        System.out.println("Done!");
                        stream.close();
                    } catch (IOException e) {
                        System.out.println("Can't create file for saving");
                    }
                } else {
                    System.out.println("Wrong request type! Try again.");
                }
            }
        }
    }

    private static File takeOutputFile(Scanner reader) throws IOException {
        System.out.println("Please, write file name for saving:");
        File file = new File(reader.nextLine());
        file.createNewFile();
        return file;
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