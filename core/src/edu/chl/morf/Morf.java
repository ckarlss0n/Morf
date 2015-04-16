package edu.chl.morf;

import com.badlogic.gdx.Game;
import edu.chl.morf.Screens.GameScreen;
import edu.chl.morf.Screens.MainMenuScreen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Morf extends Game implements PropertyChangeListener{

    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;

	@Override
	public void create () {
        gameScreen = new GameScreen();
        mainMenuScreen = new MainMenuScreen();
        setScreen(gameScreen);
        mainMenuScreen.addObserver(this);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
    }
}