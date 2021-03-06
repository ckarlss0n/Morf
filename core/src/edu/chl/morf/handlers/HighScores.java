package edu.chl.morf.handlers;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class taking care of high scores for all levels.
 * The highScores HashMap contains pairs of level names and high scores.
 *
 * This class is used by HighScoreHandler, which saves and reads high scores from a text file.
 *
 * Created by Lage on 2015-05-31.
 */
public class HighScores {
    private Map<String,Integer> highScores = new HashMap<String, Integer>(); //Level name and score
    private static final HighScores instance = new HighScores();

    public static HighScores getInstance(){
        return instance;
    }

    public void addHighScore(String levelName, int highScore){
        highScores.put(levelName, highScore);
    }

    //Get the high score for a specific level
    public Integer getHighScore(String levelName){
        if(highScores.get(levelName) != null) {
            return highScores.get(levelName);
        } else {
            return 0;
        }
    }
}
