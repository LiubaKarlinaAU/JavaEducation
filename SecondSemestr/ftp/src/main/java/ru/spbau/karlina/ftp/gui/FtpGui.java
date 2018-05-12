package ru.spbau.karlina.ftp.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.spbau.karlina.ftp.Client;
import ru.spbau.karlina.ftp.Server;

import java.io.IOException;
import java.util.ArrayList;

public class FtpGui extends Application{
    private FtpWorker ftpWorker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();

        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(15);

        root.setPrefWidth(400);
        root.setMinHeight(100);
        Label labelHostName = new Label("Host name");
        TextField fieldHostName = new TextField();

        Label labelDirPath = new Label("Directory path");

        TextField fieldDirPath = new TextField();

        Button enterButton = new Button("enter");

        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String dirPath = fieldDirPath.getText();
                String hostName = fieldHostName.getText();
                ftpWorker.run(hostName, dirPath);
            }
        });

        GridPane.setHalignment(labelHostName, HPos.RIGHT);

        root.add(labelHostName, 0, 1);
        
        GridPane.setHalignment(labelDirPath, HPos.RIGHT);
        root.add(labelDirPath, 0, 2);

        GridPane.setHalignment(fieldHostName, HPos.LEFT);
        root.add(fieldHostName, 1, 1);

        GridPane.setHalignment(fieldDirPath, HPos.LEFT);
        root.add(fieldDirPath, 1, 2);

        GridPane.setHalignment(enterButton, HPos.RIGHT);
        root.add(enterButton, 1, 3);

        ftpWorker = new FtpWorker(root);

        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("server client application");
        primaryStage.show();


    }
}
