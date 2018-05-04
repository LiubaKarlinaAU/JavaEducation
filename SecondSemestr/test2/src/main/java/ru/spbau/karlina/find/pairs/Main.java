package ru.spbau.karlina.find.pairs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


import java.io.IOException;

/**Main class to extends Application and initialize MainController*/
public class Main extends Application {
    private GridPane grid = new GridPane();
    //private Field field = new Field();
    private static Model model;
    //private static final Duration waitingTime =  Duration.ofSeconds(1);
    private Button[][] buttons;

    /**
     * Initialize application.
     * @param primaryStage a stage, where we will play.
     * @throws IOException if there is a problem with load main_menu.fxml file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int size = model.getSize();
        buttons = new Button[size][size];

        for (int row = 0 ; row < size ; row++ ){
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }
        for (int col = 0 ; col < size; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        for (int i = 0 ; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                Button button = createButton(i, j);
                grid.add(button, i, j);
            }
        }

        Scene scene = new Scene(grid);

        primaryStage.setTitle("Find Pairs");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(int x, int y) {
        buttons[x][y] = new Button();
        buttons[x][y].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        /*buttons[x][y].setOnAction(e -> {
            processEvents(field.processClick(x, y));
        });*/
        return buttons[x][y];
    }

    public static void main(String[] args) {
        int size = 4; //Integer.parseInt(args[0]);
        if (size <= 0 || size % 2 != 0) {
            System.out.println("Incorrect size of grid.");
        }
        else {
            model = new Model(size);
            launch(args);
        }
    }
}