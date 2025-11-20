package domain;

import java.awt.Color;

/**
 * Class characteristics
 * - Shape: Square (SQUARE)
 * - It is a resource, therefore not an animal
 * - Changes color on each tic-tac (each time the button is pressed).
 * - Color: alternates between red and yellow (red → yellow → red → yellow →
 * red)
 * - Authors: Cristian Moreno / Juanita Rubiano
 */
public class Hay implements Unit {

    private Valley valley;
    private Color color;
    private int row;
    private int column;

    /**
     * Hay package constructor
     * 
     * @param row    Row where it is located
     * @param column Column where it is located
     * @param valley Valley to which it belongs (optional for tests)
     */
    public Hay(int row, int column) {
        this(null, row, column);
    }

    public Hay(Valley valley, int row, int column) {
        this.valley = valley;
        this.row = row;
        this.column = column;
        this.color = Color.red; // starts in red
        if (valley != null) {
            valley.setUnit(row, column, this);
        }
    }

    @Override
    public void act() {
        if (color.equals(Color.red)) {
            color = Color.yellow;
        } else {
            color = Color.red;
        }
    }

    @Override
    public int shape() {
        return Unit.SQUARE;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isResource() {
        return true;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
