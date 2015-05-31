package edu.chl.morf.file;

import edu.chl.morf.handlers.HighScores;
import edu.chl.morf.handlers.LevelReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A class for writing and reading high scores. Read high scores are put
 * in a Map in the singleton class HighScores.
 *
 * Created by Lage on 2015-05-12.
 */
public class HighScoreHandler extends AbstractFileHandler implements FileHandler{
    public static final String FILE_PATH = "/.morf/.highscore.txt";
    private HighScores highScores = HighScores.getInstance();
    private LevelReader levelReader = LevelReader.getInstance();

    /* Method used to write high scores to a text file.
     * Each row contains a level name and a score separated by a semicolon.
     */
    @Override
    public void write(PrintWriter printWriter) throws FileNotFoundException{
        for(String levelName : levelReader.getLevels()){
            printWriter.println(levelName + ";" + highScores.getHighScore(levelName).toString());
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
            highScores.addHighScore(levelName,highScore);
            line = bufferedReader.readLine();
        }
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }
}
