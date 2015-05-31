package edu.chl.morf.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class for handling high scores for each playable level.
 * Extends the abstract class FileHandler.
 *
 * Created by Lage on 2015-05-12.
 */
public class HighScoreHandler extends FileHandler{
    private Map<String,Integer> highScores = new HashMap<String, Integer>(); //Level name and score
    private static final HighScoreHandler instance = new HighScoreHandler();
    public static final String FILE_PATH = "/.morf/.highscore.txt";

    public static HighScoreHandler getInstance(){
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

    /* Method used to write high scores to a text file.
     * Each row contains a level name and a score separated by a semicolon.
     */
    @Override
    public void write(PrintWriter printWriter) throws FileNotFoundException{
        for(String levelName : highScores.keySet()){
            printWriter.println(levelName + ";" + highScores.get(levelName).toString());
        }
    }

    //Method used to read high scores from a text file.
    @Override
    public void read(BufferedReader bufferedReader) throws IOException{
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] lineSplit = line.split(";");
            String levelName = lineSplit[0];
            String highScoreString = lineSplit[1];
            Integer highScore = Integer.parseInt(highScoreString);
            highScores.put(levelName, highScore);
            line = bufferedReader.readLine();
        }
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }
}
