package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class TestOveja {

    private Valley valley;

    @Before
    public void setUp() {
        valley = new Valley(); // small valley size
    }

    /**
     * Verifies that a sheep is initialized with light gray color
     */
    @Test
    public void testColorInicial() {
        Sheep o = new Sheep(valley, 2, 2);
        assertEquals("The initial color must be light gray", Color.LIGHT_GRAY, o.getColor());
    }

    /**
     * Verifies that the sheep starts with initial energy 5
     */
    @Test
    public void testEnergiaInicial() {
        Sheep o = new Sheep(valley, 1, 1);
        assertEquals("The initial energy must be 5", 5, o.getEnergy());
    }

    /**
     * Verifies that the sheep dies if its energy reaches 0
     */
    @Test
    public void testMuertePorEnergia() {
        Sheep o = new Sheep(valley, 1, 1);
        o.setEnergy(0);
        o.act();
        assertNull("The sheep must die and disappear from the valley", valley.getUnit(1, 1));
    }

    /**
     * Verifies that the sheep dies when near a wolf
     */
    @Test
    public void testMuertePorLobo() {
        Sheep o = new Sheep(valley, 2, 2);
        Wolf w = new Wolf(valley, 1, 1);
        o.act();
        assertNull("The sheep must die if there is an adjacent wolf", valley.getUnit(2, 2));
    }

    /**
     * Verifies that the sheep increases its energy when near another sheep
     */
    @Test
    public void testEnergiaAumentaConOtraOveja() {
        Sheep o1 = new Sheep(valley, 2, 2);
        Sheep o2 = new Sheep(valley, 2, 3);
        int energiaInicial = o1.getEnergy();
        o1.act();
        assertTrue("Energy must increase if there is another sheep nearby", o1.getEnergy() > energiaInicial);
    }

    /**
     * Verifies that the sheep has a square shape
     */
    @Test
    public void testForma() {
        Sheep o = new Sheep(valley, 3, 3);
        assertEquals("The sheep must have a square shape", Unit.SQUARE, o.shape());
    }
}
