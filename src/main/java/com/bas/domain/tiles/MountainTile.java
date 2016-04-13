package com.bas.domain.tiles;

/**
 * Mountain tile class
 *
 * Created by Bekezela on 11/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class MountainTile extends Tile {

    /**
     * Char which represents a mountain tile
     */
    public static final char CHAR = '^';

    private static final boolean WALKABLE = true;

    private static final int COST = 3;

    /**
     * Constructor for a mountain tile object
     *
     * @param y vertical position of tile (0...n)
     * @param x horizontal position of tile (0...n)
     */
    public MountainTile(int y, int x) {
        super(y, x);
        this.value = MountainTile.CHAR;
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
