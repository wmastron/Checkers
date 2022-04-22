/**
 * This class will be used to manipulate the CPU player to play a game of
 * Checkers
 *
 * @author Will Mastronardi
 * @version 1.0
 */

package core;


import java.util.Random;

public class CPUPlayer extends Player {

    private Point2D[] pieceLocations;
    final int START_PIECE_COUNT = 12;
    Random rand;

    /**
     * This method will initialize a CPUPlayer giving them 12 game pieces
     * and setting the game piece value to 12 for the Checkers game
     */
    public CPUPlayer() {
        super('o');
        pieceLocations = new Point2D[START_PIECE_COUNT];
        initPieceLoc();
        rand = new Random();
    }

    /**
     * Initializes the locations of all pieces into a Point2D Array
     */
    private void initPieceLoc() {
        int row = 0;
        int col = 1;
        for (int i = 0; i < 12; i++) {
            pieceLocations[i] = new Point2D(row, col);
            //System.out.printf("Row: %d\t\tCol: %d\n", row, col); //Debugging
            col += 2;
            if (col > 7) {
                col = 0;
                row++;
            }
            if (col > 8) {
                col = 1;
                row++;
            }
        }
    }

    /**
     * Generates a random point to be manipulated from other classes
     * @return the generated point
     */
    public Point2D getRandPoint() {
        return pieceLocations[rand.nextInt(get_pieces())];
    }

    /**
     * Removes a game piece from play
     *
     * @param x the column of piece to be removed
     * @param y the row of the piece to be removed
     */
    public void removeGamePiece(int x, int y) {
        for(int i = 0; i < get_pieces(); i++) {
            if (pieceLocations[i].getX() == x && pieceLocations[i].getY() == y) {
                pieceLocations[i].setX(-1);
                pieceLocations[i].setY(-1);
            }
        }
        super.removeGamePiece();
    }

    /**
     * @return A string representing the CPU player
     */
    @Override
    public String toString() {
        return "CPU " + get_pieces();
    }

}



