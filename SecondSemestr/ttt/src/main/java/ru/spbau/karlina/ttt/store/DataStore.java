package main.java.ru.spbau.karlina.ttt.store;

import main.java.ru.spbau.karlina.ttt.logic.GameResult;

import java.util.ArrayList;

public class DataStore {
    private static int gameCount = 0;
    private static int crossWins = 0;
    private static int naughtWins = 0;
    private static int drawCount = 0;
    private static int notFinished = 0;


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

    public static int getGameCount() {
        return gameCount;
    }

    public static int getCrossWins() {
        return crossWins;
    }

    public static int getNaughtWins() {
        return naughtWins;
    }

    public static int getDrawCount() {
        return drawCount;
    }

    public static int getNotFinished() {
        return notFinished;
    }
}
