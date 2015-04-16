package edu.chl.morf;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Christoffer on 2015-04-13.
 */
public final class Constants {
    public static final String GAME_NAME = "Morf";
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0,-15);
    public static final int MAX_SPEED = 5;
    public static final String CHARACTERS_ATLAS_PATH = "SpriteSheets/RunningStickman";
    public static final String[] RUNNER_RUNNING_REGION_NAMES = new String[] {"Stickman1", "Stickman2", "Stickman3"};
    public static final float WORLD_TO_SCREEN = 40;
}
