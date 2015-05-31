package edu.chl.morf.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import edu.chl.morf.handlers.*;
import edu.chl.morf.model.*;
import edu.chl.morf.controllers.collision.CollisionData;
import edu.chl.morf.controllers.collision.CollisionType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static edu.chl.morf.handlers.Constants.*;

/**
 * This class is used for updating and affecting the world with physical changes, using Box2D.
 * The class contains all bodies, which can be interacted with in the world.
 * GameLogic can be seen as the main controller of the game, updating different states, and playing sounds, for example.
 * <p>
 * It is also responsible for updating the framework independent model, which later is rendered in the view.
 * <p>
 * Created by Christoffer on 2015-05-06.
 */
public class GameLogic {

	public static final float MOVEMENT_SPEED = 15;
	public static final float JUMP_HEIGHT = 300;
	public static final float FLYING_SPEED = 2.3f;
    public static final int MAX_SPEED = 2;
	private Level level;                                                            //Model
	private World world;                                                            //Box2D world
	private Map<Body, Water> bodyBlockMap;                                          //Each body has a corresponding water block
	private Body playerCharacterBody;                                               //The body of the player character
	private PlayerCharacter player;                                            //The model of the player character
	private Vector2 movementVector;                                                 //The movement vector applied at every tick
	private Vector2 flyVector;                                                      //The vector applied when flying
	private BodyFactory bodyFactory;
	private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();    //Know which keys are pressed
	private SoundHandler soundHandler = SoundHandler.getInstance();
	private KeyBindings keyBindings = KeyBindings.getInstance();
	private boolean gamePaused;
	private Map<Block, Body> gasBlockBodyMap;                                       //Each gas block has a body
	private Map<Body, List<Timer.Task>> gasBodyTaskMap;                             //Each gas body has two countdown timers

	public GameLogic(Level level, World world) {
		this.level = level;
		this.world = world;
		bodyBlockMap = new HashMap<Body, Water>();
		player = level.getPlayer();
		movementVector = new Vector2(0, 0);
		flyVector = new Vector2(0, FLYING_SPEED);
		bodyFactory = new BodyFactory();
		gamePaused = false;
		gasBlockBodyMap = new HashMap<Block, Body>();
		gasBodyTaskMap = new HashMap<Body, List<Timer.Task>>();
		setUpLevel();
	}

