package connectfour_xianhaozhou;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/* Program Name: Connect Four
 *
 * Purpose: This program will allow two users to play a game of connect four in the form of a 
 * graphical user interface (GUI). Player's 1 (red) and 2 (yellow) will take turns placing their chips
 * on to the board by clicking on the button above the column they want to place it in. Their chip 
 * will then fall to the lowest unoccupied slot in that column. A player wins when they get four or 
 * more of their pieces in a row horizontally, vertically, or diagonally. Then a message will pop
 * up saying they won. Users will also be able to find the instructions, or about page by clicking
 * on the buttons at the bottom of the GUI. Also at the bottom of the GUI there is a button to 
 * reset the board to play again.
 *
 * Author: Xianhao Zhou
 *
 * Start Date: March 7, 2019  
 *
 * End Date: April 15, 2019
 */
//Note: If running on JCreator build file first before running.
public class ConnectFour_XianhaoZhou implements ActionListener {

    JFrame frame = new JFrame("Connect Four");

    JFrame titleFrame = new JFrame("Connect Four");

    JButton continueButton = new JButton("Continue");

    JButton instructionsButton = new JButton("Instructions");

    JButton resetButton = new JButton("Reset");

    JButton aboutButton = new JButton("About");

    JButton columnArray[] = new JButton[7];

    JLabel boardArray[][] = new JLabel[6][7];

    ImageIcon blankPiece = null, redPiece = null, yellowPiece = null;

    JLabel blankLabel, redLabel, yellowLabel;

    int currentTurn = 0;

    public static void main(String[] args) {

        new ConnectFour_XianhaoZhou();

    }

