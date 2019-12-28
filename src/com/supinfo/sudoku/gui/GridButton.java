/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.sudoku.gui;

import com.supinfo.sudoku.engine.Comparator;
import com.supinfo.sudoku.engine.Grid;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Jayson/Laélien/Gaston/Kévin
 */
public class GridButton extends JButton implements ActionListener {

    //Attributes for our grid
    public JButton[][] grid = new JButton[9][9];
    private int[][] randGrid;
    private int[][] playerGrid;
    private Grid gridMaker;

    //Constructor
    public GridButton(String difficulty) {
        //init the grid Maker and put the grids in proper attributes (puzzle + answer)
        this.gridMaker = new Grid(difficulty);
        this.randGrid = this.gridMaker.grid;
        this.playerGrid = this.gridMaker.puzzle;

        //Creation of the buttons size 50x50 
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //If button is hint
                if (this.playerGrid[i][j] != 0) {
                    this.grid[i][j] = new JButton(String.valueOf(this.playerGrid[i][j]));
                } else {
                    this.grid[i][j] = new JButton();
                    this.grid[i][j].addActionListener(this);
                    this.grid[i][j].setForeground(Color.blue);
                }

                this.grid[i][j].setPreferredSize(new Dimension(50, 50));
            }
        }
    }

    //Test if the grid is properly filled
    public boolean Winner() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //s'il y a une case vide
                if (this.playerGrid[i][j] == 0){
                    return false;
                }
                //si le test a échoué
                else if (Comparator.compareTable(this.randGrid, this.playerGrid[i][j], i, j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //if the game is not finished
        if (!SudokuGUIDisplay.playerWin) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    //If the button index corresponds to index i and j
                    if (grid[i][j] == ae.getSource()) {
                        //Show input dialog
                        String answer = JOptionPane.showInputDialog(grid[i][j], "Enter number (1-9)", null);
                        //try and catch for maximum input security
                        try {
                            int parser = Integer.parseInt(answer);
                            if (parser < 1 || parser > 9) {
                                throw new Exception();
                            }
                            this.playerGrid[i][j] = parser;
                            this.grid[i][j].setText(answer);
                        } catch (Exception e) {
                            this.playerGrid[i][j] = 0;
                            this.grid[i][j].setText("");
                        }
                    }
                }
            }
            //If the grid is properly filled
            if (Winner()) {
                SudokuGUIDisplay.isWinner.setText("YOU WIN");
                SudokuGUIDisplay.playerWin = true;
            }
        }
    }
}
