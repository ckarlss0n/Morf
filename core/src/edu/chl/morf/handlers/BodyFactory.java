package edu.chl.morf.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import edu.chl.morf.handlers.controllers.collision.CollisionData;
import edu.chl.morf.model.WaterState;
import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;
import static edu.chl.morf.handlers.controllers.collision.CollisionType.*;

/**
 * A factory class for creating bodies for a Box2D-world.
 * 
 * @author Gustav
 */
public class BodyFactory {

	private static final int playerSize = 15;
	
	//Creates a body for a player with a position in a world.
	public Body createPlayerBody(World world, Vector2 position){

		//Create body
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;
		Body body = world.createBody(bdef);

		//Create player fixture
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2 - 2 - playerSize) / PPM, (TILE_SIZE / 2 - 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_GROUND | BIT_ICE;
		body.createFixture(fdef).setUserData(new CollisionData(PLAYERCHARACTER));

		//Create right ghost fixture 
		shape.setAsBox((TILE_SIZE / 2 - 1) / PPM, 20 / PPM, new Vector2((TILE_SIZE - 2 - playerSize) / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER | BIT_ICE | BIT_GAS;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new CollisionData(GHOST_RIGHT));

		//Create left ghost fixture
		shape.setAsBox((TILE_SIZE / 2 - 1) / PPM, 20 / PPM, new Vector2((-TILE_SIZE + 2 + playerSize) / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new CollisionData(GHOST_LEFT));

		//Create active block right fixture
		shape.setAsBox(0.1f / PPM, 20 / PPM, new Vector2((90 - playerSize) / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.maskBits = BIT_WATER | BIT_ICE | BIT_GAS;
		body.createFixture(fdef).setUserData(new CollisionData(ACTIVE_BLOCK_RIGHT));

		//Create active block left fixture
		shape.setAsBox(0.1f / PPM, 20 / PPM, new Vector2((-90 + playerSize) / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new CollisionData(ACTIVE_BLOCK_LEFT));

		//Create active block bottom right fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2((60 - playerSize) / PPM, -TILE_SIZE / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new CollisionData(ACTIVE_BLOCK_BOTTOM_RIGHT));

		//Create active block bottom left fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2((-60 + playerSize) / PPM, -TILE_SIZE / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new CollisionData(ACTIVE_BLOCK_BOTTOM_LEFT));

		//Create bottom ghost fixture
		shape.setAsBox((29 - playerSize) / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.maskBits = BIT_GROUND | BIT_SPIKES | BIT_ICE;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new CollisionData(GHOST_BOTTOM));

		//Create bottom ice ghost fixture
		shape.setAsBox((29 - playerSize) / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
		fdef.shape  =shape;
		fdef.filter.maskBits = BIT_ICE;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new CollisionData(GHOST_BOTTOM_ICE));

		//Create core ghost fixture
		shape.setAsBox(15 / PPM, 15 / PPM);
		fdef.shape = shape;
		fdef.filter.maskBits = BIT_GAS | BIT_FLOWER;
		body.createFixture(fdef).setUserData(new CollisionData(GHOST_CORE));

		shape.dispose();
		return body;
	}

	//Private method to create a body for water with a position in a world, depending on its state.
	private Body createWaterBody(World world, Vector2 position, WaterState waterState){
		//Create body
		BodyDef bdef = new BodyDef();
		bdef.position.set((position.x) / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(((TILE_SIZE - 2) / 2) / PPM, ((TILE_SIZE - 2 ) / 2) / PPM);

		//Create fixture depending on water state
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		if(waterState == WaterState.GAS){
			fdef.filter.categoryBits = BIT_GAS;
			fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		}
		else if(waterState == WaterState.LIQUID){
			fdef.filter.categoryBits = BIT_WATER;
			fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		}
		else{
			fdef.filter.categoryBits = BIT_ICE;
			fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS | BIT_PLAYER;
		}
		
		body.createFixture(fdef).setUserData(new CollisionData(WATER));

		//Create ghost fixture
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_WATER;
		shape.setAsBox((TILE_SIZE * 0.9f / 2) / PPM,(TILE_SIZE / 20) / PPM,new Vector2(0, (TILE_SIZE / 2) / PPM),0);
		fdef.shape = shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData(new CollisionData(WATER_SENSOR));

		//Create flower intersection fixture
		shape.setAsBox((TILE_SIZE * 0.9f / 2) / PPM,(TILE_SIZE * 0.9f / 2) / PPM);
		fdef.filter.maskBits=BIT_FLOWER;
		fdef.shape=shape;
		body.createFixture(fdef).setUserData(new CollisionData(WATER_FLOWER_INTERSECTION));

		shape.dispose();
		return body;
	}
	
	//Methods for creating water bodies depending on the state of the water.
	//Uses private createWaterBody method.
	public Body createIceBody(World world, Vector2 position){
		Body body = createWaterBody(world, position, WaterState.SOLID);
		return body;
	}
	public Body createVaporBody(World world, Vector2 position){
		Body body = createWaterBody(world, position, WaterState.GAS);
		return body;
	}
	public Body createWaterBody(World world, Vector2 position){
		Body body = createWaterBody(world, position, WaterState.LIQUID);
		return body;
	}

	//Creates a body for a flower with a position in a world.
	public Body createFlowerBody(World world, Vector2 position){
		//Create body
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.StaticBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		//Create fixture
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2) / PPM, (TILE_SIZE / 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_FLOWER;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR;
		body.createFixture(fdef).setUserData(new CollisionData(FLOWER));

		shape.dispose();
		return body;
	}
}

