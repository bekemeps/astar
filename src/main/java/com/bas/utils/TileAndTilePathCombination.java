package com.bas.utils;

import com.bas.domain.tiles.Tile;

/**
 * Created by Bekezela on 12/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class TileAndTilePathCombination {

    private Tile tile;

    private TilePath path;

    /**
     * Constructor for tile and tilePath combination
     *
     * @param tile to start processing from
     * @param path to continue building on
     */
    public TileAndTilePathCombination(Tile tile, TilePath path) {
        this.tile = tile;
        this.path = path;
    }

    public Tile getTile() {
        return tile;
    }

    public TilePath getPath() {
        return path;
    }
}
