package edu.chl.morf.controllers;

import com.badlogic.gdx.physics.box2d.World;
import edu.chl.morf.model.Level;

import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.*;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameLogic {

	private GameController gameController;
	private MyContactListener myContactListener;
	private Level level;	//Model
	private World world;	//Box2D world
	private Map<Integer, Boolean> pressedKeys;

	public GameLogic(Level level){
		gameController = new GameController();
		myContactListener = new MyContactListener();
		this.level = level;
		world = new World(WORLD_GRAVITY, true);
		pressedKeys = new HashMap<Integer, Boolean>();
	}

	public void render(float delta){
		handleInput();
		handleContact();
		world.step(delta,6,2);
		updateLevel();
	}

	public void getPressedKeys(){
		pressedKeys = gameController.getPressedKeys();
	}

	//Handle input from GameController
	public void handleInput(){
		getPressedKeys();
		System.out.println(pressedKeys);
	}

	//Handle collisions from MyContactListener
	public void handleContact(){

	}

	//Update model with physics changes
	public void updateLevel(){

	}
}
