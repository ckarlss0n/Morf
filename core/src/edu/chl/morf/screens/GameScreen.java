package edu.chl.morf.screens;

import com.badlogic.gdx.Gdx;
import edu.chl.morf.stages.GameStage;
import static edu.chl.morf.Constants.*;

/**
 * This class represents the screen shown while playing the game.
 * <p>
 * <li><b>Responsible for: </b>
 * Showing the game.
 * <p>
 * <li><b>Used by: </b>
 * {@link edu.chl.morf.Main}
 * <p>
 * <li><b>Using: </b>
 * <li>LibGDX classes</li>
 * <li>{@link edu.chl.morf.Constants}
 * <li>{@link AbstractScreen}
 * <li>{@link GameStage}
 * <p>
 * @author Christoffer Karlsson
 */
public class GameScreen extends AbstractScreen {

    private GameStage stage;

    public GameScreen(){
        super();
        stage = new GameStage(LEVEL_2);
    }

    public void setStageInputHandler(){
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);       //Clear screen
        stage.act(delta);
        stage.draw();                                       //Redraw screen
        stage.updateCamera();
    }
}
