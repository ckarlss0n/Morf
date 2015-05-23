package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Created by Lage on 2015-05-23.
 */
public class AbstractBlock implements Block {

    protected Point2D.Float position;

    public AbstractBlock(){

    }

    public AbstractBlock(Point2D.Float position){
        this.position = position;
    }

    public Point2D.Float getPosition(){
        return position;
    }


    public void setPosition(Point2D.Float p){
        position = p;
    }

    @Override
    public void heat() {

    }

    @Override
    public void cool() {

    }
}
