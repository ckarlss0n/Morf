package edu.chl.morf.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.ScreenManager;

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
		screenManager = new ScreenManager(this);
        HighScoreHandler.getInstance().readHighScores();
	}

    @Override
    public void dispose(){
        HighScoreHandler.getInstance().writeHighScores();
    }
	
	public void render(){
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			accum -= STEP;
			screenManager.update(STEP);
			screenManager.render(STEP);
		}
	}
}
