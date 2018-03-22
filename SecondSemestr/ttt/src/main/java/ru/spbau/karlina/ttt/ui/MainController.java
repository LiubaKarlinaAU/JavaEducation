package main.java.ru.spbau.karlina.ttt.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    static private Stage primaryStage = null;

    /** Set primary stage - a content where we playing game
     * @param stage - to be set */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /** Primary stage getter
     *  @return primary stage */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Loads statistics.
     * @throws IOException if something wrong.
     */
    @FXML
    public void runStatisticScene() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/statistic.fxml"));
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    public void runTwoPlayerScene() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/two_player_game.fxml"));
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    public void runSinglePlayerScene() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/one_player_game.fxml"));
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    public void gameExit() {
        Platform.exit();
    }
}