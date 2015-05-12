package edu.chl.morf.handlers;

import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.userdata.UserDataType.*;
import static edu.chl.morf.handlers.LevelGenerator.TILE_SIZE;

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
		shape.setAsBox(20 / PPM, 20 / PPM, new Vector2(30 / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_RIGHT));
		
		//Create left ghost fixture
		shape.setAsBox(20 / PPM, 20 / PPM, new Vector2(-30 / PPM, 0), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_SENSOR;
		fdef.filter.maskBits = BIT_GROUND | BIT_WATER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new UserData(GHOST_LEFT));
		
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

		
		return body;
	}
}

