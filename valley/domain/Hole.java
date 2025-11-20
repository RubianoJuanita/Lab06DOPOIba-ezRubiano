package domain;

import java.awt.Color;

/**
 * Hole Class - Static Obstacle
 * Color : green (color.green)
 * Shape : round (Round)
 * Has fixed position, does not move
 * It is a resource/obstacle, not an animal
 * Represents a hole in the valley
 * @ Cristian Moreno - Juanita Rubiano
 */

public class Hole implements Unit {
    private Valley valley;
    private int row;
    private int column;

    /**
     * Hole constructor
     * 
     * @param row    Row where it is located
     * @param column Column where it is located
     */
    public Hole(int row, int column) {
        this.row = row;
        this.column = column;
        this.valley = valley;
    }

    /**
     * The hole does nothing on each tic-tac
     * Simply exists as an obstacle
     */
    @Override
    public void act() {
    }

    /**
     * Returns the shape of the hole (Round)
     * 
     * @return Unit.ROUND
     */
    @Override
    public int shape() {
        return Unit.ROUND;
    }

    /**
     * Returns the color of the hole (green)
     * 
     * @return Color.gray
     */
    @Override
    public Color getColor() {
        return Color.green;
    }

    /**
     * Indicates that the hole is a resource/obstacle
     * 
     * @return true
     */
    @Override
    public boolean isResource() {
        return true;
    }

    /**
     * Indicates that the hole is not an animal but cataloged as a resource
     * 
     * @return false
     */
    @Override
    public boolean isAnimal() {
        return false;
    }

    /**
     * Returns the row where the hole is located
     * 
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column where the hole is located
     * 
     * @return column
     */
    public int getColumn() {
        return column;
    }

}
