package ru.spbau.karlina.ttt.logic.bot;

import ru.spbau.karlina.ttt.logic.Model;
import ru.spbau.karlina.ttt.logic.bot.BotInterface;

import java.util.ArrayList;
import java.util.Random;

import static ru.spbau.karlina.ttt.logic.CellStates.EMPTY;

/**
 * This class is representation of artificial intelligence
 * Helps playing tic-tac-toe alone
 * Easy one - his move is random
 */
public class EasyBot implements BotInterface {
    /**
     * Method lookup all empty cells on play greed and
     * choose random one to fill it
     *
     * @param model - to have access to play cells
     */
    @Override
    public void makeMove(Model model) {
        ArrayList<Integer> list = getFreeCells(model);
        Integer result = list.get(new Random().nextInt(list.size()));
        model.setSecondMove(result / 3, result % 3);
    }

    private ArrayList<Integer> getFreeCells(Model model) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                if (model.getCellType(i, j) == EMPTY) {
                    list.add(toOneValue(i, j));
                }
            }

        return list;
    }

    private Integer toOneValue(int i, int j) {
        return i * 3 + j;
    }
}
