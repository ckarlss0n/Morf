package edu.chl.morf;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.actors.PlayerCharacter;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-04-15.
 */
public class WorldUtils {

    public static World createWorld() {
        return new World(WORLD_GRAVITY, true);
    }

    //Create PlayerCharacter body
    public static PlayerCharacter createPlayerCharacter(World world){
        UserData userData=new UserData(UserDataType.PLAYERCHARACTER);
        Body body = createBody(new Vector2(1,2),0.5f,2f,(short)2,(short)4, world,userData);
        body.setType(BodyDef.BodyType.DynamicBody);
        PolygonShape shapeLeft = new PolygonShape();

        shapeLeft.setAsBox((REAL_TILE_SIZE-6)/(2*PPM),(REAL_TILE_SIZE-6)/(2*PPM),new Vector2(-1 * (REAL_TILE_SIZE-4)/(PPM),0),0); //Extra margin for ghosting boxes (64-4)
        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shapeLeft;
        fixDef.filter.categoryBits = 2;
        fixDef.filter.maskBits = 4;
        fixDef.isSensor = true;
        Fixture fixtureLeft = body.createFixture(fixDef);

        PolygonShape shapeRight = new PolygonShape();
        shapeRight.setAsBox((REAL_TILE_SIZE-6)/(2*PPM),(REAL_TILE_SIZE-6)/(2*PPM),new Vector2(1 * (REAL_TILE_SIZE-4)/(PPM),0),0);
        fixDef.shape=shapeRight;
        Fixture fixtureRight = body.createFixture(fixDef);

        shapeLeft.dispose();
        shapeRight.dispose();

        fixtureLeft.setUserData(new UserData(UserDataType.GHOST_LEFT));
        fixtureRight.setUserData(new UserData(UserDataType.GHOST_RIGHT));
        return new PlayerCharacter(body);
    }

    public static void addBlock(PlayerCharacter playerCharacter, Vector2 position,boolean facingRight,UserData userData){
        int direction = facingRight ? 1 : -1; //1 if facing right, else -1

        if(userData.getUserDataType()==UserDataType.SPIKE){
            position.x=position.x+7/PPM*direction;
        }
        Body block = createBody(new Vector2(position.x+(REAL_TILE_SIZE/PPM)*direction+1/PPM*direction,position.y),1,0.1f,(short)4,(short)6,playerCharacter.getBody().getWorld(),userData);
        if(userData.getUserDataType()==UserDataType.SPIKE){
            block.setType(BodyDef.BodyType.KinematicBody);
        } else {
            block.setType(BodyDef.BodyType.DynamicBody);
        }
        block.setUserData(userData);
    }

    public static Body createBody(Vector2 position, float density, float friction, short categoryBits, short maskBits, World world,UserData userData){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(TILE_SIZE/2, TILE_SIZE/2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);
        FixtureDef fixDef = new FixtureDef();
        fixDef.density = density;
        fixDef.shape = shape;
        fixDef.friction = friction;
        fixDef.filter.categoryBits = categoryBits;
        fixDef.filter.maskBits = maskBits;
        body.createFixture(fixDef).setUserData(userData);
        body.resetMassData();
        shape.dispose();
        return body;
    }
}
