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
	
	private TileType[][] levelMatrix;
	private ArrayList<Water> waterBlocks;
	private PlayerCharacterModel player;

	public Level(TileType[][] matrix){
		levelMatrix = matrix;
		waterBlocks = new ArrayList<Water>();
	}
	
	public TileType[][] getMatrix(){
		return levelMatrix;
	}
	
	public ArrayList<Water> getWaterBlocks(){
		return waterBlocks;
	}
	
	public void addWater(Water w){
		waterBlocks.add(w);
	}
	
	public void removeWater(Water w){
		waterBlocks.remove(w);
	}
}
