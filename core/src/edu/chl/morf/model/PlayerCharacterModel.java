package edu.chl.morf.model;

import static edu.chl.morf.Constants.*;
/**
 * Created by Harald on 2015-04-28.
 * Keeps track of information about the PlayerCharacter
 * The PlayerCharacter class updates the information stored
 * The Act method in GameScreen will use the information
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

