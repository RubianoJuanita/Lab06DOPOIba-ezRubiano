package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa un humano en el valle. El humano consume energía cada tic,
 * busca y come recursos adyacentes (paja), puede matar y consumir lobos u
 * ovejas adyacentes para ganar energía, y se mueve a casillas vacías cuando
 * es necesario.
 *
 * @author MorenoRubiano
 * @version 1.0
 */
public class Human extends Mammal {
    /** Energía máxima/inicial del humano. */
    public static final int INITIAL_ENERGY = 250;
    public static final int INITIAL_DAYS = 0;

    /** Energía ganada al comer paja (Hay). */
    private static final int ENERGY_FROM_HAY = 50;

    /** Energía ganada al matar un lobo. */
    private static final int ENERGY_FROM_WOLF = 25;

    /** Energía ganada al matar una oveja. */
    private static final int ENERGY_FROM_SHEEP = 50;

    /**
     * Crea un nuevo humano en la posición indicada dentro del valle.
     *
     * @param valley el valle donde vive
     * @param row    fila inicial
     * @param column columna inicial
     */
    public Human(Valley valley, int row, int column) {
        super(valley, row, column, INITIAL_ENERGY, INITIAL_DAYS);
        color = Color.PINK;
    }

    /**
     * Forma gráfica del humano (triángulo).
     *
     * @return constante de forma
     */
    public int shape() {
        return Unit.TRIANGLE;
    }

    /**
     * Acción que ejecuta el humano cada tic.
     */
    public void act() {
        consumeEnergy();

        if (isDead()) {
            die();
            return;
        }

        if (shouldRest()) {
            return;
        }

        performAction();
    }

    /** Resta 1 punto de energía al humano. */
    private void consumeEnergy() {
        setEnergy(getEnergy() - 1);
    }

    /**
     * Indica si el humano ha muerto por falta de energía.
     *
     * @return true si la energía es menor o igual a 0
     */
    private boolean isDead() {
        return getEnergy() <= 0;
    }

    /**
     * Determina si el humano debe descansar. Solo descansa si tiene la energía
     * inicial
     * y hay como máximo 1 lobo adyacente.
     *
     * @return true si debe descansar
     */
    private boolean shouldRest() {
        int wolfCount = countAdjacentWolves();
        return getEnergy() >= INITIAL_ENERGY && wolfCount <= 1;
    }

    /** Cuenta los lobos en las 8 casillas adyacentes. */
    private int countAdjacentWolves() {
        int count = 0;
        int[][] bounds = getAdjacentBounds();

        for (int r = bounds[0][0]; r <= bounds[0][1]; r++) {
            for (int c = bounds[1][0]; c <= bounds[1][1]; c++) {
                if (isCurrentPosition(r, c))
                    continue;

                Unit u = valley.getUnit(r, c);
                if (u instanceof Wolf) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Ejecuta la lógica de búsqueda y movimiento/comer según el escaneo de las
     * casillas adyacentes.
     */
    private void performAction() {
        List<int[]> hays = new ArrayList<>();
        List<int[]> wolves = new ArrayList<>();
        List<int[]> sheeps = new ArrayList<>();
        List<int[]> empties = new ArrayList<>();

        scanAdjacentCells(hays, wolves, sheeps, empties);
        executeMovementStrategy(hays, wolves, sheeps, empties);
    }

    /**
     * Escanea las 8 casillas adyacentes y clasifica cada posición en una de las
     * listas proporcionadas.
     */
    private void scanAdjacentCells(List<int[]> hays, List<int[]> wolves,
            List<int[]> sheeps, List<int[]> empties) {
        int[][] bounds = getAdjacentBounds();

        for (int r = bounds[0][0]; r <= bounds[0][1]; r++) {
            for (int c = bounds[1][0]; c <= bounds[1][1]; c++) {
                if (isCurrentPosition(r, c))
                    continue;

                classifyCell(r, c, hays, wolves, sheeps, empties);
            }
        }
    }

    /**
     * Clasifica una celda adyacente añadiendo su posición a la lista
     * correspondiente.
     */
    private void classifyCell(int r, int c, List<int[]> hays, List<int[]> wolves,
            List<int[]> sheeps, List<int[]> empties) {
        Unit u = valley.getUnit(r, c);
        int[] position = new int[] { r, c };

        if (u == null) {
            empties.add(position);
        } else if (u instanceof Hay) {
            hays.add(position);
        } else if (u instanceof Wolf) {
            wolves.add(position);
        } else if (u instanceof Sheep) {
            sheeps.add(position);
        }
    }

    /**
     * Decide la acción a partir de las listas clasificadas: comer paja, matar
     * lobos,
     * matar ovejas o moverse a vacío.
     */
    private void executeMovementStrategy(List<int[]> hays, List<int[]> wolves,
            List<int[]> sheeps, List<int[]> empties) {
        if (tryEatHay(hays))
            return;
        if (tryEatWolf(wolves))
            return;
        if (tryEatSheep(sheeps))
            return;
        tryMoveToEmpty(empties);
    }

    /** Intenta comer paja si hay; devuelve true si logra moverse y comer. */
    private boolean tryEatHay(List<int[]> hays) {
        if (hays.isEmpty())
            return false;

        int[] target = selectRandomTarget(hays);
        if (move(target[0], target[1])) {
            gainEnergy(ENERGY_FROM_HAY);
            return true;
        }
        return false;
    }

    /** Intenta matar un lobo y ganar energía; devuelve true si lo hace. */
    private boolean tryEatWolf(List<int[]> wolves) {
        if (wolves.isEmpty())
            return false;

        int[] target = selectRandomTarget(wolves);
        if (move(target[0], target[1])) {
            gainEnergy(ENERGY_FROM_WOLF);
            return true;
        }
        return false;
    }

    /** Intenta matar una oveja y ganar energía; devuelve true si lo hace. */
    private boolean tryEatSheep(List<int[]> sheeps) {
        if (sheeps.isEmpty())
            return false;

        int[] target = selectRandomTarget(sheeps);
        if (move(target[0], target[1])) {
            gainEnergy(ENERGY_FROM_SHEEP);
            return true;
        }
        return false;
    }

    /** Mueve a una casilla vacía aleatoria si existe alguna. */
    private void tryMoveToEmpty(List<int[]> empties) {
        if (empties.isEmpty())
            return;

        int[] target = selectRandomTarget(empties);
        move(target[0], target[1]);
    }

    /** Selecciona aleatoriamente una posición de la lista. */
    private int[] selectRandomTarget(List<int[]> targets) {
        Random rnd = new Random();
        return targets.get(rnd.nextInt(targets.size()));
    }

    /** Aumenta la energía hasta un máximo de {@link #INITIAL_ENERGY}. */
    private void gainEnergy(int amount) {
        setEnergy(Math.min(getEnergy() + amount, INITIAL_ENERGY));
    }

    /**
     * Calcula los límites (filas y columnas) para revisar las casillas adyacentes.
     */
    private int[][] getAdjacentBounds() {
        int last = valley.getSize() - 1;
        int minR = Math.max(0, row - 1);
        int maxR = Math.min(last, row + 1);
        int minC = Math.max(0, column - 1);
        int maxC = Math.min(last, column + 1);

        return new int[][] { { minR, maxR }, { minC, maxC } };
    }

    /** Comprueba si las coordenadas son la posición actual del humano. */
    private boolean isCurrentPosition(int r, int c) {
        return r == row && c == column;
    }
}