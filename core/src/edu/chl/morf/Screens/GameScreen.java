package edu.chl.morf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.chl.morf.Stages.GameStage;
import edu.chl.morf.Stages.TestStage;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class GameScreen implements Screen{

    private Stage stage;

    public GameScreen(){
    	//stage = new GameStage();
        stage = new TestStage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);       //Clear screen
        stage.act(delta);
        stage.draw();                                       //Redraw screen
        //stage.fall();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
