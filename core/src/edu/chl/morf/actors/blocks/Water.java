package edu.chl.morf.actors.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Harald on 2015-04-15.
 */
public class Water extends Image{
    private Texture texture;
    private Body body;
    private edu.chl.morf.model.Water water;

    public Water(){
        water = new edu.chl.morf.model.Water();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.setProjectionMatrix(camera.combined);                         //Tells the spritebatch to render according to camera
        super.draw(batch, parentAlpha);

        //Draw image at water position
        batch.draw(texture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
    }
}
