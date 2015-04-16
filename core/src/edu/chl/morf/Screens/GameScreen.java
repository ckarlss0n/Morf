package edu.chl.morf.Screens;

import com.badlogic.gdx.Gdx;

import edu.chl.morf.Stages.GameStage;
import edu.chl.morf.Stages.TestStage;
import edu.chl.morf.Stages.TestStage2;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class GameScreen extends ObservableScreen{

    private TestStage2 stage;

    public GameScreen(){
        super();
        stage = new TestStage2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);       //Clear screen
        stage.act(delta);
        stage.draw();                                       //Redraw screen
        stage.updateCamera();
    }
}
