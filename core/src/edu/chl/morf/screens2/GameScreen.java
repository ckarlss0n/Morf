package edu.chl.morf.screens2;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;

public abstract class GameScreen implements Screen{
	
	protected ScreenManager screenManager;
	protected Main game;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	protected GameScreen(ScreenManager sm){
		screenManager = sm;
		game = sm.getGame();
		spriteBatch = game.getSpriteBatch();
		cam = game.getCamera();
		hudCam = game.getHudCam();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
}
