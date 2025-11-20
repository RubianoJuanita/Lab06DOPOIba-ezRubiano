package domain;

import java.util.*;

/**
 * Valle que contiene una matriz de unidades y gestiona la simulación.
 *
 * Guarda, crea y actualiza las unidades (ovejas, lobos, heno, etc.) durante
 * cada tic.
 *
 * @author MorenoRubiano
 * @version 1.0
 */
public class Valley {
    static private int SIZE = 25;
    private Unit[][] places;

    /**
     * Crea un nuevo valle con un tamaño fijo.
     * Inicializa todas las posiciones vacías y agrega algunas unidades por defecto.
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
     * Retorna el tamaño del valle.
     * 
     * @return tamaño del valle
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Retorna la unidad ubicada en una posición específica.
     * 
     * @param r fila
     * @param c columna
     * @return unidad en la posición indicada o null si está vacía
     */
    public Unit getUnit(int r, int c) {
        return places[r][c];
    }

    /**
     * Asigna una unidad a una posición específica del valle.
     * 
     * @param r fila
     * @param c columna
     * @param e unidad a colocar
     */
    public void setUnit(int r, int c, Unit e) {
        places[r][c] = e;
    }

    /**
     * Crea y ubica algunas unidades iniciales (lobos, ovejas, heno y hoyos)
     * en posiciones predefinidas del valle.
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
     * Calcula cuántas unidades vecinas son del mismo tipo que la ubicada en la
     * posición dada.
     * 
     * @param r fila
     * @param c columna
     * @return número de vecinos iguales
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
     * Verifica si una posición está vacía dentro del valle.
     * 
     * @param r fila
     * @param c columna
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty(int r, int c) {
        return (inValley(r, c) && places[r][c] == null);
    }

    /**
     * Verifica si una posición está dentro de los límites del valle.
     * 
     * @param r fila
     * @param c columna
     * @return true si la posición es válida
     */
    private boolean inValley(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Ejecuta un ciclo de simulación (tic-tac) para todas las unidades del valle.
     * Cada unidad realiza su acción correspondiente llamando a su método act().
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
}
