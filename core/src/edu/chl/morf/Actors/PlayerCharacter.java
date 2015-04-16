package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.WorldUtils;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image {

    private boolean facingRight=true;
    private boolean moving=false;
    private Texture texture;
    private Body body;
    private Vector2 movementVector = new Vector2(0,0);
    private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
    private int blockWidth = 1;
    private int blockHeight = 1;

    public PlayerCharacter(Body body){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        this.body = body;
        pressedKeys.put(Input.Keys.LEFT, false);
        pressedKeys.put(Input.Keys.RIGHT, false);
        pressedKeys.put(Input.Keys.UP, false);
        addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        pressedKeys.put(Input.Keys.LEFT, true);
                        moveLeft();
                        break;
                    case Input.Keys.RIGHT:
                        pressedKeys.put(Input.Keys.RIGHT, true);
                        moveRight();
                        break;
                    case Input.Keys.UP:
                        pressedKeys.put(Input.Keys.UP, true);
                        jump();
                        break;
                    case Input.Keys.SPACE:
                        doAction();
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        fly();
                        break;
                    case Input.Keys.X:
                        addBlock(getBody().getPosition());
                }
                return true;
            }
        });

        addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    pressedKeys.put(Input.Keys.LEFT, false);
                    stop();
                } else if (keycode == Input.Keys.RIGHT) {
                    pressedKeys.put(Input.Keys.RIGHT, false);
                    stop();
                } else if (keycode == Input.Keys.UP) {
                    pressedKeys.put(Input.Keys.UP, false);
                    stop();
                } else if (keycode == Input.Keys.SHIFT_LEFT) {
                    stop();
            }
                return true;
            }
        });
    }

    public void addBlock(Vector2 position) {
        WorldUtils.addBlock(this, position, blockWidth, blockHeight);
    }

    public boolean isFacingRight(){
        return facingRight;
    }

    public void moveLeft(){
        facingRight=false;
        moving=true;
        if(body.getLinearVelocity().x >= 0){    //If moving right
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(-200, 0);
    }
    public void moveRight(){
        facingRight=true;
        moving=true;
        if(body.getLinearVelocity().x <= 0){    //If moving left
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(200, 0);
    }
    public void stop(){
        moving=false;
        if(!(pressedKeys.get(Input.Keys.LEFT) || pressedKeys.get(Input.Keys.RIGHT))) {
            movementVector = new Vector2(0, 0);
        } else if(pressedKeys.get(Input.Keys.LEFT) && !pressedKeys.get(Input.Keys.RIGHT)) {
            moveLeft();
        } else {
            moveRight();
        }
    }
    public void jump(){
        if(body.getLinearVelocity().y == 0) {   //If standing (could be improved, also 0 at top of jump)
            body.applyForceToCenter(new Vector2(0, 5000), true);
        }
    }
    public void fly(){
        if(body.getLinearVelocity().y == 0) {   //If standing (could be improved, also 0 at top of jump)
            movementVector = new Vector2(0, 200);
        }
    }

    public void doAction(){
        body.setTransform(10,5,0);
        body.setLinearVelocity(0,0);
        System.out.println("reset");
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.draw(texture,getX(),getY());
        //batch.draw(texture,body.getPosition().x,body.getPosition().y);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(Math.abs(body.getLinearVelocity().x) < MAX_SPEED) {
            body.applyForceToCenter(movementVector, true);
        }
    }

    public Body getBody(){
        return this.body;
    }
}
