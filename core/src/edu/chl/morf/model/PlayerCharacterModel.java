package edu.chl.morf.model;

import static edu.chl.morf.Constants.*;

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
    public PlayerCharacterModel(){

    }
    private boolean emptyRight=true;
    private boolean emptyLeft=true;
    private boolean facingRight=true;
    private boolean moving=false;
    private boolean alive = true;
    private int waterLevel=WATER_LEVEL;

    //Getters
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
    public void stop(){
        moving=false;
    }

    //WaterLevel setters
    public void decreaseWaterLevel(){
        waterLevel=waterLevel-1;
    }
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}
