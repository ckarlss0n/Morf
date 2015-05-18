package edu.chl.morf.model;

import static edu.chl.morf.Constants.GROUND_FRICTION;
import static edu.chl.morf.Constants.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


/*
 * Class for representing a level.
 * A level consists of a matrix containing solid tiles and a list containing the water in the level.
 */
public class Level {

	private String name;
	private ArrayList<Water> waterBlocks;
	private PlayerCharacterModel player;
	private Matrix levelMatrix;
	private boolean levelWon = false;

	public Level(Matrix matrix, String name, PlayerCharacterModel player, ArrayList<Water> waterBlocks){
		this.name = name;
		levelMatrix = matrix;
		this.waterBlocks = waterBlocks;
		this.player = player;
	}
	
	//Getters
	public Matrix getMatrix(){
		return levelMatrix;
	}
	public ArrayList<Water> getWaterBlocks(){
		return waterBlocks;
	}
	public PlayerCharacterModel getPlayer(){
		return player;
	}

	//Setters
	public void setActiveBlockLeft(Block block){
			player.setActiveBlockLeft(block);
	}
	public void setActiveBlockRight(Block block){
			player.setActiveBlockRight(block);
	}

	//Method for killing the player
	public void killPlayer(){
		player.setAlive(false);
	}

	//Method for pouring water
	public void pourWater(){
		if (player.getWaterLevel()!=0){
			if(player.getActiveBlock() instanceof Flower){
				levelWon = true;
			}
			else if(player.isGhostEmpty()){
				addWater(player.pourWater());
			}
		}

	}
	public void setGhostEmptyLeft(boolean ghostEmptyLeft){
		player.setGhostEmptyLeft(ghostEmptyLeft);
	}
	public void setGhostEmptyRight(boolean ghostEmptyRight){
		player.setGhostEmptyRight(ghostEmptyRight);
	}

	public boolean isLevelWon(){
		return levelWon;
	}
	public boolean isPlayerDead(){
		return player.isDead();
	}
	
	//Methods for heating and cooling blocks
	public void heatBlock(){
		player.heatActiveBlock();
	}
	public void coolBlock(){
		player.coolActiveBlock();
	}
	
	//Methods for adding and removing Water
	public void addWater(Water w){
		waterBlocks.add(w);
	}
	public void removeWater(Water w){
		waterBlocks.remove(w);
	}
    public String getName(){
        return this.name;
    }
}
