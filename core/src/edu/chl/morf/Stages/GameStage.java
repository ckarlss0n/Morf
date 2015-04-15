package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Actors.Blocks.Ground;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage {

    private PlayerCharacter playerCharacter;
    private Image ground;
    private Vector2 left = new Vector2(-10,0);
    private Vector2 right = new Vector2(10,0);
    private Vector2 up = new Vector2(0,6);
    private Vector2 down = new Vector2(0,-0.2f);
    private Vector2 currentVector = new Vector2(0,0);
    private World world;

    float accumulator;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    public GameStage() {
        world  = new World(Constants.WORLD_GRAVITY, true);
        accumulator = 0f;



        //Create PlayerCharacter body
        Body body = createBody(new Vector2(10,4),0.5f,1,1,2f,(short)2,(short)4);
        body.setType(BodyDef.BodyType.DynamicBody);
        playerCharacter = new PlayerCharacter(body);

        //Create Ground body
        body = createBody(new Vector2(0,0),0.5f,500,2,0.1f,(short)4,(short)2);
        body.setType(BodyDef.BodyType.StaticBody);
        ground = new Ground(body);

        Gdx.input.setInputProcessor(this);
        addActor(ground);
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);

        renderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(20, 13);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }
    public void updateCamera (){
        camera.position.set(playerCharacter.getBody().getPosition().x,playerCharacter.getBody().getPosition().y,0f);
        camera.update();
    }

    public void setYVelocity(float f){
        this.currentVector = new Vector2(this.currentVector.x, f);
    }

    public Body createBody(Vector2 position, float density, int width, int height,
                           float friction, short categoryBits, short maskBits){
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

    @Override
    public void act(float delta){
        super.act(delta);

        // Fixed timestep
        float TIME_STEP = 1/300f;
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

    }

    @Override
    public void draw(){
        super.draw();
        renderer.render(world, camera.combined);
    }
}
