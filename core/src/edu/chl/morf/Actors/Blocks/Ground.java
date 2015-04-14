package edu.chl.morf.Actors.Blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static edu.chl.morf.Constants.*;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class Ground extends Image{
    private ShapeRenderer renderer = new ShapeRenderer();
    private Texture texture;

    public Ground(){
        setHeight(200);
        setWidth(100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.rect(300, 0, getWidth(), getHeight());
        renderer.end();
        batch.begin();
    }
}
