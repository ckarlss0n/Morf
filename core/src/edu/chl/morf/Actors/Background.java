package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static edu.chl.morf.Constants.*;

/**
 * Created by Christoffer on 2015-04-16.
 */
public class Background extends Image{

    private TextureRegion textureRegion;
    private Rectangle leftBounds;
    private Rectangle rightBounds;
    private float speed = BACKGROUND_SCROLLING_SPEED;

    public Background() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(BACKGROUND_IMAGE_PATH)));
        leftBounds = new Rectangle(0 - GAME_WIDTH/2, 0, GAME_WIDTH, GAME_HEIGHT);      //Half rectangle out of bounds on left side of screen
        rightBounds = new Rectangle(GAME_WIDTH/2, 0, GAME_WIDTH, GAME_HEIGHT);         //Half rectangle out of bounds on right side of screen
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    private void updateBounds(float delta){
        leftBounds.x += delta * speed;
        rightBounds.x += delta * speed;
    }

    private void scrolledOut(){
        if(rightBounds.x >= GAME_WIDTH) {
            leftBounds.x = -GAME_WIDTH;
            rightBounds.x = 0;
        } else if(rightBounds.x <= 0){
            leftBounds.x = 0;
            rightBounds.x = GAME_WIDTH;
        }
    }

    @Override
    public void act(float delta){
        updateBounds(delta);
        scrolledOut();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, leftBounds.x, leftBounds.y, GAME_WIDTH, GAME_HEIGHT);
        batch.draw(textureRegion, rightBounds.x, rightBounds.y, GAME_WIDTH, GAME_HEIGHT);
    }
}
