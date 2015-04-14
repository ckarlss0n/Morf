package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.Actors.Blocks.Ground;
import edu.chl.morf.Actors.PlayerCharacter;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage {

    private Actor playerCharacter;

    public GameStage(){
        Gdx.input.setInputProcessor(this);
        addActor(new Ground());
        playerCharacter = new PlayerCharacter();
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);
        playerCharacter.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        ((PlayerCharacter) event.getTarget()).setVelocity(new Vector2(-10, 0));
                        break;
                    case Input.Keys.RIGHT:
                        ((PlayerCharacter) event.getTarget()).setVelocity(new Vector2(10, 0));
                        break;
                }
                return true;
            }
        });
        playerCharacter.addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.LEFT) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(new Vector2(0, 0));
                }else if(keycode == Input.Keys.RIGHT){
                    ((PlayerCharacter) event.getTarget()).setVelocity(new Vector2(0, 0));
                }
                return true;
            }
        });
    }


}
