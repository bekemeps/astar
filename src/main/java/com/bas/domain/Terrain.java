package com.bas.domain;

import com.bas.domain.tiles.FlatlandTile;
import com.bas.domain.tiles.ForestTile;
import com.bas.domain.tiles.MountainTile;
import com.bas.domain.tiles.NonWalkableTile;
import com.bas.domain.tiles.Tile;
import com.bas.domain.tiles.WaterTile;
import com.bas.utils.TilePath;
import com.bas.utils.TileAndTilePathCombination;
import com.bas.utils.TilePathComparator;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Terrain object to be examined
 *
 * Created by Bekezela on 11/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class Terrain {
    public static final char START_TILE_CHAR = '@';
    public static final char GOAL_TILE_CHAR = 'X';

    private int x_length = -1;
    private int y_length = -1;
    private Tile startTile;
    private Tile goalTile;
    private Tile[][] tiles;
    private Queue<TileAndTilePathCombination> processQueue;
    private PriorityQueue<TilePath> tilePaths;
    private PriorityQueue<TilePath> finalTilePaths;

    /**
     * Constructor for a new terrain
     *
     * @param rows to extract terrain from
     *
     * @throws Exception when the terrain has errors
     */
    public Terrain(List<String> rows) throws Exception {
        init(rows);
        processQueue = new LinkedList<>();
        tilePaths = new PriorityQueue<>(11, new TilePathComparator());
        finalTilePaths = new PriorityQueue<>(11, new TilePathComparator());
    }

    /**
     * Initializes this terrain object
     *
     * @param rows list of rows of this terrain
     *
     * @throws Exception
     */
    private void init(List<String> rows) throws Exception {
        String row;
        Tile tile;
        Tile[] tileRow;
        for (int y = 0; y < rows.size(); y++) {
            row = rows.get(y);
            if (x_length == -1) {
                x_length = row.length();
                y_length = rows.size();
                tiles = new Tile[rows.size()][x_length];
            }
            if (row.length() != x_length) {
                throw new Exception("Inconsistent terrain! Rows not all the same size.");
            }
            tileRow = new Tile[x_length];
            for (int x = 0; x < row.length(); x++) {
                tile = makeTile(row, y, x);
                if (tile == null) {
                    throw new Exception(String.format("Invalid tile! Char symbol '%s' unknown", row.charAt(x)));
                }
                tileRow[x] = tile;
            }
            tiles[y] = tileRow;
        }
    }

    /**
     * Makes a tile object based on the char symbol at position (x, y)
     *
     * @param row of char symbols
     * @param y value of position
     * @param x value of position
     *
     * @return created tile
     */
    private Tile makeTile(String row, int y, int x) {
        char value = row.charAt(x);
        switch (value) {
            case FlatlandTile.CHAR:
                return new FlatlandTile(y, x);
            case START_TILE_CHAR:
                this.startTile = new FlatlandTile(y, x);
                this.startTile.setAsStartTile();
                return this.startTile;
            case GOAL_TILE_CHAR:
                this.goalTile = new FlatlandTile(y, x);
                this.goalTile.setAsGoalTile();
                return this.goalTile;
            case ForestTile.CHAR:
                return new ForestTile(y, x);
            case MountainTile.CHAR:
                return new MountainTile(y, x);
            case WaterTile.CHAR:
                return new WaterTile(y, x);
            case NonWalkableTile.CHAR:
                return new NonWalkableTile(y, x);
            default:
                return null;
        }
    }

    /**
     * Processes terrain to determine the optimal path from start tile to goal tile
     */
    public void processForBestPath() {
        TilePath path = new TilePath();
        path.addToPath(startTile, 0);
        processQueue.add(new TileAndTilePathCombination(startTile, path));
        doProcessForBestPath();
        while (!tilePaths.isEmpty()) {
            TilePath tilePath = tilePaths.poll();
            if (tilePath.getLastTile() == goalTile) {
                finalTilePaths.add(tilePath);
            }
        }
    }

    private void doProcessForBestPath() {
        while (!processQueue.isEmpty()) {
            TileAndTilePathCombination combination = processQueue.poll();
            doProcessAdjacentTiles(combination.getTile(), combination.getPath());
        }
    }

    /**
     * Scans all eight adjacent tiles for walkable tiles and builds paths
     *
     * @param tile from-tile
     * @param path to build upon
     */
    private void doProcessAdjacentTiles(Tile tile, TilePath path) {
        TilePath currPath;
        for (int i = 0; i < 8; i++) {
            currPath = path.clone();
            Tile nextTile = getAdjacentTile(tile, i);
            if (nextTile != null && nextTile.isWalkable() && !isInPreviousPaths(nextTile, currPath)) {
                currPath.addToPath(nextTile, getCostToTile(nextTile));
                tilePaths.add(currPath);
                if (nextTile != goalTile) {
                    processQueue.add(new TileAndTilePathCombination(nextTile, currPath));
                }
            }
        }
    }

    /**
     * Checks whether the tile already exists in another more efficient path
     *
     * @param tile to check
     * @param currPath path to build upon, then compare with existing path
     *
     * @return boolean whether tile already exists in a more efficient path
     */
    private boolean isInPreviousPaths(Tile tile, TilePath currPath) {
        TilePath clone = currPath.clone();
        for (TilePath path : tilePaths) {
            if (path.contains(tile)) {
                clone.addToPath(tile, getCostToTile(tile));
                if (path.getTotalCost() > clone.getTotalCost()) {
                    tilePaths.remove(path);
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates total cost to tile (Manhattan distance + cost to tile)
     *
     * @param tile to calculate cost to
     *
     * @return total cost to tile
     */
    private int getCostToTile(Tile tile) {
        return manhattanDistance(tile, goalTile) + tile.getCost();
    }

    /**
     * Calculates the distance from the tile to the goal using Manhattan distance formula |x1 - x2| + |y1 - y2|
     *
     * @param currTile from-tile
     * @param goalTile to-tile
     *
     * @return Manhattan distance
     */
    private int manhattanDistance(Tile currTile, Tile goalTile) {
        return Math.abs(currTile.getX() - goalTile.getX()) + Math.abs(currTile.getY() - goalTile.getY());
    }

    /**
     * Gets the adjacent tile to reference tile,
     * sequentially an all 8 adjacent positions, starting at top-left corner in an anti-clockwise direction
     *
     * @param tile reference tile
     * @param pos adjacent position number
     *
     * @return adjacent tile or null, if already processed or out-of-bounce
     */
    private Tile getAdjacentTile(Tile tile, int pos) {
        int x_var, y_var;
        Tile adjTile = null;
        switch (pos) {
            case 0:
                x_var = tile.getX() - 1;
                y_var = tile.getY() - 1;
                break;
            case 1:
                x_var = tile.getX() - 1;
                y_var = tile.getY();
                break;
            case 2:
                x_var = tile.getX() - 1;
                y_var = tile.getY() + 1;
                break;
            case 3:
                x_var = tile.getX();
                y_var = tile.getY() + 1;
                break;
            case 4:
                x_var = tile.getX() + 1;
                y_var = tile.getY() + 1;
                break;
            case 5:
                x_var = tile.getX() + 1;
                y_var = tile.getY();
                break;
            case 6:
                x_var = tile.getX() + 1;
                y_var = tile.getY() - 1;
                break;
            case 7:
                x_var = tile.getX();
                y_var = tile.getY() - 1;
                break;
            default:
                x_var = -1;
                y_var = -1;
                break;
        }
        if ((x_var >= 0 && x_var < x_length)
                && (y_var >= 0 && y_var < y_length)) {
            adjTile = tiles[y_var][x_var];
        }
        return adjTile;
    }

    /**
     * Displays this terrain's matrix
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(System.lineSeparator());
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                stringBuilder.append(tile.getValue());
            }
            stringBuilder.append(System.lineSeparator());
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    /**
     * Sets the optimal path to '#' chars on this terrain
     */
    public void setOptimalPath() {
        TilePath optimalPath = finalTilePaths.peek();
        optimalPath.setOptimalPath();
    }
}
