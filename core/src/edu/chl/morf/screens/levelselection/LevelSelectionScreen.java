package edu.chl.morf.screens.levelselection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.handlers.HighScores;
import edu.chl.morf.handlers.LevelList;
import edu.chl.morf.main.Main;
import edu.chl.morf.screens.GameScreen;
import edu.chl.morf.screens.ScreenManager;

import java.io.File;
import java.util.List;

/**
 * This class represents the screen that shows all available levels.
 * When a level is clicked, that level is started.
 *
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen {

    //Stage holding all components shown in the screen
    private class LevelSelectionStage extends Stage {

        private LevelSelectionStage(){
            super();

            //Measurements between previews
            float levelPreviewHeight = 0.25f * Main.V_HEIGHT;
            float levelPreviewWidth = 0.3f * Main.V_WIDTH;
            float previewGapX = (Main.V_WIDTH - 3 * levelPreviewWidth)/4;
            float previewGapY = (Main.V_HEIGHT - 2 * levelPreviewHeight)/3;

            LevelList levelList = LevelList.getInstance();
            List<String> levelNames = levelList.getLevels();
            HighScores highScores = HighScores.getInstance();
            int nbrOfLevels = levelNames.size();
            int i = 0;

            //Adds previews in a 2x3 grid
            for(int y = 0; y < 2; y ++) {
                for(int x = 0; x < 3; x++){
                    if(i < nbrOfLevels) {
                        String levelName = levelNames.get(i).split("\\.")[0];   //Remove file extension from .tmx file
                        LevelPreview levelPreview = new LevelPreview(levelName, previewGapX + x * (previewGapX + levelPreviewWidth),
                                Main.V_HEIGHT - (previewGapY + levelPreviewHeight) - 50 - y * (levelPreviewHeight + previewGapY));
                        this.addActor(levelPreview);
                        if(i > 0) {
                            //Adds a texture on top of preview if previous level is not completed
                            if (highScores.getHighScore(levelNames.get(i - 1)) == 0) {
                                Texture notAvailableTexture = new Texture("levelselection" + File.separator + levelName + "_Thumb_Disabled.png");
                                Image notAvailableImage = new Image(notAvailableTexture);
                                notAvailableImage.setPosition(levelPreview.getX(),levelPreview.getY());
                                notAvailableImage.setSize(levelPreviewWidth,levelPreviewHeight);
                                this.addActor(notAvailableImage);
                            }
                        }
                        i++;
                    }
                }
            }

            //Add back button in upper left corner
            BackButton backButton = new BackButton();
            backButton.setPosition(30,600);
            backButton.setSize(300,100);
            this.addActor(backButton);
        }
    }

    private Stage stage;

    public LevelSelectionScreen(ScreenManager sm) {
        super(sm);
        stage = new LevelSelectionStage();
        setFocus();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   //Clears the screen.
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void setFocus() {
        Gdx.input.setInputProcessor(stage);
    }
}
