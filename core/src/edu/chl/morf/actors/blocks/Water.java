package edu.chl.morf.actors.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.model.WaterState;

/**
 * This class represents water.
 * Using information from the Water class from the model package, this class can draw itself.
 * The class contains textures for the different states of water (gas/liquid/solid).
 * <p>
 * <li><b>Responsible for: </b>
 * <li>Representing water.
 * <li>Drawing the correct texture, depending on the current state of the water
 * <p>
 * <li><b>Used by: </b>
 * <li>{@link edu.chl.morf.stages.GameStage}
 * <p>
 * <li><b>Using: </b>
 * <li>{@link edu.chl.morf.model.Water}
 * <li>{@link edu.chl.morf.model.Block}
 * <p>
 * @author Harald
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
        if(water.getState() == WaterState.LIQUID) {
            //batch.draw(waterTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }else if(water.getState() == WaterState.GAS) {
            //batch.draw(vaporTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }else if(water.getState() == WaterState.SOLID) {
            //batch.draw(iceTexture, water.getPosition().x, water.getPosition().y, 30 / 100f, 30 / 100f);
        }
    }
}
