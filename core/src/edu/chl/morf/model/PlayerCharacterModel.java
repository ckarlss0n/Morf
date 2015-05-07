package edu.chl.morf.model;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import edu.chl.morf.Constants;

import static edu.chl.morf.Constants.*;

import java.awt.Point;

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
	private boolean emptyRight=true;
    private boolean emptyLeft=true;
    private boolean facingRight=true;
    private boolean moving=false;
    private boolean inAir=false;
    private boolean alive = true;
    private int waterLevel=WATER_LEVEL;
    private Block activeBlock;
    
    private Point position;
    
    //Constructors
    public PlayerCharacterModel(){
    	position = new Point(0, 0);
    }
    public PlayerCharacterModel(Point position){
    	this.position = position;
    }
    public PlayerCharacterModel(int x, int y){
    	position = new Point(x, y);
    }
    

    //Getters
    public Point getPosition(){
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

    public boolean isInAir() {
        return inAir;
    }

    //Ghost setters
    public void setEmptyRight(boolean emptyRight){
        this.emptyRight=emptyRight;
    }
    public void setEmptyLeft(boolean emptyLeft) {
        this.emptyLeft = emptyLeft;
    }

    //Move setters
    public void moveLeft(){
        moving=true;
        facingRight=false;
    }
    public void moveRight(){
        moving=true;
        facingRight=true;
    }
    public void setInAir(boolean inAir){
        this.inAir=inAir;
    }
    public void setAlive(boolean alive){
        this.alive=alive;
    }
    public void stop(){
        moving=false;
    }
    public void setPosition(Point position){
        this.position.setLocation(position);
    }

    public Water pourWater(){
    	if(activeBlock instanceof EmptyBlock){
            Point point=new Point(position.x-36,position.y);
            if(facingRight){
                point = new Point(position.x+36*2,position.y);
            }
            return new Water(point);
        }

        return new Water(position);
    }
    
    //WaterLevel setters
    public void decreaseWaterLevel(){
        waterLevel=waterLevel-1;
    }
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setActiveBlock(Block block){
        this.activeBlock = block;
    }

    //Methods for manipulating active block
    public void heatActiveBlock(){
        activeBlock.heat();
    }
    public void coolActiveBlock(){
        activeBlock.cool();
    }
}

