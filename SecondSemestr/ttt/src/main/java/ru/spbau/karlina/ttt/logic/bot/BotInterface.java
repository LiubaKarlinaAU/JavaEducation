package main.java.ru.spbau.karlina.ttt.logic.bot;

import main.java.ru.spbau.karlina.ttt.logic.Model;

/**
 * This interface is representation of artificial intelligence
 * Helps playing tic-tac-toe alone
*/
public interface BotInterface {
    /**
     * Method lookup all empty cells on play greed and
     * Choose one cell to make move
     *
     * @param model - to have access to play greed
     */
    void makeMove(Model model);
}
