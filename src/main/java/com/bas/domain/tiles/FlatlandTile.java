package com.bas.domain.tiles;

/**
 * Flatland tile class - represents standard, start or goal tile
 *
 * Created by Bekezela on 11/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class FlatlandTile extends Tile {

    /**
     * Char which represents a flatland tile
     */
    public static final char CHAR = '.';

    private static final boolean WALKABLE = true;

    private static final int COST = 1;

    /**
     * Constructor for a flatland tile object
     *
     * @param y vertical position of tile (0...n)
     * @param x horizontal position of tile (0...n)
     */
    public FlatlandTile(int y, int x) {
        super(y, x);
        this.value = FlatlandTile.CHAR;
    }

    /**
     * Getter for whether this tile is walkable
     *
     * @return walkable boolean
     */
    @Override
    public boolean isWalkable() {
        return WALKABLE;
    }

    /**
     * Getter for the cost of moving to this tile (without Manhattan distance)
     *
     * @return cost
     */
    @Override
    public int getCost() {
        return COST;
    }
}
