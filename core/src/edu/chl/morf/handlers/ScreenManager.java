package edu.chl.morf.handlers;

import java.util.Stack;

import com.badlogic.gdx.Gdx;

import edu.chl.morf.main.Main;
import edu.chl.morf.screens2.*;

public class ScreenManager {
	
	private Main game;
	
	private Stack<GameScreen> screens;
	
	public static enum ScreenType{
		PLAY,
		MAIN_MENU,
		LEVEL_SELECTION,
		OPTIONS
	}
	
	public ScreenManager(Main game){
		this.game = game;
		screens = new Stack<GameScreen>();
		pushScreen(ScreenType.MAIN_MENU, null);
	}
	
	public Main getGame(){
		return game;
	}
	
	public void render(float dt){
		screens.peek().render(dt);
	}
	
	private GameScreen getScreen(ScreenType screen, String levelName){
		if(screen == ScreenType.PLAY){
			return new PlayScreen(this, levelName);
		}
		else if(screen == ScreenType.MAIN_MENU){
			return new MainMenuScreen(this);
		}
        else if(screen == ScreenType.LEVEL_SELECTION){
            return new LevelSelectionScreen(this);
        }
		else if(screen == ScreenType.OPTIONS){
			return new OptionsScreen(this);
		}
		return null;
	}
	
	public void setScreen(ScreenType screen, String levelName){
		clearScreens();
		pushScreen(screen, levelName);
	}
	
	public void pushScreen(ScreenType screen, String levelName){
		screens.push(getScreen(screen, levelName));
	}
	
	public void popScreen(){
		GameScreen gs = screens.pop();
		gs.dispose();
		screens.peek().setFocus();
	}
	
	public void clearScreens(){
		screens.clear();
	}
	
}
