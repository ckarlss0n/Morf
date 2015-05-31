package edu.chl.morf.model.blocks;

import java.awt.geom.Point2D;

/**
 * Class for representing a flower block to be placed in a level.
 * 
 * @author Lage Bergman
 */

public class Flower extends AbstractBlock{
	//Constructors
    public Flower(Point2D.Float position) {
        super(position);
    }
    public Flower(float x, float y){
    	this(new Point2D.Float(x, y));
    }
}
