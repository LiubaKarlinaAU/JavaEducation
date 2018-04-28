package ru.spbau.karlina.ftp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ClientTest {
    private final static String LOCALHOST = "localhost";

   // @BeforeEach
    public void runServer(){
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }


String path = "/home/liuba/Second_Year/JavaEducation/SecondSemestr/ftp/src/test/resources";
    @Test
    public void getFile() throws Exception {
 System.out.println("5");
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        Client client = new Client("localhost", 4444);

        client.request(RequestType.FILES_LIST,path);
        //serverThread.join();

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

    @Test
    public void test() throws Exception {

        StringBuilder builder = new StringBuilder();
        File file = new File(path);

        findAllFiles(file, new StringBuilder().append('.'), builder);

        String str = builder.toString();
        System.out.println(str);
    }

    @Test
    public void getSocketPort() throws Exception {
    }


}