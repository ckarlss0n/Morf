package edu.chl.morf.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Actors.PlayerCharacter;
import edu.chl.morf.WorldUtils;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage {

    float accumulator;
    private PlayerCharacter playerCharacter;
    private Image ground;
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    public GameStage() {
        world = WorldUtils.createWorld();
        accumulator = 0f;
        playerCharacter = WorldUtils.createPlayerCharacter(world);
        WorldUtils.createGround(world);
        Gdx.input.setInputProcessor(this);
        addActor(playerCharacter);
        setKeyboardFocus(playerCharacter);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(20, 13);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }
    public void updateCamera (){
        camera.position.set(playerCharacter.getBody().getPosition(),0f);
        camera.update();
    }
    @Override
    public void act(float delta) {
        super.act(delta);

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
}
