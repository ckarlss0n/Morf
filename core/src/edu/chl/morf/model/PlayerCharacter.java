package edu.chl.morf.model;

import java.awt.geom.Point2D;

/**
 * Class for representing a player character.
 * A PlayerCharacter has a position, movement and an amount of remaining water.
 * A PlayerCharacter also has information of how it's moving, 
 * what it's doing and the surrounding environment.
 * 
 * @author Harald
 */
public class PlayerCharacter {
    private Point2D.Float position;
    private Point2D.Float movementVector;
    private int waterAmount;
	
    private boolean moving;
    private boolean facingRight;
    private boolean onGround;
    private boolean onIce;
    private boolean insideFlower;
    private boolean flyingEnabled;
    private boolean dead;
    
    private boolean flying;
    private boolean stoppedFlying;
    
    private boolean pouringWater;
    private boolean coolingWater;
    private boolean heatingWater;
    
    private Block activeBlockRight;
    private Block activeBlockLeft;
    private Block activeBlockBottomRight;
    private Block activeBlockBottomLeft;
    private Block activeBlockBottom;
    private Block activeBlock;
    private boolean ghostEmptyRight;
    private boolean ghostEmptyLeft;
    private boolean ghostEmpty;


    //Constructors
    public PlayerCharacter(Point2D.Float position, int waterAmount){
    	this.position = position;
    	movementVector = new Point2D.Float(0,0);
    	this.waterAmount = waterAmount;
    	
    	moving = false;
        facingRight = true;
        onGround = false;
        onIce = false;
        insideFlower = false;
        flyingEnabled = false;
        dead = false;
        
        flying = false;
        stoppedFlying = false;
        
        pouringWater = false;
        coolingWater = false;
        heatingWater = false;

        activeBlockRight = EmptyBlock.getEmptyBlock();
        activeBlockLeft = EmptyBlock.getEmptyBlock();
        activeBlockBottomLeft = EmptyBlock.getEmptyBlock();
        activeBlockBottomRight = EmptyBlock.getEmptyBlock();
        activeBlockBottom = EmptyBlock.getEmptyBlock();
        activeBlock = EmptyBlock.getEmptyBlock();
        
        ghostEmptyRight = true;
        ghostEmptyLeft = true;
        ghostEmpty = true;
    }

    public PlayerCharacter(int x, int y, int waterAmount){
        this(new Point2D.Float(x, y), waterAmount);
    }

    //Getters
    public Point2D.Float getPosition(){
        return position;
    }
    public Point2D.Float getMovementVector(){
        return movementVector;
    }
    public int getWaterAmount(){
        return waterAmount;
    }
    
    public boolean isMoving(){
        return moving;
    }
    public boolean isFacingRight(){
        return facingRight;
    }
    public boolean isOnGround(){
    	return onGround;
    }
    public boolean isOnIce(){
    	return onIce;
    }
    public boolean isInsideFlower(){
    	return insideFlower;
    }
    public boolean isFlyingEnabled(){
    	return flyingEnabled;
    }
    public boolean isDead(){
        return dead;
    }
    
    public boolean isFlying(){
        return this.flying;
    }
    public boolean stoppedFlying(){
        return this.stoppedFlying;
    }
    
    public boolean isPouringWater(){
        return this.pouringWater;
    }
    public boolean isCoolingWater(){
        return this.coolingWater;
    }
    public boolean isHeatingWater(){
        return this.heatingWater;
    }
    
    public Block getActiveBlockBottom(){
    	return activeBlockBottom;
    }
    public Block getActiveBlock(){
        return activeBlock;
    }
    public boolean isGhostEmpty(){
    	return ghostEmpty;
    }
    
    //Setters
    public void setPosition(float x, float y){
        position.setLocation(x, y);
    }
    public void setSpeed(float x, float y){
        movementVector.setLocation(x, y);
    }
    
    ////////////////////////////////////////////////////////////
    //Should be chain called from level
    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }
    public void setOnIce(boolean onIce){
    	this.onIce=onIce;
    }
    //////////////////////////////////////////////////////////////
    public void setInsideFlower(boolean insideFlower){
    	this.insideFlower = insideFlower;
    }
    public void setFlyingEnabled(boolean flyingEnabled){
    	this.flyingEnabled=flyingEnabled;
    }
    public void setDead(boolean dead){
        this.dead = dead;
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

    //Move setters
    public void moveLeft(){
        moving = true;
        stopFlying();
        facingRight = false;
        setActiveBlock(activeBlockLeft);
        setGhostEmpty(ghostEmptyLeft);
        activeBlockBottom = activeBlockBottomLeft;
    }
    public void moveRight(){
        moving = true;
        stopFlying();
        facingRight = true;
        setActiveBlock(activeBlockRight);
        setGhostEmpty(ghostEmptyRight);
        activeBlockBottom = activeBlockBottomRight;
    }
    public void stop(){
        moving = false;
    }
    
    ////////////////////////////////////////////////////
    //should be chain called from level
    public void setFlying(){
        flying = true;
    }
    public void stopFlying(){
        if(flying){
            stoppedFlying = true;
        }
        this.flying = false;
    }
    ///////////////////////////////////////////////////////
    public void doneFlying(){
        this.stoppedFlying = false;
    }
    
    //Action setters
    public Water pourWater(){
        pouringWater = true;
        Point2D.Float point=new Point2D.Float(position.x - 64+15, position.y);
        if(facingRight){
            point = new Point2D.Float(position.x + 64-15, position.y);
        }
        decreaseWaterAmount();
        return new Water(point);
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
    
    public void stopPouring(){
        pouringWater = false;
    }
    public void stopCooling(){
        coolingWater = false;
    }
    public void stopHeating(){
        heatingWater = false;
    }

    //Water amount decrease
    public void decreaseWaterAmount(){
        waterAmount = waterAmount - 1;
        if(waterAmount < 1){
            dead = true;
        }
    }
}

