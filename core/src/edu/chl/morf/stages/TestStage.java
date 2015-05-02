package edu.chl.morf.stages;

import static edu.chl.morf.Constants.BACKGROUND_BOTTOM_IMAGE_PATH;
import static edu.chl.morf.Constants.BACKGROUND_IMAGE_PATH;
import static edu.chl.morf.Constants.BACKGROUND_TOP_IMAGE_PATH;
import static edu.chl.morf.Constants.BOTTOM_CLOUDS_IMAGE_PATH;
import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.Constants.MOUNTAINS_IMAGE_PATH;
import static edu.chl.morf.Constants.PPM;
import static edu.chl.morf.Constants.TOP_CLOUDS_IMAGE_PATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.chl.morf.Constants;
import edu.chl.morf.WorldUtils;
import edu.chl.morf.actors.BackgroundLayer;
import edu.chl.morf.actors.PlayerCharacter;
import edu.chl.morf.controllers.MyContactListener;
import edu.chl.morf.model.Level;

public class TestStage{
	private MyContactListener contactListener;
	private World world;
	private Body playerBody;
    private float accumulator;
    
    private Level level;
    
    private TiledMap tileMap;
    private TiledMapTileLayer groundLayer;
    private float tileSize;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera b2dCam;
    
    public TestStage(){
    	world = WorldUtils.createWorld();
    	
    	
    	accumulator = 0f;
    	
      tileMap = new TmxMapLoader().load(LEVEL_PATH + "Level_2");
      groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");
      tileSize = groundLayer.getTileWidth();
      tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1/PPM);

      //Gdx.input.setInputProcessor(this);
      //setKeyboardFocus(playerCharacter);
      renderer = new Box2DDebugRenderer();
      b2dCam = new OrthographicCamera();
      b2dCam.setToOrtho(false, Constants.GAME_WIDTH / PPM, Constants.GAME_HEIGHT / PPM);
      //playerCharacter.setCamera(b2dCam);
      
      world.setContactListener(contactListener);
    	
    }
//    public GameStage(String levelName) {
//        world = WorldUtils.createWorld();
//        playerCharacter = WorldUtils.createPlayerCharacter(world);
//        accumulator = 0f;


//        addActor(playerCharacter);
//
//        tileMap = new TmxMapLoader().load(LEVEL_PATH+levelName);
//        groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");
//        tileSize = groundLayer.getTileWidth();
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1/PPM);
//
//        Gdx.input.setInputProcessor(this);
//        setKeyboardFocus(playerCharacter);
//        renderer = new Box2DDebugRenderer();
//        b2dCam = new OrthographicCamera();
//        b2dCam.setToOrtho(false, Constants.GAME_WIDTH / PPM, Constants.GAME_HEIGHT / PPM);
//        playerCharacter.setCamera(b2dCam);
//        world.setContactListener(this);
//        
//        
//        level = createLevel();
//        
//        generateLevel();
//    }
}
