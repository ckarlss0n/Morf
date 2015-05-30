package edu.chl.morf.screens2.levelselection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.LevelReader;
import edu.chl.morf.main.Main;
import java.io.File;
import java.util.List;

/**
 * Created by Lage on 2015-05-21.
 */
public class LevelSelectionStage extends Stage {

    public LevelSelectionStage(){
        super();
        float levelPreviewHeight = 0.25f * Main.V_HEIGHT;
        float levelPreviewWidth = 0.3f * Main.V_WIDTH;
        float previewGapX = (Main.V_WIDTH - 3 * levelPreviewWidth)/4;
        float previewGapY = (Main.V_HEIGHT - 2 * levelPreviewHeight)/3;
        LevelReader levelReader = LevelReader.getInstance();
        List<String> levelNames = levelReader.getLevels();
        HighScoreHandler highScoreHandler = HighScoreHandler.getInstance();
        int nbrOfLevels = levelNames.size();
        int i = 0;
        for(int y = 0; y < 2; y ++) {
            for(int x = 0; x < 3; x++){
                if(i < nbrOfLevels) {
                    String levelName = levelNames.get(i).split("\\.")[0];
                    LevelPreview levelPreview = new LevelPreview(levelName, previewGapX + x * (previewGapX + levelPreviewWidth),
                            Main.V_HEIGHT - (previewGapY + levelPreviewHeight) - 50 - y * (levelPreviewHeight + previewGapY));
                    this.addActor(levelPreview);
                    if(i > 0) {
                        if (highScoreHandler.getHighScore(levelNames.get(i-1)) == 0) {

                            //Change when new textures are added
                            //Texture notAvailableTexture = new Texture("levelselection" + File.separator + levelName + "_Thumb_Disabled.png");
                            Texture notAvailableTexture = new Texture("levelselection" + File.separator + "Level_1" + "_Thumb_Disabled.png");

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
