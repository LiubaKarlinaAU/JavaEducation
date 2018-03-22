package main.java.ru.spbau.karlina.ttt.ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ru.spbau.karlina.ttt.logic.*;
import main.java.ru.spbau.karlina.ttt.store.DataStore;
import main.java.ru.spbau.karlina.ttt.store.GameType;
import main.java.ru.spbau.karlina.ttt.store.PlayerType;

import java.io.IOException;

import static main.java.ru.spbau.karlina.ttt.store.PlayerType.*;

public class OnePlayerGameController {
    static private Scene scene;
    static private Model model;
    static private BotInterface bot;
    static private DataStore store;

    private PlayerType playerType = X_PLAYER;
    private Button button00;
    private Button button01;
    private Button button02;
    private Button button10;
    private Button button20;
    private Button button11;
    private Button button12;
    private Button button21;
    private Button button22;

    static private Button[][] buttons = new Button[3][3];
    public RadioButton levelButton;
    public TextField player_name;

    /** Setter dataStore to make records of games
     * @param dataStore - to be set */
    public static void setDataStore(DataStore dataStore) {
        store = dataStore;
    }

    public OnePlayerGameController(){
        buttons[0][0] = button00;
        buttons[1][0] = button10;
        buttons[2][0] = button20;

        buttons[1][1] = button11;
        buttons[0][1] = button01;
        buttons[0][2] = button02;

        buttons[2][2] = button22;
        buttons[2][1] = button21;
        buttons[1][2] = button12;

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) { ;
                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    private final int x = i;
                    private final int y = j;


                    @Override
                    public void handle(ActionEvent event) {
                        if (tryToFill(x, y)) {
                            buttons[x][y].setText("X");
                            GameResult result = model.gameStatus();
                            if (result !=GameResult.GAME_IN_PROGRESS) {
                                makeGameRecord(player_name, result);
                            } else {
                                makeBotMove();
                            }
                        }
                    }
                });
            }
    }

    private void makeBotMove() {

    }

    public static void initialize() {
        OnePlayerGameController.scene = scene;
        model = new Model();
        model.makeEmpty();
        bot = new EasyBot();
    }

    public void backToMainMenu() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/main_menu.fxml"));
        Stage primaryStage = MainController.getPrimaryStage();
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }

    public void changeLevel(ActionEvent actionEvent) {
    }

    private boolean tryToFill(int i, int j) {
        if (model.getCellType(i, j) != CellStates.EMPTY) {
            return false;
        }

        if (playerType == X_PLAYER) {
            model.setFirstMove(i, j);
        }
        GameResult result = model.gameStatus();
        if (result != GameResult.GAME_IN_PROGRESS) {
            makeGameRecord(player_name.getText(), result);
        }

        return true;
    }

    private void makeGameRecord(String playerName, GameResult result) {
       store.addRecord(GameType.SINGLE_GAME, playerName, result);
    }

    public void button00Clicked(ActionEvent actionEvent) {
        //Button button = (Button)scene.lookup("button00");
        Button but = button00;
        but.setText("X");
        if (tryToFill(0, 0)) {
            but.setText("X");
        }
    }
}
