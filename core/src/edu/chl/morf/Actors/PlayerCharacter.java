package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image{

    private Body body;
    private Texture texture;
    private Vector2 acceleration;
    private Vector2 velocity;
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
    }

    public void moveLeft(){
        direction = -2;
    }
    public void moveRight(){
        direction = 2;
    }
    public void stop(){
        direction = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.draw(texture,getX(),getY());
        //batch.draw(texture,body.getPosition().x,body.getPosition().y);
    }


    @Override
    public void act(float delta){
        super.act(delta);
        this.body.applyForceToCenter(new Vector2(20*direction,0),true);
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    public Body getBody(){
        return this.body;
    }
}
