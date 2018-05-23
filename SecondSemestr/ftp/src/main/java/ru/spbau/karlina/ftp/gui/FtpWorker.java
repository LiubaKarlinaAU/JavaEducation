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

/**
 * Special class for making representation of ftp server/client application working.
 */
public class FtpWorker {
    @NotNull
    private GridPane gridPane;
    @NotNull
    private Client client;
    @NotNull
    private String prev;
    private final int PORT = 40444;

    /**
     * Constructor makes setting gridPane field and create client.
     *
     * @param hostName - host name to create client.
     * @param gridPane - layout to work on.
     */
    public FtpWorker(@NotNull String hostName, @NotNull GridPane gridPane) throws IOException {
        this.gridPane = gridPane;
        client = new Client(hostName, PORT);
    }

    /**
     * Ask user directory name to make listing of it.
     */
    public void start() {
        gridPane.getChildren().clear();

        Label labelDirPath = new Label("Directory path");
        TextField fieldDirPath = new TextField();

        Button enterButton = new Button("Enter");

        enterButton.setOnAction(actionEvent -> {
            String dirPath = fieldDirPath.getText();
            if (dirPath.length() != 0) {
                prev = dirPath;
                try {
                    run(dirPath);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        GridPane.setHalignment(labelDirPath, HPos.RIGHT);
        gridPane.add(labelDirPath, 0, 2);

        GridPane.setHalignment(fieldDirPath, HPos.LEFT);
        gridPane.add(fieldDirPath, 1, 2);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        gridPane.add(enterButton, 1, 3);
    }

    /**
     * Make initial directory listing request.
     *
     * @param dirPath - directory name to send request.
     */
    private void run(@NotNull String dirPath) throws IOException {
        prev = dirPath;
        showList(client.getDirectoryList(dirPath));
    }

    /**
     * Show list of values from given pair list.
     *
     * @param directoryList - to be printed on screen.
     */
    private void showList(@NotNull ArrayList<Pair<String, Boolean>> directoryList) {
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
                    try {
                        run(prev + "/" + fileName);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    getOutputFileName(item);
                }
            }
        });

        gridPane.getChildren().add(listView);
    }

    private void getOutputFileName(String item) {
        gridPane.getChildren().clear();

        Label labelDirPath = new Label("Save file as ");
        TextField fieldDirPath = new TextField();

        Button enterButton = new Button("Enter");

        enterButton.setOnAction(actionEvent -> {
            String dirPath = fieldDirPath.getText();
            if (dirPath.length() != 0) {
                loadFile(item, dirPath);
            }
        });

        GridPane.setHalignment(labelDirPath, HPos.RIGHT);
        gridPane.add(labelDirPath, 0, 2);

        GridPane.setHalignment(fieldDirPath, HPos.LEFT);
        gridPane.add(fieldDirPath, 1, 2);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        gridPane.add(enterButton, 1, 3);
    }

    private void loadFile(String item, String outputFileName) {
        String fileName = prev + "/" + item;
        try (FileOutputStream stream = new FileOutputStream(outputFileName)) {
            client.getFileContent(fileName, stream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            start();
        }
    }
}
