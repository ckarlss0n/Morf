package edu.chl.morf.model;

import java.awt.geom.Point2D;

public class Ground extends AbstractBlock{
    public Ground(){
        this(new Point2D.Float(0,0));
    }
    public Ground(Point2D.Float position) {
        super(position);
    }
}
