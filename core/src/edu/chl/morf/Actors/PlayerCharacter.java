package edu.chl.morf.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.Constants;
import edu.chl.morf.UserData.UserData;
import edu.chl.morf.UserData.UserDataType;
import edu.chl.morf.WorldUtils;

import java.util.HashMap;
import java.util.Map;

import static edu.chl.morf.Constants.GAME_HEIGHT;
import static edu.chl.morf.Constants.MAX_SPEED;

/**
 * Created by Lage on 2015-04-13.
 */
public class PlayerCharacter extends Image {

    private boolean emptyRight=true;
    private boolean emptyLeft=true;
    private boolean facingRight=true;
    private boolean moving=false;
    private boolean alive = true;
    private Body body;
    private Vector2 movementVector = new Vector2(0,0);
    private Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
    private float blockWidth = 15/100f;
    private float blockHeight = 15/100f;
    private Animation runningRightAnimation;
    private Animation runningLeftAnimation;
    private TextureRegion idleTexture;
    private float stateTime;
    private OrthographicCamera camera;

    public PlayerCharacter(Body body){
        //Load sprite sheet from assets
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[Constants.PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES.length];
        for (int i = 0; i < Constants.PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES.length; i++) {
            String path = Constants.PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningLeftAnimation = new Animation(0.1f, runningFrames);

        runningFrames = new TextureRegion[Constants.PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES.length];
        for (int i = 0; i < Constants.PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES.length; i++) {
            String path = Constants.PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES [i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningRightAnimation = new Animation(0.1f, runningFrames);

        idleTexture = textureAtlas.findRegion(Constants.PLAYERCHARACTER_IDLE_REGION_NAME);
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
                        addBlock(getBody().getPosition(),UserDataType.GROUND);
                        break;
                    case Input.Keys.C:
                        addBlock(getBody().getPosition(),UserDataType.SPIKE);
                        break;
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

    public void addBlock(Vector2 position,UserDataType userDataType) {
        if((facingRight&&emptyRight)||(!facingRight&&emptyLeft)) {
            UserData userData=new UserData(userDataType);
            WorldUtils.addBlock(this, position, blockWidth, blockHeight, facingRight,userData);
        }
    }

    public void moveLeft(){
        facingRight=false;
        moving=true;
        if(body.getLinearVelocity().x >= 0){    //If moving right
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(-3, 0);
    }
    public void moveRight(){
        facingRight=true;
        moving=true;
        if(body.getLinearVelocity().x <= 0){    //If moving left
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        movementVector = new Vector2(3, 0);
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
        if(Math.abs(body.getLinearVelocity().y) < 0.01f) {   //If standing (could be improved, also 0 at top of jump)
            body.applyForceToCenter(new Vector2(0, 50), true);
        }
    }
    public void fly(){
        if(Math.abs(body.getLinearVelocity().y) < 0.01f && Math.abs(body.getLinearVelocity().x) < 0.01f) {
            movementVector = new Vector2(0, 4);
        }
    }
    public void setEmptyRight(boolean b){
        emptyRight=b;
    }

    public void setEmptyLeft(boolean b){
        emptyLeft=b;
    }

    public void doAction(){
        body.setTransform(10, 5, 0);
        body.setLinearVelocity(0,0);
        System.out.println("reset");
    }

    public void die(){
        remove();
        alive = false;
        System.out.println("You have died. Game over!");
    }

    public boolean isAlive(){
        return alive;
    }

    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }

    public void setCamera(OrthographicCamera camera){
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setProjectionMatrix(camera.combined);                         //Tells the spritebatch to render according to camera
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        //Draw correct animation at player character position
        if(moving) {
            if(facingRight) {
                batch.draw(runningRightAnimation.getKeyFrame(stateTime, true),
                        body.getPosition().x - 15/100f, body.getPosition().y - 15/100f, 30/100f, 30/100f);
            }else{
                batch.draw(runningLeftAnimation.getKeyFrame(stateTime, true),
                        body.getPosition().x - 15/100f, body.getPosition().y - 15/100f, 30/100f, 30/100f);
            }
        }else{
            batch.draw(idleTexture, body.getPosition().x - 15/100f, body.getPosition().y - 15/100f, 30/100f, 30/100f);
        }
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
