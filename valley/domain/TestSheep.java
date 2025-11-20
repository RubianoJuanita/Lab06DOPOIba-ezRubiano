package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class TestSheep {

    private Valley valley;

    @Before
    public void setUp() {
        valley = new Valley(); // small valley size used for tests
    }

    /**
     * Verifies that a sheep initializes with light gray color.
     */
    @Test
    public void testInitialColor() {
        Sheep o = new Sheep(valley, 2, 2);
        assertEquals("Initial color should be light gray", Color.LIGHT_GRAY, o.getColor());
    }

    /**
     * Verifies that the sheep starts with initial energy 5.
     */
    @Test
    public void testInitialEnergy() {
        Sheep o = new Sheep(valley, 1, 1);
        assertEquals("Initial energy should be 5", 5, o.getEnergy());
    }

    /**
     * Verifies that the sheep dies if its energy reaches 0.
     */
    @Test
    public void testDeathByEnergy() {
        Sheep o = new Sheep(valley, 1, 1);
        o.setEnergy(0);
        o.act();
        assertNull("The sheep should die and be removed from the valley", valley.getUnit(1, 1));
    }

    /**
     * Verifies that the sheep dies when a wolf is nearby.
     */
    @Test
    public void testDeathByWolf() {
        Sheep o = new Sheep(valley, 2, 2);
        Wolf w = new Wolf(valley, 1, 1);
        o.act();
        assertNull("The sheep should die if a wolf is adjacent", valley.getUnit(2, 2));
    }

    /**
     * Verifies that the sheep increases energy when near another sheep.
     */
    @Test
    public void testEnergyIncreasesWithNearbySheep() {
        Sheep o1 = new Sheep(valley, 2, 2);
        Sheep o2 = new Sheep(valley, 2, 3);
        int initialEnergy = o1.getEnergy();
        o1.act();
        assertTrue("Energy should increase when another sheep is nearby", o1.getEnergy() > initialEnergy);
    }

    /**
     * Verifies that the sheep has a square shape.
     */
    @Test
    public void testShape() {
        Sheep o = new Sheep(valley, 3, 3);
        assertEquals("The sheep should have a square shape", Unit.SQUARE, o.shape());
    }
}
