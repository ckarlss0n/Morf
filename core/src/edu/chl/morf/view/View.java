package edu.chl.morf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import edu.chl.morf.backgrounds.BackgroundFactory;
import edu.chl.morf.backgrounds.BackgroundGroup;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.PlayerCharacterModel;

import java.awt.*;
import java.awt.geom.Point2D;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-05-06.
 */
public class View {

    private Level level;
    private Batch batch;

    //PlayerCharacter render varaibles
    private Animation runningRightAnimation;
    private Animation runningLeftAnimation;
    private TextureRegion idleTexture;
    private float stateTime;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private BackgroundFactory backgroundFactory;
    private BackgroundGroup backgroundGroup;

    public View(){
        batch = new SpriteBatch();                      //Create SpriteBatch for drawing textures
        level = new Level(new Matrix(0,0,null), "");    //Create a level
        camera = new OrthographicCamera();              //Create new camera

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
        backgroundFactory = new BackgroundFactory();
        backgroundGroup = backgroundFactory.createBackgroundGroup(level.getName());
    }

    public View(Level level, OrthographicCamera camera){
        this(level);
        TiledMap tileMap = new TmxMapLoader().load(LEVEL_PATH + level.getName());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap);
        this.camera = camera;
    }

    public View(Level level, OrthographicCamera camera, Batch batch){
        this(level,camera);
        this.batch =  batch;
    }

    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
        updateCamera();

        batch.begin();
        //Render background layers
        backgroundGroup.renderLayers(batch, delta);
        batch.end();

        //Render map
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();

        batch.setProjectionMatrix(camera.combined);                 //Tells the spritebatch to render according to camera
        stateTime += Gdx.graphics.getDeltaTime();

        //Render character animation
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if(playerCharacter.isMoving()) {
            if(playerCharacter.isFacingRight()) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x, playerCharPos.y, 15/100f,15/100f);
            }else{
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x, playerCharPos.y, 15/100f,15/100f);
            }
        }else{
            batch.draw(idleTexture, playerCharPos.x, playerCharPos.y, 15/100f,15/100f);
        }

        //Render water blocks
        //INSERT CODE HERE


        batch.end();

    }

    public void updateCamera() {
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        camera.position.set(playerCharPos.x, playerCharPos.y, 0f);
        camera.update();
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }
}
