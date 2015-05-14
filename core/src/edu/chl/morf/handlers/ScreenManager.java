package edu.chl.morf.handlers;

import java.util.Stack;

import edu.chl.morf.main.Main;
import edu.chl.morf.screens2.*;

public class ScreenManager {
	
	private Main game;
	
	private Stack<GameScreen> screens;
	
	public static final int PLAY = 656987;
	public static final int MAINMENU = 123456;
    public static final int LEVELSELECTION = 654321;
	public static final int OPTIONS_SCREEN = 246810;
	
	public ScreenManager(Main game){
		this.game = game;
		screens = new Stack<GameScreen>();
		pushState(MAINMENU);
	}
	
	public Main getGame(){
		return game;
	}
	
	public void update(float dt){
		screens.peek().update(dt);
	}
	
	public void render(float dt){
		screens.peek().render(dt);
	}
	
	private GameScreen getScreen(int screen){
		if(screen == PLAY){
			return new PlayScreen(this, "Level_1.tmx");
		}
		else if(screen == MAINMENU){
			return new MainMenuScreen(this);
		}
        else if(screen == LEVELSELECTION){
            return new LevelSelectionScreen(this);
        }
		else if(screen == OPTIONS_SCREEN){
			return new OptionsScreen(this);
		}
		return null;
	}
	
	public void setState(int screen){
		popScreen();
		pushState(screen);
	}
	
	public void pushState(int screen){
		screens.push(getScreen(screen));
	}
	
	public void popScreen(){
		GameScreen gs = screens.pop();
		gs.dispose();
	}
	
}
