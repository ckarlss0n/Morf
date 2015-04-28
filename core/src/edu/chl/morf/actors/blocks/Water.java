package edu.chl.morf.actors.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.model.WaterState;

/**
 * Created by Harald on 2015-04-15.
 */
public class Water extends Image{
    private Texture iceTexture;
    private Texture waterTexture;
    private Texture vaporTexture;
    private Body body;
    private edu.chl.morf.model.Water water;

    public Water(){
        water = new edu.chl.morf.model.Water();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //batch.setProjectionMatrix(camera.combined);                         //Tells the spritebatch to render according to camera
        super.draw(batch, parentAlpha);

        //Draw current water state texture at water position
        if(water.toString().equals("liquid")) {
            //batch.draw(waterTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }else if(water.toString().equals("gas")) {
            //batch.draw(vaporTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }else if(water.toString().equals("solid")) {
            //batch.draw(iceTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }
    }
}
