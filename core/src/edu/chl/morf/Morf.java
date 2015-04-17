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
        setScreen(mainMenuScreen);
        mainMenuScreen.addObserver(this);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Event " + evt.getPropertyName()+ " mottaget i Morf");
        String screenName = evt.getPropertyName();
        if(screenName.equals("gamescreen")){
            System.out.println("Byter till skärm");
            gameScreen.setStageInputHandler();
            gameScreen.show();
            setScreen(gameScreen);
        }
    }
}