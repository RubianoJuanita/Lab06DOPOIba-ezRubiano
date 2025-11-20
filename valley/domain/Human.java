package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a human in the valley. The human consumes energy each tick,
 * searches for and eats adjacent resources (hay), can kill and consume
 * adjacent wolves or sheep to gain energy, and moves to empty cells when
 * necessary.
 *
 * Author: MorenoRubiano
 * Version: 1.0
 */
public class Human extends Mammal {
    /** Maximum / initial energy for the human. */
    public static final int INITIAL_ENERGY = 250;
    public static final int INITIAL_DAYS = 0;

    /** Energy gained when eating hay (Hay). */
    private static final int ENERGY_FROM_HAY = 50;

    /** Energy gained when killing a wolf. */
    private static final int ENERGY_FROM_WOLF = 25;

    /** Energy gained when killing a sheep. */
    private static final int ENERGY_FROM_SHEEP = 50;

    /**
     * Creates a new human at the indicated position inside the valley.
     *
     * @param valley the valley where it lives
     * @param row    initial row
     * @param column initial column
     */
    public Human(Valley valley, int row, int column) {
        super(valley, row, column, INITIAL_ENERGY, INITIAL_DAYS);
        color = Color.PINK;
    }

    /**
     * Graphical form of the human (triangle).
     *
     * @return shape constant
     */
    public int shape() {
        return Unit.TRIANGLE;
    }

    /**
     * Action executed by the human on each tick.
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

    /** Subtracts 1 energy point from the human. */
    private void consumeEnergy() {
        setEnergy(getEnergy() - 1);
    }

    /**
     * Indicates whether the human has died due to lack of energy.
     *
     * @return true if energy is less than or equal to 0
     */
    private boolean isDead() {
        return getEnergy() <= 0;
    }

    /**
     * Determines whether the human should rest. The human rests only if it has
     * the initial energy and there is at most one adjacent wolf.
     *
     * @return true if it should rest
     */
    private boolean shouldRest() {
        int wolfCount = countAdjacentWolves();
        return getEnergy() >= INITIAL_ENERGY && wolfCount <= 1;
    }

    /** Counts wolves in the 8 adjacent cells. */
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
     * Performs the search and move/eat logic based on scanning adjacent cells.
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
     * Scans the 8 adjacent cells and classifies each position into the
     * provided lists.
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
     * Classifies an adjacent cell by adding its position to the corresponding
     * list.
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
     * Decides the action based on the classified lists: eat hay, kill wolves,
     * kill sheep, or move to an empty cell.
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

    /** Attempts to eat hay if available; returns true if it moves and eats. */
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

    /** Attempts to kill a wolf and gain energy; returns true if successful. */
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

    /** Attempts to kill a sheep and gain energy; returns true if successful. */
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

    /** Moves to a random empty cell if any exist. */
    private void tryMoveToEmpty(List<int[]> empties) {
        if (empties.isEmpty())
            return;

        int[] target = selectRandomTarget(empties);
        move(target[0], target[1]);
    }

    /** Selects a random position from the list. */
    private int[] selectRandomTarget(List<int[]> targets) {
        Random rnd = new Random();
        return targets.get(rnd.nextInt(targets.size()));
    }

    /** Increases energy up to the maximum defined by {@link #INITIAL_ENERGY}. */
    private void gainEnergy(int amount) {
        setEnergy(Math.min(getEnergy() + amount, INITIAL_ENERGY));
    }

    /**
     * Calculates the bounds (rows and columns) to check adjacent cells.
     */
    private int[][] getAdjacentBounds() {
        int last = valley.getSize() - 1;
        int minR = Math.max(0, row - 1);
        int maxR = Math.min(last, row + 1);
        int minC = Math.max(0, column - 1);
        int maxC = Math.min(last, column + 1);

        return new int[][] { { minR, maxR }, { minC, maxC } };
    }

    /** Checks whether the coordinates correspond to the human's current position. */
    private boolean isCurrentPosition(int r, int c) {
        return r == row && c == column;
    }
}