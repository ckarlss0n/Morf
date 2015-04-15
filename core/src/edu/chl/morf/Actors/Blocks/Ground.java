package edu.chl.morf.Actors.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class Ground extends Image{
    private ShapeRenderer renderer = new ShapeRenderer();
    private Texture texture;
    private Body body;

    public Ground(){
        setHeight(200);
        setWidth(100);
    }

    public Ground(Body body){
        this();
        this.body = body;
    }
/*
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.rect(body.getPosition().x, body.getPosition().y, getWidth(), getHeight());
        renderer.end();
        batch.begin();
    }*/
}
