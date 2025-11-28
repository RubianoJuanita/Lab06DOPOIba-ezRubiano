package domain;

import java.awt.Color;

import java.io.Serializable;

public class Hay implements Unit, Serializable {
    private static final long serialVersionUID = 1L;

    private Valley valley;
    private Color color;
    private int row;
    private int column;

    /**
     * Constructor del paquete de heno
     * 
     * @param row    Fila donde se ubica
     * @param column Columna donde se ubica
     * @param valley Valle al que pertenece (opcional para tests)
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
