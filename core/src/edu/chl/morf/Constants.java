package edu.chl.morf;

import com.badlogic.gdx.math.Vector2;

/**
 * This class contains important constants used in the project.
 * <p>
 * <li><b>Responsible for: </b>
 * Providing important constants for global usage in the project.
 * <p>
 * <li><b>Used by: </b>
 * Numerous classes.
 * <p>
 * <li><b>Using: </b>
 * <li>LibGDX classes</li>
 * <p>
 * @author Christoffer Karlsson
 */
public final class Constants {
    //Game/project constants
    public static final String GAME_NAME = "Morf";
    public static final int GAME_WIDTH = 1366;
    public static final int GAME_HEIGHT = 768;
    public static final boolean FULLSCREEN = false;
    public static final float PPM = 100f; //Pixels per meter

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
    public static final String LEVEL_2 = "Level_2.tmx";

    //Game play constants
    public static final Vector2 WORLD_GRAVITY = new Vector2(0,-15);
    public static final int MAX_SPEED = 2;
    public static final int MAX_FLYING_SPEED = 5;
    public static final int WATER_LEVEL = 50;
    public static final float GROUND_FRICTION = 0.1f;

    //Character animation constants
    public static final String CHARACTERS_ATLAS_PATH = "spritesheets/Character_Sprite_Sheet";
    public static final String[] PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES = new String[] {"runningLeft1", "runningLeft2", "runningLeft3",
            "runningLeft4", "runningLeft5", "runningLeft6", "runningLeft7"};
    public static final String[] PLAYERCHARACTER_RUNNINGRIGHT_REGION_NAMES = new String[] {"runningRight1", "runningRight2", "runningRight3",
            "runningRight4", "runningRight5", "runningRight6", "runningRight7",};
    public static final String[] PLAYERCHARACTER_POURRIGHT_REGION_NAMES = new String[] {"pourRight1", "pourRight1", "pourRight2", "pourRight2", "pourRight2",
            "pourRight3", "pourRight3", "pourRight3", "pourRight2", "pourRight2", "pourRight2", "pourRight1", "pourRight1"};
    public static final String[] PLAYERCHARACTER_COOLRIGHT_REGION_NAMES = new String[] {"idleRightEmpty", "coolRight1", "coolRight1", "coolRight2",
            "coolRight2", "coolRight2", "coolRight3", "coolRight3", "coolRight3", "coolRight2", "coolRight2", "coolRight2", "coolRight1", "coolRight1", "idleRightEmpty"};
    public static final String[] PLAYERCHARACTER_HEATRIGHT_REGION_NAMES = new String[] {"idleRightEmpty", "heatRight1", "heatRight1", "heatRight2", "heatRight2",
            "heatRight2", "heatRight3", "heatRight3", "heatRight3", "heatRight2", "heatRight2", "heatRight2", "heatRight1", "heatRight1", "idleRightEmpty"};
    public static final String[] PLAYERCHARACTER_POURLEFT_REGION_NAMES = new String[] {"pourLeft1", "pourLeft1", "pourLeft2", "pourLeft2", "pourLeft2",
            "pourLeft3", "pourLeft3", "pourLeft3", "pourLeft2", "pourLeft2", "pourLeft2", "pourLeft1", "pourLeft1"};
    public static final String[] PLAYERCHARACTER_COOLLEFT_REGION_NAMES = new String[] {"idleLeftEmpty", "coolLeft1", "coolLeft1", "coolLeft2",
            "coolLeft2", "coolLeft2", "coolLeft3", "coolLeft3", "coolLeft3", "coolLeft2", "coolLeft2", "coolLeft2", "coolLeft1", "coolLeft1", "idleLeftEmpty"};
    public static final String[] PLAYERCHARACTER_HEATLEFT_REGION_NAMES = new String[] {"idleLeftEmpty", "heatLeft1", "heatLeft1", "heatLeft2", "heatLeft2",
            "heatLeft2", "heatLeft3", "heatLeft3", "heatLeft3", "heatLeft2", "heatLeft2", "heatLeft2", "heatLeft1", "heatLeft1", "idleLeftEmpty"};
    public static final String[] PLAYERCHARACTER_FLYLEFT_REGION_NAMES = new String[] {"idleLeftEmpty", "flyingLeft1", "flyingLeft1", "flyingLeft2", "flyingLeft2",
            "flyingLeft3", "flyingLeft3", "flyingLeft4", "flyingLeft4"};
    public static final String[] PLAYERCHARACTER_DEATHLEFT_REGION_NAMES = new String[] {"idleLeftEmpty", "deathLeft1", "deathLeft1", "deathLeft2", "deathLeft2",
            "deathLeft3", "deathLeft3", "deathLeft4", "deathLeft4", "deathLeft4"};
    public static final String[] PLAYERCHARACTER_FLYRIGHT_REGION_NAMES = new String[] {"idleRightEmpty", "flyingRight1", "flyingRight1", "flyingRight2", "flyingRight2",
            "flyingRight3", "flyingRight3", "flyingRight4", "flyingRight4"};
    public static final String[] PLAYERCHARACTER_DEATHRIGHT_REGION_NAMES = new String[] {"idleRightEmpty", "deathRight1", "deathRight1", "deathRight2", "deathRight2",
            "deathRight3", "deathRight3", "deathRight4", "deathRight4", "deathRight4"};



    //Button constants
    public static final String BUTTONS_ATLAS_PATH = "spritesheets/Buttons";
    public static final String BUTTON_UNPRESSED_REGION_NAME = "ButtonUnpressed";
    public static final String BUTTON_PRESSED_REGION_NAME = "ButtonPressed";
}
