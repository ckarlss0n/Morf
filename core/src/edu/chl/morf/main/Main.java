package edu.chl.morf.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
		Music mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Morf_Music_Theme.mp3"));
		mp3Music.play();
		mp3Music.setLooping(true);
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
