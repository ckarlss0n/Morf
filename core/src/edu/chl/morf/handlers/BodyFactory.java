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

	public void createPlayerBody(World world){
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(1, 1);
		bdef.type = BodyType.StaticBody;
		bdef.fixedRotation = true;

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(15 / PPM, 15 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_GROUND;
		body.createFixture(fdef).setUserData(UserDataType.PLAYERCHARACTER);;
	}
}

