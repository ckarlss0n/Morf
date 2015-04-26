package edu.chl.morf;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Christoffer on 2015-04-13.
 */
public final class Constants {
    //Game/project constants
    public static final String GAME_NAME = "Morf";
    public static final int GAME_WIDTH = 1366;
    public static final int GAME_HEIGHT = 768;
    public static final boolean FULLSCREEN = false;
    public static final float PPM = 100f; //Pixels per meter
    public static final float REAL_TILE_SIZE = 64;
    public static final float TILE_SIZE = (REAL_TILE_SIZE-2)/PPM; //Safe tile size

    //Background constants
    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String MOUNTAINS_IMAGE_PATH = "mountains.png";
    public static final String BACKGROUND_BOTTOM_IMAGE_PATH = "backgroundBottom.png";
    public static final String BACKGROUND_TOP_IMAGE_PATH = "backgroundTop.png";
    public static final String BOTTOM_CLOUDS_IMAGE_PATH = "cloudsBottom.png";
    public static final String TOP_CLOUDS_IMAGE_PATH = "cloudsTop.png";
    public static final int BACKGROUND_SCROLLING_SPEED = 50;

    //Levels
    public static final String LEVEL_PATH = "levels/";
    public static final String LEVEL_1 = "Level_1.tmx";

    //Game play constants
    public static final Vector2 WORLD_GRAVITY = new Vector2(0,-15);
    public static final int MAX_SPEED = 2;
    public static final int MAX_FLYING_SPEED = 5;
    public static final int WATER_LEVEL = 50;
    public static final float GROUND_FRICTION = 0.1f;

    //Character animation constants
    public static final String CHARACTERS_ATLAS_PATH = "spritesheets/Stickman";
    public static final String[] PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES = new String[] {"runningLeft1", "runningLeft2", "runningLeft3"};
    public static final String[] PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES = new String[] {"runningRight1", "runningRight2", "runningRight3"};
    public static final String PLAYERCHARACTER_IDLE_REGION_NAME = "idle";

    //Button constants
    public static final String BUTTONS_ATLAS_PATH = "spritesheets/Buttons";
    public static final String BUTTON_UNPRESSED_REGION_NAME = "ButtonUnpressed";
    public static final String BUTTON_PRESSED_REGION_NAME = "ButtonPressed";
}
