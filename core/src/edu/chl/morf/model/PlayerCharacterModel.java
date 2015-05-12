package edu.chl.morf.model;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import edu.chl.morf.Constants;

import static edu.chl.morf.Constants.*;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * This class is used to keep track of information about the player character.
 * The PlayerCharacter class updates the information stored here.
 * The Act method in GameScreen will use the information.
 * <p>
 * <li><b>Responsible for: </b>
 * Storing and keeping track of information about the player character.
 * <p>
 * <li><b>Used by: </b>
 * None.
 * <p>
 * <li><b>Using: </b>
 * <li>{@link edu.chl.morf.Constants}
 * <p>
 * @author Harald
 */
public class PlayerCharacterModel {
    private boolean facingRight=true;
    private boolean moving=false;
    private boolean onGround=false;
    private boolean alive = true;
    private int waterLevel=WATER_LEVEL;
    private Block activeBlockRight;
    private Block activeBlockLeft;
    private Block activeBlock;
    
    private Point2D.Float position;
    private Point2D.Float speed;
    
    //Constructors
    public PlayerCharacterModel(){
    	position = new Point2D.Float(100, 100);
        speed = new Point2D.Float(0,0);
    }
    public PlayerCharacterModel(Point2D.Float position){
    	this.position = position;
        speed = new Point2D.Float(0,0);
    }
    public PlayerCharacterModel(int x, int y){
    	position = new Point2D.Float(x, y);
        speed = new Point2D.Float(0,0);
    }
    

    //Getters
    public Point2D.Float getPosition(){
    	return position;
    }
    public boolean isMoving(){
        return moving;
    }
    public boolean isAlive(){
        return alive;
    }
    public boolean isFacingRight(){
        return facingRight;
    }
    public boolean hasWater(){
        return waterLevel>0;
    }
    public int getWaterLevel(){
        return waterLevel;
    }
    public Block getActiveBlock(){
    	return activeBlock;
    }
    public Point2D.Float getSpeed(){
    	return speed;
    }
    
    public boolean onGround() {
        return onGround;
    }

    //Move setters
    public void moveLeft(){
        moving = true;
        facingRight = false;
        setActiveBlock(activeBlockLeft);
    }
    public void moveRight(){
        moving = true;
        facingRight = true;
        setActiveBlock(activeBlockRight);
    }
    
    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public void stop(){
        moving = false;
    }
    public void setPosition(Point2D.Float position){
        position.setLocation(position);
    }
    public void setPosition(float x, float y){
    	position.setLocation(x, y);
    }
    public void setSpeed(float x, float y){
        speed.setLocation(x, y);
    }

    public Water pourWater(){
        Point2D.Float point=new Point2D.Float(position.x - 36, position.y);
        if(facingRight){
            point = new Point2D.Float(position.x + 36 * 2, position.y);
        }
        decreaseWaterLevel();
        return new Water(point);
    }
    
    //WaterLevel setters
    public void decreaseWaterLevel(){
        waterLevel=waterLevel-1;
    }
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setActiveBlockRight(Block block){
    	activeBlockRight = block;
    	if(facingRight){
    		setActiveBlock(block);
    	}
    }
    public void setActiveBlockLeft(Block block){
    	activeBlockLeft = block;
    	if(!facingRight){
    		setActiveBlock(block);
    	}
    }
    public void setActiveBlock(Block block){
        activeBlock = block;
    }

    //Methods for manipulating active block
    public void heatActiveBlock(){
        activeBlock.heat();
    }
    public void coolActiveBlock(){
        activeBlock.cool();
    }
}

