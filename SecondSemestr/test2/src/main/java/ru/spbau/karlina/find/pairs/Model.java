package ru.spbau.karlina.find.pairs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Model {
    private Cell[][] greed;
    private int size;

    public Model(int n) {
        size = n;
        greed = new Cell[n][n];
        initializeGreed();
    }

    private void initializeGreed() {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int count = size * size / 2;
        int i = 0, j = 0;
        for (; i < size && count > 0; ++i) {
            for (; j < size && count > 0; ++j) {
                int currentNumber = random.nextInt(size * size / 2 + 1) * 2;
                greed[i][j] = new Cell(currentNumber);
                list.add(currentNumber);
                count--;
            }
        }

        Collections.shuffle(list);
        Iterator<Integer> iter = list.iterator();
        for (; i < size; ++i) {
            for (; j < size; ++j) {
                greed[i][j] = new Cell(iter.next());
            }
        }

    }

    /**
     * Getter function
     *
     * @return field size
     */

    public int getSize(){
        return size;
    }
}