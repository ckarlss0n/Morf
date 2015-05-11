package edu.chl.morf.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.chl.morf.actors.BackgroundLayer;
import edu.chl.morf.actors.PlayerCharacter;
import edu.chl.morf.Constants;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.TileType;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;
import edu.chl.morf.WorldUtils;
import static edu.chl.morf.Constants.*;

/**
 * This class is used to create a new level.
 * It generates a playable map from a Tiled map editor file.
 * It adds visual elements, such as different background layers for the parallax scrolling effect.
 * It also keeps track of collisions in the game.
 * <p>
 * <li><b>Responsible for: </b>
 * <li>Generating a map from a .tmx-file
 * <li>Adding visual elements, such as background layers
 * <li>Keeping track of collisions
 * <p>
 * <li><b>Used by: </b>
 * {@link edu.chl.morf.screens.GameScreen}
 * <p>
 * <li><b>Using: </b>
 * <li>LibGDX classes</li>
 * <li>{@link Constants}
 * <li>{@link WorldUtils}
 * <li>{@link BackgroundLayer}
 * <li>{@link PlayerCharacter}
 * <li>{@link Water}
 * <li>{@link UserData}
 * <li>{@link UserDataType}
 * <p>
 * @author Christoffer Karlsson
 */
public class GameStage extends Stage implements ContactListener {

    private World world;
    private PlayerCharacter playerCharacter;
    private float accumulator;
    
    private Level level;

    private BackgroundLayer background;
    private BackgroundLayer mountains;
    private BackgroundLayer backgroundBottom;
    private BackgroundLayer backgroundTop;
    private BackgroundLayer bottomClouds;
    private BackgroundLayer topClouds;

    private TiledMap tileMap;
    private TiledMapTileLayer groundLayer;
    private float tileSize;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera b2dCam;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public GameStage(String levelName) {
        world = WorldUtils.createWorld();
        playerCharacter = WorldUtils.createPlayerCharacter(world);
        accumulator = 0f;

        background = new BackgroundLayer(BACKGROUND_IMAGE_PATH);
        mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH);
        backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH);
        backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH);
        bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH);
        topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH);

        addActor(background);
        addActor(mountains);
        addActor(backgroundBottom);
        addActor(backgroundTop);
        addActor(bottomClouds);
        addActor(topClouds);
        addActor(playerCharacter);

        tileMap = new TmxMapLoader().load(LEVEL_PATH+levelName);
        groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");
        tileSize = groundLayer.getTileWidth();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1/PPM);

        Gdx.input.setInputProcessor(this);
        setKeyboardFocus(playerCharacter);
        renderer = new Box2DDebugRenderer();
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Constants.GAME_WIDTH / PPM, Constants.GAME_HEIGHT / PPM);
        playerCharacter.setCamera(b2dCam);
        world.setContactListener(this);
        
        
        //level = createLevel();
        
        //generateLevel();
    }
    
//    public Level createLevel(){
//        TileType[][] matrix = new TileType[groundLayer.getHeight()][groundLayer.getWidth()];
//        
//        for (int row = 0; row < groundLayer.getHeight(); row++) {
//            for (int col = 0; col < groundLayer.getWidth(); col++) {
//                TiledMapTileLayer.Cell cell = groundLayer.getCell(col, row);
//   
//                if (cell == null) continue;
//                if (cell.getTile() == null) continue;
//
//                matrix[groundLayer.getHeight() - 1 - row][col] = TileType.GROUND;
//            }
//        }
//        return new Level(matrix);
//    }

