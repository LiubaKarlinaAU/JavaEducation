package ru.spbau.karlina.find.pairs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CellTest {
    @Test
    public void getNumber() throws Exception {
        Cell cell = new Cell(4);
        assertEquals(4, cell.getNumber());
    }

    @Test
    public void setOpenState() throws Exception {
        Cell cell = new Cell(4);
        cell.setState(CellStates.OPEN);
        assertEquals(CellStates.OPEN, cell.getState());
    }

    @Test
    public void setHiddenState() throws Exception {
        Cell cell = new Cell(4);
        cell.setState(CellStates.HIDDEN);
        assertEquals(CellStates.HIDDEN, cell.getState());
    }

    @Test
    public void setChoosenState() throws Exception {
        Cell cell = new Cell(4);
        cell.setState(CellStates.CHOOSEN);
        assertEquals(CellStates.CHOOSEN, cell.getState());
    }

}