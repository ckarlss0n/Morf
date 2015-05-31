package edu.chl.morf.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.chl.morf.main.Main;

/**
 * An abstract class for representing a screen in the game.
 * A GameScreen has a ScreenManager, a SpriteBatch and cameras.
 * 
 * @author gustav
 */

public abstract class GameScreen implements Screen{
	
	protected ScreenManager screenManager;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	protected GameScreen(){}
	
	protected GameScreen(ScreenManager sm){
		screenManager = sm;
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
	}
	public abstract void setFocus();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
