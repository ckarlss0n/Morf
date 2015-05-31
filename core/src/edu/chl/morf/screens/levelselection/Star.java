package edu.chl.morf.screens.levelselection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Lage on 2015-05-21.
 */
public class Star extends Image {
    private Texture texture;
    public Star(){
        this.texture = new Texture("levelselection/level_selection_star.png");
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
        batch.draw(texture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
    }
}
