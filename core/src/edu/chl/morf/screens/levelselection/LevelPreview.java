package edu.chl.morf.screens.levelselection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chl.morf.handlers.HighScores;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;
import edu.chl.morf.screens.ScreenManager;

import java.util.ArrayList;

/**
 * A class representing a small preview of a level.
 * When preview is clicked the level is started.
 * The stars on the preview represent level high score.
 *
 * Created by Lage on 2015-05-21.
 */
public class LevelPreview extends SelectionComponent{

    //Class representing each star of the preview
    private static class Star extends Image {
        private Texture texture;
        public Star(){
            this.texture = new Texture("levelselection/level_selection_star.png");
        }
        @Override
        public void draw(Batch batch, float parentAlpha){
            super.draw(batch,parentAlpha);
            batch.draw(texture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }
    }

    ArrayList<Star> stars;
    String levelName;
    ScreenManager screenManager = ScreenManager.getInstance();

    public LevelPreview(String levelName, float x, float y) {
        super("levelselection/" + levelName + "_Thumb.png","levelselection/" + levelName + "_Thumb_Focus.png"); //Get level texture
        this.setSize(0.3f * Main.V_WIDTH, 0.25f * Main.V_HEIGHT);
        this.setPosition(x, y);
        this.levelName = levelName;
        Integer levelScore = HighScores.getInstance().getHighScore(this.levelName + ".tmx");                    //Get high score

        //Set score required per star
        Level level = LevelFactory.getInstance().getLevel(levelName + ".tmx",false);
        int starScore = level.getStartingWaterAmount();

        stars = new ArrayList<Star>();
        //Add stars representing the level high score
        if(levelScore != null) {
            for (int i = 0; i < 8; i++) {
                if (levelScore >= (i+1) * (starScore / 8)) {
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
        super.draw(batch,parentAlpha);      //Draw preview texture
        //Draw stars
        for(Star star : stars){
            star.draw(batch,parentAlpha);
        }
    }

    @Override
    public void clickAction() {
        screenManager.pushScreen(ScreenManager.ScreenType.PLAY, levelName + ".tmx");    //Play previewed level
    }
}
