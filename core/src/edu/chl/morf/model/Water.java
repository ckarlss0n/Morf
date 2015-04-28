package edu.chl.morf.model;

import java.awt.*;

/**
 * Created by Lage on 2015-04-28.
 */
public class Water {

    private Point position;
    private WaterState state;

    public Water(){
        this.state = new LiquidState();
    }

    public Point getPosition(){
        return this.position;
    }

    public void heat(){
        if(state.heat() != null) {
            this.state = state.heat();
        }
    }

    public void cool(){
        if(state.cool() != null) {
            this.state = state.cool();
        }
    }

    public WaterState getState(){
        return this.state;
    }

    @Override
    public String toString(){
        return state.toString();
    }

}
