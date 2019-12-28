/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.sudoku.engine;

import java.util.Random;

/**
 *
 * @author Jayson/Laélien/Gaston/Kévin
 */
public class Grid {

    /**
     *
     * @param difficulty
     * @return int[][]
     */
    public int[][] grid;
    public int[][] puzzle;
    public Random rand = new Random();
    
    public Grid(String difficulty){
        //Init new grid
        this.grid = new int[9][9];
        GenerateGrid(difficulty);
    }

    private void GenerateGrid(String difficulty) {
        //test variables
        int[] iValue = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] cValue;
        int[] temp;
        int random;
        int fValue;
        int isGood;
        //Count the number of iterations
        int iteration = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //init test for validity of cell
                isGood = 0;

                //Cell values (this is our track)
                cValue = iValue;

                //Validation loop of cell
                do {
                    //We choose a random word
                    random = rand.nextInt(cValue.length);

                    fValue = cValue[random];

                    isGood = Comparator.compareTable(grid, fValue, i, j);

                    //If the test fails
                    if (isGood == 0 || fValue == grid[i][j]) {
                        //We will remove the fail value from the track
                        temp = new int[cValue.length - 1];
                        int index = 0;
                        for (int k = 0; k < cValue.length; k++) {
                            if (cValue[k] != fValue) {
                                temp[index++] = cValue[k];
                            }
                        }
                        cValue = temp;
                    } //If success
                    else {
                        grid[i][j] = fValue;
                    }
                    iteration++;
                    //If we are stuck
                    if (iteration > 1300){
                        this.grid = new int[9][9];
                        GenerateGrid(difficulty);
                    }
                    
                } while (cValue.length > 0 && isGood == 0);

                //if fails + cell track empty
                if (cValue.length == 0 && isGood == 0) {
                    if (j > 0) {
                        j = 7;
                        i--;
                    } else {
                        j -= 2;
                    }
                }
            }
        }
        System.gc();
        CreatePuzzle(difficulty);
    }

    private void CreatePuzzle(String difficulty) {
        //declare var containing number of random cells not empty
        int randCells = 0;

        //init value of randCells depending on the difficulty
        switch (difficulty) {
            case "Easy":
                randCells = 35;
                break;
            case "Medium":
                randCells = 30;
                break;
            case "Hard":
                randCells = 25;
                break;
        }

        //Init new grid
        puzzle = new int[9][9];
        int x, y;
        
        //Remplir le puzzle jusqu'à ce que l'on obtienne le nombre de cases 
        //remplie désiré
        do {
            x = rand.nextInt(9);
            y = rand.nextInt(9);

            if (puzzle[x][y] == 0) {
                puzzle[x][y] = grid[x][y];
                randCells--;
            }
        } while (randCells > 0);
    }
}
