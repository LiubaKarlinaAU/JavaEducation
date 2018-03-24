package test.java.ru.spbau.karlina.ttt.logic.bot;

import main.java.ru.spbau.karlina.ttt.logic.CellStates;
import main.java.ru.spbau.karlina.ttt.logic.GameResult;
import main.java.ru.spbau.karlina.ttt.logic.Model;
import main.java.ru.spbau.karlina.ttt.logic.bot.BotInterface;
import main.java.ru.spbau.karlina.ttt.logic.bot.HardBot;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class HardBotTest {
    @Test
    public void makeMoveOnEmptyGreed() throws Exception {
        Model model = new Model();

        BotInterface bot = new HardBot();
        bot.makeMove(model);

        int filledCellCount = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (model.getCellType(i, j) != CellStates.EMPTY) {
                    filledCellCount++;
                }
            }
        }

        assertEquals(1, filledCellCount);
    }

    @Test
    public void makeMoveOnAlmostFilledGreed() throws Exception {
        Model model = new Model();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                model.setFirstMove(i, j);
            }
        }

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertFalse(model.isEmpty(0, 2) && model.isEmpty(1, 2) && model.isEmpty(2, 2));
    }

    @Test
    public void makeCertainMoveInARowNotToLose() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);

        for (int i = 0; i < 2; ++i) {
                model.setFirstMove(j, i);
        }

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(CellStates.SECOND, model.getCellType(j,2));
    }

    @Test
    public void makeCertainMoveInAColumnNotToLose() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);

        for (int i = 1; i < 3; ++i) {
            model.setFirstMove(i, j);
        }

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(CellStates.SECOND, model.getCellType(0,j));
    }

    @Test
    public void makeCertainMoveInADiagonalNotToLose() throws Exception {
        Model model = new Model();

        model.setFirstMove(0, 0);
        model.setFirstMove(2,2);

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(CellStates.SECOND, model.getCellType(1,1));
    }


    @Test
    public void makeCertainMoveInARowToWin() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);

        for (int i = 0; i < 2; ++i) {
            model.setSecondMove(j, i);
        }

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(CellStates.SECOND, model.getCellType(j,2));
        assertEquals(GameResult.SECOND_WIN, model.gameStatus());
    }

    @Test
    public void makeCertainMoveInAColumnToWin() throws Exception {
        Model model = new Model();
        int j = new Random().nextInt(3);

        for (int i = 1; i < 3; ++i) {
            model.setSecondMove(i, j);
        }

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(GameResult.SECOND_WIN, model.gameStatus());
        assertEquals(CellStates.SECOND, model.getCellType(0,j));

    }

    @Test
    public void makeCertainMoveInADiagonalToWin() throws Exception {
        Model model = new Model();

        model.setSecondMove(0, 2);
        model.setSecondMove(2,0);

        BotInterface bot = new HardBot();

        bot.makeMove(model);

        assertEquals(CellStates.SECOND, model.getCellType(1,1));
        assertEquals(GameResult.SECOND_WIN, model.gameStatus());
    }

}