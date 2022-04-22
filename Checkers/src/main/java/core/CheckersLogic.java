
/**
 * This class will be used to play the game checkers. It will be used throughout the com.example.checkers.ui.CheckersTextConsole
 * Class to handle any of the operations the users may experience in a game of checkers based through the
 * console.
 *
 *
 * @author Will Mastronardi
 * @version 1.0 Implemented the display and initialization
 *              Display will be used throughout to test the code and ensure it is working properly
 *              Implemented all methods for game logic defined by the instructions.
 */

package core;

import java.util.Random;

public class CheckersLogic {

    private char [][] gameBoard;
    private Player playerX;
    private Player playerO;

    /**
     * This method will create and initialize the game board as a 2D array of chars
     * as well as initialize the players.
     *
     * @param playAgainstCPU given the argument, determines if there will be 2 person users
     *                       or 1 person and 1 CPU user
     */
    public CheckersLogic(boolean playAgainstCPU) {
        gameBoard = new char[8][8];
        char currentPiece = 'o';
        for(int rows = 0; rows < 8; rows++) {
            int col = 0;
            if(rows % 2 == 0)
                col = 1;
            if(rows < 3) { // check for 'o' Player
                for (; col < 8; col = col + 2) {
                    gameBoard[rows][col] = currentPiece;
                }
            }
            if(rows > 4) {
                currentPiece = 'x';
                for (; col < 8; col = col + 2) {
                    gameBoard[rows][col] = currentPiece;
                }
            }
        }
        currentPiece = '_';
        for(int rows = 0; rows < 8; rows++) {
            for(int col = 0; col < 8; col++) {
                if(gameBoard[rows][col] != 'o' && gameBoard[rows][col] != 'x')
                    gameBoard[rows][col] = currentPiece;
            }
        }
//        toString();
        if(playAgainstCPU) {
            playerO = new CPUPlayer();
        }
        else {
            playerO = new Player('o');
        }
        playerX = new Player('x');
    }

    /**
     * The toString method will be used to allow a display of the game board from the
     * com.example.checkers.ui.CheckersTextConsole Class
     *
     * @return A String that reflects the current state of the game board
     */
    public String toString() {
        String gbDisplay = "";
        for(int rows = 0; rows < 8; rows++) {
            gbDisplay += (8 - rows) + " ";
            for (int col = 0; col < 8; col++) {
                gbDisplay += "| " + gameBoard[rows][col] + " ";
                if(col == 7) {
                    gbDisplay += "|\n";
                }
            }
        }
        gbDisplay += "    a   b   c   d   e   f   g   h";
        return gbDisplay;
    }


    /**
     * This method checks if the player making a move can legally make that move
     * following the rules outlined by the checkers game rules
     *
     * The logic used in this is if the player is the 'o' piece player the game board will
     * check if the column is +- 1 from the current column and the row is +1 from the current row, without a skip
     * and opposite for the 'x' piece player. If the row or column is outside the scope of the
     * game board array, then it will return false.
     *
     * This method is public to ensure the current player can move the piece to
     * a valid location in the text console
     *
     * @param current the current Player, used to check for the game piece
     * @param row the row that the piece they want to move
     * @param col the column of the piece they want to move
     * @param destRow the row where the piece will move to
     * @param destCol the column where the piece will move to
     * @return if the move is valid
     */
    public boolean isValidMove(Player current, int row, int col, int destRow, int destCol) {
        if(current.getGamePiece() != gameBoard[row][col])
            return false; //Make sure player is attempting to move their own piece
        if(destCol < 0 || destCol > 7 || destRow < 0 || destRow > 7 || row < 0 || row > 7
            || col < 0 || col > 7) //Ensure the choice is within the range of the gameboard
            return false;
        if(!checkForSkip(current, row, col, destRow,destCol)) {
            char currGamePiece = current.getGamePiece(); //Set a local variable to the current players game piece
            if (col == destCol) //The game logic doesn't allow for a situation where a piece can move to a position in the same column
                return false;
            int rowDiff = destRow - row;
            if (currGamePiece == 'o' && Math.abs(rowDiff) != 1) //Makes sure the o piece only moves forward on the board
                return false;
            else if (currGamePiece == 'x' && Math.abs(rowDiff) != 1) //Makes sure the x piece only moves forward on the board
                return false;
            int colDiff = destCol - col;
            if (Math.abs(colDiff) != 1 && Math.abs(rowDiff) != 1) //Makes sure the  column has a difference of absolute value of 1, if not it is not a valid move
                return false;
            if(gameBoard[destRow][destCol] != '_') //Make sure the space is empty
                return false;
        }
        else if(checkForSkip(current, row, col, destRow, destCol)){
            char currGamePiece = current.getGamePiece(); //Set a local variable to the current players game piece
            if (col == destCol) //The game logic doesn't allow for a situation where a piece can move to a position in the same column
                return false;
            int rowDiff = destRow - row;
            if (currGamePiece == 'o' && Math.abs(rowDiff) != 2) //Makes sure the o piece only moves forward on the board
                return false;
            else if (currGamePiece == 'x' && Math.abs(rowDiff) != 2) //Makes sure the x piece only moves forward on the board
                return false;
            int colDiff = destCol - col;
            if (Math.abs(colDiff) != 2 && Math.abs(rowDiff) != 2) //Makes sure the  column has a difference of absolute value of 1, if not it is not a valid move
                return false;
            if(gameBoard[destRow][destCol] != '_') //Make sure the space is empty
                return false;

        }
        return true; //If it fails all other checks it is a valid move
    }

