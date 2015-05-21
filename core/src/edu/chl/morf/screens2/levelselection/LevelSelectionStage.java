package edu.chl.morf.screens2.levelselection;

import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;

/**
 * Created by Lage on 2015-05-21.
 */
public class LevelSelectionStage extends Stage {

    public LevelSelectionStage(){
        super();
        LevelFactory levelFactory = LevelFactory.getInstace();
        String levelName;
        Level level;
        LevelPreview levelPreview;
        float levelPreviewHeight = 0.25f * Main.V_HEIGHT;
        float levelPreviewWidth = 0.3f * Main.V_WIDTH;
        float previewGapX = (Main.V_WIDTH - 2 * levelPreviewWidth)/3;
        float previewGapY = (Main.V_HEIGHT - 2 * levelPreviewHeight)/3;
        int i = 1;
        for(int y = 0; y < 2; y ++) {
            for(int x = 0; x < 2; x++){
                levelName = "Level_" + i + ".tmx";
                level = levelFactory.getLevel(levelName, false);
                levelPreview = new LevelPreview(level, previewGapX + x * (previewGapX + levelPreviewWidth),
                        Main.V_HEIGHT - (previewGapY + levelPreviewHeight) - y * (levelPreviewHeight + previewGapY));
                this.addActor(levelPreview);
                i++;
            }
        }
        BackButton backButton = new BackButton();
        backButton.setPosition(30,600);
        backButton.setSize(300,100);
        this.addActor(backButton);
    }
}
