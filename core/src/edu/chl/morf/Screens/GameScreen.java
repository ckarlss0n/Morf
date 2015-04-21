package edu.chl.morf.Screens;

import com.badlogic.gdx.Gdx;
import edu.chl.morf.Stages.GameStage;
import static edu.chl.morf.Constants.*;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class GameScreen extends ObservableScreen{

    private GameStage stage;

    public GameScreen(){
        super();
        stage = new GameStage(LEVEL_1);
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
