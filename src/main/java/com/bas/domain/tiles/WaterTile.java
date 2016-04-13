package com.bas.domain.tiles;

/**
 * Water tile class
 *
 * Created by Bekezela on 11/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class WaterTile extends Tile {

    /**
     * Char which represents a water tile
     */
    public static final char CHAR = '-';

    private static final boolean WALKABLE = false;

    /**
     * Constructor for a water tile object
     *
     * @param y vertical position of tile (0...n)
     * @param x horizontal position of tile (0...n)
     */
    public WaterTile(int y, int x) {
        super(y, x);
        this.value = WaterTile.CHAR;
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
     * Getter for the cost of moving to this unreachable tile
     *
     * @return cost (dummy value)
     */
    @Override
    public int getCost() {
        return Integer.MAX_VALUE;
    }
}
