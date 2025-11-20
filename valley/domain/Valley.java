package domain;

import java.io.*;
import java.util.*;

/**
 * Valley that contains a matrix of units and manages the simulation.
 *
 * Stores, creates and updates units (sheep, wolves, hay, etc.) during each
 * tick.
 *
 * Basic persistence methods (open/save/import/export) have been added as
 * placeholders for reading and writing valley state to files.
 *
 * Authors: MorenoRubiano, IbañezRubiano
 * Version: 2
 */
public class Valley implements Serializable {
    private static final long serialVersionUID = 1L;
    static private int SIZE = 25;
    private Unit[][] places;

    /**
     * Creates a new valley with a fixed size.
     * Initializes all positions as empty and adds some default units.
     */
    public Valley() {
        places = new Unit[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someUnits();
    }

    /**
     * Returns the size of the valley.
     *
     * @return valley size
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Returns the unit located at a specific position.
     *
     * @param r row
     * @param c column
     * @return the unit at the given position or null if empty
     */
    public Unit getUnit(int r, int c) {
        return places[r][c];
    }

    /**
     * Sets a unit at a specific position in the valley.
     *
     * @param r row
     * @param c column
     * @param e unit to place
     */
    public void setUnit(int r, int c, Unit e) {
        places[r][c] = e;
    }

    /**
     * Creates and places some initial units (wolves, sheep, hay and holes)
     * in predefined positions in the valley.
     */
    public void someUnits() {
        Wolf akela = new Wolf(this, 10, 10);
        Wolf larka = new Wolf(this, 15, 15);

        Sheep chaun = new Sheep(this, 1, 5);
        Sheep woolly = new Sheep(this, 13, 10);

        Hay alarm = new Hay(this, 0, 0);
        Hay alert = new Hay(this, 0, SIZE - 1);
        places[0][0] = alarm;
        places[0][SIZE - 1] = alert;

        setUnit(10, 10, akela);
        setUnit(15, 15, larka);

        Human moreno = new Human(this, 15, 5);
        Human rubiano = new Human(this, 18, 20);

        // Hole Moreno = new Hole(this, 9, 9);
        // Hole Rubiano = new Hole(this, 18, 18);

        // places[9][9] = Moreno;
        // places[18][18] = Rubiano;
    }

    /**
     * Calculates how many neighboring units are of the same type as the one
     * located at the given position.
     *
     * @param r row
     * @param c column
     * @return number of equal neighbors
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inValley(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inValley(r + dr, c + dc) &&
                            (places[r + dr][c + dc] != null)
                            && (places[r][c].getClass() == places[r + dr][c + dc].getClass()))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Checks whether a position is empty inside the valley.
     *
     * @param r row
     * @param c column
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(int r, int c) {
        return (inValley(r, c) && places[r][c] == null);
    }

    /**
     * Checks whether a position is inside the valley bounds.
     *
     * @param r row
     * @param c column
     * @return true if the position is valid
     */
    private boolean inValley(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Runs one simulation cycle (tick) for all units in the valley.
     * Each unit performs its action by calling its act() method.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Unit unit = places[r][c];
                if (unit != null) {
                    unit.act();
                }
            }
        }
    }

    /**
     * Abre un valle desde un archivo.
     * 
     * @param file archivo desde donde se abrirá el valle
     * @throws ValleyException si la operación no está implementada
     */
    public void open(File file) throws ValleyException {
        throw new ValleyException("Opción open en construcción. Archivo " + file.getName());
    }

    /**
     * Guarda el valle actual en un archivo.
     * 
     * @param file archivo donde se guardará el valle
     * @throws ValleyException si la operación no está implementada
     */
    public void save(File file) throws ValleyException {
        throw new ValleyException("Opción save en construcción. Archivo " + file.getName());
    }

    /**
     * Imports a valley from a file.
     *
     * @param file file to import the valley from
     * @throws ValleyException if the operation is not implemented
     */
    public void importValley(File file) throws ValleyException {
        throw new ValleyException("Import option under construction. File: " + file.getName());
    }

    /**
     * Exports the current valley to a file.
     *
     * @param file file where the valley will be exported
     * @throws ValleyException if the operation is not implemented
     */
    public void export(File file) throws ValleyException {
        throw new ValleyException("Export option under construction. File: " + file.getName());
    }
}