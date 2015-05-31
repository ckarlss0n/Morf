package edu.chl.morf.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import edu.chl.morf.file.*;
import edu.chl.morf.handlers.LevelList;
import edu.chl.morf.screens.ScreenManager;

import java.util.List;

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
	public static final float SCALING = V_WIDTH/1920f;

	public static final float STEP = 1 / 60f;
	private float accum;

	private ScreenManager screenManager;
    private FileHandler settingsHandler;
    private FileHandler highScoreHandler;

	@Override
	public void create() {
        //Load saved settings
        settingsHandler = new SettingsHandler();
		settingsHandler.load();

        //Load saved high scores
        highScoreHandler = new HighScoreHandler();
        highScoreHandler.load();

		screenManager = ScreenManager.getInstance();

        //List all available levels in the LevelList class
        IFileLister fileLister = new FileLister();
        List<String> levels = fileLister.getFileNames("levels");
        LevelList.getInstance().setLevels(levels);
	}

	@Override
	public void dispose() {
		highScoreHandler.save();
		settingsHandler.save();
	}

	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			screenManager.render(STEP);
		}
	}
}
