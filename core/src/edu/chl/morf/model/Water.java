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
public class Water implements Block{

    private Point position;
    private WaterState state;

    //Constructors
    public Water(Point position, WaterState state){
    	this.position = position;
    	this.state = state;
    }
    public Water(int x, int y, WaterState state){
    	this(new Point(x, y), state);
    }
    public Water(Point position){
    	this(position, WaterState.LIQUID);
    }
    public Water(int x, int y){
    	this(new Point(x, y));
    }
    public Water(WaterState state){
    	this(new Point(0, 0), state);
    }
    public Water(){
    	this(new Point(0, 0), WaterState.LIQUID);
    }




    public Point getPosition(){
        return this.position;
    }

    public void setPosition(Point p){
        position = p;
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
