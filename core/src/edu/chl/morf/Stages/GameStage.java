package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Actors.Blocks.Ground;
import edu.chl.morf.Actors.PlayerCharacter;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage {

    private PlayerCharacter playerCharacter = new PlayerCharacter();
    private Image ground = new Ground();
    private Vector2 left = new Vector2(-10,0);
    private Vector2 right = new Vector2(10,0);
    private Vector2 up = new Vector2(0,6);
    private Vector2 down = new Vector2(0,-0.2f);
    private Vector2 currentVector = new Vector2(0,0);

    public boolean isIdleY(){
        if(currentVector.y == 0){
            return true;
        }
        return false;
    }

    public void fall(){
        System.out.println(playerCharacter.getY());
        if(playerCharacter.getY() > 0f) {
            playerCharacter.setVelocity(currentVector.add(down));
        } else {
            setYVelocity(0f);
            playerCharacter.setY(0f);
        }
    }

    public GameStage() {
        Gdx.input.setInputProcessor(this);
        addActor(ground);
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);
        playerCharacter.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        currentVector = currentVector.add(left);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        break;
                    case Input.Keys.RIGHT:
                        currentVector = currentVector.add(right);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        break;
                    case Input.Keys.UP:
                        currentVector = currentVector.add(up);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        break;
                }
                return true;
            }
        });
        playerCharacter.addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(left));
                } else if (keycode == Input.Keys.RIGHT) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(right));
                } else if (keycode == Input.Keys.UP) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(up));
                }
                return true;
            }
        });
    }

    public void setYVelocity(float f){
        this.currentVector = new Vector2(this.currentVector.x, f);
    }
}
