package domain;

import java.awt.Color;

/*
 * Unit interface representing any element that can exist in the valley
 * (animals, resources, obstacles, ...). Implementing classes should provide
 * behavior for `act()` and may override default visual and type properties.
 */
public interface Unit {
    public static final int SQUARE = 2;
    public static final int ROUND = 1;
    public static final int TRIANGLE = 0;

    public void act();

    public default int shape() {
        return SQUARE;
    }

    public default Color getColor() {
        return Color.black;
    };

    public default boolean isResource() {
        return true;
    }

    public default boolean isAnimal() {
        return false;
    }

}
