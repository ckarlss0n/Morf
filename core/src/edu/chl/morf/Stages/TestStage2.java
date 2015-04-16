package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.chl.morf.Constants;
import edu.chl.morf.WorldUtils;
import edu.chl.morf.Actors.PlayerCharacter;

public class TestStage2 extends Stage{
	
	private PlayerCharacter playerCharacter;
    private World world;
    float accumulator;
    
    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera b2dCam;
    
    private OrthographicCamera cam;
	
	public TestStage2(){
        world = WorldUtils.createWorld();
        accumulator = 0f;

        
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(10 / 100f, 10 / 100f);
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(400 / 100f, 400 / 100f);
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.DynamicBody;
		Body body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.filter.categoryBits = 2;
		fixDef.filter.maskBits = 4;
		body.createFixture(fixDef);
		
		playerCharacter = new PlayerCharacter(body);
        
        
        tileMap = new TmxMapLoader().load("testmap.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        
        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");

        tileSize = layer.getTileWidth();

        
        for(int row = 0; row < layer.getHeight(); row++){
        	for(int col = 0; col < layer.getWidth(); col++){
        		TiledMapTileLayer.Cell cell = layer.getCell(col, row);
        		
        		if(cell == null) continue;
        		if(cell.getTile() == null) continue;
        		
        		bodyDef.type = BodyType.StaticBody;
        		bodyDef.position.set((col + 0.5f)*tileSize / 100f, (row + 0.5f)*tileSize / 100f);
        		
        		ChainShape cs = new ChainShape();
        		Vector2[] v = new Vector2[5];
        		v[0] = new Vector2(-tileSize / 2 / 100f, -tileSize / 2 / 100f);
        		v[1] = new Vector2(-tileSize / 2 / 100f, tileSize / 2 / 100f);
        		v[2] = new Vector2(tileSize / 2 / 100f, tileSize / 2 / 100f);
        		v[3] = new Vector2(tileSize / 2 / 100f, -tileSize / 2 / 100f);
        		v[4] = new Vector2(-tileSize / 2 / 100f, -tileSize / 2 / 100f);
        		
        		cs.createChain(v);
        		fixDef.friction = 0;
        		fixDef.shape = cs;
        		fixDef.filter.categoryBits = 4;
        		fixDef.filter.maskBits = -1;
        		fixDef.isSensor = false;
        		world.createBody(bodyDef).createFixture(fixDef);
        	}
        }
        
        Gdx.input.setInputProcessor(this);
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);
        renderer = new Box2DDebugRenderer();
        
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        
        
        b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Constants.GAME_WIDTH / 100f, Constants.GAME_HEIGHT / 100f);

	}
	
    public void updateCamera (){
        b2dCam.update();
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        float TIME_STEP = 1 / 300f;
        accumulator += delta;

        tmr.setView(cam);
        tmr.render();
        
        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, b2dCam.combined);
    }
}
