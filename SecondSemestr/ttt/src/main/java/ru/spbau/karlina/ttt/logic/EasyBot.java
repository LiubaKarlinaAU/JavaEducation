package main.java.ru.spbau.karlina.ttt.logic;

import java.util.ArrayList;
import java.util.Random;

public class EasyBot implements BotInterface {
    @Override
    public void makeMove(Model model) {
        ArrayList<Integer> list = model.getFreeCells();
        Integer result = list.get(new Random().nextInt(list.size()));
        model.setSecondMove(result);
    }
}
