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
     * Verifica que el color inicial del humano sea rosado.
     */
    @Test
    public void testColorInicial() {
        Human h = new Human(valley, 2, 2);
        assertEquals("El color inicial del humano debe ser rosado", Color.PINK, h.getColor());
    }

    /**
     * Verifica que el humano comience con energía inicial de 250.
     */
    @Test
    public void testEnergiaInicial() {
        Human h = new Human(valley, 1, 1);
        assertEquals("La energía inicial debe ser 250", 250, h.getEnergy());
    }

    /**
     * Verifica que el humano muere al quedarse sin energía.
     */
    @Test
    public void testMuertePorEnergia() {
        Human h = new Human(valley, 1, 1);
        h.setEnergy(0);
        h.act();
        assertNull("El humano debe morir y desaparecer del valle", valley.getUnit(1, 1));
    }

}