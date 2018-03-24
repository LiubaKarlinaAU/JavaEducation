package test.java.ru.spbau.karlina.ttt.logic;

import main.java.ru.spbau.karlina.ttt.logic.CellStates;
import main.java.ru.spbau.karlina.ttt.logic.GameResult;
import main.java.ru.spbau.karlina.ttt.logic.Model;

import java.util.Random;

import static org.junit.Assert.*;

public class ModelTest {
    @org.junit.Test
    public void checkInitialAllCellsType() throws Exception {
        Model model = new Model();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                assertEquals(CellStates.EMPTY, model.getCellType(i, j));
            }
        }
    }

    @org.junit.Test
    public void checkInitialGameStatus() throws Exception {
        Model model = new Model();
        assertEquals(GameResult.GAME_IN_PROGRESS, model.gameStatus());
    }

    @org.junit.Test
    public void setFirstMoveAndCheckCellType() throws Exception {
        Model model = new Model();
        int i = new Random().nextInt(3);
        int j = new Random().nextInt(3);
        model.setFirstMove(i, j);
        assertEquals(CellStates.FIRST, model.getCellType(i, j));
    }

    @org.junit.Test
    public void setSecondMoveAndCheckCellType() throws Exception {
        Model model = new Model();
        int i = new Random().nextInt(3);
        int j = new Random().nextInt(3);
        model.setSecondMove(i, j);
        assertEquals(CellStates.SECOND, model.getCellType(i, j));
    }


    @org.junit.Test
    public void setFirstMoveAndCheckIsCellEmpty() throws Exception {
        Model model = new Model();
        int i = new Random().nextInt(3);
        int j = new Random().nextInt(3);
        model.setFirstMove(i, j);
        assertFalse(model.isEmpty(i, j));
    }

    @org.junit.Test
    public void setSecondMoveAndCheckIsCellEmpty() throws Exception {
        Model model = new Model();
        int i = new Random().nextInt(3);
        int j = new Random().nextInt(3);
        model.setSecondMove(i, j);
        assertFalse(model.isEmpty(i, j));
    }


    @org.junit.Test
    public void setRandomMarkAndMakeEmpty() {
        Model model = new Model();

        for (int randomCount = 0; randomCount < 6; randomCount++) {
            int i = new Random().nextInt(3);
            int j = new Random().nextInt(3);
            model.setSecondMove(i, j);
        }

        model.makeEmpty();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                assertEquals(CellStates.EMPTY, model.getCellType(i, j));
            }
        }

    }

    @org.junit.Test
    public void threeInColumnGameStatusTest() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);
        for (int i = 0; i < 3; ++i) {
            model.setSecondMove(i, j);
        }

        assertEquals(GameResult.SECOND_WIN, model.gameStatus());
    }

    @org.junit.Test
    public void threeInRowGameStatusTest() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);
        for (int i = 0; i < 3; ++i) {
            model.setSecondMove(j, i);
        }

        assertEquals(GameResult.SECOND_WIN, model.gameStatus());
    }

    @org.junit.Test
    public void threeInDiagonalGameStatusTest() throws Exception {
        Model model = new Model();
        for (int i = 0; i < 3; ++i) {
            model.setFirstMove(i, i);
        }

        assertEquals(GameResult.FIRST_WIN, model.gameStatus());
    }

    @org.junit.Test
    public void drawGameStatusTest() throws Exception {
        Model model = new Model();

        model.setFirstMove(0, 0);
        model.setSecondMove(1, 1);
        model.setFirstMove(2, 2);
        model.setSecondMove(0, 1);
        model.setFirstMove(2, 1);
        model.setSecondMove(2, 0);
        model.setFirstMove(0, 2);
        model.setSecondMove(1, 2);
        model.setFirstMove(1, 0);

        assertEquals(GameResult.DRAW, model.gameStatus());
    }
}