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

	public Level(Matrix matrix, String name){
		this.name = name;
		levelMatrix = matrix;
		waterBlocks = new ArrayList<Water>();
		player = new PlayerCharacterModel();
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
	
	//Method for pouring water
	public void pourWater(){
		addWater(player.pourWater());
	}
	
	//Methods for heating and cooling blocks
	public void heat(){
		player.heatActiveBlock();
	}
	public void cool(){
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
