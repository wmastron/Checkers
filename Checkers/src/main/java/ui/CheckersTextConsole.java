/**
 * This class will be used as the driver for the CheckersLogic class. This aspect of the
 * game of checkers handles all user facing aspects of the game, primarily the IO.
 *
 * @author Will Mastronardi
 * @version
 *          2.0 Implemented the game working with a CPU player so Player can play against CPU
 *          1.0 Implemented game working between PvP purposes
 *
 */
package ui;
import java.util.Scanner;
import core.*;

public class CheckersTextConsole {

    private Scanner keyboard; //User input

    public CheckersTextConsole() {
        CheckersLogic game;
        keyboard = new Scanner(System.in);
        //Display rules
        displayRules();

        //Pick if PvP or PvCPU
        game = initGame();
        boolean againstCPU = game.checkForVSCPU();

        //Begin game
        Player winner;

        //PvCPU
        if(againstCPU)
            winner = playGameCPU(game);

        //PvP
        else
            winner = playGame(game); //Iterates through game until either player loses and display board after each turn

        //Display Winner
        System.out.println("Congratulations " + winner + "! You are the winner!");
    }


    /**
     * This method initizalizes the game, based on the user's menu choice
     *
     * @return the initialized game board
     */
    private CheckersLogic initGame() {
        char in;
        do {
            System.out.println("Enter 'P' if you want to play against another player; enter 'C' to play " +
                    "against the computer.");
            in = keyboard.nextLine().charAt(0);
            if(in != 'C' && in != 'P')
                System.out.println("Invalid input. Enter the character 'C' or 'P'.");
        }while(in != 'C' && in != 'P');
        return new CheckersLogic((in == 'C'));
    }

    /**
     * This method will be used as the driver for the checkers game, and is primarily to keep the
     * main method clean for easier readability and to identify flow of the program
     */
    public Player playGame(CheckersLogic game) {
        boolean continueGame = true;
        char x, xDest;
        int y, yDest;
        Player currentPlayer = game.getPlayerX();
        while(continueGame) {
            System.out.println(game + "\n\n" + currentPlayer
                    + "'s turn, pick a piece to move based on position in the board: ");
            do {
                x = getColumnPosition();
                y = getRowPosition();
                System.out.println(currentPlayer + ", pick a destination for the piece at "
                        + x + ", " + y);
                xDest = getColumnPosition();
                yDest = getRowPosition();
                if(!game.isValidMove(currentPlayer, (8-y), charToInt(x), (8-yDest), charToInt(xDest)))
                    System.out.println("Invalid Move, try again\n\n" + game);
            }while(!game.isValidMove(currentPlayer, (8-y), charToInt(x), (8-yDest), charToInt(xDest)));
            if(currentPlayer.equals(game.getPlayerX())) {
                game.makeMove(currentPlayer,  (8-y), charToInt(x), (8-yDest), charToInt(xDest), game.getPlayerO());

                currentPlayer = game.getPlayerO(); //Alternate to other player's turn
            }
            else {
                game.makeMove(currentPlayer,  (8-y), charToInt(x), (8-yDest), charToInt(xDest), game.getPlayerX());

                currentPlayer = game.getPlayerX(); //Alternate to other player's turn
            }
            if(game.checkForWin() != null){
                continueGame = false;
            }
        }
        return game.checkForWin();

    }

    /**
     * Gets user input from teh keyboard for the y coordinate
     *
     * @return the user validated row or y position
     */
    private int getRowPosition() {
        int in;
        do{
            System.out.print("Enter the row position of the piece: ");
            while (!keyboard.hasNextInt()) { //Validate the input to ensure user input an integer value
                System.out.println("That's not a valid input! Enter an integer value: ");
                keyboard.next();
            }
            in = keyboard.nextInt();
            if(in < 1 || in > 8)
                System.out.println("Invalid input, please enter a value between 1 and 8");
        }while(in < 1 || in > 8);
        System.out.println("You entered: " + in);
        return in;
    }

    /**
     * gets user input for a column or x position
     * @return the validated user given input
     */
    private char getColumnPosition() {
        char in;
        do{
            System.out.print("Enter the column position of the piece: ");
            in = keyboard.next().charAt(0);
            if(in < 'a' || in > 'h')
                System.out.println("Invalid input, please enter a value between a and h");
        }while(in < 'a' || in > 'h');
        System.out.println("You entered: " + in);
        return in;
    }

    /**
     * converts the given x coordinate char into the corresponding array index for the x value
     * @param x the column coordinate
     * @return the x coordinate as an int value
     */
    private int charToInt(char x) {
        switch (x) {
            case 'a': return 0;
            case 'b': return 1;
            case 'c': return 2;
            case 'd': return 3;
            case 'e': return 4;
            case 'f': return 5;
            case 'g': return 6;
            case 'h': return 7;
            default: return -1;
        }
    }

    /**
     * Displays the rules, separated from the main for easier readability in main
     */
    private void displayRules() {
        String rules = "This is a console version of checkers\n\nThe goal of this game is to either capture all" +
                "of your opponent's pieces. \nThere is no crowning or moving backwards in this version, so if\n" +
                "a player has no moves remaining, then the game will end. \n\n\n\n";
        System.out.println(rules);
    }

    /**
     * This method allows the game to be played between a human player and a cpu player
     *
     * @param game the initialized gameboard
     * @return the winner of the game
     */
    public Player playGameCPU(CheckersLogic game) {
        boolean continueGame = true;
        char x, xDest;
        int y, yDest;
        int playerTurn = 0;
        Point2D[] cpuMoves;
        Player player = game.getPlayerX();
        CPUPlayer cpu = game.getPlayerOCPU();
        while(continueGame) {
            if(playerTurn == 0)
                System.out.println(game + "\n\n" + player
                        + "'s turn, pick a piece to move based on position in the board: ");
            else
                System.out.println(game + "\n\nCPU's turn: ");
            //Player turn
            if(playerTurn == 0) {
                do {
                    x = getColumnPosition();
                    y = getRowPosition();
                    System.out.println("Player x, pick a destination for the piece at "
                            + x + ", " + y);
                    xDest = getColumnPosition();
                    yDest = getRowPosition();
                    if(!game.isValidMove(player, (8-y), charToInt(x), (8-yDest), charToInt(xDest)))
                        System.out.println("Invalid Move, try again\n\n" + game);
                }while(!game.isValidMove(player, (8-y), charToInt(x), (8-yDest), charToInt(xDest)));
                game.makeMove(player,  (8-y), charToInt(x), (8-yDest), charToInt(xDest), game.getPlayerO());

                playerTurn = 1; //Alternate to other player's turn
            }
            else {
                cpuMoves = game.makeMoveCPU(cpu, player);
                System.out.println("CPU will move piece from " + intToChar(cpuMoves[0].getX()) + ", " +
                        (8-cpuMoves[0].getY()) + " to " + intToChar(cpuMoves[1].getX()) + ", " +
                        (8-cpuMoves[1].getY()));
                playerTurn = 0; //Alternate to other player's turn
            }
            if(game.checkForWin() != null){
                continueGame = false;
            }
        }
        return game.checkForWin();
    }

    /**
     * converts the given x coordinate into the corresponding lower case char
     * @param x the column coordinate
     * @return the x coordinate as a char value
     */
    private char intToChar(int x) {
        char val = (char) x;
        return (char) (val + 'a');
    }

}
