package edu.chl.morf.backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.*;

/**
 * This class represents a background layer used in the game.
 * The background layers are stacked and shown upon each other.
 * All background layers are infinitely horizontally scrolling, (often at different speeds) creating a repeating background.
 * This makes it possible to mimic depth in the game (The effect is known as <i>parallax scrolling</i>).
 * <p>
 * <li><b>Responsible for: </b>
 * <li>Representing a background layer.
 * <li>Infinitely scrolling horizontally.
 * <li>Drawing itself.
 * <p>
 * <li><b>Used by: </b>
 * {@link edu.chl.morf.stages.GameStage}
 * <p>
 * <li><b>Using: </b>
 * <li>LibGDX-classes
 * <li>{@link edu.chl.morf.Constants}
 * <p>
 * @author Christoffer Karlsson
 */
public class BackgroundLayer{

    private TextureRegion textureRegion;
    private Rectangle leftBounds;
    private Rectangle rightBounds;
    private float speed = BACKGROUND_SCROLLING_SPEED;
    private BitmapFont font = new BitmapFont();

    public BackgroundLayer(String imagePath) {
        font.setColor(Color.BLACK);
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(imagePath)));
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

    public void draw(Batch batch, float delta){
        updateBounds(delta);
        scrolledOut();
        batch.draw(textureRegion, leftBounds.x, leftBounds.y, GAME_WIDTH, GAME_HEIGHT);
        batch.draw(textureRegion, rightBounds.x, rightBounds.y, GAME_WIDTH, GAME_HEIGHT);
    }
}
