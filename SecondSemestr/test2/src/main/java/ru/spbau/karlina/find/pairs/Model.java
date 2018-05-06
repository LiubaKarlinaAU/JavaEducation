package ru.spbau.karlina.find.pairs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import org.reactfx.*;


public class Model {
    private Cell[][] greed;
    private int size;
    private boolean isChoosen = false;
    private int choosenX, choosenY;
    private Button choosenButton;
    private int foundPairs = 0;

    public Model(int n) {
        size = n;
        greed = new Cell[n][n];
        initializeGreed();
    }

    private void initializeGreed() {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int count = size * size / 2;
        while (count-- > 0) {
            int newNumber = random.nextInt(size * size / 2 + 1) * 2;
            list.add(newNumber);
            list.add(newNumber);
        }

        Collections.shuffle(list);
        Iterator<Integer> iter = list.iterator();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                greed[i][j] = new Cell(iter.next());
            }
        }

    }

    /**
     * Getter function
     *
     * @return field size
     */

    public int getSize() {
        return size;
    }

    /**
     * Function that handle button click
     *
     * @param i      - button first coordinate
     * @param j      - button second coordinate
     * @param button
     */
    public void clicked(int i, int j, Button button) {
        button.setText(String.valueOf(greed[i][j].getNumber()));
        if (isChoosen) {
            if (greed[i][j].getState() != CellStates.HIDDEN) {
                return;
            } else {
                if (isEqual(i, j)) {
                    greed[i][j].setState(CellStates.OPEN);
                    greed[choosenX][choosenY].setState(CellStates.OPEN);
                    foundPairs++;
                } else {
                    Timeline timeline = new Timeline(new KeyFrame(
                            javafx.util.Duration.millis(1000), ae -> {
                            button.setText("*");
                            choosenButton.setText("*");
                            greed[i][j].setState(CellStates.HIDDEN);
                            greed[choosenX][choosenY].setState(CellStates.HIDDEN);
                        }));
                    timeline.play();
                    greed[i][j].setState(CellStates.CHOOSEN);
                    button.fire();
                }
                isChoosen = false;
            }
        } else {
            isChoosen = true;
            choosenX = i;
            choosenY = j;
            choosenButton = button;
            greed[i][j].setState(CellStates.CHOOSEN);
        }
    }

    /**
     * Define current game state
     *
     * @return state of the game
     */
    public GameStates getGameState() {
        if (foundPairs * 2 == size * size) {
            return GameStates.WIN;
        }

        return GameStates.RUNNING;
    }

    private boolean isEqual(int i, int j) {
        return greed[i][j].getNumber() == greed[choosenX][choosenY].getNumber();
    }
}