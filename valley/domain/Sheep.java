package domain;

import java.awt.Color;

/**
 * Sheep in the valley.
 *
 * Basic behavior: loses energy when moving, avoids wolves (dies if a wolf is
 * adjacent), benefits from nearby sheep (gains energy), and moves north/south
 * changing direction at the boundaries.
 *
 * Author: MorenoRubiano
 * Version: 1.0
 */
public class Sheep extends Mammal {

    private static final int INITIAL_ENERGY = 5;
    private static final int INITIAL_DAYS = 0;
    private boolean movingNorth = true;

    /**
     * Creates a sheep at the indicated position.
     *
     * @param valley the valley where it is placed
     * @param row    initial row
     * @param column initial column
     */
    public Sheep(Valley valley, int row, int column) {
        super(valley, row, column, INITIAL_ENERGY, INITIAL_DAYS);
        color = Color.LIGHT_GRAY;
        movingNorth = true;
    }

    /**
     * Graphical shape (square).
     *
     * @return {@link Unit#SQUARE}
     */
    public int shape() {
        return Unit.SQUARE;
    }

    /**
     * Action per tick: interact with neighbors, update energy, and move.
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
