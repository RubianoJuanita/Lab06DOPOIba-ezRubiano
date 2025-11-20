package domain;

import java.awt.Color;

/**
 * Hay (bundle of hay) resource.
 *
 * Characteristics:
 * - Shape: square (SQUARE)
 * - It's a resource (not an animal)
 * - Changes color at each tick (each time the button is pressed)
 * - Color alternates between red and yellow (red → yellow → red ...)
 * - Authors: Cristian Moreno / Juanita Rubiano
 */
public class Hay implements Unit {

    private Valley valley;
    private Color color;
    private int row;
    private int column;

    /**
     * Constructor for a hay bundle.
     *
     * @param row    row where it is located
     * @param column column where it is located
     * @param valley valley it belongs to (optional for tests)
     */
    public Hay(int row, int column) {
        this(null, row, column);
    }

    public Hay(Valley valley, int row, int column) {
        this.valley = valley;
        this.row = row;
        this.column = column;
        this.color = Color.red; // inicia en rojo
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
