package main.java.ru.spbau.karlina.ttt.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.ru.spbau.karlina.ttt.logic.*;
import main.java.ru.spbau.karlina.ttt.logic.bot.BotInterface;
import main.java.ru.spbau.karlina.ttt.logic.bot.EasyBot;
import main.java.ru.spbau.karlina.ttt.logic.bot.HardBot;
import main.java.ru.spbau.karlina.ttt.store.DataStore;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.java.ru.spbau.karlina.ttt.store.PlayerType.*;

/**
 * Controls one player version of tic-tac-toe game.
 * Handle one_player_game.fxml scene events.
 */
public class OnePlayerGameController implements Initializable {
    private static Model model;
    private static BotInterface bot;
    private static DataStore store;
    private static Button[][] buttons = new Button[3][3];

    @FXML
    private RadioButton levelButton;
    @FXML
    private Text gameResult;

    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button02;
    @FXML
    private Button button10;
    @FXML
    private Button button20;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button21;
    @FXML
    private Button button22;

    /**
     * Set dataStore, model and bot.
     *
     * @param dataStore - to be set.
     */
    public static void settings(DataStore dataStore) {
        store = dataStore;
        model = new Model();
        model.makeEmpty();
        bot = new EasyBot();
    }

    /**
     * Override method initialize buttons array.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            for (int j = 0; j < 3; ++j) {
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnAction(new EventHandler<>() {
                    private final int x = finalI;
                    private final int y = finalJ;

                    @Override
                    public void handle(ActionEvent event) {
                        if (isEmpty(x, y)) {
                            model.setFirstMove(x,y);
                            drawCells();
                            if (model.gameStatus() != GameResult.GAME_IN_PROGRESS) {
                                runNewGame();
                            } else {
                                bot.makeMove(model);
                                drawCells();
                                if (model.gameStatus() != GameResult.GAME_IN_PROGRESS) {
                                    runNewGame();
                                }
                            }
                        }
                    }
                });
            }
    }

    /**
     * Run main menu scene after saving current game status.
     *
     * @throws IOException if there is problem with loading main_menu.fxml file.
     */
    @FXML
    private void backToMainMenu() throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/main/resources/main_menu.fxml"));
        Stage primaryStage = MainController.getPrimaryStage();
        Scene scene = new Scene(layout,
                primaryStage.getScene().getWidth(),
                primaryStage.getScene().getHeight());
        primaryStage.setScene(scene);
    }


    /** Saves current game result and make settings for new one. */
    @FXML
    private void runNewGame() {
        GameResult result = model.gameStatus();

        if (gameStarted()) {
            gameResult.setText(TwoPlayerGameController.makeNotification(result));
            makeGameRecord(result);
        }model.makeEmpty();
        drawCells();
    }

    /** Execute changing between easy and hard bot */
    @FXML
    private void changeLevel() {
        if (levelButton.isSelected()) {
            bot = new HardBot();
        } else {
            bot = new EasyBot();
        }
    }

    /** Draw model greed on scene buttons. */
    private void drawCells() {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                switch (model.getCellType(i, j)) {
                    case FIRST:
                        buttons[i][j].setText("X");
                        break;
                    case SECOND:
                        buttons[i][j].setText("O");
                        break;
                    case EMPTY:
                        buttons[i][j].setText("");
                        break;
                }

            }
    }

    /**
     * Checking is on the play greed filled cell
     *
     * @return true - if there is filled cel and false otherwise
     */
    private boolean gameStarted() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (model.getCellType(i, j) != CellStates.EMPTY)
                    return true;
            }
        }

        return false;
    }

    /**Checking is cell with given coordinate empty.
     * @param i - first coordinate.
     * @param j - second coordinate. */
    private boolean isEmpty(int i, int j) {
        return model.getCellType(i, j) == CellStates.EMPTY;
    }

    /** Save game result. */
    private void makeGameRecord(GameResult result) {
        store.addRecord(result);
    }
}
