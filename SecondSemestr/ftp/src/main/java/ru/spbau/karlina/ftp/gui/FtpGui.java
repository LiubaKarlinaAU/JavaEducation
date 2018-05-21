package ru.spbau.karlina.ftp.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FtpGui extends Application{
    private FtpWorker ftpWorker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GridPane root = new GridPane();

        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(15);

        root.setPrefWidth(400);
        root.setMinHeight(100);
        Label labelHostName = new Label("Host name");
        TextField fieldHostName = new TextField();

        Button enterButton = new Button("enter");

        enterButton.setOnAction(actionEvent -> {
            String hostName = fieldHostName.getText();
            try {
                ftpWorker = new FtpWorker(hostName, root);
                ftpWorker.start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        GridPane.setHalignment(labelHostName, HPos.RIGHT);

        root.add(labelHostName, 0, 1);

        GridPane.setHalignment(fieldHostName, HPos.LEFT);
        root.add(fieldHostName, 1, 1);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        root.add(enterButton, 1, 3);

        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("server client application");
        primaryStage.show();


    }
}
