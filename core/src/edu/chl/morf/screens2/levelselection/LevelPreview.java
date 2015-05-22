package edu.chl.morf.screens2.levelselection;

import com.badlogic.gdx.graphics.g2d.Batch;
import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-21.
 */
public class LevelPreview extends SelectionComponent{
    ArrayList<Star> stars;
    private Level level;
    String levelName;
    ScreenManager screenManager = ScreenManager.getInstance();

    public LevelPreview(Level level, float x, float y) {
        //super("levelselection/" + level.getName().split("\\.")[0]+ "_Thumb.png","levelselection/Level_1_Thumb_Focus.png");
        super("levelselection/Level_1_Thumb.png","levelselection/Level_1_Thumb_Focus.png");
        this.level = level;
        this.setSize(0.3f * Main.V_WIDTH, 0.25f * Main.V_HEIGHT);
        this.setPosition(x, y);
        this.levelName = level.getName().split("\\.")[0];
        Integer levelScore = HighScoreHandler.getInstance().getHighScore(this.level.getName());

        stars = new ArrayList<Star>();
        if(levelScore != null) {
            for (int i = 0; i < 8; i++) {
                if (levelScore >= (i+1) * (30 / 8)) {
                    Star star = new Star();
                    float starSize = 71f / 1920f * Main.V_WIDTH;
                    star.setSize(starSize, starSize);
                    star.setPosition(this.getX() + 19 + 42.5f * i, this.getY() + 14);
                    stars.add(star);
                }
            }
        }

    }

    @Override
    public void draw(Batch batch,float parentAlpha) {
        super.draw(batch,parentAlpha);
        for(Star star : stars){
            star.draw(batch,parentAlpha);
        }
    }

    @Override
    public void clickAction() {
        screenManager.pushScreen(ScreenManager.ScreenType.PLAY, levelName + ".tmx");
    }
}
