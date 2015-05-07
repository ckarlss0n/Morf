package edu.chl.morf.model;

import java.awt.*;

/**
 * Created by Lage on 2015-05-07.
 */
public class LevelObject {

    private TileType tileType;
    private Point position;

    public LevelObject(TileType tileType, Point position){
        this.tileType = tileType;
        this.position = position;
    }
}
