package com.bas.domain.tiles;

/**
 * Forest tile class
 *
 * Created by Bekezela on 11/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class ForestTile extends Tile {

    /**
     * Char which represents a forest tile
     */
    public static final char CHAR = '*';

    private static final boolean WALKABLE = true;

    private static final int COST = 2;

    /**
     * Constructor for a forest tile object
     *
     * @param y vertical position of tile (0...n)
     * @param x horizontal position of tile (0...n)
     */
    public ForestTile(int y, int x) {
        super(y, x);
        this.value = ForestTile.CHAR;
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
