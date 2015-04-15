package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.chl.morf.Constants;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Actors.Blocks.Ground;

public class TestStage extends Stage{
    private PlayerCharacter playerCharacter;
    private Vector2 left = new Vector2(-10,0);
    private Vector2 right = new Vector2(10,0);
    private Vector2 up = new Vector2(0,6);
    private Vector2 down = new Vector2(0,-0.2f);
    private Vector2 currentVector = new Vector2(0,0);
    private World world;
    
    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    float accumulator;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    public boolean isIdleY(){
        if(currentVector.y == 0){
            return true;
        }
        return false;
    }

    public void fall(){
        if(playerCharacter.getY() > 0f) {
            playerCharacter.setVelocity(currentVector.add(down));
        } else {
            setYVelocity(0f);
            playerCharacter.setY(0f);
        }
    }

    public TestStage() {

        world  = new World(Constants.WORLD_GRAVITY, true);

        accumulator = 0f;

        //Create PlayerCharacter body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(10, 100));        //PlayerCharacter position
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 10);                       //PlayerCharacter Width/Height
        Body body = world.createBody(bodyDef);
        FixtureDef fixDef = new FixtureDef();
        fixDef.density = 0.5f;
        fixDef.shape = shape;
        fixDef.filter.categoryBits = 2;
        fixDef.filter.maskBits = 4;
        body.createFixture(fixDef);                //PlayerCharacter shape and density
        body.resetMassData();
        shape.dispose();
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
        		bodyDef.position.set((col + 0.5f)*tileSize, (row + 0.5f)*tileSize);
        		
        		ChainShape cs = new ChainShape();
        		Vector2[] v = new Vector2[3];
        		v[0] = new Vector2(-tileSize / 2, -tileSize / 2);
        		v[1] = new Vector2(-tileSize / 2, tileSize / 2);
        		v[2] = new Vector2(tileSize / 2, tileSize / 2);
        		
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
        playerCharacter.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        currentVector = currentVector.add(left);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        ((PlayerCharacter) event.getTarget()).moveLeft();
                        break;
                    case Input.Keys.RIGHT:
                        currentVector = currentVector.add(right);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        ((PlayerCharacter) event.getTarget()).moveRight();
                        break;
                    case Input.Keys.UP:
                        currentVector = currentVector.add(up);
                        ((PlayerCharacter) event.getTarget()).setVelocity(currentVector);
                        ((PlayerCharacter) event.getTarget()).stop();
                        break;
                }
                return true;
            }
        });
        playerCharacter.addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(left));
                } else if (keycode == Input.Keys.RIGHT) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(right));
                } else if (keycode == Input.Keys.UP) {
                    ((PlayerCharacter) event.getTarget()).setVelocity(currentVector.sub(up));
                }
                return true;
            }
        });

        renderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(800,600);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    public void setYVelocity(float f){
        this.currentVector = new Vector2(this.currentVector.x, f);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        // Fixed timestep
        float TIME_STEP = 1/300f;
        accumulator += delta;
        
        tmr.setView(camera);
        tmr.render();

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

    }

    @Override
    public void draw(){
        super.draw();
        renderer.render(world, camera.combined);
    }
}

