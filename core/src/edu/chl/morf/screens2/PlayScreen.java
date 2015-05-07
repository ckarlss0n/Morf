package edu.chl.morf.screens2;

import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.handlers.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.controllers.GameController;
import edu.chl.morf.controllers.GameLogic;
import edu.chl.morf.controllers.MyContactListener;
import edu.chl.morf.handlers.Constants;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.TileType;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;
import edu.chl.morf.view.View;

public class PlayScreen extends GameScreen{

	private GameLogic gameLogic;
	private OrthographicCamera box2dCam;
	private Level level;
	private View view;
	
	private MyContactListener cl;
	private GameController input;

	public PlayScreen(ScreenManager sm, String levelName){
		super(sm);
		
		LevelFactory levelFactory = new LevelFactory();
		
		level = levelFactory.getLevel(levelName);
		gameLogic = new GameLogic(level);
		
		cl = new MyContactListener(gameLogic);
		input = new GameController(gameLogic);
		

		//Set up box2d camera
		box2dCam = new OrthographicCamera();
		box2dCam.setToOrtho(false, Main.V_WIDTH / 10, Main.V_HEIGHT / 10);
		
		this.view = new View(level, box2dCam);
		view.setBatch(spriteBatch);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		gameLogic.render(delta);
		view.render(delta);
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

	}


}
