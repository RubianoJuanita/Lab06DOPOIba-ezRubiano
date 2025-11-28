package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class TestOveja {

    private Valley valley;

    @Before
    public void setUp() {
        valley = new Valley(); // tamaño pequeño de valle
    }

    /**
     * Verifica que una oveja se inicializa con color gris claro
     */
    @Test
    public void testColorInicial() {
        Oveja o = new Oveja(valley, 2, 2);
        assertEquals("El color inicial debe ser gris claro", Color.LIGHT_GRAY, o.getColor());
    }

    /**
     * Verifica que la oveja comienza con energía inicial 5
     */
    @Test
    public void testEnergiaInicial() {
        Oveja o = new Oveja(valley, 1, 1);
        assertEquals("La energía inicial debe ser 5", 5, o.getEnergy());
    }

    /**
     * Verifica que la oveja muere si su energía llega a 0
     */
    @Test
    public void testMuertePorEnergia() {
        Oveja o = new Oveja(valley, 1, 1);
        o.setEnergy(0);
        o.act();
        assertNull("La oveja debe morir y desaparecer del valle", valley.getUnit(1, 1));
    }

    /**
     * Verifica que la oveja muere al estar cerca de un lobo
     */
    @Test
    public void testMuertePorLobo() {
        Oveja o = new Oveja(valley, 2, 2);
        Wolf w = new Wolf(valley, 1, 1);
        o.act();
        assertNull("La oveja debe morir si hay un lobo adyacente", valley.getUnit(2, 2));
    }

    /**
     * Verifica que la oveja aumenta su energía al estar cerca de otra oveja
     */
    @Test
    public void testEnergiaAumentaConOtraOveja() {
        Oveja o1 = new Oveja(valley, 2, 2);
        Oveja o2 = new Oveja(valley, 2, 3);
        int energiaInicial = o1.getEnergy();
        o1.act();
        assertTrue("La energía debe aumentar si hay otra oveja cerca", o1.getEnergy() > energiaInicial);
    }

    /**
     * Verifica que la oveja tiene forma cuadrada
     */
    @Test
    public void testForma() {
        Oveja o = new Oveja(valley, 3, 3);
        assertEquals("La oveja debe tener forma cuadrada", Unit.SQUARE, o.shape());
    }
}
