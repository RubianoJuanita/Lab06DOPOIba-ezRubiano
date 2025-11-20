package domain;
import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;

public class TestHay {

    /**
     * Test to verify that the initial color is red.
     * The Hay constructor sets the initial color to red.
     */
    @Test
    public void testInitialColor() {
        Hay h = new Hay(5, 5);
        assertEquals("The initial color of hay should be red", Color.red, h.getColor());
    }

    
    /**
     * Test to verify that the color alternates as expected.
     * Uses assertEquals to verify behavior after ticks.
     */
    @Test
    public void testColorToggle() {
        Hay h = new Hay(3, 3);

        // Initial color: red
        assertEquals(Color.red, h.getColor());

        // After first tick it should become yellow
        h.act();
        assertEquals("After the first click it should be yellow", Color.yellow, h.getColor());

        // After second tick it should return to red
        h.act();
        assertEquals("After the second act() it should return to red", Color.red, h.getColor());
    }
}
