package edu.chl.morf.screens2.levelselection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.chl.morf.handlers.SoundHandler;

/**
 * Created by Lage on 2015-05-21.
 */
public abstract class SelectionComponent extends Image {
    private Texture normalTexture;
    private Texture highlightTexture;
    private boolean highlighted;
    private SoundHandler soundHandler = SoundHandler.getInstance();

    public SelectionComponent(String normalTexturePath, String highlightTexturePath) {
        this.normalTexture = new Texture(normalTexturePath);
        this.highlightTexture = new Texture(highlightTexturePath);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickAction();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                highlighted = true;
                soundHandler.playSoundEffect(soundHandler.getButtonHover());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                highlighted = false;
            }
        });
    }

    @Override
    public void draw(Batch batch,float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(this.highlighted){
            batch.draw(highlightTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }else{
            batch.draw(normalTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }
    }
    public abstract void clickAction();
}
