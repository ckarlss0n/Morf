package edu.chl.morf.handlers;

import edu.chl.morf.model.Level;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lage on 2015-05-12.
 */
public class HighScoreHandler extends FileHandler{
    private Map<String,Integer> highScores = new HashMap<String, Integer>();
    private static final HighScoreHandler instance = new HighScoreHandler();

    public static HighScoreHandler getInstance(){
        return instance;
    }

    public void addHighScore(String levelName, int highScore){
        highScores.put(levelName, highScore);
    }

    public Integer getHighScore(String levelName){
        if(highScores.get(levelName) != null) {
            return highScores.get(levelName);
        } else {
            return 0;
        }
    }

    @Override
    public void write(PrintWriter printWriter) throws FileNotFoundException{
        for(String levelName : highScores.keySet()){
            printWriter.println(levelName + ";" + highScores.get(levelName).toString());
        }
    }

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
        return "/.morf/.highscore.txt";
    }
}
