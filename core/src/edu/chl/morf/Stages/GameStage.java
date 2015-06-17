package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.Actors.Background;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Constants;
import edu.chl.morf.UserData.UserData;
import edu.chl.morf.UserData.UserDataType;
import edu.chl.morf.WorldUtils;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage implements ContactListener{

    float accumulator;
    private PlayerCharacter playerCharacter;
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Background background;

    public GameStage() {
        world = WorldUtils.createWorld();
        background = new Background();
        addActor(background);
        accumulator = 0f;
        playerCharacter = WorldUtils.createPlayerCharacter(world);
        WorldUtils.createGround(world);
        Gdx.input.setInputProcessor(this);
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);
        renderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH / 100f, Constants.GAME_HEIGHT / 100f);


        playerCharacter.setCamera(camera);
    }

    public void updateCamera() {
        camera.position.set(playerCharacter.getBody().getPosition(), 0f);
        camera.update();
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        background.setSpeed(playerCharacter.getBody().getLinearVelocity().x*-10+20);
        // Fixed timestep
        float TIME_STEP = 1 / 300f;
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

    @Override
    public void beginContact(Contact contact) {
        Body a=contact.getFixtureA().getBody();
        Body b=contact.getFixtureB().getBody();

        if((a.getUserData())!=null&&((UserData)a.getUserData()).getUserDataType()== UserDataType.GHOST_LEFT ||
           (b.getUserData())!=null&&((UserData)b.getUserData()).getUserDataType()== UserDataType.GHOST_LEFT){
            System.out.println("123hej");
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
