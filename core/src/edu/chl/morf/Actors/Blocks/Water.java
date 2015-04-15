package edu.chl.morf.Actors.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Harald on 2015-04-15.
 */
public class Water extends Image{
    private ShapeRenderer renderer=new ShapeRenderer();
    private Texture texture;
    private Body body;

    public Water(){

    }
}
