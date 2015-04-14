package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image{

    private Texture texture;
    Vector2 acceleration;
    Vector2 velocity;

    public PlayerCharacter(){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        acceleration = new Vector2(0,0);
        velocity = new Vector2(0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture,getX(),getY());
    }

    @Override
    public void act(float delta){
        super.act(delta);
        this.moveBy(velocity.x,velocity.y);
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }
}
