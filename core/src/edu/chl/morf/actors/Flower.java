package edu.chl.morf.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lage on 2015-04-16.
 */
public class Flower extends Image {
    private Body body;
    private Texture texture;

    public Flower(){
        super();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
    }
}
