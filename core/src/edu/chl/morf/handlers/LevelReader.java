package edu.chl.morf.handlers;

import edu.chl.morf.file.FileLister;
import edu.chl.morf.file.IFileLister;

import java.util.List;

/**
 * This class reads all map files (created in Tiled Map Editor) from the levels folder in assets.
 * The name of the TMX-files from Tiled Map Editor are then placed in the levels ArrayList.
 * In this way, we have an ArrayList with all the names of the different levels.
 *
 * LevelReader is used by HighScoreHandler, in order to get the names of all the levels.
 */
public class LevelReader {
	
	private static LevelReader instance;
	private List<String> levels;
    private IFileLister fileLister;
	
	private LevelReader(){
        fileLister = new FileLister();
		this.levels = fileLister.getFileNames("levels");
	}
	
    public static LevelReader getInstance(){
    	if(instance == null){
    		instance = new LevelReader();
    	}
        return instance;
    }
    
	public List<String> getLevels(){
		return levels;
	}
}
