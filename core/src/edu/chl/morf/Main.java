package edu.chl.morf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chl.morf.screens.GameScreen;
import edu.chl.morf.screens.MainMenuScreen;

public class Main extends Game implements EventListener{

    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;

	@Override
	public void create () {
        gameScreen = new GameScreen();
        mainMenuScreen = new MainMenuScreen();
        mainMenuScreen.setButtonsListener(this);
        setScreen(mainMenuScreen);
    }

    @Override
    public boolean handle(Event event) {
        if(event instanceof ChangeListener.ChangeEvent){
            ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)event;
            if(changeEvent.getTarget() instanceof TextButton){
                TextButton button = (TextButton)changeEvent.getTarget();
                String buttonText = button.getLabel().getText().toString();
                if(buttonText.equals("PLAY")){
                    gameScreen.setStageInputHandler();
                    gameScreen.show();
                    setScreen(gameScreen);
                }else if(buttonText.equals("SETTINGS")){

                }
            }
        }
        return true;
    }
}