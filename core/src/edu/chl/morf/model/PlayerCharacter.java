package edu.chl.morf.model;

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
public class PlayerCharacter {
    private boolean facingRight;
    private boolean moving;
    private boolean onGround;
    private boolean onIce;
    private boolean dead;
    private boolean flyingEnabled;
    private int waterLevel;
    private Block activeBlockRight;
    private Block activeBlockLeft;
    private Block activeBlockBottomRight;
    private Block activeBlockBottomLeft;
    private Block activeBlockBottom;
    private Block activeBlock;
    private boolean ghostEmptyRight;
    private boolean ghostEmptyLeft;
    private boolean ghostEmpty;
    private boolean pouringWater;
    private boolean coolingWater;
    private boolean heatingWater;
    private boolean flying;
    private boolean stoppedFlying;
    private boolean insideFlower;

    private Point2D.Float position;
    private Point2D.Float speed;

    //Constructors
    public PlayerCharacter(){
        facingRight = true;
        moving = false;
        onGround = true;
        dead = false;
        activeBlockRight = new EmptyBlock();
        activeBlockLeft = new EmptyBlock();
        activeBlockBottomLeft = new EmptyBlock();
        activeBlockBottomRight = new EmptyBlock();
        activeBlockBottom = new EmptyBlock();
        activeBlock = new EmptyBlock();
        speed = new Point2D.Float(0,0);
        ghostEmptyRight = true;
        ghostEmptyLeft = true;
        ghostEmpty = true;
        waterLevel = 10;
    }
    public PlayerCharacter(Point2D.Float position){
        this();
        this.position = position;
    }
    public PlayerCharacter(int x, int y){
        this(new Point2D.Float(x, y));
    }


    //Getters
    public boolean isOnGround(){
    	return onGround;
    }
    public boolean isGhostEmpty(){
    	return ghostEmpty;
    }
    public Point2D.Float getPosition(){
        return position;
    }
    public boolean isFlyingEnabled(){
    	return flyingEnabled;
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
    public boolean isOnIce(){
    	return onIce;
    }
    public boolean isInsideFlower(){
    	return insideFlower;
    }

    public int getWaterLevel(){
        return waterLevel;
    }
    public Block getActiveBlock(){
        return activeBlock;
    }
    public Block getActiveBlockBottom(){
    	return activeBlockBottom;
    }
    public Point2D.Float getSpeed(){
        return speed;
    }

    //Move setters
    public void moveLeft(){
        moving = true;
        stopFlying();
        facingRight = false;
        setActiveBlock(activeBlockLeft);
        setGhostEmpty(ghostEmptyLeft);
        activeBlockBottom=activeBlockBottomLeft;
    }
    public void moveRight(){
        moving = true;
        stopFlying();
        facingRight = true;
        setActiveBlock(activeBlockRight);
        setGhostEmpty(ghostEmptyRight);
        activeBlockBottom=activeBlockBottomRight;
    }

    public void setOnIce(boolean onIce){
    	this.onIce=onIce;
    }
    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }
    public void setDead(boolean dead){
        this.dead = dead;
    }
    public void setFlyingEnabled(boolean flyingEnabled){
    	this.flyingEnabled=flyingEnabled;
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
    public void setGhostEmptyLeft(boolean ghostEmptyLeft){
    	this.ghostEmptyLeft = ghostEmptyLeft;
    	if(!facingRight){
    		ghostEmpty = ghostEmptyLeft;
    	}
    }
    public void setGhostEmptyRight(boolean ghostEmptyRight){
    	this.ghostEmptyRight = ghostEmptyRight;
    	if(facingRight){
    		ghostEmpty = ghostEmptyRight;
    	}
    }
    public void setGhostEmpty(boolean ghostEmpty){
    	this.ghostEmpty = ghostEmpty;
    }
    public void setInsideFlower(boolean insideFlower){
    	this.insideFlower = insideFlower;
    }
    public void stopPouring(){
        pouringWater = false;
    }
    public void stopCooling(){
        coolingWater = false;
    }
    public void stopHeating(){
        heatingWater = false;
    }
    public void setFlying(){
        this.flying = true;
    }
    public void stopFlying(){
        if(flying){
            this.stoppedFlying = true;
        }
        this.flying = false;
    }
    public void doneFlying(){
        this.stoppedFlying = false;
    }
    public boolean isPouringWater(){
        return this.pouringWater;
    }
    public boolean isHeatingWater(){
        return this.heatingWater;
    }
    public boolean isCoolingWater(){
        return this.coolingWater;
    }
    public boolean isFlying(){
        return this.flying;
    }
    public boolean stoppedFlying(){
        return this.stoppedFlying;
    }

    public Water pourWater(){
        pouringWater = true;
        Point2D.Float point=new Point2D.Float(position.x - 64+15, position.y);
        if(facingRight){
            point = new Point2D.Float(position.x + 64-15, position.y);
        }
        decreaseWaterLevel();
        return new Water(point);
    }

    //WaterLevel setters
    public void decreaseWaterLevel(){
        waterLevel = waterLevel - 1;
        if(waterLevel < 1){
            dead = true;
        }
    }
    
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setActiveBlock(Block block){
        activeBlock = block;
    }
    public void setActiveBlock(Block block, ActiveBlockPosition position){
        if(position == ActiveBlockPosition.LEFT){
            activeBlockLeft = block;
            if(!facingRight){
                activeBlock=block;
            }
        }else if(position == ActiveBlockPosition.RIGHT){
            activeBlockRight = block;
            if(facingRight){
                activeBlock=block;
            }
        }else if(position == ActiveBlockPosition.BOTTOM_LEFT){
            activeBlockBottomLeft = block;
            if(!facingRight){
                activeBlockBottom = activeBlockBottomLeft;
            }
        }else if(position == ActiveBlockPosition.BOTTOM_RIGHT){
            activeBlockBottomRight = block;
            if(facingRight){
                activeBlockBottom = activeBlockBottomRight;
            }
        }else if(position == ActiveBlockPosition.BOTTOM){
            activeBlockBottom = block;
        }
    }

    //Methods for manipulating active block
    public void heatActiveBlock(){
        Water activeWater;
        if(activeBlock instanceof EmptyBlock){
            if(activeBlockBottom instanceof Water){
                activeWater = (Water)activeBlockBottom;
                WaterState state = activeWater.getState();
                heatingWater = state == WaterState.SOLID || state == WaterState.LIQUID;
            }
            activeBlockBottom.heat();
        }
        else if(activeBlock instanceof Water) {
            activeWater = (Water)activeBlock;
            WaterState state = activeWater.getState();
            heatingWater = state == WaterState.SOLID || state == WaterState.LIQUID;
            activeBlock.heat();
        }
    }
    public void coolActiveBlock(){
        Water activeWater;
        if(activeBlock instanceof EmptyBlock){
            if (activeBlockBottom instanceof Water){
                activeWater = (Water)activeBlockBottom;
                WaterState state = activeWater.getState();
                coolingWater = state == WaterState.GAS || state == WaterState.LIQUID;
            }
            activeBlockBottom.cool();
        }
        else if(activeBlock instanceof Water) {
            activeWater = (Water)activeBlock;
            WaterState state = activeWater.getState();
            coolingWater = state == WaterState.GAS || state == WaterState.LIQUID;
            activeBlock.cool();
        }
    }
}

