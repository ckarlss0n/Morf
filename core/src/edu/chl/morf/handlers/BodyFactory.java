package edu.chl.morf.handlers;

import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.userdata.UserDataType.*;
import static edu.chl.morf.handlers.LevelFactory.TILE_SIZE;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.actors.PlayerCharacter;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

public class BodyFactory {

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
		shape.setAsBox(30 / PPM, 30 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_GROUND;
		body.createFixture(fdef).setUserData(new UserData(PLAYERCHARACTER));
		
		//Create right ghost fixture 
		shape.setAsBox(32 / PPM, 20 / PPM, new Vector2(60 / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_RIGHT));
		
		//Create left ghost fixture
		shape.setAsBox(32 / PPM, 20 / PPM, new Vector2(-60 / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_LEFT));

		//Create active block right fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2(93 / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_RIGHT));

		//Create active block left fixture
		shape.setAsBox(1 / PPM, 20 / PPM, new Vector2(-93 / PPM, 0), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(new UserData(ACTIVE_BLOCK_LEFT));


		//Create bottom ghost fixture
		shape.setAsBox(28 / PPM, 20 / PPM, new Vector2(0, -15 / PPM), 0);
		fdef.shape=shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_BOTTOM));

		shape.dispose();
		return body;
	}

	public Body createWaterBody(World world, Vector2 position){

		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((TILE_SIZE / 2) / PPM, (TILE_SIZE / 2) / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_WATER;
		fdef.filter.maskBits = BIT_GROUND | BIT_SENSOR | BIT_WATER;
		body.createFixture(fdef).setUserData(new UserData(WATER));


		//ghostFixture
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_WATER;
		shape.setAsBox((TILE_SIZE * 0.9f / 2) / PPM,(TILE_SIZE / 20) / PPM,new Vector2(0, (TILE_SIZE / 2) / PPM),0);
		fdef.shape = shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData(new UserData(WATER_SENSOR));

		shape.dispose();
		return body;
	}
}

