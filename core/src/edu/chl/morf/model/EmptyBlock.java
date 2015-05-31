package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Created by Harald Brorsson on 5/7/15.
 */
public class EmptyBlock extends AbstractBlock{

    private static EmptyBlock emptyBlock = new EmptyBlock(new Point2D.Float(0,0));

    private EmptyBlock(Point2D.Float position) {
        super(position);
    }

    public static EmptyBlock getEmptyBlock(){return emptyBlock;}
}
