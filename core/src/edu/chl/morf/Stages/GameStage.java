package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.Actors.BackgroundLayer;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.Constants;
import edu.chl.morf.UserData.UserData;
import edu.chl.morf.UserData.UserDataType;
import edu.chl.morf.WorldUtils;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage implements ContactListener{

    float accumulator;
    private PlayerCharacter playerCharacter;
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private BackgroundLayer background;
    private BackgroundLayer bottomClouds;
    private BackgroundLayer topClouds;

    public GameStage() {
        world = WorldUtils.createWorld();
        background = new BackgroundLayer(BACKGROUND_IMAGE_PATH);
        bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH);
        topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH);
        addActor(background);
        addActor(bottomClouds);
        addActor(topClouds);
        accumulator = 0f;
        playerCharacter = WorldUtils.createPlayerCharacter(world);
        WorldUtils.createGround(world);
        Gdx.input.setInputProcessor(this);
        world.setContactListener(this);
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
        //background.setSpeed(playerCharacter.getBody().getLinearVelocity().x * -10 + 20);
        Vector2 playerVelocity = playerCharacter.getVelocity();
        background.setSpeed(playerVelocity.x * -10);        //Only move if player is moving
        bottomClouds.setSpeed(playerVelocity.x * -10 + 5);  //Slow scroll
        topClouds.setSpeed(playerVelocity.x * -10 + 20);    //Faster scroll

        // Fixed timestep
        float TIME_STEP = 1 / 300f;
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
        String alive = "Yes! You are alive.";
        if(!playerCharacter.isAlive()){
            alive = "No. You are dead.";
        }
        int width = Gdx.graphics.getWidth();
        int height = Gdx.app.getGraphics().getHeight();
        int fps = Gdx.graphics.getFramesPerSecond();
        background.setMessage(width + "x" + height, "Resolution");
        background.setMessage(Integer.toString(fps), "FPS");
        background.setMessage(alive, "Alive");
        background.setMessage(playerCharacter.getBody().getPosition().toString(), "Position");
        background.setMessage(playerCharacter.getVelocity().toString(), "Velocity");
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
        boolean fallingBeforeTouch = false;
        Fixture fa=contact.getFixtureA();
        Fixture fb=contact.getFixtureB();
        System.out.println("contact");
        if(contact.isTouching()==true) {
            System.out.println("touch");
            if ((fa.getUserData()) != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT ||
                    (fb.getUserData()) != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_LEFT) {
                playerCharacter.setEmptyLeft(false);
                System.out.println("full Left");
            } else if ((fa.getUserData()) != null && ((UserData) fa.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT ||
                    (fb.getUserData()) != null && ((UserData) fb.getUserData()).getUserDataType() == UserDataType.GHOST_RIGHT) {
                playerCharacter.setEmptyRight(false);
                System.out.println("full Right");
            }
        }
        if(playerCharacter.getVelocity().y < -10){
            fallingBeforeTouch = true;
        }
        if(fallingBeforeTouch && contact.isTouching()==true) {
            playerCharacter.die();
            playerCharacter.remove();
        }
        else {
            System.out.println("no touch");
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa=contact.getFixtureA();
        Fixture fb=contact.getFixtureB();
        System.out.println("end contact");

        if((fa.getUserData())!=null&&((UserData)fa.getUserData()).getUserDataType()== UserDataType.GHOST_LEFT ||
                (fb.getUserData())!=null&&((UserData)fb.getUserData()).getUserDataType()== UserDataType.GHOST_LEFT){
            playerCharacter.setEmptyLeft(true);
            System.out.println("empty Left");
        }
        if((fa.getUserData())!=null&&((UserData)fa.getUserData()).getUserDataType()== UserDataType.GHOST_RIGHT ||
                (fb.getUserData())!=null&&((UserData)fb.getUserData()).getUserDataType()== UserDataType.GHOST_RIGHT){
            playerCharacter.setEmptyRight(true);
            System.out.println("empty Right");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
