package com.bas.utils;

import java.util.Comparator;

/**
 * Tile Path comparator to sort paths stored in the priority queue
 *
 * Note: A priority queue does not permit null elements, so null-check on comparator was omitted
 *
 * Created by Bekezela on 12/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class TilePathComparator implements Comparator<TilePath> {
    @Override
    public int compare(TilePath tp1, TilePath tp2) {
        return tp1.getTotalCost() - tp2.getTotalCost();
    }
}
