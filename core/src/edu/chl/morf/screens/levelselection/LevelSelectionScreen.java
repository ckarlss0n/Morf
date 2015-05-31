package edu.chl.morf.screens.levelselection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.handlers.HighScores;
import edu.chl.morf.handlers.LevelReader;
import edu.chl.morf.main.Main;
import edu.chl.morf.screens.GameScreen;
import edu.chl.morf.screens.ScreenManager;

import java.io.File;
import java.util.List;

/**
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen {

    private class LevelSelectionStage extends Stage {

        private LevelSelectionStage(){
            super();
            float levelPreviewHeight = 0.25f * Main.V_HEIGHT;
            float levelPreviewWidth = 0.3f * Main.V_WIDTH;
            float previewGapX = (Main.V_WIDTH - 3 * levelPreviewWidth)/4;
            float previewGapY = (Main.V_HEIGHT - 2 * levelPreviewHeight)/3;
            LevelReader levelReader = LevelReader.getInstance();
            List<String> levelNames = levelReader.getLevels();
            HighScores highScores = HighScores.getInstance();
            int nbrOfLevels = levelNames.size();
            int i = 0;
            for(int y = 0; y < 2; y ++) {
                for(int x = 0; x < 3; x++){
                    if(i < nbrOfLevels) {
                        String levelName = levelNames.get(i).split("\\.")[0];
                        if(levelName.equals("Level_8") || levelName.equals("Level_9") || levelName.equals("Level_7")){
                            levelName = "Level_1";
                        }
                        LevelPreview levelPreview = new LevelPreview(levelName, previewGapX + x * (previewGapX + levelPreviewWidth),
                                Main.V_HEIGHT - (previewGapY + levelPreviewHeight) - 50 - y * (levelPreviewHeight + previewGapY));
                        this.addActor(levelPreview);
                        if(i > 0) {
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void setFocus() {
        Gdx.input.setInputProcessor(stage);
    }
}
