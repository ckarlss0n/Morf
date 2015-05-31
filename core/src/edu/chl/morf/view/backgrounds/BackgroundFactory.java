package edu.chl.morf.view.backgrounds;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used for creating backgrounds.
 * The class creates BackGroundLayers from textures in assets and
 * adds them to a single BackGroundGroup.
 * Created by Lage on 2015-05-07.
 */
public class BackgroundFactory {

    //Texture paths
    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String MOUNTAINS_IMAGE_PATH = "mountains.png";
    public static final String BACKGROUND_BOTTOM_IMAGE_PATH = "backgroundBottom.png";
    public static final String BACKGROUND_TOP_IMAGE_PATH = "backgroundTop.png";
    public static final String BOTTOM_CLOUDS_IMAGE_PATH = "cloudsBottom.png";
    public static final String TOP_CLOUDS_IMAGE_PATH = "cloudsTop.png";

    
    public List<BackgroundLayer> createBackgroundGroup(){
        List<BackgroundLayer> backgroundGroup = new ArrayList<BackgroundLayer>();
        BackgroundLayer background = new BackgroundLayer(BACKGROUND_IMAGE_PATH, 0, 0);
        backgroundGroup.add(background);
        BackgroundLayer mountains = new BackgroundLayer(MOUNTAINS_IMAGE_PATH, -10, 0);
        backgroundGroup.add(mountains);
        BackgroundLayer backgroundBottom = new BackgroundLayer(BACKGROUND_BOTTOM_IMAGE_PATH, -30, 0);
        backgroundGroup.add(backgroundBottom);
        BackgroundLayer backgroundTop = new BackgroundLayer(BACKGROUND_TOP_IMAGE_PATH, -50, 0);
        backgroundGroup.add(backgroundTop);
        BackgroundLayer bottomClouds = new BackgroundLayer(BOTTOM_CLOUDS_IMAGE_PATH, -10, 5);
        backgroundGroup.add(bottomClouds);
        BackgroundLayer topClouds = new BackgroundLayer(TOP_CLOUDS_IMAGE_PATH, -10, 20);
        backgroundGroup.add(topClouds);
        return backgroundGroup;
    }
}
