package ru.spbau.karlina.find.pairs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Main class to extends Application and initialize MainController
 */
public class Main extends Application {
    private GridPane grid = new GridPane();
    private static Model model;
    private Button[][] buttons;

    /**
     * Initialize application.
     *
     * @param primaryStage a stage, where we will play.
     * @throws IOException if there is a problem with load main_menu.fxml file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        grid.setAlignment(Pos.CENTER);

        int size = model.getSize();
        buttons = new Button[size][size];

        for (int row = 0; row < size; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            grid.getRowConstraints().add(rowConstraints);
        }
        for (int col = 0; col < size; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            grid.getColumnConstraints().add(columnConstraints);
        }

        initializeButtons();

        Scene scene = new Scene(grid);

        primaryStage.setTitle("Find Pairs");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main methoud that start game process
     * @param args - application parametrs: should be one number - size of greed
     */
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        if (size <= 0 || size % 2 != 0) {
            System.out.println("Incorrect size of grid.");
        } else {
            model = new Model(size);
            launch(args);
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                buttons[i][j] = newButton(i, j);
                grid.add(buttons[i][j], i, j);
            }
        }

    }

    /** Destroy application */
    public static void destroy() {
        Platform.exit();
    }

    private Button newButton(int i, int j) {
        Button button = new Button();
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setPrefHeight(50);
        button.setPrefWidth(50);
        button.setText("*");
        button.setOnAction(actionEvent -> model.clicked(i, j, buttons[i][j]));

        return button;
    }
}