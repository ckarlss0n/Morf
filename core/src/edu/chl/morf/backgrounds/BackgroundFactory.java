package edu.chl.morf.backgrounds;

import static edu.chl.morf.Constants.*;

/**
 * Created by Lage on 2015-05-07.
 */
public class BackgroundFactory {
    public BackgroundGroup createBackgroundGroup(String levelName){
        System.out.println(levelName);
        if(levelName.equals("Level_1.tmx")) {
            BackgroundGroup backgroundGroup = new BackgroundGroup();
            BackgroundLayer background = new BackgroundLayer(BACKGROUND_IMAGE_PATH, 0, 0);
            backgroundGroup.addBackgroundLayer(background);
            BackgroundLayer mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH, -10, 0);
            backgroundGroup.addBackgroundLayer(mountains);
            BackgroundLayer backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH, -30, 0);
            backgroundGroup.addBackgroundLayer(backgroundBottom);
            BackgroundLayer backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH, -50, 0);
            backgroundGroup.addBackgroundLayer(backgroundTop);
            BackgroundLayer bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH, -10, 5);
            backgroundGroup.addBackgroundLayer(bottomClouds);
            BackgroundLayer topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH, -10, 20);
            backgroundGroup.addBackgroundLayer(topClouds);
            return backgroundGroup;
        } else if(levelName.equals("Level_2.tmx")){
            BackgroundGroup backgroundGroup = new BackgroundGroup();
            BackgroundLayer background = new BackgroundLayer(BACKGROUND_IMAGE_PATH, 0, 0);
            backgroundGroup.addBackgroundLayer(background);
            BackgroundLayer mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH, -10, 0);
            backgroundGroup.addBackgroundLayer(mountains);
            BackgroundLayer backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH, -30, 0);
            backgroundGroup.addBackgroundLayer(backgroundBottom);
            BackgroundLayer backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH, -50, 0);
            backgroundGroup.addBackgroundLayer(backgroundTop);
            BackgroundLayer bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH, -10, 5);
            backgroundGroup.addBackgroundLayer(bottomClouds);
            BackgroundLayer topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH, -10, 20);
            backgroundGroup.addBackgroundLayer(topClouds);
            return backgroundGroup;
        } else if(levelName.equals("Level_3.tmx")){
        	BackgroundGroup backgroundGroup = new BackgroundGroup();
            BackgroundLayer background = new BackgroundLayer(BACKGROUND_IMAGE_PATH, 0, 0);
            backgroundGroup.addBackgroundLayer(background);
            BackgroundLayer mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH, -10, 0);
            backgroundGroup.addBackgroundLayer(mountains);
            BackgroundLayer backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH, -30, 0);
            backgroundGroup.addBackgroundLayer(backgroundBottom);
            BackgroundLayer backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH, -50, 0);
            backgroundGroup.addBackgroundLayer(backgroundTop);
            BackgroundLayer bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH, -10, 5);
            backgroundGroup.addBackgroundLayer(bottomClouds);
            BackgroundLayer topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH, -10, 20);
            backgroundGroup.addBackgroundLayer(topClouds);
            return backgroundGroup;
        }
        else{
            return null;
        }
    }
}