    /**
     * Checks if the other players game piece can be skipped.
     *
     * @param current the current Player, used to check for the gamepiece
     * @param row the row that the piece they want to move
     * @param col the column of the piece they want to move
     * @param destRow the row where the piece will move to
     * @param destCol the column where the piece will move to
     * @return checks if there is a skip over an opponent's piece
     */
    private boolean checkForSkip(Player current, int row, int col, int destRow, int destCol) {
        int tempCol = (destCol + col) / 2; //Opponents game piece x location
        int tempRow = (destRow + row) / 2; //Where the opponents game piece y location
        if(row < 0 || row > 7 || col < 0 || col > 7 || destRow > 7 || destRow < 0
                || destCol < 0 || destCol > 7 || tempCol < 0 || tempCol > 7 || tempRow < 0 || tempRow > 7)
            return false;
        char otherPlayer;
        if(current.getGamePiece() == 'o') //Get the opponent's game piece
            otherPlayer = 'x';
        else
            otherPlayer = 'o';
        if(gameBoard[tempRow][tempCol] == otherPlayer) //Ensure there is an opponent piece at the location
            return true;
        return false;
    }


    /**
     * This method changes the array to reflect the valid move made by the player.
     * In using this method, it will verify that the move is valid before updating the game board.
     *
     * This could be redundant in the logic pf the CheckersTextCosole, due to the algorithm
     * from the com.example.checkers.ui checking for a valid move already, however this just guarantees the program
     * can run smoothly isolated from the isValidMove method.
     *
     * @param current the current Player, used to check for the gamepiece
     * @param row the row that the piece they want to move
     * @param col the column of the piece they want to move
     * @param destRow the row where the piece will move to
     * @param destCol the column where the piece will move to
     */
    public void makeMove(Player current, int row, int col, int destRow, int destCol, Player opponent) {
        if(isValidMove(current, row,  col, destRow, destCol)) { //Ensures valid move prior to altering game board state
            gameBoard[destRow][destCol] = gameBoard[row][col]; //Move the gamepiece to the new location
            gameBoard[row][col] = '_'; //Replace where current player's game piece was with empty space
            if(checkForSkip(current, row, col, destRow, destCol)) { //Check if a player jumped over the opponent's piece
                int tempCol = (destCol + col) / 2; //Opponents game piece x location
                int tempRow = (destRow + row) / 2; //Where the opponents game piece y location
                gameBoard[tempRow][tempCol] = '_'; //Insert an empty space into the board where opponent's game piece was
                opponent.removeGamePiece(); //Remove a game piece from the other player
            }
        }
    }


    /**
     * This method is the getter for the player with the game piece 'o'.
     *
     * @return the current state of the player with the 'o' game piece
     */
    public Player getPlayerO() {
        return playerO;
    }

    /**
     * This method is the getter for the player with the game piece 'o'.
     * This method is only used for PvCPU games
     *
     * @return the current state of the player with the 'o' game piece
     */
    public CPUPlayer getPlayerOCPU() {
        return (CPUPlayer) playerO;
    }

    /**
     * This method is the getter for the player with the game piece 'x'.
     *
     * @return the current state of the player with the 'x' game piece
     */
    public Player getPlayerX() {
        return playerX;
    }

    /**
     *  This method will be used to check for any wins by absence of player pieces
     *
     * @return  if the method returns null then both players still have pieces, otherwise
     *          the method returns the player with 0 pieces remaining
     */
    private Player checkPieceWin() {
        if(playerO.get_pieces() == 0)
            return playerO;
        if(playerX.get_pieces() == 0)
            return playerX;
        return null;
    }

    /**
     * This method will get information on a player who may have won
     *
     * @return the player that won, if neither player won then returns null
     */
    public Player checkForWin() {
        Player temp = checkPieceWin();
        if(temp != null)
            return temp;
        temp = checkForRemainingMoves();
        if(temp != null)
            return temp;
        return null;
    }

