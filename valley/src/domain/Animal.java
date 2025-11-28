package domain;

import java.awt.Color;
import java.io.Serializable;

public abstract class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    private int days;
    private int energy;

    /**
     * Create a new animal
     * 
     */

    public Animal(int energy, int days) {
        this.energy = energy;
        this.days = days;
    }

    /**
     * The animal makes one step
     * 
     */
    final boolean step() {
        boolean ok = false;
        if (energy >= 1) {
            energy -= 1;
            ok = true;
        }
        return ok;
    }

    /**
     * The animal eats
     * 
     */
    protected void eat() {
        energy = 100;
    }

    /**
     * Returns the energy
     * 
     * @return
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * It's an animal
     */
    public final boolean isAnimal() {
        return true;
    }

    /**
     * Permite actualizar la energía del animal.
     * 
     * @param e Nueva cantidad de energía
     */
    public final void setEnergy(int e) {
        energy = e;
    }

}
