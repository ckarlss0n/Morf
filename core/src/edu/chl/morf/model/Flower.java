package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Created by Lage on 2015-04-29.
 */
public class Flower extends AbstractBlock{
    public Flower(){
        this(new Point2D.Float(0,0));
    }
    public Flower(Point2D.Float position) {
        super(position);
    }
}
