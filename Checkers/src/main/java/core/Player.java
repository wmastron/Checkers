/**
 * @author William Mastronardi
 *
 * @version 1.0
 *
 * This is a Player class used for the players in a game of checkers. This class
 * will be used to store any and all data associated with a checkers player.
 *
 */
package core;

public class Player {

    private char _gamePiece;
    private int _pieces;

    /**
     * This method will initialize a Player giving them 12 game pieces
     * and setting the game piece value to 12 for the Checkers game
     *
     * @param gamePiece
     */
    public Player(char gamePiece) {
        _pieces = 12;
        _gamePiece = gamePiece;
    }


    /**
     * This method will be used in the checkers logic file to gather the players piece, to
     * ensure the player cannot move a different player's piece
     *
     * @return the char value associated with the player ('o' or 'x')
     */
    public char getGamePiece() {
        return _gamePiece;
    }

    /**
     * This method will be used when a player loses a game piece during a move in the checkers logic
     * class
     */
    public void removeGamePiece() {
        _pieces--;
    }

    public int get_pieces() {
        return _pieces;
    }

    public String toString() {
        return "Player " + _gamePiece;
    }

    public boolean equals(Object other) {
        if(other.getClass() != this.getClass())
            return false;
        else {
            Player o = (Player) other;
            return (o.getGamePiece() == getGamePiece());
        }
    }
}
