package edu.chl.morf.model;

import java.awt.geom.Point2D;

import edu.chl.morf.model.blocks.AbstractBlock;

/**
 * The EmptyBlock class represents that there is no block.
 * This is relevant regarding the activeBlock. If the activeBlock was
 * an instance of Water, but later becomes empty the instance of water
 * needs to be replaced. This is where EmptyBlock is used instead of setting
 * activeBlock to null.
 *
 * The EmptyBlock class contains a static instance of itself which is referenced
 * everywhere instead of creating new instances of EmptyBlock.
 *
 * @author Harald Brorsson
 */
public class EmptyBlock extends AbstractBlock{

    private static EmptyBlock emptyBlock = new EmptyBlock(new Point2D.Float(0,0));

    private EmptyBlock(Point2D.Float position) {
        super(position);
    }

    public static EmptyBlock getEmptyBlock(){return emptyBlock;}
}
