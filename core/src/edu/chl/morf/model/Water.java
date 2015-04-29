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
 * <li>{@link Block}
 * <li>{@link LiquidState}
 * <p>
 * @author Lage Bergman
 */
public class Water {

    private Point position;
    private WaterState state;

    public Water(){
        this.state = WaterState.SOLID;
    }

    public Point getPosition(){
        return this.position;
    }

    public void heat(){
        if(state == WaterState.SOLID) {
            this.state = WaterState.LIQUID;
        } else if(state == WaterState.LIQUID) {
            this.state = WaterState.GAS;
        }
    }

    public void cool(){
        if(state == WaterState.GAS) {
            this.state = WaterState.LIQUID;
        } else if(state == WaterState.LIQUID) {
            this.state = WaterState.SOLID;
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
