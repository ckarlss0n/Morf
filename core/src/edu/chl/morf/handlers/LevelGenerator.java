package edu.chl.morf.handlers;

import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.Constants.GROUND_FRICTION;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.model.Flower;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.LevelObject;
import edu.chl.morf.model.TileType;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;

public class LevelGenerator {
	
	public void generateLevel(Level level, World world, Map<Body, Water> bodyBlockMap){

		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		FixtureDef fixDef = new FixtureDef();
		BodyFactory bodyFactory = new BodyFactory();
		
		//Generate ground and spike bodies
		for (LevelObject object : level.getMatrix().getLevelObjects()){
			if (object.getTileType() == TileType.GROUND){
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((object.getPosition().y + 0.5f) * TILE_SIZE / PPM, (object.getPosition().x + 0.5f) * TILE_SIZE / PPM);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[1] = new Vector2(-TILE_SIZE / 2 / PPM, TILE_SIZE / 2 / PPM);
				v[2] = new Vector2(TILE_SIZE / 2 / PPM, TILE_SIZE / 2 / PPM);
				v[3] = new Vector2(TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[4] = new Vector2(-TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);

				chainShape.createChain(v);
				fixDef.friction = GROUND_FRICTION;
				fixDef.shape = chainShape;
				fixDef.filter.categoryBits = BIT_GROUND;
				fixDef.filter.maskBits = -1;
				fixDef.isSensor = false;
				world.createBody(bodyDef).createFixture(fixDef);
			}
			else if (object.getTileType() == TileType.SPIKES){
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((object.getPosition().y + 0.5f) * TILE_SIZE / PPM, (object.getPosition().x + 0.5f) * TILE_SIZE / PPM);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-TILE_SIZE*0.95f / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[1] = new Vector2(-TILE_SIZE*0.95f / 2 / PPM, TILE_SIZE * 0.6f / 2 / PPM);
				v[2] = new Vector2(TILE_SIZE*0.95f / 2 / PPM, TILE_SIZE * 0.6f/ 2 / PPM);
				v[3] = new Vector2(TILE_SIZE*0.95f / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[4] = new Vector2(-TILE_SIZE*0.95f / 2 / PPM, -TILE_SIZE / 2 / PPM);

				chainShape.createChain(v);
				fixDef.shape = chainShape;
				fixDef.filter.categoryBits = BIT_SPIKES;
				fixDef.filter.maskBits = BIT_SENSOR;
				fixDef.isSensor = false;
				world.createBody(bodyDef).createFixture(fixDef).setUserData(new UserData(UserDataType.SPIKE));
			}
		}

		//Generate water bodies
		for (Water water : level.getWaterBlocks()) {
			if (water.getState() == WaterState.LIQUID){
				Body body = bodyFactory.createWaterBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
			}
			else if (water.getState() == WaterState.SOLID){
				Body body = bodyFactory.createIceBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
			}
			else if (water.getState() == WaterState.GAS){
				Body body = bodyFactory.createVaporBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
			}
		}
		
		//Generate flower body
		if (level.getFlower() != null){
			Flower flower = level.getFlower();
			bodyFactory.createFlowerBody(world, new Vector2(flower.getPosition().x, flower.getPosition().y));
		}
	}
}
