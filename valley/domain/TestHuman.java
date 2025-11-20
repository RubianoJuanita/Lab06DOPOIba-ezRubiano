package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class TestHuman {

    private Valley valley;

    @Before
    public void setUp() {
        valley = new Valley();
    }

    /**
     * Verifies that the human's initial color is pink.
     */
    @Test
    public void testInitialColor() {
        Human h = new Human(valley, 2, 2);
        assertEquals("The human's initial color should be pink", Color.PINK, h.getColor());
    }

    /**
     * Verifies that the human starts with initial energy of 250.
     */
    @Test
    public void testInitialEnergy() {
        Human h = new Human(valley, 1, 1);
        assertEquals("Initial energy should be 250", 250, h.getEnergy());
    }

    /**
     * Verifies that the human dies when energy reaches zero.
     */
    @Test
    public void testDeathByEnergy() {
        Human h = new Human(valley, 1, 1);
        h.setEnergy(0);
        h.act();
        assertNull("The human should die and be removed from the valley", valley.getUnit(1, 1));
    }

}