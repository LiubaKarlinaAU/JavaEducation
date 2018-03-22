package main.java.ru.spbau.karlina.ttt.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Initialize application.
     * @param primaryStage a stage, where we will are playing
     * @throws Exception if needed.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/main_menu.fxml"));
        primaryStage.setTitle("Tic-tac-toe");

        MainController.initialize(primaryStage);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
