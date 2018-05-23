package ru.spbau.karlina.find.pairs;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {
    @Test
    public void getSizeAndGameState1() throws Exception {
        Model model = new Model(8);
        assertEquals(8, model.getSize());
        assertEquals(GameStates.RUNNING, model.getGameState());
    }

    @Test
    public void getSizeAndGameState2() throws Exception {
        Model model = new Model(0);
        assertEquals(0, model.getSize());
        assertEquals(GameStates.WIN, model.getGameState());
    }

}