package ru.spbau.karlina.ttt.store;

import ru.spbau.karlina.ttt.logic.GameResult;

import java.util.ArrayList;

/**
 * Class represent a store for game result recording
 */
public class DataStore {
    private int gameCount = 0;
    private int crossWins = 0;
    private int naughtWins = 0;
    private int drawCount = 0;
    private int notFinished = 0;

    /**
     * Add a record about given game result
     *
     * @param result - to take information for recording
     */
    public void addRecord(GameResult result) {
        gameCount++;

        switch (result) {
            case FIRST_WIN:
                crossWins++;
                break;
            case SECOND_WIN:
                naughtWins++;
                break;
            case DRAW:
                drawCount++;
                break;
            case GAME_IN_PROGRESS:
                notFinished++;
                break;
        }
    }

    /**
     * Game count getter.
     *
     * @return count of played games.
     */
    public int getGameCount() {
        return gameCount;
    }

    /**
     * Cross wins game count getter.
     *
     * @return count of cross wins games.
     */
    public int getCrossWins() {
        return crossWins;
    }

    /**
     * Naught wins game count getter.
     *
     * @return count of naught wins games.
     */
    public int getNaughtWins() {
        return naughtWins;
    }

    /**
     * Draw game count getter.
     *
     * @return count of played draw games.
     */
    public int getDrawCount() {
        return drawCount;
    }

    /**
     * Game not finished count getter.
     *
     * @return count of not finished games.
     */
    public int getNotFinished() {
        return notFinished;
    }
}
