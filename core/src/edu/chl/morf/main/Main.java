package edu.chl.morf.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import edu.chl.morf.file.HighScoreHandler;
import edu.chl.morf.file.SettingsHandler;
import edu.chl.morf.screens.ScreenManager;

/**
 * This class starts the game.
 * Main is created in DesktopLauncher.
 */
public class Main extends Game {

	public static final String TITLE = "Morf";
	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;
	public static final boolean FULLSCREEN = false;
	public static final boolean RESIZABLE = false;

	public static final float STEP = 1 / 60f;
	private float accum;

	private ScreenManager screenManager;

	@Override
	public void create() {
		HighScoreHandler.getInstance().load();
		SettingsHandler.getInstance().load();
		screenManager = ScreenManager.getInstance();
	}

	@Override
	public void dispose() {
		HighScoreHandler.getInstance().save();
		SettingsHandler.getInstance().save();
	}

	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			screenManager.render(STEP);
		}
	}
}
