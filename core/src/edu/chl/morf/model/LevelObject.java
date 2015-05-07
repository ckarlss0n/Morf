package edu.chl.morf.model;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Lage on 2015-05-07.
 */
public class LevelObject {

    private TileType tileType;
    private Point2D.Float position;

    public LevelObject(TileType tileType, Point2D.Float position){
        this.tileType = tileType;
        this.position = position;
    }

    public Point2D.Float getPosition(){
        return this.position;
    }
}
