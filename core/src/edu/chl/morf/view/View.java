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
import com.badlogic.gdx.physics.box2d.World;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.model.blocks.Water;
import edu.chl.morf.view.backgrounds.BackgroundFactory;
import edu.chl.morf.view.backgrounds.BackgroundLayer;

import java.awt.geom.Point2D;
import java.util.List;

import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;

/**
 * This class is what makes us see the graphical representation of the game, by rendering the game model to the screen.
 * It takes data from the logical model Level, affected by GameLogic, and renders it to the screen.
 * View contains different textures for the blocks, and sprite sheets for animating the player character.
 * View is also responsible for the fading effects, shown upon starting and completing a level.
 * <p>
 * Created by Lage on 2015-05-06.
 */
public class View {

    //Character animation constants
    public static final String CHARACTERS_ATLAS_PATH = "spritesheets/Character_Sprite_Sheet";
    protected final String[] PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES = new String[]{"runningLeft1", "runningLeft2", "runningLeft3",
            "runningLeft4", "runningLeft5", "runningLeft6", "runningLeft7"};
    protected final String[] PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES = new String[]{"runningRight1", "runningRight2", "runningRight3",
            "runningRight4", "runningRight5", "runningRight6", "runningRight7",};
    protected final String[] PLAYERCHARACTER_POURRIGHT_REGION_NAMES = new String[]{"pourRight1", "pourRight1", "pourRight2", "pourRight2", "pourRight2",
            "pourRight3", "pourRight3", "pourRight3", "pourRight2", "pourRight2", "pourRight2", "pourRight1", "pourRight1"};
    protected final String[] PLAYERCHARACTER_COOLRIGHT_REGION_NAMES = new String[]{"idleRightEmpty", "coolRight1", "coolRight1", "coolRight2",
            "coolRight2", "coolRight2", "coolRight3", "coolRight3", "coolRight3", "coolRight2", "coolRight2", "coolRight2", "coolRight1", "coolRight1", "idleRightEmpty"};
    protected final String[] PLAYERCHARACTER_HEATRIGHT_REGION_NAMES = new String[]{"idleRightEmpty", "heatRight1", "heatRight1", "heatRight2", "heatRight2",
            "heatRight2", "heatRight3", "heatRight3", "heatRight3", "heatRight2", "heatRight2", "heatRight2", "heatRight1", "heatRight1", "idleRightEmpty"};
    protected final String[] PLAYERCHARACTER_POURLEFT_REGION_NAMES = new String[]{"pourLeft1", "pourLeft1", "pourLeft2", "pourLeft2", "pourLeft2",
            "pourLeft3", "pourLeft3", "pourLeft3", "pourLeft2", "pourLeft2", "pourLeft2", "pourLeft1", "pourLeft1"};
    protected final String[] PLAYERCHARACTER_COOLLEFT_REGION_NAMES = new String[]{"idleLeftEmpty", "coolLeft1", "coolLeft1", "coolLeft2",
            "coolLeft2", "coolLeft2", "coolLeft3", "coolLeft3", "coolLeft3", "coolLeft2", "coolLeft2", "coolLeft2", "coolLeft1", "coolLeft1", "idleLeftEmpty"};
    protected final String[] PLAYERCHARACTER_HEATLEFT_REGION_NAMES = new String[]{"idleLeftEmpty", "heatLeft1", "heatLeft1", "heatLeft2", "heatLeft2",
            "heatLeft2", "heatLeft3", "heatLeft3", "heatLeft3", "heatLeft2", "heatLeft2", "heatLeft2", "heatLeft1", "heatLeft1", "idleLeftEmpty"};
    protected final String[] PLAYERCHARACTER_FLYLEFT_REGION_NAMES = new String[]{"flyingLeft1", "flyingLeft1", "flyingLeft2", "flyingLeft2",
            "flyingLeft3", "flyingLeft3", "flyingLeft4", "flyingLeft4", "flyingLeft5", "flyingLeft5"};
    protected final String[] PLAYERCHARACTER_DEATHLEFT_REGION_NAMES = new String[]{"idleLeftEmpty", "deathLeft1", "deathLeft1", "deathLeft2", "deathLeft2",
            "deathLeft3", "deathLeft3", "deathLeft4", "deathLeft4", "deathLeft4"};
    protected final String[] PLAYERCHARACTER_FLYRIGHT_REGION_NAMES = new String[]{"flyingRight1", "flyingRight1", "flyingRight2", "flyingRight2",
            "flyingRight3", "flyingRight3", "flyingRight4", "flyingRight4", "flyingRight5", "flyingRight5"};
    protected final String[] PLAYERCHARACTER_DEATHRIGHT_REGION_NAMES = new String[]{"idleRightEmpty", "deathRight1", "deathRight1", "deathRight2", "deathRight2",
            "deathRight3", "deathRight3", "deathRight4", "deathRight4", "deathRight4"};

