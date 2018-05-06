package ru.spbau.karlina.find.pairs;

/**
 * Representation of cell in game field
 */
public class Cell {
    private CellStates state;
    private int number;

    /**
     * Constructor with default setting state and certain setting number
     *
     * @param number - to be set
     */
    public Cell(int number) {
        this.number = number;
        state = CellStates.HIDDEN;
    }

    /**
     * Getter function
     *
     * @return cell state
     */
    public CellStates getState() {
        return state;
    }

    /**
     * Getter function
     *
     * @return cell number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set new cell state
     *
     * @param newState - to be set
     */
    public void setState(CellStates newState) {
        state = newState;
    }
}