	//Method used to set up a new level, resetting and initializing all necessary things
	public void setUpLevel() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			world.destroyBody(body);
		}
		playerCharacterBody = bodyFactory.createPlayerBody(world, new Vector2(player.getPosition().x, player.getPosition().y));
        new LevelGenerator().generateLevel(level, world, bodyBlockMap);
		initPressedKeys();
		player.setOnGround(false);
	}

	//Method used to update and change the current level being played
	public void changeLevel(Level level) {
		this.level = level;
		player = level.getPlayer();
		bodyBlockMap.clear();
		setUpLevel();
	}

	public void pauseGame() {
		gamePaused = true;
	}

	public void resumeGame() {
		gamePaused = false;
		movementVector = new Vector2(0, 0);
		player.stop();
		initPressedKeys();
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	//Reset and initialize the pressedKeys hashMap
	public void initPressedKeys() {
		pressedKeys.clear();
		pressedKeys.put(Input.Keys.valueOf(keyBindings.getMoveLeftKey()), false);
		pressedKeys.put(Input.Keys.valueOf(keyBindings.getMoveRightKey()), false);
		pressedKeys.put(Input.Keys.valueOf(keyBindings.getJumpKey()), false);
	}

	//Update the pressedKeys hashMap, setting a boolean value for a special keyCode
	public void setKeyState(int keyCode, boolean bool) {
		pressedKeys.put(keyCode, bool);
	}

	//START OF METHODS USED FOR PLAYER CHARACTER MOVEMENT
	//Method used to move the player character to the left
	public void moveLeft() {
		player.moveLeft();
		if (playerCharacterBody.getLinearVelocity().x > 0) {    //If moving right
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(-MOVEMENT_SPEED, 0);
	}

	//Method used to move the player character to the right
	public void moveRight() {
		player.moveRight();
		if (playerCharacterBody.getLinearVelocity().x < 0) {    //If moving left
			playerCharacterBody.setLinearVelocity(new Vector2(0, playerCharacterBody.getLinearVelocity().y));
		}
		movementVector = new Vector2(MOVEMENT_SPEED, 0);
	}

	//Method used to make the player character jump
	public void jump() {
		player.stopFlying();
		if (player.isOnGround()) {
			playerCharacterBody.applyForceToCenter(new Vector2(0, JUMP_HEIGHT), true);
		}
	}

	//Method used to make the player character fly
	public void fly() {
		if (level.isPlayerFlyingEnabled()) {
			player.setFlying();
			movementVector = new Vector2(0, 0);
		}
	}

	//Method used to make the player character stop. Runs the stop method with the normal slowFactor.
	public void stop() {
		player.stopFlying();
		stop(3); //Stop with normal slowFactor
	}

	//Method used to make the player character stop (used to prevent sliding)
	public void stop(float slowFactor) {
		if (!(pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveLeftKey())) || pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveRightKey())))) {
			player.stop();
			player.stopFlying();
			if (player.isOnGround() && !player.isOnIce()) {
				playerCharacterBody.setLinearVelocity(playerCharacterBody.getLinearVelocity().x / slowFactor, playerCharacterBody.getLinearVelocity().y / slowFactor);
			}
			movementVector = new Vector2(0, 0);
		} else if (pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveLeftKey())) && !pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveRightKey()))) {
			moveLeft();
		} else if (!pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveLeftKey())) && pressedKeys.get(Input.Keys.valueOf(keyBindings.getMoveRightKey()))) {
			moveRight();
		}
	}
	//END OF METHODS USED FOR PLAYER CHARACTER MOVEMENT

	public void setOnGround(boolean isOnGround) {
		player.setOnGround(isOnGround);
	}

	public boolean isLevelWon() {
		if (level.isLevelWon()) {
            HighScores highScores = HighScores.getInstance();
			if (player.getWaterAmount() > highScores.getHighScore(level.getName())) {
				highScores.addHighScore(level.getName(), player.getWaterAmount());
			}
		}
		return level.isLevelWon();
	}

	public void setLevelWon(boolean levelWon) {
		level.setLevelWon(levelWon);
	}

	public WaterState getWaterState(Body body) {
		Water waterBlock = bodyBlockMap.get(body);
		return waterBlock.getState();
	}

	public boolean isFlyingEnabled() {
		return level.isPlayerFlyingEnabled();
	}

	public void setFlyingEnabled(boolean flyingEnabled) {
		level.setPlayerFlyingEnabled(flyingEnabled);
	}

	public void setIntersectsFlower(Body body, boolean intersectsFlower) {
		if (bodyBlockMap.get(body) != null) {
			bodyBlockMap.get(body).setIntersectsFlower(intersectsFlower);
		}
	}

	//Method used to place a water block in the world
	public void placeWater() {
		int size = level.getWaterBlocks().size();
		level.pourWater();
		if (level.getWaterBlocks().size() > size) {
			Water water = level.getWaterBlocks().get(size);
            Body waterBody = bodyFactory.createWaterBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
            bodyBlockMap.put(waterBody, water);
            soundHandler.playSoundEffect(soundHandler.getPour());
		}
	}

	public void heatBlock() {
		Block activeBlock = level.getPlayer().getActiveBlock();
		if (activeBlock instanceof EmptyBlock) {    //If normal active block is empty, get active block below
			activeBlock = level.getPlayer().getActiveBlockBottom();
		}

		//Sound effects
		if (activeBlock instanceof Water) {
			WaterState state = ((Water) activeBlock).getState();
			if (state == WaterState.SOLID) {
				soundHandler.playSoundEffect(soundHandler.getPour());
				if (((Water) activeBlock).getIntersectsFlower()) {
					setLevelWon(true);
				}
			} else if (state == WaterState.LIQUID) {
				soundHandler.playSoundEffect(soundHandler.getHeat());
			}
		}

		level.heatBlock();

		if (activeBlock instanceof Water) {
			Water activeWater = (Water) activeBlock;
			for (Body body : bodyBlockMap.keySet()) {
				if (bodyBlockMap.get(body) == activeBlock) {
					body.getFixtureList().get(0).setFilterData(getFilter(activeWater));

					//If water is heated and turns into gas
					if (activeWater.getState() == WaterState.GAS) {

							/*
							Bind the gas block to it's body, if not already existing in map.
							Also apply flying and removal tasks if not already applied.
							*/
						if (!gasBlockBodyMap.containsKey(activeBlock) && !gasBodyTaskMap.containsKey(body)) {
							gasBlockBodyMap.put(activeBlock, body);
							flyAndRemove(body, 1, 3);    //Make body fly and disappear using tasks
							body.setGravityScale(0f); //Body no longer affected by gravity
						}
					}
					break;
				}
			}
		}
	}

	/*
	If a block is in the gas state it is affected by two tasks.
	The first task makes the gas block rise, and the second task removes the gas block from the world.
	If a gas block however is cooled before those two tasks are finished, it should be turned into water, thus cancelling the two tasks.
	 */
	public void resetBody(Body body) {
		body.setGravityScale(1);    //Body should be affected by gravity as usual
		body.setLinearVelocity(0, 0);

		//Cancel the the two tasks linked to the body
		for (Timer.Task task : gasBodyTaskMap.get(body)) {
			task.cancel();
		}
		gasBodyTaskMap.remove(body); //Remove body from gasBodyTaskMap, as it no longer has the tasks, due to changed water state.
	}

	/*
	Add two tasks to a body (normally the body of the active block)
	 */
	public void flyAndRemove(final Body body, float flyDelay, float destroyDelay) {
		List<Timer.Task> timerTaskList = new LinkedList<Timer.Task>();

		//Make body fly
		Timer.Task waitAndFlyTask = new Timer.Task() {
			@Override
			public void run() {
				body.setLinearVelocity(0, 2);
			}
		};

		//Remove body
		Timer.Task waitAndRemoveTask = new Timer.Task() {
			@Override
			public void run() {
				level.getWaterBlocks().remove(bodyBlockMap.get(body));
				gasBlockBodyMap.remove(bodyBlockMap.get(body));
				bodyBlockMap.remove(body);
				gasBodyTaskMap.remove(body);
				world.destroyBody(body);
			}
		};

		Timer.schedule(waitAndFlyTask, flyDelay);
		Timer.schedule(waitAndRemoveTask, destroyDelay);

		timerTaskList.add(waitAndFlyTask);
		timerTaskList.add(waitAndRemoveTask);

		gasBodyTaskMap.put(body, timerTaskList);
	}

	public void coolBlock() {
		Block activeBlock = level.getPlayer().getActiveBlock();
		if (activeBlock instanceof EmptyBlock) {
			activeBlock = level.getPlayer().getActiveBlockBottom();
		}
		if (activeBlock instanceof Water) {
			WaterState state = ((Water) activeBlock).getState();
			if (state == WaterState.GAS) {
				soundHandler.playSoundEffect(soundHandler.getPour());
				if (((Water) activeBlock).getIntersectsFlower()) {
					setLevelWon(true);
				}
				//If the active block is of type gas, it should be reset upon cooling
				if (gasBlockBodyMap.containsKey(activeBlock)) {
					resetBody(gasBlockBodyMap.get(activeBlock));
					gasBlockBodyMap.remove(activeBlock); //As the block no longer is in the gas state, it should not exist in the gasBlockBodyMap
				}

			} else if (state == WaterState.LIQUID) {
				soundHandler.playSoundEffect(soundHandler.getFreeze());
			}
		}
		level.coolBlock();
		
		/////////////////////////////////////////////////////////
		//if-statement similar to previous one
		if (activeBlock instanceof Water) {
			Water activeWater = (Water) activeBlock;
			for (Body body : bodyBlockMap.keySet()) {
				if (bodyBlockMap.get(body) == activeWater) {
					body.getFixtureList().get(0).setFilterData(getFilter(activeWater));
					break;
				}
			}
		}
	}

	public Filter getFilter(Water water) {
		Filter filter = new Filter();
		if (water.getState() == WaterState.GAS) {
			filter.categoryBits = BIT_GAS;
			filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		} else if (water.getState() == WaterState.LIQUID) {
			filter.categoryBits = BIT_WATER;
			filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		} else if (water.getState() == WaterState.SOLID) {
			filter.categoryBits = BIT_ICE;
			filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS | BIT_PLAYER;
		}
		return filter;
	}

	public void setOnIce(boolean onIce) {
		player.setOnIce(onIce);
	}

	public void setPlayerInsideFlower(boolean playerInsideFlower) {
		level.setPlayerInsideFlower(playerInsideFlower);
	}

	public void setActiveBody(Body body, ActiveBlockPosition position) {
		if (body == null) {
			level.setActiveBlock(EmptyBlock.getEmptyBlock(), position);
		} else if (bodyBlockMap.get(body) != null) {
			Block block = bodyBlockMap.get(body);
			level.setActiveBlock(block, position);
		} else if (body.getFixtureList().get(0).getUserData() != null) {
			if (((CollisionData) body.getFixtureList().get(0).getUserData()).getCollisionType() == CollisionType.FLOWER) {
				Block block = level.getFlower();
				level.setActiveBlock(block, position);
			}
		} else {
			level.setActiveBlock(new Ground(), position);
		}
	}

	public void setGhostEmptyLeft(boolean ghostEmptyLeft) {
		level.setPlayerGhostEmptyLeft(ghostEmptyLeft);
	}

	public void setGhostEmptyRight(boolean ghostEmptyRight) {
		level.setPlayerGhostEmptyRight(ghostEmptyRight);
	}

	public void killPlayer() {
		soundHandler.playSoundEffect(soundHandler.getDie());
		level.killPlayer();
	}

	public boolean isPlayerDead() {
		return level.isPlayerDead();
	}

	public void setWaterBottom(Body body, boolean bottomBlock) {
		if (bodyBlockMap.get(body) != null) {
			Water waterBlock = bodyBlockMap.get(body);
			waterBlock.setBottomBlock(bottomBlock);
		}
	}

	public void setWaterTop(Body body, boolean topBlock) {
		if (bodyBlockMap.get(body) != null) {
			Water waterBlock = bodyBlockMap.get(body);
			waterBlock.setTopBlock(topBlock);
		}
	}

	public void render(float delta) {
		world.step(delta, 6, 2);
		if (Math.abs(playerCharacterBody.getLinearVelocity().x) < MAX_SPEED) {
			/////////////////////////////////////////////////////////////
			//isflyingenabled check needed?
			if (player.isFlying() && isFlyingEnabled()) {
				playerCharacterBody.setLinearVelocity(flyVector);
			} else {
				playerCharacterBody.applyForceToCenter(movementVector, true);
			}
		}

        //Update model with physics changes
        Vector2 bodyPos = playerCharacterBody.getPosition();
        Vector2 bodySpeed = playerCharacterBody.getLinearVelocity();
        player.setPosition(bodyPos.x * PPM, bodyPos.y * PPM);
        player.setSpeed(bodySpeed.x, bodySpeed.y);
        if (player.getPosition().y < 0) { //If below screen/out of map
            killPlayer();
        }
        for (Body waterBody : bodyBlockMap.keySet()) {
            Water waterBlock = bodyBlockMap.get(waterBody);
            Vector2 waterPos = waterBody.getPosition();
            waterBlock.setPosition(waterPos.x * PPM, waterPos.y * PPM);
        }
	}
}
