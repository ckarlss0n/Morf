package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import edu.chl.morf.controllers.GameController;
import edu.chl.morf.controllers.GameLogic;
import edu.chl.morf.controllers.MyContactListener;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.view.View;

import static edu.chl.morf.Constants.WORLD_GRAVITY;
import static edu.chl.morf.handlers.Constants.PPM;

public class PlayScreen extends GameScreen{

    private GameLogic gameLogic;
    private OrthographicCamera box2dCam;
    private Level level;
    private View view;
    private World world;

    private MyContactListener cl;
    private GameController input;

    @Override
    public void setFocus(){
        Gdx.input.setInputProcessor(input);
    }

    public PlayScreen(ScreenManager sm, String levelName){
        super(sm);

        world = new World(WORLD_GRAVITY, true);
        
        LevelFactory levelFactory = LevelFactory.getInstace();

        level = levelFactory.getLevel(levelName);
        gameLogic = new GameLogic(level, world);

        cl = new MyContactListener(gameLogic);
        input = new GameController(gameLogic);
        
        world.setContactListener(cl);

        //Set up box2d camera
        box2dCam = new OrthographicCamera();
        box2dCam.setToOrtho(false, Main.V_WIDTH / PPM, Main.V_HEIGHT / PPM);

        this.view = new View(level, cam, hudCam, box2dCam, spriteBatch, world);
    }
    private void newLevel(String levelName){}

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    public void resumeGame(){
        gameLogic.resumeGame();
    }

    @Override
    public void render(float delta) {
        if(!gameLogic.isGamePaused()) {
            gameLogic.render(delta);
            view.render(delta);
            if (gameLogic.isLevelWon()) {
                newLevel("nextLevel");
                gameLogic.fly();
            }
            if (gameLogic.isPlayerDead()) {
                newLevel("thisLevel");
                gameLogic.resetGame();
            }
        } else {
            screenManager.pushScreen(ScreenManager.ScreenType.PAUSE_SCREEN, null);
            resumeGame();
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    public void handleInput(){

    }

    public void update(float dt){

    }


}