    ConnectFour_XianhaoZhou() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //main panel and 3 panels that will go on it
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel columnButtons = new JPanel(new FlowLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JPanel boardPanel = new JPanel(new GridLayout(6, 7));

        //add buttons to button panel then to panel to main panel
        instructionsButton.addActionListener(this);
        resetButton.addActionListener(this);
        aboutButton.addActionListener(this);

        buttonPanel.add(instructionsButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(aboutButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //get images
        BufferedImage titleImage = null;
        try {
            redPiece = new ImageIcon(this.getClass().getResource("RedPiece.jpg"));
            yellowPiece = new ImageIcon(this.getClass().getResource("YellowPiece.jpg"));
            blankPiece = new ImageIcon(this.getClass().getResource("BlankPiece.jpg"));

            titleImage = ImageIO.read(this.getClass().getResource("TitleScreen.jpg"));

        } catch (IOException ex) {
            Logger.getLogger(ConnectFour_XianhaoZhou.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        //convert to JLabel
        redLabel = new JLabel(redPiece);
        yellowLabel = new JLabel(yellowPiece);
        blankLabel = new JLabel(blankPiece);
        JLabel titleLabel = new JLabel(new ImageIcon(titleImage));

        //create board of using array of Jlabels
        for (int row = 0; row < 6; row++) {

            for (int col = 0; col < 7; col++) {

                boardArray[row][col] = new JLabel(blankPiece);
                boardPanel.add(boardArray[row][col]);

            }

        }

        //add board to panel
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        //create JButtons to select which column using array
        for (int i = 0; i < 7; i++) {

            int num = i + 1;

            columnArray[i] = new JButton("      " + num + "      ");

            columnButtons.add(columnArray[i]);

            columnArray[i].addActionListener(this);

        }

        //add to main panel
        mainPanel.add(columnButtons, BorderLayout.NORTH);

        //set up main frame
        frame.setContentPane(mainPanel);

        frame.setSize(605, 600);

        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        frame.setVisible(false);

        //set up title frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        titlePanel.add(titleLabel, BorderLayout.CENTER);

        continueButton.addActionListener(this);

        titlePanel.add(continueButton, BorderLayout.SOUTH);

        titleFrame.setContentPane(titlePanel);

        titleFrame.setSize(605, 600);

        titleFrame.setResizable(false);

        titleFrame.setLocationRelativeTo(null);

        titleFrame.setVisible(true);
        //595 x 510 board size
    }

    boolean placePiece = true;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == continueButton) {
            titleFrame.setVisible(false);
            frame.setVisible(true);
        }
        if (e.getSource() == instructionsButton) {
            String instructions = "";
            try {
                BufferedReader infile = new BufferedReader(new InputStreamReader(ConnectFour_XianhaoZhou.class.getResourceAsStream("instructions.txt")));
                for (int i = 0; i < 11; i++) {
                    instructions += infile.readLine();
                    instructions += "\n";
                }
                infile.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectFour_XianhaoZhou.class.getName()).log(Level.SEVERE, null, ex);
                instructions = "Error";
            }
            JOptionPane.showMessageDialog(null, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == resetButton) {
            currentTurn = 0;
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {

                    boardArray[row][col].setIcon(blankPiece);
                }
            }
            placePiece = true;
        }
        if (e.getSource() == aboutButton) {
            String aboutInfo = "";
            try {
                BufferedReader infile = new BufferedReader(new InputStreamReader(ConnectFour_XianhaoZhou.class.getResourceAsStream("aboutInfo.txt")));
                for (int i = 0; i < 11; i++) {
                    aboutInfo += infile.readLine();
                    aboutInfo += "\n";
                }
                infile.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectFour_XianhaoZhou.class.getName()).log(Level.SEVERE, null, ex);
                aboutInfo = "Error";
            }
            JOptionPane.showMessageDialog(null, aboutInfo, "About", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == columnArray[0]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][0].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][0].setIcon(redPiece);
                            if (checkWinnerRed(j, 0)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][0].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 0)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;//stops the loop
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[1]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][1].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][1].setIcon(redPiece);
                            if (checkWinnerRed(j, 1)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][1].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 1)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[2]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][2].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][2].setIcon(redPiece);
                            if (checkWinnerRed(j, 2)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][2].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 2)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[3]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][3].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][3].setIcon(redPiece);
                            if (checkWinnerRed(j, 3)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][3].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 3)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[4]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][4].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][4].setIcon(redPiece);
                            if (checkWinnerRed(j, 4)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][4].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 4)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[5]) {
            if (placePiece) {
                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][5].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][5].setIcon(redPiece);
                            if (checkWinnerRed(j, 5)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][5].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 5)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1;
                    }
                } //for loop
            }
        }
        if (e.getSource() == columnArray[6]) {
            if (placePiece) {

                for (int j = boardArray.length - 1; j >= 0; j--) {
                    if (boardArray[j][6].getIcon().equals(blankLabel.getIcon())) {
                        if (currentTurn == 0) {
                            boardArray[j][6].setIcon(redPiece);
                            if (checkWinnerRed(j, 6)) {
                                JOptionPane.showMessageDialog(null, "Player 1 (RED) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 1;
                        } else if (currentTurn == 1) {
                            boardArray[j][6].setIcon(yellowPiece);
                            if (checkWinnerYellow(j, 6)) {
                                JOptionPane.showMessageDialog(null, "Player 2 (YELLOW) has won!\nPlease reset to play again.", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                                placePiece = false;
                            }
                            currentTurn = 0;
                        }
                        j = -1; //stops the loop
                    }
                } //for loop
            }
        }
        //System.out.println("Button clicked");
    }

    private boolean checkWinnerRed(int currentRow, int currentCol) {
        //horizontal win
        for (int row = 0; row < 6; row++) {
            int counter = 0;
            for (int col = 0; col < 7; col++) {
                if (boardArray[row][col].getIcon().equals(redLabel.getIcon())) {
                    counter++;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        //verticle win
        for (int col = 0; col < 7; col++) {
            int counter = 0;
            for (int row = 0; row < 6; row++) {
                if (boardArray[row][col].getIcon().equals(redLabel.getIcon())) {
                    counter++;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        //check for diagonal wins from the most recent piece placed
        //digagonal wins top left to bottom right
        int counter = 0;
        for (int i = currentRow, j = currentCol; i < boardArray.length && j < boardArray[0].length; i++, j++) {
            if (boardArray[i][j].getIcon().equals(redLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        counter--; //accounts for for current slot being counted twice
        for (int i = currentRow, j = currentCol; i >= 0 && j >= 0; i--, j--) {
            if (boardArray[i][j].getIcon().equals(redLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        if (counter >= 4) {
            return true;
        }
        //diagonal wins top right to bottom left
        counter = 0;
        for (int i = currentRow, j = currentCol; i < boardArray.length && j >= 0; i++, j--) {
            if (boardArray[i][j].getIcon().equals(redLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        counter--;
        for (int i = currentRow, j = currentCol; i >= 0 && j < boardArray[0].length; i--, j++) {
            if (boardArray[i][j].getIcon().equals(redLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        if (counter >= 4) {
            return true;
        }
        return false;
    }

    private boolean checkWinnerYellow(int currentRow, int currentCol) {
        //horizontal win
        for (int row = 0; row < 6; row++) {
            int counter = 0;
            for (int col = 0; col < 7; col++) {
                if (boardArray[row][col].getIcon().equals(yellowLabel.getIcon())) {
                    counter++;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        //verticle win
        for (int col = 0; col < 7; col++) {
            int counter = 0;
            for (int row = 0; row < 6; row++) {
                if (boardArray[row][col].getIcon().equals(yellowLabel.getIcon())) {
                    counter++;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        //check for diagonal wins in all four directions from the current placed piece
        int counter = 0;
        for (int i = currentRow, j = currentCol; i < boardArray.length && j < boardArray[0].length; i++, j++) {
            if (boardArray[i][j].getIcon().equals(yellowLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        counter--;
        for (int i = currentRow, j = currentCol; i >= 0 && j >= 0; i--, j--) {
            if (boardArray[i][j].getIcon().equals(yellowLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        if (counter >= 4) {
            return true;
        }
        //other direction diagonal
        counter = 0;
        for (int i = currentRow, j = currentCol; i < boardArray.length && j >= 0; i++, j--) {
            if (boardArray[i][j].getIcon().equals(yellowLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        counter--;
        for (int i = currentRow, j = currentCol; i >= 0 && j < boardArray[0].length; i--, j++) {
            if (boardArray[i][j].getIcon().equals(yellowLabel.getIcon())) {
                counter++;
            } else {
                break;
            }
        }
        if (counter >= 4) {
            return true;
        }
        return false;
    }

}
