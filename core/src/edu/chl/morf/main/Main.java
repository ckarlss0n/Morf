package edu.chl.morf.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.handlers.SettingsHandler;

public class Main extends Game{

	public static final String TITLE = "Morf";
	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;
	public static final boolean FULLSCREEN = false;
	
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
    public void dispose(){
        HighScoreHandler.getInstance().save();
        SettingsHandler.getInstance().save();
    }
	
	public void render(){
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			accum -= STEP;
			screenManager.render(STEP);
		}
	}
}
