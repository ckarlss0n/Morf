package edu.chl.morf.handlers;

import edu.chl.morf.screens.LevelSelectionScreen;
import edu.chl.morf.screens2.*;

import java.util.Stack;

/**
 * A singleton class managing the different screens of the game.
 * All screens are stored in a stack.
 * The screen on top of the stack is the one to get rendered.
 */
public class ScreenManager {

	private Stack<GameScreen> screens;
    private static ScreenManager instance = new ScreenManager();

	public enum ScreenType{
		PLAY,
		MAIN_MENU,
		LEVEL_SELECTION,
		OPTIONS,
		PAUSE_SCREEN
	}
	
	private ScreenManager(){
		screens = new Stack<GameScreen>();
		pushScreen(ScreenType.MAIN_MENU, null);
	}

    public static ScreenManager getInstance(){
        return instance;
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
		else if(screen == ScreenType.PAUSE_SCREEN){
			return new PauseScreen(this, screens.peek()); //Show and render current screen (PlayScreen most likely) behind PauseScreen
		}
		return null;
	}

	//TODO Remove?
	public void setScreen(ScreenType screen, String levelName){
		clearScreens();
		pushScreen(screen, levelName);
	}
	
	public void pushScreen(ScreenType screen, String levelName){
		screens.push(getScreen(screen, levelName));
	}

	//Pop the screen on top of the stack, to return to the previously shown screen
	public void popScreen(){
		GameScreen gs = screens.pop();
		gs.dispose();
		screens.peek().setFocus();
		//Resume game if the screen that appears after pop is PlayScreen
		if(screens.peek() instanceof PlayScreen){
			((PlayScreen)screens.peek()).resumeGame();
		}
	}

	//TODO Remove?
	public void clearScreens(){
		screens.clear();
	}
	
}
