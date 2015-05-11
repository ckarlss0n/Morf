package edu.chl.morf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.backgrounds.BackgroundFactory;
import edu.chl.morf.backgrounds.BackgroundGroup;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

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
    private Texture waterTexture;
    private float stateTime;
    
    private PlayerCharacterModel playerCharacter;

    private OrthographicCamera camera;
    private OrthographicCamera box2dCam;

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    
    private Box2DDebugRenderer b2dr;
    private World world;

    private BackgroundFactory backgroundFactory;
    private BackgroundGroup backgroundGroup;
    
    public View(Level level, OrthographicCamera camera, OrthographicCamera b2dCam, Batch batch, World world){
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
        
        waterTexture = new Texture("Tiles/waterTile.png");
        
        stateTime = 0f;
    	this.level = level;
    	playerCharacter = level.getPlayer();
    	
    	backgroundFactory = new BackgroundFactory();
        backgroundGroup = backgroundFactory.createBackgroundGroup(level.getName());
        
        this.camera = camera;
        this.batch =  batch;
        this.box2dCam = b2dCam;
        this.world = world;
        
        TiledMap tileMap = new TmxMapLoader().load(LEVEL_PATH + level.getName());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap);
        b2dr = new Box2DDebugRenderer();
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
        
        b2dr.render(world, box2dCam.combined);

        batch.begin();

        batch.setProjectionMatrix(camera.combined);                 //Tells the spritebatch to render according to camera
        stateTime += Gdx.graphics.getDeltaTime();

        //Render character animation
        
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if(playerCharacter.isMoving()) {
            if(playerCharacter.isFacingRight()) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x, playerCharPos.y, 15 ,15);
            }else{
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x, playerCharPos.y, 15 ,15);
            }
        }else{
            batch.draw(idleTexture, playerCharPos.x, playerCharPos.y, 15 ,15);
        }

        //Render water blocks
        for(Water water : level.getWaterBlocks()){
        	if(water.getState() == WaterState.LIQUID){
        		batch.draw(waterTexture, water.getPosition().x, water.getPosition().y, 64, 64);
        	}
        }


        batch.end();

    }

    public void updateCamera() {
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        camera.position.set(playerCharPos.x, playerCharPos.y, 0f);
        camera.update();
        box2dCam.position.set(playerCharPos.x / PPM, playerCharPos.y / PPM, 0f);
        box2dCam.update();
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }
}
