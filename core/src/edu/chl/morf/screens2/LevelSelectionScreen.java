package edu.chl.morf.screens2;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chl.morf.handlers.ScreenManager;

/**
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen implements EventListener{

    public LevelSelectionScreen(ScreenManager sm) {
        super(sm);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean handle(Event event) {
        if(event instanceof ChangeListener.ChangeEvent){
            ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)event;
            if(changeEvent.getTarget() instanceof TextButton){
                TextButton button = (TextButton)changeEvent.getTarget();
                String buttonText = button.getLabel().getText().toString();
                if(buttonText.equals("PLAY")){
                    /*sm.setStageInputHandler();
                    gameScreen.show();
                    setScreen(gameScreen);*/
                }else if(buttonText.equals("SETTINGS")){

                }
            }
        }
        return true;
    }
}
