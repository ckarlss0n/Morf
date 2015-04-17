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
        PolygonShape shapeLeft = new PolygonShape();
        shapeLeft.setAsBox(14/100f,14/100f,new Vector2(-1 * 0.3f,0),0);
        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shapeLeft;
        fixDef.filter.categoryBits = 2;
        fixDef.filter.maskBits = 4;
        fixDef.isSensor = true;
        Fixture fixtureLeft = body.createFixture(fixDef);
        PolygonShape shapeRight = new PolygonShape();
        shapeRight.setAsBox(14/100f,14/100f,new Vector2(1 * 0.3f,0),0);
        fixDef.shape=shapeRight;
        Fixture fixtureRight = body.createFixture(fixDef);

        shapeLeft.dispose();
        shapeRight.dispose();

        fixtureLeft.setUserData(new UserData(UserDataType.GHOST_LEFT));
        fixtureRight.setUserData(new UserData(UserDataType.GHOST_RIGHT));
        return new PlayerCharacter(body);
    }
    /*
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(position.x+blockWidth*2*facingRight,position.y);
        bodyDef.fixedRotation=true;
        Body body= playerCharacter.getBody().getWorld().createBody(bodyDef);
        body.setUserData(new UserData(UserDataType.GHOSTBLOCK));

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.isSensor=false;
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(blockWidth,blockHeight);
        fixtureDef.shape=shape;
        Fixture fixture=body.createFixture(fixtureDef);
        fixture.setUserData(playerCharacter);
        Array<Contact> contactList=playerCharacter.getBody().getWorld().getContactList();
        Boolean createBlock=false;
        System.out.println("hej");
        for(Contact c : contactList) {
            System.out.println("for");
            System.out.println(""+c.getFixtureB().getBody().getPosition()+"   "+c.getFixtureA().getBody().getPosition());
            if(c.getFixtureA().getUserData()!=null||c.getFixtureB().getUserData()!=null){
                System.out.println("hej");
                if(c.isTouching()){
                    createBlock=false;
                }
            }
        }

        if(createBlock) {
            Body block = createBody(new Vector2(position.x + blockWidth * 2 * facingRight, position.y), 1, blockWidth, blockHeight, 0.1f, (short) 4, (short) 2, playerCharacter.getBody().getWorld());
            block.setType(BodyDef.BodyType.StaticBody);
        }
    */

    public static void createGround(World world){
        //Create Ground body
        Body body = createBody(new Vector2(0,0),0.5f,500/100f,2/100f,0.1f,(short)4,(short)2, world);
        body.setType(BodyDef.BodyType.StaticBody);
        body.setUserData(new UserData(UserDataType.GROUND));
        Body block = createBody(new Vector2(0,3),1,4,1,0.1f,(short)4,(short)2,world);
        block.setType(BodyDef.BodyType.StaticBody);
    }

    public static void addBlock(PlayerCharacter playerCharacter, Vector2 position, int blockWidth, int blockHeight,boolean facingRight){
        int direction=-1;
        if(facingRight){
            direction = 1;
        }

        Body block = createBody(new Vector2(position.x+blockWidth*2*direction+1/40f*direction,position.y),1,15/100f,15/100f,0.1f,(short)4,(short)2,playerCharacter.getBody().getWorld());
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
