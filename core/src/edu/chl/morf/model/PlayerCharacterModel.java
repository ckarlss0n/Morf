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
    private boolean facingRight;
    private boolean moving;
    private boolean onGround;
    private boolean dead;
    private int waterLevel=30;
    private Block activeBlockRight;
    private Block activeBlockLeft;
    private Block activeBlock;
    private boolean ghostEmptyRight;
    private boolean ghostEmptyLeft;
    private boolean ghostEmpty;
    
    private Point2D.Float position;
    private Point2D.Float speed;
    
    //Constructors
    public PlayerCharacterModel(){
        facingRight = true;
        moving = false;
        onGround = true;
        dead = false;
        activeBlockRight = new EmptyBlock();
        activeBlockLeft = new EmptyBlock();
        activeBlock = new EmptyBlock();
        speed = new Point2D.Float(0,0);
        ghostEmptyRight = true;
        ghostEmptyLeft = true;
        ghostEmpty = true;
    }
    public PlayerCharacterModel(Point2D.Float position){
    	this();
    	this.position = position;
    }
    public PlayerCharacterModel(int x, int y){
    	this(new Point2D.Float(x, y));
    }
    

    //Getters
    public boolean isOnGround(){return onGround;}
    public boolean isGhostEmpty(){
        if(facingRight){
            return ghostEmptyRight;
        } else {
            return ghostEmptyLeft;
        }
    }
    public Point2D.Float getPosition(){
    	return position;
    }
    public boolean isMoving(){
        return moving;
    }
    public boolean isDead(){
        return dead;
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
        setGhostEmpty(ghostEmptyLeft);
    }
    public void moveRight(){
        moving = true;
        facingRight = true;
        setActiveBlock(activeBlockRight);
        setGhostEmpty(ghostEmptyRight);
    }
    
    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }
    public void setAlive(boolean dead){
        this.dead = dead;
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
    public void setGhostEmptyLeft(boolean ghostEmptyLeft){this.ghostEmptyLeft=ghostEmptyLeft;}
    public void setGhostEmptyRight(boolean ghostEmptyRight){this.ghostEmptyRight=ghostEmptyRight;}
    public void setGhostEmpty(boolean ghostEmpty){this.ghostEmpty=ghostEmpty;}

    public Water pourWater(){
        Point2D.Float point=new Point2D.Float(position.x - 64, position.y);
        if(facingRight){
            point = new Point2D.Float(position.x + 64, position.y);
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

