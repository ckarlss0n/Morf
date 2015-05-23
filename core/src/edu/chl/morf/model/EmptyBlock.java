package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Created by Harald Brorsson on 5/7/15.
 */
public class EmptyBlock extends AbstractBlock{

    public EmptyBlock(){
        this(new Point2D.Float(0,0));
    }

    public EmptyBlock(Point2D.Float position) {
        super(position);
    }
}
