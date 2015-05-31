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

        goToNextLevelTask = new Timer.Task() {
            @Override
            public void run() {
                nextLevel();
                timerScheduled = false;
            }
        };
    }

    public void nextLevel(){
		List<String> levels = LevelReader.getInstance().getLevels();
    	int currentLevel = levels.indexOf(level.getName());
    	String name;
    	if(currentLevel == levels.size() - 1){
        	name = levels.get(0);
    	} else {
        	name = levels.get(currentLevel + 1);
    	}

    	level = levelFactory.getLevel(name, true);

    	gameLogic.changeLevel(level);
    	view.changeLevel(level);
    }
    public void resetLevel(){
        if(view.isDeathAnimationDone()) {
            level = levelFactory.getLevel(level.getName(), true);
            gameLogic.changeLevel(level);
            view.changeLevel(level);
        }
    }

    public void resumeGame(){
        gameLogic.resumeGame();
    }

    @Override
    public void render(float delta) {
        if(!gameLogic.isGamePaused()) {
            gameLogic.render(delta);
            view.render(delta);
            if (gameLogic.isLevelWon() && !goToNextLevelTask.isScheduled() && !timerScheduled) {
                Timer.schedule(goToNextLevelTask, 2);
                timerScheduled = true;
            }
            if (gameLogic.isPlayerDead() && !goToNextLevelTask.isScheduled() && !timerScheduled) {
                resetLevel();
            }
        } else {
            screenManager.pushScreen(ScreenManager.ScreenType.PAUSE_SCREEN, null);
            resumeGame();
        }
    }
}
