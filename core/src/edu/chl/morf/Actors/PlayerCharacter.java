package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image{

    private Texture texture;


    public PlayerCharacter(){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture,getX(),getY());
    }
}
