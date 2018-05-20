package ru.spbau.karlina.ftp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
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
            server.run();
        });
        serverThread.setDaemon(true);
        serverThread.start();

        return serverThread;
    }

    public FtpWorker(GridPane gridPane){
        this.gridPane = gridPane;
    }

    public void run(String hostName, String dirPath) throws IOException {
        client = new Client(hostName, PORT);

        showList(client.getDirectoryList(dirPath));
    }
    
    public void showList(@NotNull ArrayList<Pair<String, Boolean>> directoryList) {
        gridPane.getChildren().clear();

        ObservableList names = FXCollections.observableArrayList();

        for (Pair<String, Boolean> content : directoryList) {
            names.add(content.getKey());
        }

        ListView<String> listView = new ListView<>(names);
        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() >= 2) {
                String item = listView.getSelectionModel().getSelectedItem();
                if (item.endsWith("/")) {
                    showList(item.substring(0, item.length() - 1));
                } else {
                    loadFile(item);
                }
            }
        });

        gridPane.getChildren().add(listView);
    }

    private void loadFile(String item) {
    }
}
