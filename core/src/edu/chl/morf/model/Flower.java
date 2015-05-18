package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Created by Lage on 2015-04-29.
 */
public class Flower implements Block{
	
	private Point2D.Float position;
	
	public Flower(Point2D.Float position){
		this.position = position;
	}
	
	public Point2D.Float getPosition(){
		return position;
	}
	
    @Override
    public void heat() {

    }

    @Override
    public void cool() {

    }
}
