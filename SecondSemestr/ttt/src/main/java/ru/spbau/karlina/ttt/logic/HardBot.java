package main.java.ru.spbau.karlina.ttt.logic;

/**
 * This class is representation of artificial intelligence
 * Helps playing tic-tac-toe alone
 * Hard one - his move is based on greed situation
 */
public class HardBot implements BotInterface{
    static private class Cell {
        static int i = 0;
        static int j = 0;
    }

    /**
     * Method lookup all empty cells on play greed and
     * Choose one cell to make move after analysed greed situation
     *
     * @param model - to have access to play greed
     */
    @Override
    public void makeMove(Model model) {
        if (checkingRowForTwoZero(model)){
            return;
        }

        if (checkingColumnForTwoZero(model)){
            return;
        }

        if (checkingRowForTwoCross(model)){
            return;
        }

        if (checkingColumnForTwoCross(model)){
            return;
        }

        if (model.getCellType(1, 1) == CellStates.EMPTY) {
            model.setSecondMove(1,1);
            return;
        }

        if (checkingAngles(model)){
            return;
        }

        markAnyEmptyCell(model);
    }

    private void markAnyEmptyCell(Model model) {
    }

    private boolean checkingAngles(Model model) {
       for (int i = 0; i < 3; i += 2) {
           for (int j = 0; j < 3; j += 2) {
               if (model.getCellType(i, j) == CellStates.EMPTY) {
                   model.setSecondMove(i,j);
                   return true;
               }
           }
       }
       return false;
    }

    private boolean checkingColumnForTwoZero(Model model) {
       return false;
    }

    private boolean checkingRowForTwoZero(Model model) {
        return false;
    }


    private boolean checkingColumnForTwoCross(Model model) {
        return false;
    }

    private boolean checkingRowForTwoCross(Model model) {
        return false;
    }
}
