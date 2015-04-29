package edu.chl.morf.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This class represents the flower, which is at the end of each level.
 * You complete a level by reaching the flower and watering it.
 * <p>
 * <li><b>Responsible for: </b>
 * Representing the flower. Completing the level upon being watered.
 * <p>
 * <li><b>Used by: </b>
 * None
 * <p>
 * <li><b>Using: </b>
 * LibGDX-classes
 * <p>
 * @author Lage Bergman
 */
public class Flower extends Image {
    private Body body;
    private Texture texture;
    private edu.chl.morf.model.Flower flower;

    public Flower(){
        super();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
    }
}
