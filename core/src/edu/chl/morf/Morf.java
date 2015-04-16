package edu.chl.morf;

import com.badlogic.gdx.Game;
import edu.chl.morf.Screens.GameScreen;
import edu.chl.morf.Screens.MainMenuScreen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Morf extends Game {

    PropertyChangeListener pcs;
    GameScreen gameScreen;
    MainMenuScreen mainMenuScreen;
	@Override
	public void create () {
        gameScreen = new GameScreen();
        mainMenuScreen = new MainMenuScreen();
        pcs = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

            }
        };
        setScreen(gameScreen);
	}
}