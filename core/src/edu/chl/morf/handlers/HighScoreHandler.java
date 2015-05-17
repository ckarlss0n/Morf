package edu.chl.morf.handlers;

import edu.chl.morf.model.Level;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lage on 2015-05-12.
 */
public class HighScoreHandler extends FileHandler{
    private Map<Level,Integer> highScores = new HashMap<Level, Integer>();
    private static final HighScoreHandler instance = new HighScoreHandler();

    public static HighScoreHandler getInstance(){
        return instance;
    }

    public void addHighScore(Level level, int highScore){
        highScores.put(level, highScore);
    }

    public Integer getHighScore(Level level){
        return highScores.get(level);
    }

    @Override
    public void write(PrintWriter printWriter) throws FileNotFoundException{
        for(Level level : highScores.keySet()){
            printWriter.println(level.getName() + ";" + highScores.get(level).toString());
        }
    }

    @Override
    public void read(BufferedReader bufferedReader) throws IOException{
        LevelFactory levelFactory = new LevelFactory();
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] lineSplit = line.split(";");
            String levelName = lineSplit[0];
            String highScoreString = lineSplit[1];
            Integer highScore = Integer.parseInt(highScoreString);
            Level level = levelFactory.getLevel(levelName);
            highScores.put(level, highScore);
            line = bufferedReader.readLine();
        }
    }

    @Override
    public String getFilePath() {
        return "/.morf/.highscore.txt";
    }
}
