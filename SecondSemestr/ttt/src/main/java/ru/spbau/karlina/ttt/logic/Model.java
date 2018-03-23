package main.java.ru.spbau.karlina.ttt.logic;

import static main.java.ru.spbau.karlina.ttt.logic.CellStates.*;
import static main.java.ru.spbau.karlina.ttt.logic.GameResult.*;

public class Model {

    private CellStates[][] greed = new CellStates[3][3];

    /**
     * Greed cells setts out with CellStates.EMPTY
     */
    public void makeEmpty() {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                greed[i][j] = EMPTY;
            }
    }

    /**
     * Get type of cell with given coordinate
     *
     * @param i - first coordinate
     * @param j - second coordinate
     */
    public CellStates getCellType(int i, int j) {
        return greed[i][j];
    }

    /**
     * Fill cell with given coordinate SECOND value
     *
     * @param i - first coordinate
     * @param j - second coordinate
     */
    public void setSecondMove(int i, int j) {
        greed[i][j] = SECOND;
    }

    /**
     * Fill cell with given coordinate FIRST value
     *
     * @param i - first coordinate
     * @param j - second coordinate
     */
    public void setFirstMove(int i, int j) {
        greed[i][j] = FIRST;
    }

    /**
     * Check is game complete
     *
     * @return result of current game
     * Possible return value: FIRST_WIN, SECOND_WIN, DRAW, GAME_IN_PROGRESS
     */
    public GameResult gameStatus() {
        GameResult result = checkRows();
        if (result != GAME_IN_PROGRESS) {
            return result;
        }

        result = checkColumns();
        if (result != GAME_IN_PROGRESS) {
            return result;
        }

        result = checkDiagonals();
        if (result != GAME_IN_PROGRESS) {
            return result;
        }

        if (countEmptyCells() != 9) {
            return GAME_IN_PROGRESS;
        }

        return DRAW;
    }

    private int countEmptyCells() {
        int count = 0;

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (greed[i][j] == EMPTY) {
                    count++;
                }

        return count;
    }

    private GameResult checkRows() {
        for (int i = 0; i < 3; ++i) {
            if (greed[i][0] == greed[i][1] && greed[i][0] == greed[i][2]) {
                if (greed[i][0] == FIRST)
                    return FIRST_WIN;
                else if (greed[i][0] == SECOND)
                    return SECOND_WIN;
            }
        }

        return GAME_IN_PROGRESS;
    }

    private GameResult checkColumns() {
        for (int i = 0; i < 3; ++i) {
            if (greed[0][i] == greed[1][i] && greed[0][i] == greed[2][i]) {
                if (greed[0][i] == FIRST)
                    return FIRST_WIN;
                else if (greed[0][i] == SECOND)
                    return SECOND_WIN;
            }
        }

        return GAME_IN_PROGRESS;
    }

    private GameResult checkDiagonals() {
        if (greed[0][0] == greed[1][1] && greed[1][1] == greed[2][2]) {
            if (greed[1][1] == FIRST)
                return FIRST_WIN;
            else if (greed[1][1] == SECOND)
                return SECOND_WIN;
        }

        if (greed[2][0] == greed[1][1] && greed[1][1] == greed[0][2]) {
            if (greed[1][1] == FIRST)
                return FIRST_WIN;
            else if (greed[1][1] == SECOND)
                return SECOND_WIN;
        }

        return GAME_IN_PROGRESS;
    }

}
