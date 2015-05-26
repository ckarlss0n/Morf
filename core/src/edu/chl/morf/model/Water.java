package edu.chl.morf.model;

import java.awt.*;
import java.awt.geom.Point2D;

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
public class Water extends AbstractBlock{

    private WaterState state;
    private boolean bottomBlock;
    private boolean topBlock;
    private boolean intersectsFlower = false;

    //Constructors
    public Water(Point2D.Float position, WaterState state){
        super(position);
    	this.state = state;
    }
    public Water(int x, int y, WaterState state){
    	this(new Point2D.Float(x, y), state);
    }
    public Water(Point2D.Float position){
    	this(position, WaterState.LIQUID);
    }
    public Water(int x, int y){
    	this(new Point2D.Float(x, y));
    }
    public Water(WaterState state){
    	this(new Point2D.Float(0, 0), state);
    }
    public Water(){
    	this(new Point2D.Float(0, 0), WaterState.LIQUID);
    }

    public void setPosition(float x, float y){
        setPosition(new Point2D.Float(x,y));
    }
    public void setIntersectsFlower(boolean intersectsFlower){this.intersectsFlower=intersectsFlower;}
    public boolean getIntersectsFlower(){return intersectsFlower;}

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
    public void setBottomBlock(boolean bottomBlock){
        this.bottomBlock=bottomBlock;
    }
    public boolean isBottomBlock(){
        return bottomBlock;
    }
    public void setTopBlock(boolean topBlock){
        this.topBlock=topBlock;
    }

    public WaterState getState(){
        return this.state;
    }

    @Override
    public String toString(){
        return state.toString();
    }

}
