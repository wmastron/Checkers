/**
 * This is a basic 2-Dimensional point class, used to represent a position on a grid
 *
 * @author Will Mastronardi
 * @version 1.0
 */

package core;

public class Point2D{
    private char owner;
    private int x;
    private int y;

    public Point2D(int _x, int _y) {
        x = _x;
        y = _y;
    }

    /**
     * This is different than the other constructor as it allows an owner to be associated with
     * a given piece
     * @param _x
     * @param _y
     * @param _owner
     */
    public Point2D(int _x, int _y, char _owner) {
        x = _x;
        y = _y;
        owner = _owner;
    }

    /**
     * Returns the x value
     * @return x position in a grid
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value
     * @return y position in a grid
     */
    public int getY() {
        return y;
    }

    /**
     * changes the x value in a grid
     * @param x the value to be changed
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * changes the y value in a grid
     * @param y the value to be changed
     */
    public void setY(int y) {
        this.y = y;
    }
}
