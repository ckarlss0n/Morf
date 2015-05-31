package edu.chl.morf.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import edu.chl.morf.model.*;
import edu.chl.morf.userdata.CollisionData;
import edu.chl.morf.userdata.CollisionType;

import java.util.Map;

import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;

/**
 * This class is used for creating bodies in the world.
 * It loops through the different level objects contained in the matrix of the level.
 * The class also creates bodies for the water blocks, and puts them in the bodyBlockMap.
 * <p>
 * The class creates fixtures using chain shapes outlining the blocks.
 * The fixtures are later used for collision detection in the game.
 */
public class LevelGenerator {

	public void generateLevel(Level level, World world, Map<Body, Water> bodyBlockMap) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		FixtureDef fixDef = new FixtureDef();
		BodyFactory bodyFactory = new BodyFactory();
		float tileSide = TILE_SIZE/PPM/2;	//Size used when creating fixture shapes

		//Generate ground and spike bodies
		for (LevelObject object : level.getMatrix().getLevelObjects()) {
			if (object.getTileType() == TileType.GROUND) {
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((object.getPosition().y + 0.5f) * tileSide*2, (object.getPosition().x + 0.5f) * tileSide*2);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-tileSide, -tileSide); //Bottom left corner of fixture
				v[1] = new Vector2(-tileSide, tileSide);  //Top left corner of fixture
				v[2] = new Vector2(tileSide, tileSide);   //Top right corner of fixture
				v[3] = new Vector2(tileSide, -tileSide);  //Bottom right corner of fixture
				v[4] = new Vector2(-tileSide, -tileSide); //Bottom left corner of fixture

				chainShape.createChain(v); //Create a shape from all vertices
				fixDef.friction = 0.1f;
				fixDef.shape = chainShape;
				fixDef.filter.categoryBits = BIT_GROUND;
				fixDef.filter.maskBits = -1;
				fixDef.isSensor = false;
				world.createBody(bodyDef).createFixture(fixDef);
			} else if (object.getTileType() == TileType.SPIKES) {
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((object.getPosition().y + 0.5f) * TILE_SIZE / PPM, (object.getPosition().x + 0.5f) * TILE_SIZE / PPM);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-tileSide*0.8f, -tileSide);      //Bottom left corner of fixture
				v[1] = new Vector2(-tileSide*0.8f, tileSide*0.2f);	//Top left corner of fixture (make it shorter to match height of spikes)
				v[2] = new Vector2(tileSide*0.8f, tileSide*0.2f);   //Top right corner of fixture (make it shorter to match height of spikes)
				v[3] = new Vector2(tileSide*0.8f, -tileSide);       //Bottom right corner of fixture
				v[4] = new Vector2(-tileSide*0.8f, -tileSide);		//Bottom left corner of fixture

				chainShape.createChain(v); //Create a shape from all vertices
				fixDef.shape = chainShape;
				fixDef.filter.categoryBits = BIT_SPIKES;
				fixDef.filter.maskBits = BIT_SENSOR;
				fixDef.isSensor = false;
				world.createBody(bodyDef).createFixture(fixDef).setUserData(new CollisionData(CollisionType.SPIKE));
			}
		}

		//Generate water bodies
		for (Water water : level.getWaterBlocks()) {
			if (water.getState() == WaterState.LIQUID) {
				Body body = bodyFactory.createWaterBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
			} else if (water.getState() == WaterState.SOLID) {
				Body body = bodyFactory.createIceBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
				bodyBlockMap.get(body).cool();
			} else if (water.getState() == WaterState.GAS) {
				Body body = bodyFactory.createVaporBody(world, new Vector2(water.getPosition().x, water.getPosition().y));
				bodyBlockMap.put(body, water);
				bodyBlockMap.get(body).heat();
			}
		}

		//Generate flower body
		if (level.getFlower() != null) {
			Flower flower = level.getFlower();
			bodyFactory.createFlowerBody(world, new Vector2(flower.getPosition().x, flower.getPosition().y));
		}
	}
}
