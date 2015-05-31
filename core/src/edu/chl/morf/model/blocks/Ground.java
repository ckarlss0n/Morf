package edu.chl.morf.model.blocks;

import java.awt.geom.Point2D;

/**
 * Class for representing a ground block to be placed in a level.
 * 
 * @author gustav
 */

public class Ground extends AbstractBlock{
	/////////////////////////////////////////////////////////
	//is this class needed?
	//Constructors
    public Ground(Point2D.Float position) {
        super(position);
    }
    public Ground(float x, float y){
    	this(new Point2D.Float(x, y));
    }
}
