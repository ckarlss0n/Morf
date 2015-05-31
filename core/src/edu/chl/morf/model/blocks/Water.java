package edu.chl.morf.model.blocks;

import java.awt.geom.Point2D;

import edu.chl.morf.model.WaterState;

/**
 * Class for representing a water block to be placed i a level.
 * Water has a state (gas, liquid or solid).
 * It also has information of its contacts with other blocks.
 * 
 * @author Lage Bergman
 */
public class Water extends AbstractBlock{

    private WaterState state;
    private boolean bottomBlock;
    private boolean intersectingWithFlower;

    //Constructors
    public Water(Point2D.Float position, WaterState state){
        super(position);
    	this.state = state;
    	bottomBlock = false;
    	intersectingWithFlower = false;
    }
    public Water(int x, int y, WaterState state){
    	this(new Point2D.Float(x, y), state);
    }
    
    //Getters
    public WaterState getState(){
        return state;
    }
    public boolean isBottomBlock(){
        return bottomBlock;
    }
    public boolean isIntersectingWithFlower(){
    	return intersectingWithFlower;
    }

    //Setters
    public void setBottomBlock(boolean bottomBlock){
        this.bottomBlock = bottomBlock;
    }
    public void setIntersectingWithFlower(boolean intersectingWithFlower){
    	this.intersectingWithFlower = intersectingWithFlower;
    }

    //Methods for heating and cooling water, changing its state.
    @Override
    public void heat(){
        if(state == WaterState.SOLID) {
            this.state = WaterState.LIQUID;
        } else if(state == WaterState.LIQUID) {
            this.state = WaterState.GAS;
        }
    }
    
    @Override
    public void cool(){
        if(state == WaterState.GAS) {
            this.state = WaterState.LIQUID;
        } else if(state == WaterState.LIQUID) {
            this.state = WaterState.SOLID;
        }
    }
}
