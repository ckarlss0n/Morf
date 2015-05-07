package edu.chl.morf.controllers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import edu.chl.morf.model.Block;
import edu.chl.morf.model.Level;

import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.WORLD_GRAVITY;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameLogic {

	private Level level;	//Model
	private World world;	//Box2D world
	private Map<Body, Block> bodyBlockMap;	//Bind bodies to blocks in model
	private Body playerCharacterBody;
	private TiledMapTileLayer tiledMapTileLayer;

	public GameLogic(Level level){
		this.level = level;
		world = new World(WORLD_GRAVITY, true);
		bodyBlockMap = new HashMap<Body, Block>();
		//levelName = level.getName();
		//tiledMapTileLayer = new TmxMapLoader().load(LEVEL_PATH + levelName);
		createPlayerCharacter();
	}

	public void createPlayerCharacter(){

	}

	public void generateLevel(){

	}

	public void moveLeft(){

	}

	public void moveRight(){

	}

	public void jump(){

	}

	public void resetGame(){

	}

	public void fly(){

	}

	public void placeWater(){

	}

	public void placeSpikes(){

	}

	public void stop(){

	}

	public void setActiveBodyLeft(Body bodyLeft){
		Block activeBlockLeft=bodyBlockMap.get(bodyLeft);
		level.setActiveBlockLeft(activeBlockLeft);
	}

	public void setActiveBodyRight(Body bodyRight){
		Block activeBlockRight=bodyBlockMap.get(bodyRight);
		level.setActiveBlockRight(activeBlockRight);
	}

	public void setActiveBodyLeftEmpty(){
		level.setActiveBlockLeftEmpty();
	}

	public void setActiveBodyRightEmpty(){
		level.setActiveBlockRightEmpty();
	}

	public void killPlayer(){
		level.killPlayer();
	}

	public void render(float delta){
		world.step(delta,6,2);
		updateLevel();
	}

	//Update model with physics changes
	public void updateLevel(){

	}
}
