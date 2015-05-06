package edu.chl.morf.handlers;

import java.util.Stack;

import edu.chl.morf.main.Main;
import edu.chl.morf.screens2.GameScreen;
import edu.chl.morf.screens2.PlayScreen;

public class ScreenManager {
	
	private Main game;
	
	private Stack<GameScreen> screens;
	
	public static final int PLAY = 656987;
	
	public ScreenManager(Main game){
		this.game = game;
		screens = new Stack<GameScreen>();
		pushState(PLAY);
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
