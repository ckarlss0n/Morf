package edu.chl.morf.model;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Class for representing a level object (i.e. ground tile or spikes).
 * A LevelObject has a TileType and a position.
 * 
 * @author Lage
 */
public class LevelObject {

    private TileType tileType;
    private Point2D.Float position;

    //Constructor
    public LevelObject(TileType tileType, Point2D.Float position){
        this.tileType = tileType;
        this.position = position;
    }

    //Getters
    public TileType getTileType(){
    	return tileType;
    }
    public Point2D.Float getPosition(){
        return position;
    }
}
