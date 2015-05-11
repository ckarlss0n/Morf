package edu.chl.morf.backgrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static edu.chl.morf.main.Main.V_HEIGHT;
import static edu.chl.morf.main.Main.V_WIDTH;

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
    private float speed;
    private float relativeSpeed;
    private float constantSpeed;
    private BitmapFont font = new BitmapFont();

    public BackgroundLayer(String imagePath, float relativeSpeed, float constantSpeed) {
        this.relativeSpeed = relativeSpeed;
        this.constantSpeed = constantSpeed;
        font.setColor(Color.BLACK);
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(imagePath)));
        leftBounds = new Rectangle(0 - V_WIDTH/2, 0, V_WIDTH, V_HEIGHT);      //Half rectangle out of bounds on left side of screen
        rightBounds = new Rectangle(V_WIDTH/2, 0, V_WIDTH, V_HEIGHT);         //Half rectangle out of bounds on right side of screen
    }

    public void setSpeed(float speed){
        this.speed = constantSpeed + speed * relativeSpeed;
    }

    private void updateBounds(float delta){
        leftBounds.x += delta * speed;
        rightBounds.x += delta * speed;
    }

    private void scrolledOut(){
        if(rightBounds.x >= V_WIDTH) {
            leftBounds.x = -V_WIDTH;
            rightBounds.x = 0;
        } else if(rightBounds.x <= 0){
            leftBounds.x = 0;
            rightBounds.x = V_WIDTH;
        }
    }

    public void draw(Batch batch, float delta){
        updateBounds(delta);
        scrolledOut();
        batch.draw(textureRegion, leftBounds.x, leftBounds.y, V_WIDTH, V_HEIGHT);
        batch.draw(textureRegion, rightBounds.x, rightBounds.y, V_WIDTH, V_HEIGHT);
    }
}