    //PlayerCharacter render variables
    private TextureRegion idleRightTexture;
    private Animation deathRightAnimation;
    private Animation runningRightAnimation;
    private Animation pourRightAnimation;
    private Animation coolRightAnimation;
    private Animation heatRightAnimation;
    private Animation flyingRightAnimation;
    private Animation deathLeftAnimation;
    private TextureRegion idleLeftTexture;
    private Animation runningLeftAnimation;
    private Animation pourLeftAnimation;
    private Animation coolLeftAnimation;
    private Animation heatLeftAnimation;
    private Animation flyingLeftAnimation;
    private int currentAnimationTime;
    private boolean deathAnimationDone;

    //Environment render variables
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Texture waterTexture;
    private Texture waterTextureBottom;
    private Texture iceTexture;
    private Texture vaporTexture;
    private Texture flowerTexture;
    private Texture waterMeterTexture;
    private Texture waterLevelTexture;
    private Texture levelCompletedTexture;
    private float stateTime;    //Used to draw correct animation frame of player character

    //Model variables
    private PlayerCharacter playerCharacter;
    private Level level;

    private Batch batch; //Used to draw textures

    private OrthographicCamera camera;

    private BackgroundFactory backgroundFactory;
    private List<BackgroundLayer> backgroundGroup;

    private OrthographicCamera hudCam;
    private BitmapFont font;

    private RayHandler rayHandler;                      //RayHandler is used to render box2dLights
    private box2dLight.DirectionalLight fadeOutLight;   //Light used to create a fade out effect
    private box2dLight.DirectionalLight fadeInLight;    //Light used to create a fade in effect

    //Fade effect values
    private float timeToFadeOut;
    private float timeToFadeIn;
    private float timePassedDuringFadeOut;
    private float timePassedDuringFadeIn;
    private float fadeOutAlpha;
    private float fadeInAlpha;
    private boolean isNewLevel; //Used to know when to fade in

