package edu.chl.morf.handlers;

import edu.chl.morf.model.Level;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lage on 2015-05-12.
 */
public class HighScoreHandler {
    private BufferedReader bufferedReader;
    PrintWriter printWriter;
    private Map<Level,Integer> highScores;
    private static final HighScoreHandler instance = new HighScoreHandler();

    private HighScoreHandler(){
        highScores = new HashMap<Level, Integer>();
    }

    public static HighScoreHandler getInstance(){
        return instance;
    }

    public void addHighScore(Level level, int highScore){
        highScores.put(level, highScore);
    }

    public Integer getHighScore(Level level){
        return highScores.get(level);
    }

    public void writeHighScores(){
        try{
            printWriter = new PrintWriter(System.getProperty("user.home") + "/.morf/.highscore.txt");
            for(Level level : highScores.keySet()){
                printWriter.println(level.getName() + ";" + highScores.get(level).toString());
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        printWriter.close();
    }

    public void readHighScores(){
        try{
            //Trys to read file
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.morf/.highscore.txt"));
        }catch (FileNotFoundException e){
            //Creates file if file not found
            File f = new File(System.getProperty("user.home") + "/.morf/.highscore.txt");
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {

            }
        }

        try{
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.morf/.highscore.txt"));
            String line = bufferedReader.readLine();
            LevelFactory levelFactory = new LevelFactory();
            while (line != null) {
                String[] lineSplit = line.split(";");
                String levelName = lineSplit[0];
                String highScoreString = lineSplit[1];
                Integer highScore = Integer.parseInt(highScoreString);
                Level level = levelFactory.getLevel(levelName);
                highScores.put(level, highScore);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }
}