//    public void generateLevel(){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.fixedRotation = true;
//        FixtureDef fixDef = new FixtureDef();
//        
//        for (int row = 0; row < level.getMatrix().length; row++) {
//            for (int col = 0; col < level.getMatrix()[0].length; col++) {
//                TiledMapTileLayer.Cell cell = groundLayer.getCell(col, row);
//                
//                
//                if (cell == null) continue;
//                if (cell.getTile() == null) continue;
//                
//                bodyDef.type = BodyType.StaticBody;
//                bodyDef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);
//
//                ChainShape chainShape = new ChainShape();
//                Vector2[] v = new Vector2[5];
//                v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
//                v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
//                v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
//                v[3] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM);
//                v[4] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
//
//                chainShape.createChain(v);
//                fixDef.friction = GROUND_FRICTION;
//                fixDef.shape = chainShape;
//                fixDef.filter.categoryBits = 4;
//                fixDef.filter.maskBits = -1;
//                fixDef.isSensor = false;
//                world.createBody(bodyDef).createFixture(fixDef);
//            }
//        }
//        
//    }

    public World getWorld() { return world; }
    public PlayerCharacter getPlayerCharacter() { return playerCharacter; }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        float TIME_STEP = 1 / 300f;
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        Vector2 playerVelocity = playerCharacter.getVelocity();
        background.setSpeed(0);        //Only move if player is moving
        mountains.setSpeed(playerVelocity.x * - 10);
        backgroundBottom.setSpeed(playerVelocity.x * - 30);
        backgroundTop.setSpeed(playerVelocity.x * - 50);
        bottomClouds.setSpeed(playerVelocity.x * -10 + 5);  //Slow scroll
        topClouds.setSpeed(playerVelocity.x * -10 + 20);    //Faster scroll

        String alive = "Yes! You are alive.";
        if (!playerCharacter.isAlive()) {
            alive = "No. You are dead.";
        }
        int width = Gdx.graphics.getWidth();
        int height = Gdx.app.getGraphics().getHeight();
        int fps = Gdx.graphics.getFramesPerSecond();

        background.setMessage(Integer.toString(playerCharacter.getWaterLevel()), "Water level");
        background.setMessage(width + "x" + height, "Resolution");
        background.setMessage(Integer.toString(fps), "FPS");
        background.setMessage(alive, "Alive");
        background.setMessage(playerCharacter.getBody().getPosition().toString(), "Position");
        background.setMessage(playerCharacter.getVelocity().toString(), "Velocity");
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, b2dCam.combined);
        tiledMapRenderer.setView(b2dCam);
        tiledMapRenderer.render();
    }

    public void updateCamera() {
        b2dCam.position.set(playerCharacter.getBody().getPosition(), 0f);
        b2dCam.update();
    }

    //TODO Extract code/tidy up
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Begin touch");
        boolean fallingBeforeTouch = false;
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (contact.isTouching()) {
            if (fa.getUserData() != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT) {
                playerCharacter.setEmptyLeft(false);
                ((UserData) fa.getUserData()).increment();
            } else if (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT) {
                playerCharacter.setEmptyLeft(false);
                ((UserData) fb.getUserData()).increment();
            } else if ((fa.getUserData()) != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT) {
                playerCharacter.setEmptyRight(false);
                ((UserData) fa.getUserData()).increment();
            } else if (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT) {
                playerCharacter.setEmptyRight(false);
                ((UserData) fb.getUserData()).increment();
            } else if ((fa.getUserData() != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.SPIKE) &&
                    (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.PLAYERCHARACTER)) {
                playerCharacter.die();
            } else if ((fa.getUserData() != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.PLAYERCHARACTER) &&
                    (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.SPIKE)) {
                playerCharacter.die();
            }
        }
        if (playerCharacter.getVelocity().y < -10) {
            fallingBeforeTouch = true;
        }
        if (fallingBeforeTouch && contact.isTouching()) {
            System.out.println("DIE");
            playerCharacter.die();
        } else {
            System.out.println("No touch!");
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.println("End contact!");

        if ((fa.getUserData()) != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT) {
            ((UserData) fa.getUserData()).decrement();
            if (((UserData) fa.getUserData()).getNumOfContacts() == 0) {
                playerCharacter.setEmptyLeft(true);
            }
        } else if (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT) {
            ((UserData) fb.getUserData()).decrement();
            if (((UserData) fb.getUserData()).getNumOfContacts() == 0) {
                playerCharacter.setEmptyLeft(true);
            }
        } else if ((fa.getUserData()) != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT) {
            ((UserData) fa.getUserData()).decrement();
            if (((UserData) fa.getUserData()).getNumOfContacts() == 0) {
                playerCharacter.setEmptyRight(true);
            }
        } else if (fb.getUserData() != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT) {
            ((UserData) fb.getUserData()).decrement();
            if (((UserData) fb.getUserData()).getNumOfContacts() == 0) {
                playerCharacter.setEmptyRight(true);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
