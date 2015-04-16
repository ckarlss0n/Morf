package edu.chl.morf.Stages;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.chl.morf.Constants;
import edu.chl.morf.WorldUtils;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Constants;

public class TestStage extends Stage{
	float accumulator;
	private PlayerCharacter playerCharacter;
	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;

	public TestStage() {
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
		
		shape.setAsBox(500 / 100f, 2 / 100f);
		bodyDef.position.set(100 / 100f, 100 / 100f);
		bodyDef.type = BodyType.StaticBody;
		body = world.createBody(bodyDef);
		
		fixDef.shape = shape;
		fixDef.filter.categoryBits = 4;
		fixDef.filter.maskBits = 2;
		body.createFixture(fixDef);


		
		
		Gdx.input.setInputProcessor(this);
		addActor(playerCharacter);
		setKeyboardFocus(playerCharacter);
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.GAME_WIDTH / 100f, Constants.GAME_HEIGHT / 100f);
	}

	public void updateCamera (){
		camera.position.set(playerCharacter.getBody().getPosition(),0f);
		camera.update();
	}
	
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
	}

	@Override
	public void draw() {
		super.draw();
		renderer.render(world, camera.combined);
	}
}