    /**
     * This method checks the gameboard for all possible moves for a given piece. It iterates throughout the
     * entire gameboard and checks each spot for a gamepiece. After getting the char at a position it goes through a
     * series of checks to determine if a piece can move. If there are no moves remaining then it will return a player
     * with no moves remaining, however if there are still possible moves for both players then it will return null
     *
     * @return the player with no moves left, if null both players still have moves remaining
     */
    Player checkForRemainingMoves() {
        int x = playerX.get_pieces(); //Get amount of pieces remaining for the players
        int o = playerO.get_pieces();
        for(int row = 0; row < 7; row++) { //Traverse through the game board and check for all possible moves remaining
            for(int col = 0; col < 7; col++) {
                if(gameBoard[row][col] == 'x') {
                    if(!isValidMove(playerX, row, col, (row - 1), (col + 1)) && //Checks for possible moves for the player '
                            !isValidMove(playerX, row, col, (row - 1), (col - 1)))
                        x--;
                }
                else if(gameBoard[row][col] == 'o') {
                    if(!isValidMove(playerO, row, col, (row + 1), (col + 1)) &&
                            !isValidMove(playerO, row, col, (row + 1), (col - 1)))
                        o--;
                }
                if('x' == 0)
                    return playerX;
                if('o' == 0)
                    return playerO;
            }
        }
        return null;
    }


    /**
     * Randomly generates a move for the CPU to execute, goes through validation to ensure the move is possible
     *
     * @param cpu the computer player
     * @param player the human player
     * @return the 2 points, the index 0 is the original point, and index 1 is the destination for a piece
     */
    public Point2D[] makeMoveCPU(CPUPlayer cpu, Player player) {
        int validMove = 0;
        int row, col, destRow, destColLeft, destColRight, destRowSkip, rand,
                destColLeftSkip, destColRightSkip, destCol;
        Random rgen = new Random();
        Point2D curr;
        do {
            curr = cpu.getRandPoint();
            row = curr.getY();
            col = curr.getX();

            //Initializes all values to possible
            if (row < 7)
                destRow = row + 1;
            else
                destRow = -1;
            if (row < 6)
                destRowSkip = row + 2;
            else
                destRowSkip = -1;
            if (col > 0)
                destColLeft = col - 1;
            else
                destColLeft = -1;
            if (col < 7)
                destColRight = col + 1;
            else
                destColRight = -1;
            if (col > 1)
                destColLeftSkip = col - 2;
            else
                destColLeftSkip = -1;
            if (col < 6)
                destColRightSkip = col + 2;
            else
                destColRightSkip = -1;

            if (isValidMove(cpu, row, col, destRow, destColLeft) &&
                    isValidMove(cpu, row, col, destRow, destColRight))
                validMove = 1;
            else if (isValidMove(cpu, row, col, destRow, destColLeft))
                validMove = 2;
            else if (isValidMove(cpu, row, col, destRow, destColRight))
                validMove = 3;
            if (isValidMove(cpu, row, col, destRowSkip, destColLeftSkip) &&
                    isValidMove(cpu, row, col, destRowSkip, destColRightSkip))
                validMove = 4;
            else if (isValidMove(cpu, row, col, destRowSkip, destColLeftSkip))
                validMove = 5;
            else if (isValidMove(cpu, row, col, destRowSkip, destColRightSkip))
                validMove = 6;

        } while (validMove == 0);
        switch (validMove) {
            case 1:
                rand = rgen.nextInt(2);
                if (rand == 1) {
                    makeMove(cpu, row, col, destRow, destColRight, player);
                    destCol = destColRight;
                }
                else {
                    makeMove(cpu, row, col, destRow, destColLeft, player);
                    destCol = destColLeft;
                }
                break;
            case 2:
                makeMove(cpu, row, col, destRow, destColLeft, player);
                destCol = destColLeft;
                break;
            case 3:
                makeMove(cpu, row, col, destRow, destColRight, player);
                destCol = destColRight;
                break;
            case 4:
                rand = rgen.nextInt(2);
                if (rand == 1) {
                    makeMove(cpu, row, col, destRowSkip, destColRightSkip, player);
                    destCol = destColRight;
                }
                else {
                    makeMove(cpu, row, col, destRowSkip, destColLeftSkip, player);
                    destCol = destColLeftSkip;
                }
                destRow = destRowSkip;
                break;
            case 5:
                makeMove(cpu, row, col, destRowSkip, destColLeftSkip, player);
                destRow = destRowSkip;
                destCol = destColLeftSkip;
                break;
            case 6:
                makeMove(cpu, row, col, destRowSkip, destColRightSkip, player);
                destRow = destRowSkip;
                destCol = destColRightSkip;
                break;
            default:
                destCol = -1;
        }
        Point2D[] moves = new Point2D[2];
        moves[0] = new Point2D(col, row);
        moves[1] = new Point2D(destCol, destRow);
        return moves;
    }

    public boolean checkForVSCPU() {
        return playerO instanceof CPUPlayer;
    }
}

/*
array uses 'o' going upwards in rows but displays them moving downwards and vice versa for 'x'
 */