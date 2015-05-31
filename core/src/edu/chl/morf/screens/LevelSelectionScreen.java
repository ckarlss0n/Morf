package edu.chl.morf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.screens2.GameScreen;
import edu.chl.morf.screens.levelselection.LevelSelectionStage;

/**
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen {
    private Stage stage;

    public LevelSelectionScreen(ScreenManager sm) {
        super(sm);
        stage = new LevelSelectionStage();
        setFocus();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void setFocus() {
        Gdx.input.setInputProcessor(stage);
    }
}