    public View(Level level, OrthographicCamera camera, OrthographicCamera hudCam, Batch batch, World world) {

        //Load PayerCharacter sprite sheet from assets
        TextureAtlas characterTextureAtlas = new TextureAtlas(CHARACTERS_ATLAS_PATH);
        runningLeftAnimation = generateAnimation(PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES, characterTextureAtlas, 0.1f);
        pourLeftAnimation = generateAnimation(PLAYERCHARACTER_POURLEFT_REGION_NAMES, characterTextureAtlas, 1);
        heatLeftAnimation = generateAnimation(PLAYERCHARACTER_HEATLEFT_REGION_NAMES, characterTextureAtlas, 1);
        coolLeftAnimation = generateAnimation(PLAYERCHARACTER_COOLLEFT_REGION_NAMES, characterTextureAtlas, 1);
        flyingLeftAnimation = generateAnimation(PLAYERCHARACTER_FLYLEFT_REGION_NAMES, characterTextureAtlas, 1);
        deathLeftAnimation = generateAnimation(PLAYERCHARACTER_DEATHLEFT_REGION_NAMES, characterTextureAtlas, 1);
        runningRightAnimation = generateAnimation(PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES, characterTextureAtlas, 0.1f);
        pourRightAnimation = generateAnimation(PLAYERCHARACTER_POURRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        heatRightAnimation = generateAnimation(PLAYERCHARACTER_HEATRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        coolRightAnimation = generateAnimation(PLAYERCHARACTER_COOLRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        flyingRightAnimation = generateAnimation(PLAYERCHARACTER_FLYRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        deathRightAnimation = generateAnimation(PLAYERCHARACTER_DEATHRIGHT_REGION_NAMES, characterTextureAtlas, 1);
        idleRightTexture = characterTextureAtlas.findRegion("idleRight");
        idleLeftTexture = characterTextureAtlas.findRegion("idleLeft");

        //Load remaining textures
        waterTexture = new Texture("tiles/waterTile.png");
        waterTextureBottom = new Texture("tiles/waterTile-Middle.png");
        iceTexture = new Texture("tiles/ice.png");
        vaporTexture = new Texture("tiles/steam.png");
        flowerTexture = new Texture("tiles/flower.png");
        waterMeterTexture = new Texture("waterMeter.png");
        waterLevelTexture = new Texture("waterLevel.png");
        levelCompletedTexture = new Texture("levelCompleted.png");

        stateTime = 0f;
        this.level = level;
        playerCharacter = level.getPlayer();

        backgroundFactory = new BackgroundFactory();
        backgroundGroup = backgroundFactory.createBackgroundGroup();

        this.camera = camera;
        this.batch = batch;
        this.hudCam = hudCam;
        this.hudCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
        font = new BitmapFont();

        //Load level's tiled map .tmx file
        TiledMap tileMap = new TmxMapLoader().load("levels/" + level.getName());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap);

        rayHandler = new RayHandler(world);
        fadeOutLight = new DirectionalLight(rayHandler, 50, new Color(255, 0, 0, 0), -90);
        fadeInLight = new DirectionalLight(rayHandler, 50, new Color(255, 0, 0, 0), -90);
        isNewLevel = true;

        initFadeValues();
    }

    //Reset/initialize values used by fade effects
    public void initFadeValues() {
        timeToFadeOut = 2;
        timeToFadeIn = 2;
        timePassedDuringFadeOut = 0;
        timePassedDuringFadeIn = 0;
        fadeOutAlpha = 0;
        fadeInAlpha = 0;
    }

    public void changeLevel(Level level) {
        deathAnimationDone = false;
        this.level = level;
        playerCharacter = level.getPlayer();
        tiledMapRenderer.setMap(new TmxMapLoader().load("levels/" + level.getName()));
        initFadeValues();
        isNewLevel = true;
    }

    public Animation generateAnimation(String[] textureNames, TextureAtlas textureAtlas, float frameDuration) {
        TextureRegion[] animationFrames = new TextureRegion[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            String path = textureNames[i];
            animationFrames[i] = textureAtlas.findRegion(path);
        }
        return new Animation(frameDuration, animationFrames);
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   //Clears the screen.
        updateCamera();
        batch.begin();

        //Render background layers
        batch.setProjectionMatrix(hudCam.combined);
        try {
            //Make background move as player character does
            for(BackgroundLayer backgroundLayer: backgroundGroup){
                backgroundLayer.setSpeed(playerCharacter.getMovementVector().x);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getCause());
        }
        for(BackgroundLayer backgroundLayer: backgroundGroup){
            backgroundLayer.draw(batch,delta);
        }
        batch.end();

        //Render map
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        batch.setProjectionMatrix(camera.combined); //Tells the batch to render according to camera
        stateTime += Gdx.graphics.getDeltaTime();

        //Render character animation
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if (playerCharacter.isFacingRight()) {
            if (playerCharacter.isFlying()) {
                runFlyingAnimation(flyingRightAnimation, batch);
            } else if (playerCharacter.stoppedFlying()) {
                runFlyingStoppedAnimation(flyingRightAnimation, batch);
            } else if (playerCharacter.isDead()) {
                runCharacterAnimation(deathRightAnimation, batch);
                if (!(currentAnimationTime < deathRightAnimation.getKeyFrames().length * 2)) {
                    deathAnimationDone = true;
                }
            } else if (playerCharacter.isMoving()) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            } else if (playerCharacter.isPouringWater()) {
                runCharacterAnimation(pourRightAnimation, batch);
            } else if (playerCharacter.isCoolingWater()) {
                runCharacterAnimation(coolRightAnimation, batch);
            } else if (playerCharacter.isHeatingWater()) {
                runCharacterAnimation(heatRightAnimation, batch);
            } else {
                batch.draw(idleRightTexture, playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            }
        } else {
            if (playerCharacter.isFlying()) {
                runFlyingAnimation(flyingLeftAnimation, batch);
            } else if (playerCharacter.stoppedFlying()) {
                runFlyingStoppedAnimation(flyingLeftAnimation, batch);
            } else if (playerCharacter.isDead()) {
                runCharacterAnimation(deathLeftAnimation, batch);
                if (!(currentAnimationTime < deathLeftAnimation.getKeyFrames().length * 2)) {
                    deathAnimationDone = true;
                }
            } else if (playerCharacter.isMoving()) {
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            } else if (playerCharacter.isPouringWater()) {
                runCharacterAnimation(pourLeftAnimation, batch);
            } else if (playerCharacter.isCoolingWater()) {
                runCharacterAnimation(coolLeftAnimation, batch);
            } else if (playerCharacter.isHeatingWater()) {
                runCharacterAnimation(heatLeftAnimation, batch);
            } else {
                batch.draw(idleLeftTexture, playerCharPos.x - TILE_SIZE / 2, playerCharPos.y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            }
        }

        //Render water blocks
        for (Water water : level.getWaterBlocks()) {
            if (water.getState() == WaterState.LIQUID) {
                if (water.isBottomBlock()) {
                    batch.draw(waterTextureBottom, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(waterTexture, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                }
            } else if (water.getState() == WaterState.SOLID) {
                batch.draw(iceTexture, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            } else if (water.getState() == WaterState.GAS) {
                batch.draw(vaporTexture, water.getPosition().x - TILE_SIZE / 2, water.getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            }
        }

        //Render flower
        batch.draw(flowerTexture, level.getFlower().getPosition().x - TILE_SIZE / 2, level.getFlower().getPosition().y - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);

        //Render HUD (Water level)
        batch.setProjectionMatrix(hudCam.combined);
        float deltaWidth = (waterMeterTexture.getWidth() - waterLevelTexture.getWidth()) / 2f;
        float deltaHeight = (waterMeterTexture.getHeight() - waterLevelTexture.getHeight()) / 2f;
        int currentWaterLevel = playerCharacter.getWaterAmount();
        float computedWidth = (float) waterLevelTexture.getWidth() / level.getStartingWaterAmount() * currentWaterLevel;
        float paddingTop = 20;
        float paddingLeft = 20;

        //Water meter (background texture)
        batch.draw(waterMeterTexture, paddingLeft, Main.V_HEIGHT - waterMeterTexture.getHeight() - paddingTop);

        //Water level (blue texture on top)
        batch.draw(waterLevelTexture, paddingLeft + deltaWidth, Main.V_HEIGHT - waterMeterTexture.getHeight() - paddingTop + deltaHeight, computedWidth, waterLevelTexture.getHeight());

        //Also show water level as text
        String waterLevelString = currentWaterLevel + " / " + level.getStartingWaterAmount();
        BitmapFont.TextBounds messageBounds = font.getBounds(waterLevelString); //Actual size of the drawn message
        font.draw(batch, waterLevelString, paddingLeft + waterMeterTexture.getWidth() / 2f - messageBounds.width / 2f, Main.V_HEIGHT - waterMeterTexture.getHeight() / 2f - paddingTop + messageBounds.height / 2f);
        batch.end();

        if (isNewLevel) {
            fadeIn(camera.combined, delta);    //Fade in if new level
        } else if (level.isLevelWon()) {
            fadeOut(camera.combined, delta);    //Fade out if level is completed
            batch.begin();
            batch.draw(levelCompletedTexture, Main.V_WIDTH / 2 - levelCompletedTexture.getWidth() / 2, Main.V_HEIGHT / 2 - levelCompletedTexture.getHeight() / 2);    //Show level completed message
            batch.end();
        }
    }

    /*
    Fade out effect (used when the level is completed).
    Makes the color of the fadeOutLight go towards zero over time.
    This creates the effect of the screen fading to black.
     */
    public void fadeOut(Matrix4 cameraCombined, float delta) {
        fadeInLight.setActive(false);
        fadeOutLight.setActive(true);
        if (timePassedDuringFadeOut < timeToFadeOut) {
            fadeOutAlpha = 1 - timePassedDuringFadeOut / timeToFadeOut;
            fadeOutLight.setColor(new Color(0, 0, 0, fadeOutAlpha)); //Alpha of color goes towards zero (from transparent to black)
            rayHandler.setCombinedMatrix(cameraCombined);
            timePassedDuringFadeOut += delta;
        }
        rayHandler.updateAndRender();
    }

    /*
    Fade in effect (used when a new level is started).
    Makes the color of the fadeInLight go towards one over time.
    This creates the effect of the screen fading from black to transparent.
     */
    public void fadeIn(Matrix4 cameraCombined, float delta) {
        fadeOutLight.setActive(false);
        fadeInLight.setActive(true);
        if (timePassedDuringFadeIn < timeToFadeIn) {
            fadeInAlpha = 1 - timePassedDuringFadeIn / timeToFadeIn;
            fadeInLight.setColor(new Color(0, 0, 0, 1 - fadeInAlpha)); //Alpha of color goes towards one (from black to transparent)
            rayHandler.setCombinedMatrix(cameraCombined);
            timePassedDuringFadeIn += delta;
        } else {
            isNewLevel = false;     //Set isNewLevel to false when fade in effect is done
        }
        rayHandler.updateAndRender();
    }

    public void updateCamera() {
        PlayerCharacter playerCharacter = level.getPlayer();
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        camera.position.set(playerCharPos.x, Main.V_HEIGHT / 2, 0f);  //Camera follows x-axis of player character, but has fixed y-axis
        camera.update();
    }

    public boolean isDeathAnimationDone() {
        return deathAnimationDone;
    }

    public void runCharacterAnimation(Animation animation, Batch batch) {
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if (currentAnimationTime < animation.getKeyFrames().length * 2) {
            batch.draw(animation.getKeyFrame(currentAnimationTime / 2f, true),
                    playerCharPos.x - TILE_SIZE / 2f, playerCharPos.y - TILE_SIZE / 2f, TILE_SIZE, TILE_SIZE);
            currentAnimationTime++;
        } else {
            playerCharacter.stopHeating();
            playerCharacter.stopPouring();
            playerCharacter.stopCooling();
            currentAnimationTime = 0;
        }
    }

    public void runFlyingAnimation(Animation animation, Batch batch) {
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if (currentAnimationTime < animation.getKeyFrames().length * 2 - 1) {
            currentAnimationTime++;
        }
        batch.draw(animation.getKeyFrame(currentAnimationTime / 2f, true),
                playerCharPos.x - TILE_SIZE / 2f, playerCharPos.y - TILE_SIZE / 2f, TILE_SIZE, TILE_SIZE * 1.5f);
    }

    public void runFlyingStoppedAnimation(Animation animation, Batch batch) {
        Point2D.Float playerCharPos = playerCharacter.getPosition();
        if (currentAnimationTime > 0) {
            currentAnimationTime--;
        } else {
            playerCharacter.doneFlying();
            playerCharacter.stopCooling();
            playerCharacter.stopHeating();
            playerCharacter.stopPouring();
        }
        batch.draw(animation.getKeyFrame(currentAnimationTime / 2f, true),
                playerCharPos.x - TILE_SIZE / 2f, playerCharPos.y - TILE_SIZE / 2f, TILE_SIZE, TILE_SIZE * 1.5f);
    }
}
