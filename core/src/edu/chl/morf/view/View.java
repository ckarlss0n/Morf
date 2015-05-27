package edu.chl.morf.view;

import box2dLight.DirectionalLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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
    private TextureRegion idleRightTexture;
    private TextureRegion flyingRightTexture;
    private Animation runningRightAnimation;
    private Animation pourRightAnimation;
    private Animation coolRightAnimation;
    private Animation heatRightAnimation;
    private Animation flyingRightAnimation;
    private Animation deathLeftAnimation;
    private TextureRegion idleLeftTexture;
    private TextureRegion flyingLeftTexture;
    private Animation runningLeftAnimation;
    private Animation pourLeftAnimation;
    private Animation coolLeftAnimation;
    private Animation heatLeftAnimation;
    private Animation flyingLeftAnimation;
    private int currentAnimationTime;

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

    private RayHandler rayHandler;
    private box2dLight.DirectionalLight fadeOutLight;
    private box2dLight.DirectionalLight fadeInLight;

    private float timeToFadeOut;
    private float timeToFadeIn;
    private float timePassedDuringFadeOut;
    private float timePassedDuringFadeIn;
    private float fadeOutAlpha;
    private float fadeInAlpha;
    private boolean isNewLevel;

    public View(Level level, OrthographicCamera camera, OrthographicCamera hudCam, OrthographicCamera b2dCam, Batch batch, World world){

        //Load PayerCharacter sprite sheet from assets
        TextureAtlas characterTextureAtlas = new TextureAtlas(CHARACTERS_ATLAS_PATH);
        runningLeftAnimation = generateAnimation(PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES,characterTextureAtlas,0.1f);
        pourLeftAnimation = generateAnimation(PLAYERCHARACTER_POURLEFT_REGION_NAMES,characterTextureAtlas,1);
        heatLeftAnimation = generateAnimation(PLAYERCHARACTER_HEATLEFT_REGION_NAMES,characterTextureAtlas,1);
        coolLeftAnimation = generateAnimation(PLAYERCHARACTER_COOLLEFT_REGION_NAMES,characterTextureAtlas,1);
        flyingLeftAnimation = generateAnimation(PLAYERCHARACTER_FLYLEFT_REGION_NAMES,characterTextureAtlas,1);

        runningRightAnimation = generateAnimation(PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES,characterTextureAtlas,0.1f);
        pourRightAnimation = generateAnimation(PLAYERCHARACTER_POURRIGHT_REGION_NAMES,characterTextureAtlas,1);
        heatRightAnimation = generateAnimation(PLAYERCHARACTER_HEATRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        coolRightAnimation = generateAnimation(PLAYERCHARACTER_COOLRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        flyingRightAnimation = generateAnimation(PLAYERCHARACTER_FLYRIGHT_REGION_NAMES,characterTextureAtlas,1);

        idleRightTexture = characterTextureAtlas.findRegion("idleRight");
        flyingRightTexture  = characterTextureAtlas.findRegion("flyingRight5");
        idleLeftTexture = characterTextureAtlas.findRegion("idleLeft");
        flyingLeftTexture  = characterTextureAtlas.findRegion("flyingLeft5");

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

        rayHandler = new RayHandler(world);
        fadeOutLight = new DirectionalLight(rayHandler, 50, new Color(255, 0, 0, 0), -90);
        fadeInLight = new DirectionalLight(rayHandler, 50, new Color(255, 0, 0, 0), -90);
        isNewLevel = true;

        initFadeValues();
    }

    public void initFadeValues(){
        timeToFadeOut = 2;
        timeToFadeIn = 2;
        timePassedDuringFadeOut = 0;
        timePassedDuringFadeIn = 0;
        fadeOutAlpha = 0;
        fadeInAlpha = 0;
    }

    public void changeLevel(Level level){
        this.level = level;
        playerCharacter = level.getPlayer();
        tiledMapRenderer.setMap(new TmxMapLoader().load(LEVEL_PATH + level.getName()));
        initFadeValues();
        isNewLevel = true;
    }

    public Animation generateAnimation(String[] textureNames, TextureAtlas textureAtlas, float frameDuration){
        TextureRegion[] animationFrames = new TextureRegion[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            String path = textureNames[i];
            animationFrames[i] = textureAtlas.findRegion(path);
        }
        return new Animation(frameDuration, animationFrames);
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

        if(playerCharacter.isFacingRight()) {
            if (playerCharacter.isMoving()) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);

            } else if(playerCharacter.isFlying()){
                if(currentAnimationTime < flyingRightAnimation.getKeyFrames().length) {
                    batch.draw(flyingRightAnimation.getKeyFrame(currentAnimationTime, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                    currentAnimationTime++;
                }else{
                    batch.draw(flyingRightTexture, playerCharPos.x - TILE_SIZE / 2,
                            playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                }
            } else if(playerCharacter.stoppedFlying()){
                if(currentAnimationTime > 0) {
                    batch.draw(flyingRightAnimation.getKeyFrame(currentAnimationTime, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                    currentAnimationTime--;
                }else{
                    playerCharacter.doneFlying();
                }
            } else if(playerCharacter.isPouringWater()){
                if(currentAnimationTime < pourRightAnimation.getKeyFrames().length * 2) {
                    batch.draw(pourRightAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopPouring();
                    currentAnimationTime = 0;
                }
            } else if(playerCharacter.isCoolingWater()){
                if(currentAnimationTime < coolRightAnimation.getKeyFrames().length * 2) {
                    batch.draw(coolRightAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopCooling();
                    currentAnimationTime = 0;
                }
            } else if(playerCharacter.isHeatingWater()){
                if(currentAnimationTime < heatRightAnimation.getKeyFrames().length * 2) {
                    batch.draw(heatRightAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopHeating();
                    currentAnimationTime = 0;
                }
            } else{
                batch.draw(idleRightTexture, playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            }
        }else{
            if(playerCharacter.isMoving()){
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x-TILE_SIZE/2, playerCharPos.y-TILE_SIZE/2, TILE_SIZE ,TILE_SIZE);
            } else if(playerCharacter.isFlying()){
                if(currentAnimationTime < flyingLeftAnimation.getKeyFrames().length) {
                    batch.draw(flyingLeftAnimation.getKeyFrame(currentAnimationTime, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                    currentAnimationTime++;
                }else{
                    batch.draw(flyingLeftTexture, playerCharPos.x - TILE_SIZE / 2,
                            playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                }
            } else if(playerCharacter.stoppedFlying()){
                if(currentAnimationTime > 0) {
                    batch.draw(flyingLeftAnimation.getKeyFrame(currentAnimationTime, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE * 1.5f);
                    currentAnimationTime--;
                }else{
                    playerCharacter.doneFlying();
                }
            } else if(playerCharacter.isPouringWater()){
                if(currentAnimationTime < pourLeftAnimation.getKeyFrames().length * 2) {
                    batch.draw(pourLeftAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopPouring();
                    currentAnimationTime = 0;
                }
            } else if(playerCharacter.isCoolingWater()){
                if(currentAnimationTime < coolLeftAnimation.getKeyFrames().length * 2) {
                    batch.draw(coolLeftAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopCooling();
                    currentAnimationTime = 0;
                }
            } else if(playerCharacter.isHeatingWater()){
                if(currentAnimationTime < heatLeftAnimation.getKeyFrames().length * 2) {
                    batch.draw(heatLeftAnimation.getKeyFrame(currentAnimationTime/2, true),
                            playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    currentAnimationTime++;
                }else{
                    playerCharacter.stopHeating();
                    currentAnimationTime = 0;
                }
            } else{
                batch.draw(idleLeftTexture, playerCharPos.x-TILE_SIZE/2, playerCharPos.y-TILE_SIZE/2, TILE_SIZE ,TILE_SIZE);
            }
        }

        //Render water blocks
        for(Water water : level.getWaterBlocks()){
            if(water.getState() == WaterState.LIQUID) {
                if (water.isBottomBlock()) {
                    batch.draw(waterTextureBottom, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(waterTexture, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                }
            } else if(water.getState() == WaterState.SOLID){
                batch.draw(iceTexture, water.getPosition().x-TILE_SIZE/2, water.getPosition().y-TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
            } else if(water.getState() == WaterState.GAS){
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
        font.draw(batch, waterLevelString, paddingLeft + waterMeterTexture.getWidth() / 2 - messageBounds.width / 2, Main.V_HEIGHT - waterMeterTexture.getHeight() / 2 - paddingTop + messageBounds.height / 2);
        batch.end();

        if(isNewLevel){
            fadeIn(camera.combined, delta);
        } else if (level.isLevelWon()){
            fadeOut(camera.combined, delta);
            batch.begin();
            batch.draw(levelCompletedTexture, Main.V_WIDTH / 2 - levelCompletedTexture.getWidth() / 2, Main.V_HEIGHT / 2 - levelCompletedTexture.getHeight() / 2);
            batch.end();
        }
    }

    public void fadeOut(Matrix4 cameraCombined, float delta){
        fadeInLight.setActive(false);
        fadeOutLight.setActive(true);
        if(timePassedDuringFadeOut < timeToFadeOut) {
            fadeOutAlpha = 1 - timePassedDuringFadeOut/timeToFadeOut;
            fadeOutLight.setColor(new Color(0, 0, 0, fadeOutAlpha)); //Alpha goes towards zero
            rayHandler.setCombinedMatrix(cameraCombined);
            rayHandler.updateAndRender();
            timePassedDuringFadeOut += delta;
        }
    }

    public void fadeIn(Matrix4 cameraCombined, float delta){
        fadeOutLight.setActive(false);
        fadeInLight.setActive(true);
        if(timePassedDuringFadeIn < timeToFadeIn) {
            fadeInAlpha = 1 - timePassedDuringFadeIn/timeToFadeIn;
            fadeInLight.setColor(new Color(0, 0, 0, 1 - fadeInAlpha)); //Alpha goes towards one
            rayHandler.setCombinedMatrix(cameraCombined);
            rayHandler.updateAndRender();
            timePassedDuringFadeIn += delta;
        } else {
            isNewLevel = false;
        }
    }

    public void updateCamera() {
        PlayerCharacterModel playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        camera.position.set(playerCharPos.x, Main.V_HEIGHT/2, 0f);
        camera.update();
        box2dCam.position.set(playerCharPos.x / PPM, Main.V_HEIGHT/2 / PPM, 0f);
        box2dCam.update();
    }
}
