package domain;

import java.awt.Color;

/**
 * Hole class - Static obstacle
 * Color: green
 * Shape: round (ROUND)
 * Has a fixed position and does not move
 * It's a resource/obstacle, not an animal
 * Represents a hole in the valley
 * Authors: Cristian Moreno - Juanita Rubiano
 */

public class Hole implements Unit {
    private Valley valley;
    private int row;
    private int column;

    /**
     * Hole constructor
     *
     * @param row    row where it is located
     * @param column column where it is located
     */
    public Hole(int row, int column) {
        this.row = row;
        this.column = column;
        this.valley = valley;
    }

    /**
     * The hole does nothing on each tick.
     * It simply exists as an obstacle.
     */
    @Override
    public void act() {
    }

    /**
     * Returns the hole's shape (round).
     *
     * @return Unit.ROUND
     */
    @Override
    public int shape() {
        return Unit.ROUND;
    }

    /**
     * Returns the hole's color (green).
     *
     * @return Color.green
     */
    @Override
    public Color getColor() {
        return Color.green;
    }

    /**
     * Indicates that the hole is a resource/obstacle.
     *
     * @return true
     */
    @Override
    public boolean isResource() {
        return true;
    }

    /**
     * Indicates that the hole is not an animal but a resource.
     *
     * @return false
     */
    @Override
    public boolean isAnimal() {
        return false;
    }

    /**
     * Returns the row where the hole is located.
     *
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column where the hole is located.
     *
     * @return column
     */
    public int getColumn() {
        return column;
    }

}
