package edu.chl.morf.model;

import java.awt.*;

/**
 * This class represents water. The water can have one state at a time (gas/liquid/solid).
 * It transforms to water (liquid state of water) upon heating.
 * It also contains a position, as this class is used to represent a water block in the game.
 * <p>
 * <li><b>Responsible for: </b>
 * Representing water. Transforming to other forms upon interaction.
 * <p>
 * <li><b>Used by: </b>
 * None.
 * <p>
 * <li><b>Using: </b>
 * <li>{@link WaterState}
 * <li>{@link LiquidState}
 * <p>
 * @author Lage Bergman
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
