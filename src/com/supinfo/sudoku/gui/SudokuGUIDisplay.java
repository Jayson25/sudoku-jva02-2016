/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Jayson/Laélien/Gaston/Kévin
 */
public class SudokuGUIDisplay extends JButton implements ActionListener {

    //We put those variables static, so that we can modify the class without creating
    //a new object. It is important for grid verification
    public static boolean playerWin = false;
    public static JLabel isWinner = new JLabel("", SwingConstants.CENTER);

    public JFrame frame = new JFrame("Sudoku");
    //Principal 
    public JPanel principalPanel = new JPanel(new BorderLayout());

    private GridBagConstraints constraint = new GridBagConstraints();

    //Upper elements parts
    private JButton generate = new JButton("Generate");

    private String[] selectDificult = {"Easy", "Medium", "Hard"};
    private JComboBox difficulty = new JComboBox(selectDificult);

    //Grid part
    private GridButton grid = new GridButton("Easy");

    //Our subPanels
    private JPanel generatePanel = new JPanel();
    private JPanel gridPanel;

    public SudokuGUIDisplay() {
        //We will set a Border layout because it is sufficient 
        //to organize grid and generate components
        this.frame.setLayout(new BorderLayout());

        //Set size of window
        this.frame.setSize(600, 600);
        //Allows to have the ability to close the window (very useful)
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Add a trigger of action to the generate grid button
        this.generate.addActionListener(this);
        //Had our generate components into the generate panel
        this.generatePanel.add(difficulty);
        this.generatePanel.add(generate);

        //Generate a grid of JButtons
        this.NewGrid();

        //Is is used for spacing between gridbag components
        this.constraint.insets = new Insets(0, 0, 0, 0);

        //Set size of button
        this.generate.setSize(30, 10);

        //add the principal components
        this.frame.add(SudokuGUIDisplay.isWinner, BorderLayout.NORTH);
        this.frame.add(this.principalPanel, BorderLayout.CENTER);

        //add sub panel inside the principal panel
        this.principalPanel.add(this.generatePanel, BorderLayout.NORTH);
        this.principalPanel.add(this.gridPanel, BorderLayout.CENTER);

        //display components on screen
        this.frame.setVisible(true);
    }

    private void NewGrid() {
        this.gridPanel = new JPanel();

        gridPanel.setLayout(new GridBagLayout());

        this.constraint.insets = new Insets(0, 0, 0, 0);

        //this loop is used for spacing between squares
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                int spX = 0, spY = 0;

                if (j % 3 == 0) {
                    spY = 15;
                }
                if (i % 3 == 0) {
                    spX = 15;
                }

                this.constraint.insets.set(spY, spX, 0, 0);

                this.constraint.gridx = i;
                this.constraint.gridy = j;

                gridPanel.add(this.grid.grid[i][j], this.constraint);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //False to make the buttons playable
        SudokuGUIDisplay.playerWin = false;

        this.grid = new GridButton((String) this.difficulty.getSelectedItem());
        this.principalPanel.remove(this.gridPanel);

        SudokuGUIDisplay.isWinner.setText("");

        this.NewGrid();
        System.gc();
        this.principalPanel.add(this.gridPanel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }
}
