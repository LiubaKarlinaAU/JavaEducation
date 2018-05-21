package ru.spbau.karlina.ftp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import ru.spbau.karlina.ftp.Client;

import java.io.*;
import java.util.ArrayList;

public class FtpWorker {
    private GridPane gridPane;
    private Client client;
    private final int PORT = 40444;
    private String prev;

    public FtpWorker(String hostName, GridPane gridPane) throws IOException {
        this.gridPane = gridPane;
        client = new Client(hostName, PORT);
    }

    public void run(String dirPath) throws IOException {

        showList(client.getDirectoryList(dirPath));
    }

    public void showList(@NotNull ArrayList<Pair<String, Boolean>> directoryList) {
        gridPane.getChildren().clear();

        ObservableList names = FXCollections.observableArrayList();

        for (Pair<String, Boolean> content : directoryList) {
            if (content.getValue()) {
                names.add(content.getKey() + "/");
            } else {
                names.add(content.getKey() + "");
            }
        }

        ListView<String> listView = new ListView<>(names);
        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() >= 2) {
                String item = listView.getSelectionModel().getSelectedItem();
                if (item.endsWith("/")) {
                    String fileName = item.substring(0, item.length() - 1);
                    prev = prev + fileName;
                    try {
                        run(prev);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loadFile(item);
                }
            }
        });

        gridPane.getChildren().add(listView);
    }

    private void loadFile(String item) {
        String fileName = prev + item;
        String outputFileName = getOutputFileName();
        try {
            FileOutputStream stream = new FileOutputStream(outputFileName);
            client.getFileContent(item, stream);
/*
            Toast = new Toast("You file " + item + " saved to " + outputFileName);
            gridPane.add(text);
  */
            start();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        gridPane.getChildren().clear();

        Label labelDirPath = new Label("Directory path");
        TextField fieldDirPath = new TextField();

        Button enterButton = new Button("enter");

        enterButton.setOnAction(actionEvent -> {
            String dirPath = fieldDirPath.getText();
            prev = dirPath;
            try {
                run(dirPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        GridPane.setHalignment(labelDirPath, HPos.RIGHT);
        gridPane.add(labelDirPath, 0, 2);

        GridPane.setHalignment(fieldDirPath, HPos.LEFT);
        gridPane.add(fieldDirPath, 1, 2);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        gridPane.add(enterButton, 1, 3);
    }

    public String getOutputFileName() {
        gridPane.getChildren().clear();

        Label labelDirPath = new Label("path where to save: ");
        TextField fieldDirPath = new TextField();

        Button enterButton = new Button("enter");
/*
        enterButton.setOnAction(actionEvent -> {
            String dirPath = fieldDirPath.getText();
            return dirPath;
        });
*/
        GridPane.setHalignment(labelDirPath, HPos.RIGHT);
        gridPane.add(labelDirPath, 0, 2);

        GridPane.setHalignment(fieldDirPath, HPos.LEFT);
        gridPane.add(fieldDirPath, 1, 2);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        gridPane.add(enterButton, 1, 3);

        return "";
    }
}
