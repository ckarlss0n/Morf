package edu.chl.morf.backgrounds;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-05-07.
 */
public class BackgroundFactory {
    public void createBackgroundGroup(String levelName){
        if(levelName.equals("Level_1.tmx")){
            BackgroundGroup backgroundGroup = new BackgroundGroup();
            BackgroundLayer background = new BackgroundLayer(BACKGROUND_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(background);
            BackgroundLayer mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(mountains);
            BackgroundLayer backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(backgroundBottom);
            BackgroundLayer backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(backgroundTop);
            BackgroundLayer bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(bottomClouds);
            BackgroundLayer topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH);
            backgroundGroup.addBackgroundLayer(topClouds);
        }
    }
}
