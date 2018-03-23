package main.java.ru.spbau.karlina.ttt.logic.bot;

import main.java.ru.spbau.karlina.ttt.logic.CellStates;
import main.java.ru.spbau.karlina.ttt.logic.Model;
import main.java.ru.spbau.karlina.ttt.logic.bot.BotInterface;

import static main.java.ru.spbau.karlina.ttt.logic.CellStates.FIRST;
import static main.java.ru.spbau.karlina.ttt.logic.CellStates.SECOND;

/**
 * This class is representation of artificial intelligence
 * Helps playing tic-tac-toe alone
 * Hard one - his move is based on greed situation
 */
public class HardBot implements BotInterface {

    /**
     * Method lookup all empty cells on play greed and
     * Choose one cell to make move after analysed greed situation
     *
     * @param model - to have access to play greed
     */
    @Override
    public void makeMove(Model model) {
        if (checkingRowForTwoZero(model)) {
            return;
        }

        if (checkingColumnForTwoZero(model)) {
            return;
        }

        if (checkingDiagonalsForTwoZero(model)) {
            return;
        }


        if (checkingRowForTwoCross(model)) {
            return;
        }

        if (checkingColumnForTwoCross(model)) {
            return;
        }


        if (checkingDiagonalsForTwoCross(model)) {
            return;
        }

        if (model.getCellType(1, 1) == CellStates.EMPTY) {
            model.setSecondMove(1, 1);
            return;
        }

        if (checkingAngles(model)) {
            return;
        }

        markAnyEmptyCell(model);
    }

    /**
     * Checks play greed for two cross in one diagonal - dangerous to lose.
     *
     * @param model - to take information about greed.
     * @return true - if found dangerous and make move.
     * false - otherwise
     */
    private boolean checkingDiagonalsForTwoCross(Model model) {
        switch (model.getCellType(1, 1)) {
            case EMPTY:
                if ((model.getCellType(0, 0) == FIRST && model.getCellType(2, 2) == FIRST) ||
                        (model.getCellType(0, 2) == FIRST && model.getCellType(2, 0) == FIRST)) {
                    model.setSecondMove(1, 1);
                    return true;
                }
                break;
            case FIRST:
                for (int i = 0; i < 3; i += 2)
                    for (int j = 0; j < 3; j += 2) {
                        if (model.getCellType(2 - i, 2 - j) == FIRST && model.getCellType(i, j) == CellStates.EMPTY) {
                            model.setSecondMove(i, j);
                            return true;
                        }
                    }
                break;
            /**Not dangerous found*/
            case SECOND:
                break;
        }

        return false;
    }

    /**
     * Checks play greed for two zero in one diagonal to win game
     *
     * @param model - to take information about greed
     * @return true - if found move to win
     * false - otherwise
     */
    private boolean checkingDiagonalsForTwoZero(Model model) {
        switch (model.getCellType(1, 1)) {
            case EMPTY:
                if ((model.getCellType(0, 0) == SECOND && model.getCellType(2, 2) == SECOND) ||
                        (model.getCellType(0, 2) == SECOND && model.getCellType(2, 0) == SECOND)) {
                    model.setSecondMove(1, 1);
                    return true;
                }
                break;
            /**Impossible to win using diagonals */
            case FIRST:
                break;
            case SECOND:
                for (int i = 0; i < 3; i += 2)
                    for (int j = 0; j < 3; j += 2) {
                        if (model.getCellType(2 - i, 2 - j) == SECOND && model.getCellType(i, j) == CellStates.EMPTY) {
                            model.setSecondMove(i, j);
                            return true;
                        }
                    }

        }

        return false;
    }

    /**
     * Find first empty cell and set move there
     *
     * @param model - to take play greed information
     */
    private void markAnyEmptyCell(Model model) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (model.getCellType(i, j) == CellStates.EMPTY) {
                    model.setSecondMove(i, j);
                    return;
                }
            }
        }
    }

    /**
     * Checking is there any empty angles to set move there
     *
     * @param model - to take play greed information
     */
    private boolean checkingAngles(Model model) {
        for (int i = 0; i < 3; i += 2) {
            for (int j = 0; j < 3; j += 2) {
                if (model.getCellType(i, j) == CellStates.EMPTY) {
                    model.setSecondMove(i, j);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for two zero in one column to win game.
     *
     * @param model - to take information about greed.
     * @return true - if found move to win and
     *         false - otherwise.
     */
    private boolean checkingColumnForTwoZero(Model model) {
        return false;
    }

    /**
     * Checks for two zero in one row to win game.
     *
     * @param model - to take information about greed.
     * @return true - if found move to win and
     *         false - otherwise.
     */
    private boolean checkingRowForTwoZero(Model model) {
        return false;
    }

    /**
     * Checks play greed for two cross in one column - dangerous to lose.
     *
     * @param model - to take information about greed.
     * @return true - if found dangerous and make move.
     *         false - otherwise.
     */
    private boolean checkingColumnForTwoCross(Model model) {
        return false;
    }

    /**
     * Checks play greed for two cross in one row - dangerous to lose.
     *
     * @param model - to take information about greed.
     * @return true - if found dangerous and make move.
     * false - otherwise
     */
    private boolean checkingRowForTwoCross(Model model) {
        return false;
    }
}
