
package domain;

import java.awt.Color;

public class Wolf extends Mammal {
    public static final int INITIAL_ENERGY = 100;
    public static final int INITIAL_DAYS = 0;

    public Wolf(Valley valley, int row, int column) {
        super(valley, row, column, INITIAL_ENERGY, INITIAL_DAYS);
        color = Color.black;
    }

    public int shape() {
        return Unit.ROUND;
    }

    public void act() {
        if (getEnergy() == 0) {
            die();
        } else {
            if (!move(row + (int) (Math.random() * 3) - 1, column + (int) (Math.random() * 3) - 1)) {
                move(row + (int) (Math.random() * 3) - 1, column + (int) (Math.random() * 3) - 1);
            }
        }
    }
}
