package ru.spbau.karlina.task2;

import java.util.Arrays;
import java.util.Comparator;

/** Stored inverted two-dimensional array with odd count of rows and columns */
public class SpiralArray {
    private int[][] matrix;

    /** Save iverted matrix */
    public SpiralArray(int[][] newMatrix) {
        int n = newMatrix.length;
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                matrix[i][j] = newMatrix[j][i];
        }
    }

    public void simpleOutput() {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.print('\n');
        }
    }

    public void spiralOutput() {
        int n = matrix.length;
        int i = n / 2, j = n / 2;
        int tmpI, tmpJ, range = 2;
        System.out.print(matrix[i][j] + " ");

        while (range < n) {
            i--;
            j--;

            for (tmpJ = j; tmpJ < range + j; tmpJ++) {
                System.out.print(matrix[i][tmpJ] + " ");
            }

            for (tmpI = i; tmpI < range + i; tmpI++) {
                System.out.print(matrix[tmpI][tmpJ] + " ");
            }

            for (; tmpJ > j; tmpJ--) {
                System.out.print(matrix[tmpI][tmpJ] + " ");
            }

            for (; tmpI > i; tmpI--) {
                System.out.print(matrix[tmpI][tmpJ] + " ");
            }

            range += 2;
        }
    }

    public void sortedByColum() {
        Arrays.sort(matrix, Comparator.comparingInt(col->col[0]));
    }
}