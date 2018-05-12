package ru.spbau.karlina.ftp.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import ru.spbau.karlina.ftp.Client;
import ru.spbau.karlina.ftp.Server;

import java.io.IOException;
import java.util.ArrayList;

public class FtpWorker {
    private GridPane gridPane;
    private Client client;
    private Thread serverThread;
    private final int PORT = 40444;

    private void setOutStream() throws InterruptedException {
        serverThread = runServer();
        Thread.sleep(1500);
    }

    private void cleanOutStream() throws InterruptedException {
        serverThread.interrupt();
        serverThread.join();
        Thread.sleep(500);
    }

    private Thread runServer() {
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

        return serverThread;
    }

    public FtpWorker(GridPane gridPane){
        this.gridPane = gridPane;
    }

    public void run(String hostName, String dirPath) {
        try {
            setOutStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client = new Client(hostName, PORT);

        showList(client.getDirectoryList(dirPath));

        try {
            cleanOutStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void showList(@NotNull ArrayList<String> directoryList) {
        gridPane.getChildren().clear();

/*
        // Root Item
        TreeItem<Button> rootItem = new TreeItem<Button>(catJava);
        rootItem.setExpanded(true);

        // JSP Item
        TreeItem<Button> itemJSP = new TreeItem<Button>(catJSP);

        // Spring Item
        TreeItem<Button> itemSpring = new TreeItem<>(catSpring);

        // Add to Root
        rootItem.getChildren().addAll(itemJSP, itemSpring);
*/

        TreeItem<Button> root = new TreeItem<Button>(new Button("Root Node"));
        //root.setExpanded(true);

        for (String content : directoryList) {
            Button button = new Button(content);
            TreeItem<Button> treeItem = new TreeItem<>(button);
            root.getChildren().add(treeItem);
        }
        /*
        root.getChildren().addAll(
                new TreeItem<Button>(new Button("Item 1")),
                new TreeItem<Button>(new Button("Item 2")),
                new TreeItem<Button>(new Button("Item 3"))
        );*/

        TreeView<Button> tree = new TreeView<Button>(root);

        gridPane.getChildren().add(tree);
    }
}
