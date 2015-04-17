package edu.chl.morf;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Screens.PauseScreen;
import edu.chl.morf.UserData.UserData;
import edu.chl.morf.UserData.UserDataType;

import static edu.chl.morf.Constants.WORLD_GRAVITY;

/**
 * Created by Lage on 2015-04-15.
 */
public class WorldUtils {

    public static World createWorld() {
        return new World(WORLD_GRAVITY, true);
    }

    public static PlayerCharacter createPlayerCharacter(World world){
        //Create PlayerCharacter body
        Body body = createBody(new Vector2(1,1),0.5f,15/100f,15/100f,2f,(short)2,(short)4, world);
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setUserData(new UserData(UserDataType.PLAYERCHARACTER));
        for(int i = -1; i < 2; i += 2) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(15/100f,15/100f,new Vector2(i * 0.3f,0),0);
            FixtureDef fixDef = new FixtureDef();
            fixDef.shape = shape;
            fixDef.filter.categoryBits = 2;
            fixDef.filter.maskBits = 4;
            fixDef.isSensor = true;
            /*Fixture fixture = */body.createFixture(fixDef);
            //fixture.setUserData(new UserData());
            shape.dispose();
        }

        return new PlayerCharacter(body);
    }

    public static void createGround(World world){
        //Create Ground body
        Body body = createBody(new Vector2(0,0),0.5f,500/100f,2/100f,0.1f,(short)4,(short)2, world);
        body.setType(BodyDef.BodyType.StaticBody);
        body.setUserData(new UserData(UserDataType.GROUND));
        Body block = createBody(new Vector2(0,3),1,4,1,0.1f,(short)4,(short)2,world);
        block.setType(BodyDef.BodyType.StaticBody);
    }

    public static void addBlock(PlayerCharacter playerCharacter, Vector2 position, int blockWidth, int blockHeight){
        int facingRight = -1;
        if(playerCharacter.isFacingRight()){
            facingRight = 1;
        }

        Body block = createBody(new Vector2(position.x+blockWidth*2*facingRight+1/40f*facingRight,position.y),1,32/100f,32/100f,0.1f,(short)4,(short)2,playerCharacter.getBody().getWorld());
        block.setType(BodyDef.BodyType.StaticBody);

    }

    public static Body createBody(Vector2 position, float density, float width, float height,
                           float friction, short categoryBits, short maskBits, World world){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
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
        body.createFixture(fixDef);
        body.resetMassData();
        shape.dispose();
        return body;
    }
}
