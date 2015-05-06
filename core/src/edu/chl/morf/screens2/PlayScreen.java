package edu.chl.morf.screens2;

import static edu.chl.morf.handlers.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.controllers.MyContactListener;
import edu.chl.morf.handlers.Constants;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

public class PlayScreen extends GameScreen{
	
	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera box2dCam; 
	private Body playerBody;
	private Level level;

	public PlayScreen(ScreenManager sm){
		super(sm);
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(new MyContactListener(level));
		renderer = new Box2DDebugRenderer();
		
		//platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 120 / PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / PPM, 5 / PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_GROUND;
		fdef.filter.maskBits = Constants.BIT_PLAYER;
		body.createFixture(fdef).setUserData(new UserData(UserDataType.GROUND));
		
		//Player
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		shape.setAsBox(5 / PPM, 5 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = Constants.BIT_PLAYER;
		fdef.filter.maskBits = Constants.BIT_GROUND;
		playerBody.createFixture(fdef).setUserData(new UserData(UserDataType.PLAYERCHARACTER));
		
		
		//Set up box2d camera
		box2dCam = new OrthographicCamera();
		box2dCam.setToOrtho(false, Main.V_WIDTH / PPM, Main.V_HEIGHT / PPM);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//Clear screen
		Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT); 
		
		//Draw box2d world
		renderer.render(world, box2dCam.combined);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput(){
		
	}
	
	public void update(float dt){
		world.step(dt, 6, 2);
	}


}
