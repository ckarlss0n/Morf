package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Constants;
import edu.chl.morf.WorldUtils;

import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.MAX_SPEED;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image {

    private boolean facingRight=true;
    private boolean moving=false;
    private Body body;
    private Vector2 movementVector = new Vector2(0,0);
    private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
    private int blockWidth = 1;
    private int blockHeight = 1;
    private Animation runningAnimation;
    private float stateTime;
    private OrthographicCamera camera;

    public PlayerCharacter(Body body){

        //Load sprite sheet from assets
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for (int i = 0; i < Constants.RUNNER_RUNNING_REGION_NAMES.length; i++) {
            String path = Constants.RUNNER_RUNNING_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningAnimation = new Animation(0.1f, runningFrames);
        stateTime = 0f;

        this.body = body;
        pressedKeys.put(Input.Keys.LEFT, false);
        pressedKeys.put(Input.Keys.RIGHT, false);
        pressedKeys.put(Input.Keys.UP, false);
        addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        pressedKeys.put(Input.Keys.LEFT, true);
                        moveLeft();
                        break;
                    case Input.Keys.RIGHT:
                        pressedKeys.put(Input.Keys.RIGHT, true);
                        moveRight();
                        break;
                    case Input.Keys.UP:
                        pressedKeys.put(Input.Keys.UP, true);
                        jump();
                        break;
                    case Input.Keys.SPACE:
                        doAction();
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        fly();
                        break;
                    case Input.Keys.X:
                        addBlock(getBody().getPosition());
                }
                return true;
            }
        });

        addListener(new InputListener() {
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    pressedKeys.put(Input.Keys.LEFT, false);
                    stop();
                } else if (keycode == Input.Keys.RIGHT) {
                    pressedKeys.put(Input.Keys.RIGHT, false);
                    stop();
                } else if (keycode == Input.Keys.UP) {
                    pressedKeys.put(Input.Keys.UP, false);
                    stop();
                } else if (keycode == Input.Keys.SHIFT_LEFT) {
                    stop();
            }
                return true;
            }
        });
    }

    public void addBlock(Vector2 position) {
        WorldUtils.addBlock(this, position, blockWidth, blockHeight);
    }

    public boolean isFacingRight(){
        return facingRight;
    }

    public void moveLeft(){
        facingRight=false;
        moving=true;
        if(body.getLinearVelocity().x >= 0){    //If moving right
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(-200, 0);
    }
    public void moveRight(){
        facingRight=true;
        moving=true;
        if(body.getLinearVelocity().x <= 0){    //If moving left
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(200, 0);
    }
    public void stop(){
        moving=false;
        if(!(pressedKeys.get(Input.Keys.LEFT) || pressedKeys.get(Input.Keys.RIGHT))) {
            movementVector = new Vector2(0, 0);
        } else if(pressedKeys.get(Input.Keys.LEFT) && !pressedKeys.get(Input.Keys.RIGHT)) {
            moveLeft();
        } else {
            moveRight();
        }
    }
    public void jump(){
        if(body.getLinearVelocity().y == 0) {   //If standing (could be improved, also 0 at top of jump)
            body.applyForceToCenter(new Vector2(0, 5000), true);
        }
    }
    public void fly(){
        if(body.getLinearVelocity().y == 0) {   //If standing (could be improved, also 0 at top of jump)
            movementVector = new Vector2(0, 200);
        }
    }

    public void doAction(){
        body.setTransform(10, 5, 0);
        body.setLinearVelocity(0,0);
        System.out.println("reset");
    }

    public void setCamera(OrthographicCamera camera){
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setProjectionMatrix(camera.combined);                         //Tells the spritebatch to render according to camera
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(runningAnimation.getKeyFrame(stateTime, true),
                body.getPosition().x - 1,body.getPosition().y - 1, 2,2);    //Draw correct frame at player character position
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(Math.abs(body.getLinearVelocity().x) < MAX_SPEED) {
            body.applyForceToCenter(movementVector, true);
        }
    }

    public Body getBody(){
        return this.body;
    }
}
