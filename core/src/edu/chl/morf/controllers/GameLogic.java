package edu.chl.morf.controllers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import edu.chl.morf.handlers.BodyFactory;
import edu.chl.morf.handlers.LevelGenerator;
import edu.chl.morf.model.Block;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.Water;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.MAX_SPEED;
import static edu.chl.morf.Constants.WORLD_GRAVITY;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameLogic {

	private Level level;	//Model
	private World world;	//Box2D world
	private Map<Body, Water> bodyBlockMap;	//Bind bodies to blocks in model
	private Body playerCharacterBody;
	private TiledMapTileLayer tiledMapTileLayer;
	private String levelName;
	private PlayerCharacterModel playerCharacterModel;
	private Vector2 movementVector;
	private BodyFactory bodyFactory;

	public GameLogic(Level level){
		this.level = level;
		world = new World(WORLD_GRAVITY, true);
		bodyBlockMap = new HashMap<Body, Water>();
		levelName = level.getName();
		playerCharacterModel = level.getPlayer();
		movementVector = new Vector2(0,0);
		bodyFactory = new BodyFactory();
		playerCharacterBody = createPlayerBody();
		//bindWaterBlocks();
		generateLevel();
	}

	public void bindWaterBlocks(){
		ArrayList<Water> waterList = level.getWaterBlocks();
		for(Water water : waterList){
			bodyBlockMap.put(createWaterBody(), water);
		}
	}

	public Body createWaterBody(){
		return bodyFactory.createWaterBody(world, new Vector2(0, 0));
	}

	public Body createPlayerBody(){
		return bodyFactory.createPlayerBody(world);
	}

	//Convert level to a body
	public void generateLevel(){
		new LevelGenerator().generateLevel(level, world);
	}

	public void moveLeft(){
		playerCharacterModel.moveLeft();
		if(playerCharacterBody.getLinearVelocity().x >= 0){    //If moving right
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(-15, 0);
	}

	public void moveRight(){
		playerCharacterModel.moveRight();
		if(playerCharacterBody.getLinearVelocity().x <= 0){    //If moving left
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(15, 0);
	}

	public void jump(){
		if(Math.abs(playerCharacterBody.getLinearVelocity().y) < 0.01f) {   //If standing (could be improved, also 0 at top of jump)
			playerCharacterBody.applyForceToCenter(new Vector2(0, 300), true);
		}
	}

	public void resetGame(){

	}

	public void fly(){
		if (Math.abs(playerCharacterBody.getLinearVelocity().y) < 0.01f && Math.abs(playerCharacterBody.getLinearVelocity().x) < 0.01f) {
			movementVector = new Vector2(0, 20);
		}
	}

	public void placeWater(){
		level.pourWater();
	}

	public void stop(){
		playerCharacterModel.stop();
		movementVector = new Vector2(0, 0);
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
		if(Math.abs(playerCharacterBody.getLinearVelocity().x) < MAX_SPEED) {
			playerCharacterBody.applyForceToCenter(movementVector, true);
		}
		updateLevel();
	}

	public Point vectorToPoint(Vector2 v){
		return new Point((int)v.x, (int)v.y);
	}

	//Update model with physics changes
	public void updateLevel(){
		Vector2 bodyPos = playerCharacterBody.getPosition();
		playerCharacterModel.setPosition(vectorToPoint(bodyPos));

		for(Body waterBody : bodyBlockMap.keySet()){
			Water waterBlock = bodyBlockMap.get(waterBody);
			Vector2 waterPos = waterBody.getPosition();
			waterBlock.setPosition(vectorToPoint(waterPos));
		}
	}
}
