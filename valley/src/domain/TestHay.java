package domain;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TestHay {

    /**
     * Test para comprobar que el color inicial que se tiene es rojo 
     * Se define que el color rojo es el inicial por constructor de Hay
     */
    @Test
    public void testColorInicial() {
        Hay h = new Hay(5, 5);
        assertEquals("El color inicial del heno debe ser rojo", Color.red, h.getColor());
    }

    
    /**
     * Test que se hace para comprobar que se está alternando el color según pide el enunciado.
     * Se usa el assertEquals para comprobar esto
     * Se usa con base en el aplicado de los Tic-Tac 
     */
    @Test
    public void testCambioColor() {
        Hay h = new Hay(3, 3);

        // Color inicial que se tiene :rojo
        assertEquals(Color.red, h.getColor());

        // Primer tic-tac debe pasarse a amarillo como se define en la secuencia 
        h.act();
        assertEquals("Después del primer click debe ser amarillo", Color.yellow, h.getColor());

        // Segundo tic-tac debe volver a rojo como se define en la secuencia 
        h.act();
        assertEquals("Después del segundo act() debe volver a rojo", Color.red, h.getColor());
    }
}
