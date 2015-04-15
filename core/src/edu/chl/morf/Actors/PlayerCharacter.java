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

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image{

    private Body body;
    private Texture texture;
    private Vector2 acceleration;
    private Vector2 velocity;
    private Vector2 movementVector = new Vector2(0,0);
    private int direction;

    public PlayerCharacter(){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        acceleration = new Vector2(0,0);
        velocity = new Vector2(0,0);
    }

    public PlayerCharacter(Body body){
        this();
        this.body = body;
        this.setSize(10,10);
        this.setPosition(310, 400);

        addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        ((PlayerCharacter) event.getTarget()).moveLeft();
                        break;
                    case Input.Keys.RIGHT:
                        ((PlayerCharacter) event.getTarget()).moveRight();
                        break;
                    case Input.Keys.UP:
                        ((PlayerCharacter) event.getTarget()).stop();
                        break;
                }
                return true;
            }
        });

    }

    public void moveLeft(){
        movementVector = new Vector2(-100, 0);
    }
    public void moveRight(){
        movementVector = new Vector2(100, 0);
    }
    public void stop(){
        movementVector = new Vector2(0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.draw(texture,getX(),getY());
        //batch.draw(texture,body.getPosition().x,body.getPosition().y);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(Math.abs(this.getBody().getLinearVelocity().x) < 5) {
            this.body.applyForceToCenter(movementVector, true);
        }
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    public Body getBody(){
        return this.body;
    }
}
