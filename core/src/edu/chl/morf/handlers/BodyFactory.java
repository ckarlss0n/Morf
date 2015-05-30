package edu.chl.morf.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import edu.chl.morf.userdata.UserData;

import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;
import static edu.chl.morf.userdata.UserDataType.*;

/**
 * A factory class for creating bodies for the Box2D-world used in GameLogic.
 */
public class BodyFactory {

	private int pS = 15;
	public Body createPlayerBody(World world, Vector2 position){

		//Create body
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;
		Body body = world.createBody(bdef);

		//Create player fixture
		PolygonShape shape = new PolygonShape();
		//shape.setAsBox((TILE_SIZE / 2 - 2) / PPM, (TILE_SIZE / 2 - 2) / PPM);
		shape.setAsBox((30-pS) / PPM, 30 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_GROUND | BIT_ICE | BIT_FLOWER;
		body.createFixture(fdef).setUserData(new UserData(PLAYERCHARACTER));

		//Create right ghost fixture 
		shape.setAsBox(31 / PPM, 20 / PPM, new Vector2((62-pS) / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER | BIT_ICE | BIT_GAS;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_RIGHT));

		//Create left ghost fixture
		shape.setAsBox(31 / PPM, 20 / PPM, new Vector2((-62+pS) / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(GHOST_LEFT));

		//Create active block right fixture
		shape.setAsBox(0.1f / PPM, 20 / PPM, new Vector2((90-pS) / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.maskBits = BIT_WATER | BIT_ICE | BIT_GAS;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_RIGHT));

		//Create active block left fixture
		shape.setAsBox(0.1f / PPM, 20 / PPM, new Vector2((-90+pS) / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_LEFT));

		//Create active block bottom right fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2((60-pS) / PPM, -64 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_BOTTOM_RIGHT));

		//Create active block bottom left fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2((-60+pS) / PPM, -64 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_BOTTOM_LEFT));

		//Create bottom ghost fixture
		shape.setAsBox((29-pS) / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
		fdef.shape=shape;
		fdef.filter.maskBits = BIT_GROUND | BIT_SPIKES | BIT_ICE;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_BOTTOM));

		//Create bottom ice ghost fixture
		shape.setAsBox((29-pS) / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
		fdef.shape=shape;
		fdef.filter.maskBits = BIT_ICE;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_BOTTOM_ICE));

		//Create core ghost fixture
		shape.setAsBox(15 / PPM, 15 / PPM, new Vector2(0, 0), 0);
		fdef.shape=shape;
		fdef.filter.maskBits = BIT_GAS | BIT_FLOWER;
		body.createFixture(fdef).setUserData(new UserData(GHOST_CORE));

		shape.dispose();
		return body;
	}

	public Body createIceBody(World world, Vector2 position){
/*
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2) / PPM, (TILE_SIZE / 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_ICE;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS | BIT_PLAYER;
		body.createFixture(fdef).setUserData(new UserData(ICE));

		shape.dispose();
		*/
		Body body = createWaterBody(world, position);
		Filter filter = new Filter();
		filter.categoryBits = BIT_ICE;
		filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS | BIT_PLAYER;
		body.getFixtureList().get(0).setFilterData(filter);

		return body;
	}
	
	public Body createVaporBody(World world, Vector2 position){

		/*BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2) / PPM, (TILE_SIZE / 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_WATER;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		body.createFixture(fdef).setUserData(new UserData(VAPOR));

		shape.dispose();
		*/
		Body body = createWaterBody(world,position);
		Filter filter = new Filter();
		filter.categoryBits = BIT_GAS;
		filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		return body;
	}
	
	public Body createWaterBody(World world, Vector2 position){

		BodyDef bdef = new BodyDef();
		bdef.position.set((position.x) / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(((TILE_SIZE-2) / 2) / PPM, ((TILE_SIZE-2 )/ 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_WATER;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER | BIT_ICE | BIT_GAS;
		body.createFixture(fdef).setUserData(new UserData(WATER));


		//ghostFixture
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_WATER;
		shape.setAsBox((TILE_SIZE * 0.9f / 2) / PPM,(TILE_SIZE / 20) / PPM,new Vector2(0, (TILE_SIZE / 2) / PPM),0);
		fdef.shape = shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData(new UserData(WATER_SENSOR));

		//Flower intersection fixture
		shape.setAsBox((TILE_SIZE * 0.9f / 2)/PPM,(TILE_SIZE * 0.9f / 2)/PPM);
		fdef.filter.maskBits=BIT_FLOWER;
		fdef.shape=shape;
		body.createFixture(fdef).setUserData(new UserData(WATER_FLOWER_INTERSECTION));

		shape.dispose();
		return body;
	}

	public Body createFlowerBody(World world, Vector2 position){
		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.StaticBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2) / PPM, (TILE_SIZE / 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_FLOWER;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR;
		body.createFixture(fdef).setUserData(new UserData(FLOWER));

		shape.dispose();
		return body;
	}
}

