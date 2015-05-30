package edu.chl.morf.screens2.levelselection;

import com.badlogic.gdx.graphics.g2d.Batch;
import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.main.Main;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-21.
 */
public class LevelPreview extends SelectionComponent{
    ArrayList<Star> stars;
    String levelName;
    ScreenManager screenManager = ScreenManager.getInstance();

    public LevelPreview(String levelName, float x, float y) {

        //Change when new textures are added
        //super("levelselection/" + levelName + "_Thumb.png","levelselection/" + levelName + "_Thumb_Focus.png");
        super("levelselection/" + "Level_1" + "_Thumb.png","levelselection/" + "Level_1" + "_Thumb_Focus.png");

        this.setSize(0.3f * Main.V_WIDTH, 0.25f * Main.V_HEIGHT);
        this.setPosition(x, y);
        this.levelName = levelName;
        Integer levelScore = HighScoreHandler.getInstance().getHighScore(this.levelName + ".tmx");
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
