package edu.chl.morf.model;

import java.util.List;

/*
 * Class for representing a level.
 * A level consists of a matrix containing the static tiles in a level,
 * a list containing all the water objects in the level and a flower object.
 * A level also contains a PlayerCharacter that moves around in the level.
 */
public class Level {

	private String name;
	private List<Water> waterBlocks;
	private PlayerCharacter player;
	private Matrix levelMatrix;
	private boolean levelWon;
	private Flower flower;
	private int waterLevel;

	public Level(Matrix matrix, String name, PlayerCharacter player, List<Water> waterBlocks, Flower flower, int waterLevel){
		this.name = name;
		levelMatrix = matrix;
		this.waterBlocks = waterBlocks;
		this.player = player;
		this.flower = flower;
		this.waterLevel = waterLevel;
		levelWon = false;
		player.setWaterLevel(waterLevel);
	}

	//Getters
	public String getName(){
		return name;
	}
	public Matrix getMatrix(){
		return levelMatrix;
	}
	public List<Water> getWaterBlocks(){
		return waterBlocks;
	}
	public PlayerCharacter getPlayer(){
		return player;
	}
	public Flower getFlower(){
		return flower;
	}
	public boolean isLevelWon(){
		return levelWon;
	}
	public int getWaterLevel(){
		return waterLevel;
	}

	//Setter for levelWon
	public void setLevelWon(boolean levelWon){
		this.levelWon=levelWon;
	}

	//Methods for adding and removing Water in Level
	public void addWater(Water w){
		waterBlocks.add(w);
	}
	//////////////////////////////////////////////////
	//Method never called, should be called when vapor disappears?
	public void removeWater(Water w){
		waterBlocks.remove(w);
	}
	
	//Method for pouring water in Level
	public void pourWater(){
		//Level is won if player pours water on flower
		if(player.getActiveBlock() instanceof Flower){
			levelWon = true;
		}
		else if(player.isInsideFlower()){
			levelWon = true;
		}
		//Make player pour water and add it to level if empty space in front of player
		else if(player.isGhostEmpty()){
			addWater(player.pourWater());
		}
	}

	//Method for killing the player
	public void killPlayer(){
		player.setDead(true);
	}
	
	//Methods for making player heat and cool its active block
	public void heatBlock(){
		player.heatActiveBlock();
	}
	public void coolBlock(){
		player.coolActiveBlock();
	}
	
	//Getters for booleans in PlayerCharacter
	public boolean isPlayerDead(){
		return player.isDead();
	}
	public boolean isPlayerFlyingEnabled(){
		return player.isFlyingEnabled();
	}
	//////////////////////////////////////////////////////////////
	//method never called
	public boolean isPlayerInsideFlower(){
		return player.isInsideFlower();
	}
	
	//Setters for booleans in PlayerCharacter
	public void setActiveBlock(Block block, ActiveBlockPosition position){
		player.setActiveBlock(block, position);
	}
	public void setPlayerGhostEmptyLeft(boolean ghostEmptyLeft){
		player.setGhostEmptyLeft(ghostEmptyLeft);
	}
	public void setPlayerGhostEmptyRight(boolean ghostEmptyRight){
		player.setGhostEmptyRight(ghostEmptyRight);
	}
	public void setPlayerFlyingEnabled(boolean flyingEnabled){
		player.setFlyingEnabled(flyingEnabled);
	}
	public void setPlayerInsideFlower(boolean playerInsideFlower){
		player.setInsideFlower(playerInsideFlower);
	}
}
