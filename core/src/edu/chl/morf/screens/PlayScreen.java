package edu.chl.morf.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import edu.chl.morf.controllers.GameController;
import edu.chl.morf.controllers.GameLogic;
import edu.chl.morf.controllers.collision.CollisionListener;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.handlers.LevelReader;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.view.View;
import static edu.chl.morf.handlers.Constants.PPM;

/**
 * This class represents the screen that is shown when playing a level.
 */
public class PlayScreen extends GameScreen{

    public static final Vector2 WORLD_GRAVITY = new Vector2(0,-15);
    private GameLogic gameLogic;
    private OrthographicCamera box2dCam;
    private Level level;
    private View view;
    private World world;
    private CollisionListener cl;
    private GameController input;
    private LevelFactory levelFactory;
    private Timer.Task goToNextLevelTask;
    private boolean timerScheduled;

    @Override
    public void setFocus(){
        Gdx.input.setInputProcessor(input);
    }

    public PlayScreen(ScreenManager sm, String levelName){
        super(sm);

        world = new World(WORLD_GRAVITY, true);

        levelFactory = LevelFactory.getInstance();

        level = levelFactory.getLevel(levelName, true);
        gameLogic = new GameLogic(level, world);

        cl = new CollisionListener(gameLogic);
        input = new GameController(gameLogic);

        world.setContactListener(cl);

        //Set up box2d camera
        box2dCam = new OrthographicCamera();
        box2dCam.setToOrtho(false, Main.V_WIDTH / PPM, Main.V_HEIGHT / PPM);

        this.view = new View(level, cam, hudCam, box2dCam, spriteBatch, world);

        //Make nextLevel run inside a timer task, in order to change level after chosen delay
        goToNextLevelTask = new Timer.Task() {
            @Override
            public void run() {
                nextLevel();
                timerScheduled = false; //The timer is no longer scheduled
            }
        };
    }

    public void nextLevel(){
		List<String> levels = LevelReader.getInstance().getLevels();
    	int currentLevel = levels.indexOf(level.getName());
    	String name;
    	if(currentLevel == levels.size() - 1){ //If is last level
        	name = levels.get(0);   //Wrap around, return to first level
    	} else {
        	name = levels.get(currentLevel + 1);    //Go to next level
    	}

    	level = levelFactory.getLevel(name, true);

    	gameLogic.changeLevel(level);
    	view.changeLevel(level);
    }

    //Reset level upon dying etc.
    public void resetLevel(){
        if(view.isDeathAnimationDone()) {
            level = levelFactory.getLevel(level.getName(), true);
            gameLogic.changeLevel(level);
            view.changeLevel(level);
        }
    }

    //Resume the game upon closing the pause screen
    public void resumeGame(){
        gameLogic.resumeGame();
    }

    @Override
    public void render(float delta) {
        if(!gameLogic.isGamePaused()) {
            gameLogic.render(delta);
            view.render(delta);
            if (gameLogic.isLevelWon() && !goToNextLevelTask.isScheduled() && !timerScheduled) {
                Timer.schedule(goToNextLevelTask, 2);   //Go to next level after two seconds
                timerScheduled = true;                  //The timer is scheduled
            }
            if (gameLogic.isPlayerDead() && !goToNextLevelTask.isScheduled() && !timerScheduled) {
                resetLevel();
            }
        } else {
            screenManager.pushScreen(ScreenManager.ScreenType.PAUSE_SCREEN, null); //Show pause screen if game is paused
            resumeGame();
        }
    }
}
