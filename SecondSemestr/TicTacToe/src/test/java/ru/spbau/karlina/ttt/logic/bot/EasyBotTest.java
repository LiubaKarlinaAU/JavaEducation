package ru.spbau.karlina.ttt.logic.bot;

import ru.spbau.karlina.ttt.logic.CellStates;
import ru.spbau.karlina.ttt.logic.Model;
import org.junit.Test;

import static org.junit.Assert.*;

public class EasyBotTest {
    @Test
    public void makeMoveOnEmptyGreed() throws Exception {
        Model model = new Model();

        BotInterface easyBot = new EasyBot();
        easyBot.makeMove(model);

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

        BotInterface easyBot = new EasyBot();

        easyBot.makeMove(model);

        assertFalse(model.isEmpty(0, 2) && model.isEmpty(1, 2) && model.isEmpty(2, 2));
    }

}