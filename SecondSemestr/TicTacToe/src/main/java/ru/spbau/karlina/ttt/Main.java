package ru.spbau.karlina.ttt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.spbau.karlina.ttt.ui.MainController;

import java.io.IOException;

/**Main class to extends Application and initialize MainController*/
public class Main extends Application {

    /**
     * Initialize application.
     * @param primaryStage a stage, where we will play.
     * @throws IOException if there is a problem with load main_menu.fxml file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
        primaryStage.setTitle("Tic-tac-toe");
        primaryStage.setResizable(false);

        MainController.initialize(primaryStage);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
