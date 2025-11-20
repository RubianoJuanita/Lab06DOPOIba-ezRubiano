package domain;

import java.awt.Color;

/**
 * Oveja del valle.
 *
 * Comportamiento básico: pierde energía al moverse, evita lobos (muere si hay
 * uno
 * adyacente), se beneficia de ovejas cercanas y se desplaza norte/sur cambiando
 * dirección en los límites.
 *
 * @author MorenoRubiano
 * @version 1.0
 */
public class Sheep extends Mammal {

    private static final int INITIAL_ENERGY = 5;
    private static final int INITIAL_DAYS = 0;
    private boolean movingNorth = true;

    /**
     * Crea una oveja en la posición indicada.
     *
     * @param valley valle donde se coloca
     * @param row    fila inicial
     * @param column columna inicial
     */
    public Sheep(Valley valley, int row, int column) {
        super(valley, row, column, INITIAL_ENERGY, INITIAL_DAYS);
        color = Color.LIGHT_GRAY;
        movingNorth = true;
    }

    /**
     * Forma gráfica (cuadrado).
     *
     * @return {@link Unit#SQUARE}
     */
    public int shape() {
        return Unit.SQUARE;
    }

    /**
     * Acción por tic: interactúa con vecinos, actualiza energía y se mueve.
     */
    public void act() {
        if (getEnergy() == 0) {
            die();
            return;
        }

        int lastRow = valley.getSize() - 1;

        int minR = Math.max(0, row - 1);
        int maxR = Math.min(lastRow, row + 1);
        int minC = Math.max(0, column - 1);
        int maxC = Math.min(lastRow, column + 1);

        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {
                if (r == row && c == column)
                    continue;
                Unit u = valley.getUnit(r, c);
                if (u == null)
                    continue;
                if (u instanceof Wolf) {
                    die();
                    return;
                }
                if (u instanceof Sheep) {
                    setEnergy(getEnergy() + 1);
                }
            }
        }

        if (row == 0) {
            movingNorth = false;
            setEnergy(INITIAL_ENERGY);
        }

        if (movingNorth) {
            if (!move(row - 1, column)) {
                movingNorth = false;
                move(row + 1, column);
            }
        } else {
            if (!move(row + 1, column)) {
                movingNorth = true;
                move(row - 1, column);
            }
        }
    }
}
