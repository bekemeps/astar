package com.bas.utils;

import com.bas.domain.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Tile path class
 *
 * Created by Bekezela on 09/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class TilePath {
    public static final char OPTIMAL_PATH_TILE_CHAR = '#';

    private List<Tile> path;
    private int totalCost;

    public TilePath() {
        this.path = new ArrayList<>();
        totalCost = 0;
    }

    /**
     * Adds the tile to this tilePath and accumulates the total cost
     *
     * @param tile to be added
     * @param totalCostToTile total cost to tile
     */
    public void addToPath(Tile tile, int totalCostToTile) {
        path.add(tile);
        totalCost += totalCostToTile;
    }

    /**
     * Adds the tile to the tile list (used on cloning paths)
     * Note: the tiles themselves are not cloned because equality is checked on the pointers, no need to duplicate tiles!
     *
     * @param tile tile to be added
     */
    private void addToPath(Tile tile) {
        path.add(tile);
    }

    /**
     * Getter for the total cost of this path
     *
     * @return total cost
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Clones (somewhat deep) this path to produce a similar path but pointing to the same tiles (tiles are not cloned)
     *
     * @return the cloned path
     */
    public TilePath clone() {
        TilePath clone = new TilePath();
        for (Tile tile : this.path) {
            clone.addToPath(tile);
        }
        clone.totalCost = this.totalCost;
        return clone;
    }

    /**
     * Getter for the last tile in this path
     *
     * @return last tile
     */
    public Tile getLastTile() {
        int index = path.size() - 1;
        if (index >= 0) {
            return path.get(index);
        }
        return null;
    }

    /**
     * Checks whether this path contains the tile
     *
     * @param tile to check
     *
     * @return boolean whether this path contains the tile
     */
    public boolean contains(Tile tile) {
        return path.contains(tile);
    }

    /**
     * Sets this path's tiles to optimal path tiles
     */
    public void setOptimalPath() {
        for (Tile tile : this.path) {
            tile.setAsOptimalPathTile();
        }
    }
}
