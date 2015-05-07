package edu.chl.morf.screens2;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;

public abstract class GameScreen implements Screen{
	
	protected ScreenManager screenManager;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	protected GameScreen(ScreenManager sm){
		screenManager = sm;
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
}
