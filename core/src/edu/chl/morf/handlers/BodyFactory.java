package edu.chl.morf.handlers;

import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.Constants.REAL_TILE_SIZE;
import static edu.chl.morf.Constants.TILE_SIZE;

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

		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(15 / PPM, 15 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_GROUND;
		body.createFixture(fdef).setUserData(UserDataType.PLAYERCHARACTER);
		
		return body;
	}

	public Body createWaterBody(World world, Vector2 position){

		BodyDef bdef = new BodyDef();
		bdef.position.set(position.x / PPM, position.y / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32 / PPM, 32 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_WATER;
		fdef.filter.maskBits = BIT_GROUND;
		body.createFixture(fdef).setUserData(UserDataType.WATER);

		
		return body;
	}
}

