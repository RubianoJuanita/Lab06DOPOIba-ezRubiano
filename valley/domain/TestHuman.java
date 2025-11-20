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
    public void testColorInicial() {
        Human h = new Human(valley, 2, 2);
        assertEquals("The human's initial color must be pink", Color.PINK, h.getColor());
    }

    /**
     * Verifies that the human starts with initial energy of 250.
     */
    @Test
    public void testEnergiaInicial() {
        Human h = new Human(valley, 1, 1);
        assertEquals("The initial energy must be 250", 250, h.getEnergy());
    }

    /**
     * Verifies that the human dies when running out of energy.
     */
    @Test
    public void testMuertePorEnergia() {
        Human h = new Human(valley, 1, 1);
        h.setEnergy(0);
        h.act();
        assertNull("The human must die and disappear from the valley", valley.getUnit(1, 1));
    }

}