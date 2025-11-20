package domain;

import java.awt.Color;

/**
 * Clase Hole - Obstaculo Estático
 * Color : verde (color.green)
 * forma : redonda(Round)
 * Tiene posicion fija no se mueve
 * Es un recurso/obstaculo, no es un animal
 * Representa un hueco en el valle
 * @ Cristian Moreno - Juanita Rubiano
 */

public class Hole implements Unit {
    private Valley valley;
    private int row;
    private int column;

    /**
     * Constructor del hole
     * 
     * @param row    Fila donde se ubica
     * @param column Columna donde se ubica
     */
    public Hole(int row, int column) {
        this.row = row;
        this.column = column;
        this.valley = valley;
    }

    /**
     * El hole no hace nada en cada tic-tac
     * Simplemente existe como obstáculo
     */
    @Override
    public void act() {
    }

    /**
     * Retorna la forma de la del hole ( Redonda )
     * 
     * @return Unit.ROUND
     */
    @Override
    public int shape() {
        return Unit.ROUND;
    }

    /**
     * Retorna el color del hole verde
     * 
     * @return Color.gray
     */
    @Override
    public Color getColor() {
        return Color.green;
    }

    /**
     * Indica que el hueco es un recurso/obstáculo
     * 
     * @return true
     */
    @Override
    public boolean isResource() {
        return true;
    }

    /**
     * Indica que el hole no es un animal sino catalogado como recurso
     * 
     * @return false
     */
    @Override
    public boolean isAnimal() {
        return false;
    }

    /**
     * Retorna la fila donde está el hueco
     * 
     * @return fila
     */
    public int getRow() {
        return row;
    }

    /**
     * Retorna la columna donde está el hueco
     * 
     * @return columna
     */
    public int getColumn() {
        return column;
    }

}
