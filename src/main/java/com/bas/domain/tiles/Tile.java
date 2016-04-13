package com.bas.domain.tiles;

import com.bas.domain.Terrain;
import com.bas.utils.TilePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Tile class (abstract)
 *
 * Created by Bekezela on 09/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public abstract class Tile {
    private int y;
    private int x;
    protected char value;

    public Tile(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    /**
     * Getter for the char representing this tile
     *
     * @return char value
     */
    public char getValue() {
        return value;
    }

    public abstract boolean isWalkable();

    public abstract int getCost();

    /**
     * Marking this tile as a start-tile
     */
    public void setAsStartTile() {
        this.value = Terrain.START_TILE_CHAR;
    }

    /**
     * Marking this tile as a goal-tile
     */
    public void setAsGoalTile() {
        this.value = Terrain.GOAL_TILE_CHAR;
    }

    /**
     * Sets this tile char to optimal path tile char ('#')
     */
    public void setAsOptimalPathTile() {
        this.value = TilePath.OPTIMAL_PATH_TILE_CHAR;
    }
}
