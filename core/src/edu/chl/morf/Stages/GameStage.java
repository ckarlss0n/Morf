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
import edu.chl.morf.WorldUtils;

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
        world  = WorldUtils.createWorld();
        accumulator = 0f;

        playerCharacter = WorldUtils.createPlayerCharacter(world);
        ground = WorldUtils.createGround(world);

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
