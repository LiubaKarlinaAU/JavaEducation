package main.java.ru.spbau.karlina.ttt.logic;


import java.util.ArrayList;

import static main.java.ru.spbau.karlina.ttt.logic.CellStates.*;

public class Model {

    /** All cells initially free*/
    private CellStates[][] greed = new CellStates[3][3];

    public Model() {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j){
                greed[i][j] = EMPTY;
            }
    }

    public ArrayList<Integer> getFreeCells() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j){
               if (greed[i][j] == EMPTY) {
                  list.add(toOneValue(i,j));
               }
        }

        return list;
    }

    public CellStates getCellType(int i, int j) {
        return greed[i][j];
    }

    public void setSecondMove(Integer cell) {
        int i = cell / 3;
        int j = cell % 3;
        greed[i][j] = SECOND;
    }

    public void setSecondMove(int i, int j) {
        greed[i][j] = SECOND;
    }

    public void setFirstMove(Integer cell) {
        int i = cell / 3;
        int j = cell % 3;
        greed[i][j] = FIRST;
    }

    private Integer toOneValue(int i, int j) {
        return i * 3 + j;
    }
}
