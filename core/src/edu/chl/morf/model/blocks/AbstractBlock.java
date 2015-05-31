package edu.chl.morf.model.blocks;

import java.awt.geom.Point2D;

/**
 * Abstract class representing a block with a position in a 2D-level.
 * Implements Block interface.
 * 
 * @author Lage Bergman
 */

public class AbstractBlock implements Block {

    protected Point2D.Float position;

    //Constructors
    public AbstractBlock(Point2D.Float position){
        this.position = position;
    }
    public AbstractBlock(float x, float y){
    	this(new Point2D.Float(x, y));
    }

    //Getter for position
    public Point2D.Float getPosition(){
        return position;
    }

    //Setters for position
    public void setPosition(Point2D.Float p){
        position.setLocation(p);
    }
    public void setPosition(float x, float y){
    	position.setLocation(x, y);
    }

    //Empty heat and cool methods for blocks that are unable to heat and cool.
    @Override
    public void heat() {}
    @Override
    public void cool() {}
}
