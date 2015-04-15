package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Stages.GameStage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image{

    private boolean facingRight=true;
    private boolean moving=false;
    private Body body;
    private Texture texture;
    private Vector2 acceleration;
    private Vector2 velocity;
    private Vector2 movementVector = new Vector2(0,0);
    private int direction;
    private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();

    public PlayerCharacter(){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        acceleration = new Vector2(0,0);
        velocity = new Vector2(0,0);

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
                }
                return true;
            }
        });
    }

    public PlayerCharacter(Body body){
        this();
        this.body = body;
        this.setSize(10, 10);
        this.setPosition(310, 400);
    }

    public void moveLeft(){
        facingRight=false;
        moving=true;
        if(body.getLinearVelocity().x >= 0){
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(-100, 0);
    }
    public void moveRight(){
        facingRight=true;
        moving=true;
        if(body.getLinearVelocity().x <= 0){
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(100, 0);
    }
    public void stop(){
        moving=false;
        if(!(pressedKeys.get(Input.Keys.LEFT) || pressedKeys.get(Input.Keys.RIGHT))) {
            movementVector = new Vector2(0, 0);
        }
    }
    public void jump(){
        body.applyForceToCenter(new Vector2(0, 5000), true);
    }
    public void doAction(){
        
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.draw(texture,getX(),getY());
        //batch.draw(texture,body.getPosition().x,body.getPosition().y);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        body.applyForceToCenter(movementVector, true);
        System.out.println(body.getLinearVelocity());
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    public Body getBody(){
        return this.body;
    }
}
