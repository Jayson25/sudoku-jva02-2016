/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.sudoku.engine;

/**
 *
 * @author Jayson/Laélien/Gaston/Kévin
 */
public class Comparator {

    /**
     *
     * @param table
     * @param newValue
     * @param x
     * @param y
     * @return integer
     */
    public static int compareTable(int[][] table, int newValue, int x, int y) {
        //Initiate counters in order to test in what zone is the new value
        int subX = x;
        int subY = y;

        //zone numbers
        int zoneX = 0, zoneY = 0;

        //counter for testing if the grid is full
        int countEmpty = 0;

        //Loop for finding the int number of the zone(x and y)
        do {
            if (subX >= 3) {
                subX -= 3;
                zoneX++;
            }

            if (subY >= 3) {
                subY -= 3;
                zoneY++;
            }
        } while (subX >= 3 || subY >= 3);

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                //if it is not the cell of the player
                if (!(x == i && y == j)) {
                    //if the new value is in the same line/col as the testing cell
                    if (x == i || y == j) {
                        if (table[i][j] == newValue && table[i][j] != 0) {
                            return 0;
                        }
                    }

                    //if the new value is in the same zone as the testing cell
                    if ((i >= (zoneX * 3)) && (i < (zoneX + 1) * 3) && (j >= zoneY * 3) && (j < (zoneY + 1) * 3)) {
                        if (table[i][j] == newValue && table[i][j] != 0) {
                            return 0;
                        }
                    }

                    //if there is an empty cell
                    if (table[i][j] == 0) {
                        countEmpty++;
                    }
                }
            }
        }

        //if cell complete and no error
        if (countEmpty == 0) {
            return 2;
        }

        //if cell not complete and no error
        return 1;
    }
}
