package domain;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;

public class TestHay {

    /**
     * Test to verify that the initial color is red
     * It is defined that red color is the initial one by Hay constructor
     */
    @Test
    public void testColorInicial() {
        Hay h = new Hay(5, 5);
        assertEquals("The initial color of hay must be red", Color.red, h.getColor());
    }

    /**
     * Test done to verify that the color is alternating as requested by the
     * statement.
     * The assertEquals is used to verify this
     * Used based on the applied Tic-Tac
     */
    @Test
    public void testCambioColor() {
        Hay h = new Hay(3, 3);

        // Initial color: red
        assertEquals(Color.red, h.getColor());

        // First tic-tac should change to yellow as defined in the sequence
        h.act();
        assertEquals("After the first click it must be yellow", Color.yellow, h.getColor());

        // Second tic-tac should return to red as defined in the sequence
        h.act();
        assertEquals("After the second act() it must return to red", Color.red, h.getColor());
    }
}
