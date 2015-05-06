package edu.chl.morf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacterModel;

import java.awt.*;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-05-06.
 */
public class View {

    private Level level;
    private Batch batch;
    private Animation runningRightAnimation;
    private Animation runningLeftAnimation;
    private TextureRegion idleTexture;
    private float stateTime;
    private OrthographicCamera camera;


    public View(){
        //Create SpriteBatch for drawing textures
        batch = new SpriteBatch();

        //Load PayerCharacter sprite sheet from assets
        TextureAtlas textureAtlas = new TextureAtlas(CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES.length];
        for (int i = 0; i < PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES.length; i++) {
            String path = PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningLeftAnimation = new Animation(0.1f, runningFrames);
        runningFrames = new TextureRegion[PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES.length];
        for (int i = 0; i < PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES.length; i++) {
            String path = PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES [i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningRightAnimation = new Animation(0.1f, runningFrames);
        idleTexture = textureAtlas.findRegion(PLAYERCHARACTER_IDLE_REGION_NAME);
        stateTime = 0f;


    }

    public View(Level level){
        this();
        this.level = level;
    }

    public View(Level level, OrthographicCamera camera){
        this(level);
        this.camera = camera;
    }

    public void render(){
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
        batch.begin();

        batch.setProjectionMatrix(camera.combined);                 //Tells the spritebatch to render according to camera
        //super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        //Draw correct animation at player character position
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point playerCharPos = playerCharacter.getPosition();
        if(playerCharacter.isMoving()) {
            if(playerCharacter.isFacingRight()) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x - 15/100f, playerCharPos.y - 15/100f, 30/100f, 30/100f);
            }else{
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x - 15/100f, playerCharPos.y - 15/100f, 30/100f, 30/100f);
            }
        }else{
            batch.draw(idleTexture, playerCharPos.x - 15 / 100f, playerCharPos.y - 15 / 100f, 30 / 100f, 30 / 100f);
        }

        batch.end();
    }
}
