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
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;
import java.awt.geom.Point2D;
import static edu.chl.morf.Constants.*;
import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;

/**
 * Created by Lage on 2015-05-06.
 */
public class View {

    private Level level;
    private Batch batch;

    //PlayerCharacter render variables
    private Animation runningRightAnimation;
    private Animation runningLeftAnimation;
    private TextureRegion idleTexture;
    private Texture waterTexture;
    private Texture waterTextureBottom;
    private Texture iceTexture;
    private Texture vaporTexture;
    private Texture flowerTexture;
    private Texture waterMeterTexture;
    private Texture waterLevelTexture;
    private Texture levelCompletedTexture;
    private float stateTime;
    
    private PlayerCharacterModel playerCharacter;

    private OrthographicCamera camera;
    private OrthographicCamera box2dCam;

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    
    private Box2DDebugRenderer b2dr;
    private World world;

    private BackgroundFactory backgroundFactory;
    private BackgroundGroup backgroundGroup;
    private OrthographicCamera hudCam;
    private BitmapFont font;
    
    public View(Level level, OrthographicCamera camera, OrthographicCamera hudCam, OrthographicCamera b2dCam, Batch batch, World world){
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
        waterTextureBottom = new Texture("Tiles/waterTile-Middle.png");
        iceTexture = new Texture("Tiles/ice.png");
        vaporTexture = new Texture("Tiles/steam.png");
        flowerTexture = new Texture("Tiles/flower.png");
        waterMeterTexture = new Texture("waterMeter.png");
        waterLevelTexture = new Texture("waterLevel.png");
        levelCompletedTexture = new Texture("levelCompleted.png");
        
        stateTime = 0f;
    	this.level = level;
    	playerCharacter = level.getPlayer();
    	
    	backgroundFactory = new BackgroundFactory();
        backgroundGroup = backgroundFactory.createBackgroundGroup(level.getName());
        
        this.camera = camera;
        this.batch =  batch;
        this.box2dCam = b2dCam;
        this.world = world;
        this.hudCam = hudCam;
        this.hudCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
        font = new BitmapFont();
        
        TiledMap tileMap = new TmxMapLoader().load(LEVEL_PATH + level.getName());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap);
        b2dr = new Box2DDebugRenderer();
    }

    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
        updateCamera();
        batch.begin();
        
        //Render background layers
        batch.setProjectionMatrix(hudCam.combined);
        backgroundGroup.setBackgroundSpeeds(playerCharacter.getSpeed().x);
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
                        playerCharPos.x-TILE_SIZE/2, playerCharPos.y-TILE_SIZE/2, TILE_SIZE ,TILE_SIZE);
            }else{
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x-TILE_SIZE/2, playerCharPos.y-TILE_SIZE/2, TILE_SIZE ,TILE_SIZE);
            }
        }else{
            batch.draw(idleTexture, playerCharPos.x-TILE_SIZE/2, playerCharPos.y-TILE_SIZE/2, TILE_SIZE ,TILE_SIZE);
        }

        //Render water blocks
        for(Water water : level.getWaterBlocks()){
        	if(water.getState() == WaterState.LIQUID) {
                if (water.isBottomBlock()) {
                    batch.draw(waterTextureBottom, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(waterTexture, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                }
            }
        	else if(water.getState() == WaterState.SOLID){
        		batch.draw(iceTexture, water.getPosition().x-TILE_SIZE/2, water.getPosition().y-TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        	}
        	else if(water.getState() == WaterState.GAS){
        		batch.draw(vaporTexture, water.getPosition().x-TILE_SIZE/2, water.getPosition().y-TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        	}
        }
        
        //Render flower
        batch.draw(flowerTexture, level.getFlower().getPosition().x - TILE_SIZE / 2, level.getFlower().getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);

        //Render HUD (Water level)
        batch.setProjectionMatrix(hudCam.combined);
        float deltaWidth = (waterMeterTexture.getWidth()- waterLevelTexture.getWidth())/2;
        float deltaHeight = (waterMeterTexture.getHeight()- waterLevelTexture.getHeight())/2;
        int waterLevel = playerCharacter.getWaterLevel();
        float computedWidth = (float) waterLevelTexture.getWidth()/playerCharacter.getMaxWaterLevel()*waterLevel;
        float paddingTop = 20;
        float paddingLeft = 20;

        //Water meter (background texture)
        batch.draw(waterMeterTexture, paddingLeft, Main.V_HEIGHT - waterMeterTexture.getHeight() - paddingTop);

        //Water level (blue texture on top)
        batch.draw(waterLevelTexture, paddingLeft + deltaWidth, Main.V_HEIGHT - waterMeterTexture.getHeight() - paddingTop + deltaHeight, computedWidth, waterLevelTexture.getHeight());

        //Also show water level as text
        String waterLevelString = waterLevel + " / " + playerCharacter.getMaxWaterLevel();
        BitmapFont.TextBounds messageBounds = font.getBounds(waterLevelString); //Actual size of the drawn message
        font.draw(batch, waterLevelString, paddingLeft + waterMeterTexture.getWidth()/2 - messageBounds.width/2, Main.V_HEIGHT - waterMeterTexture.getHeight()/2 - paddingTop + messageBounds.height/2);

        if(level.isLevelWon()){
            batch.draw(levelCompletedTexture, Main.V_WIDTH/2 - levelCompletedTexture.getWidth()/2, Main.V_HEIGHT/2 - levelCompletedTexture.getHeight()/2);
        }

        batch.end();
    }

    public void updateCamera() {
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        camera.position.set(playerCharPos.x, Main.V_HEIGHT/2, 0f);
        camera.update();
        box2dCam.position.set(playerCharPos.x / PPM, Main.V_HEIGHT/2 / PPM, 0f);
        box2dCam.update();
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }
}
