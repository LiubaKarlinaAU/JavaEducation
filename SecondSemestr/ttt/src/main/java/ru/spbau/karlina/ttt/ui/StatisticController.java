package main.java.ru.spbau.karlina.ttt.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.ru.spbau.karlina.ttt.store.DataStore;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticController implements Initializable{
    private static DataStore dataStore;
    @FXML
    private Text countOfGames;
    @FXML
    private Text firstWins;
    @FXML
    private Text secondWins;
    @FXML
    private Text notFinished;
    @FXML
    private Text countOfDraw;

    public static void setDataStore(){
        dataStore = new DataStore();
    }

    public static DataStore getDataStore() {
        return dataStore;
    }

    public void backToMainMenu() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/main_menu.fxml"));
        Stage primaryStage = MainController.getPrimaryStage();
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataStore store = StatisticController.getDataStore();
        countOfGames.setText("After playing " + store.getGameCount() + " games");
        firstWins.setText("X wins: " + store.getCrossWins());
        secondWins.setText("O wins: " + store.getNaughtWins());
        countOfDraw.setText("Draw: " + store.getDrawCount());
        notFinished.setText("and " + store.getNotFinished() + " games haven't been finished.");
    }
}
