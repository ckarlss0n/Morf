package edu.chl.morf.controllers;

import static edu.chl.morf.handlers.Constants.*;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.handlers.BodyFactory;
import edu.chl.morf.handlers.LevelGenerator;
import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.model.Block;
import edu.chl.morf.model.EmptyBlock;
import edu.chl.morf.model.Flower;
import edu.chl.morf.model.Ground;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

import java.awt.geom.Point2D;
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
	private PlayerCharacterModel player;
	private Vector2 movementVector;
	private BodyFactory bodyFactory;
	private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	private SoundHandler soundHandler = SoundHandler.getInstance();

	public GameLogic(Level level, World world){
		this.level = level;
		this.world = world;
		bodyBlockMap = new HashMap<Body, Water>();
		levelName = level.getName();
		player = level.getPlayer();
		movementVector = new Vector2(0,0);
		bodyFactory = new BodyFactory();
		playerCharacterBody = bodyFactory.createPlayerBody(world, new Vector2(player.getPosition().x, player.getPosition().y));
		//bindWaterBlocks();
		generateLevel();
		initPressedKeys();
	}

	public void initPressedKeys(){
		pressedKeys.put(Input.Keys.LEFT, false);
		pressedKeys.put(Input.Keys.RIGHT, false);
		pressedKeys.put(Input.Keys.UP, false);
	}

	public World getWorld(){
		return world;
	}
	
	public void bindWaterToBody(Body body, Water water){
		bodyBlockMap.put(body, water);
	}

	public Body createWaterBody(Water water){
		if (water.getState() == WaterState.LIQUID){
			return bodyFactory.createWaterBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
		}
		return null;
	}

	public void setKeyState(int keyCode, boolean bool){
		pressedKeys.put(keyCode, bool);
	}

	//Convert level to a body
	public void generateLevel(){
		new LevelGenerator().generateLevel(level, world, bodyBlockMap);
	}

	public void moveLeft(){
		player.moveLeft();
		if(playerCharacterBody.getLinearVelocity().x >= 0){    //If moving right
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(-15, 0);
	}

	public void moveRight(){
		player.moveRight();
		if(playerCharacterBody.getLinearVelocity().x <= 0){    //If moving left
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(15, 0);
	}
	public void setOnGround(boolean isOnGround){
		player.setOnGround(isOnGround);
	}
	public void jump(){
		if(player.isOnGround()) {   //If standing (could be improved, also 0 at top of jump)
			playerCharacterBody.applyForceToCenter(new Vector2(0, 300), true);
		}
	}
	public boolean isLevelWon(){
		return level.isLevelWon();
	}

	public void resetGame(){

	}

	public void fly(){
		if (Math.abs(playerCharacterBody.getLinearVelocity().y) < 0.01f && Math.abs(playerCharacterBody.getLinearVelocity().x) < 0.01f) {
			movementVector = new Vector2(0, 20);
		}
	}

	public void placeWater(){
		int size = level.getWaterBlocks().size();
		level.pourWater();
		if(level.getWaterBlocks().size() > size){
			Water water = level.getWaterBlocks().get(size);
			bindWaterToBody(createWaterBody(water), water);
		}
		soundHandler.playSoundEffect(soundHandler.getPour());
	}
	
	public void heatBlock(){
		Block activeBlock = level.getPlayer().getActiveBlock();
		if(activeBlock instanceof Water){
			WaterState state = ((Water) activeBlock).getState();
			((Water) activeBlock).setBottomBlock(true);
			if(state == WaterState.SOLID) {
				soundHandler.playSoundEffect(soundHandler.getPour());
			} else if(state == WaterState.LIQUID) {
				soundHandler.playSoundEffect(soundHandler.getHeat());
			}
		}
		level.heatBlock();
	}

	public void coolBlock(){
		Block activeBlock = level.getPlayer().getActiveBlock();
		if(activeBlock instanceof Water) {
			WaterState state = ((Water) activeBlock).getState();
			if (state == WaterState.GAS) {
				soundHandler.playSoundEffect(soundHandler.getPour());
			} else if (state == WaterState.LIQUID) {
				soundHandler.playSoundEffect(soundHandler.getFreeze());
			}
		}
		level.coolBlock();
	}

	public void stop(){
		if(!(pressedKeys.get(Input.Keys.LEFT) || pressedKeys.get(Input.Keys.RIGHT))) {
			player.stop();
			movementVector = new Vector2(0, 0);
		} else if(pressedKeys.get(Input.Keys.LEFT) && !pressedKeys.get(Input.Keys.RIGHT)) {
			moveLeft();
		} else {
			moveRight();
		}
	}

	public void setActiveBodyLeft(Body body){
		if (body == null){
			level.setActiveBlockLeft(new EmptyBlock());
		}
		else if (bodyBlockMap.get(body) != null){
			Block block = bodyBlockMap.get(body);
			level.setActiveBlockLeft(block);
		}
		else{
			level.setActiveBlockLeft(new Ground());
		}
	}

	public void setActiveBodyRight(Body body){
		if (body == null){
			level.setActiveBlockRight(new EmptyBlock());
		}
		else if (bodyBlockMap.get(body) != null){
			Block block = bodyBlockMap.get(body);
			level.setActiveBlockRight(block);
		}
		else{
			level.setActiveBlockRight(new Ground());
		}
	}

	public void killPlayer(){
		level.killPlayer();
		soundHandler.playSoundEffect(soundHandler.getDie());
	}

	public boolean isPlayerDead(){
		return level.isPlayerDead();
	}
	public void setWaterBottom(Body body, boolean bottomBlock){
		Water waterBlock = bodyBlockMap.get(body);
		waterBlock.setBottomBlock(bottomBlock);
	}

	public void render(float delta){
		world.step(delta,6,2);
		if(Math.abs(playerCharacterBody.getLinearVelocity().x) < MAX_SPEED) {
			playerCharacterBody.applyForceToCenter(movementVector, true);
		}
		updateLevel();
	}

	public Point2D.Float vectorToPoint(Vector2 v){
		return new Point2D.Float(v.x, v.y);
	}

	//Update model with physics changes
	public void updateLevel(){
		Vector2 bodyPos = playerCharacterBody.getPosition();
        Vector2 bodySpeed = playerCharacterBody.getLinearVelocity();
		player.setPosition(bodyPos.x * PPM, bodyPos.y * PPM);
        player.setSpeed(bodySpeed.x,bodySpeed.y);

		for(Body waterBody : bodyBlockMap.keySet()){
			Water waterBlock = bodyBlockMap.get(waterBody);
			Vector2 waterPos = waterBody.getPosition();
			waterBlock.setPosition(waterPos.x * PPM, waterPos.y * PPM);
		}
	}
}
