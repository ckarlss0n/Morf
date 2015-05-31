package edu.chl.morf.handlers;

import java.util.List;

/**
 * A singleton class containing a list of all levels. It gets them from a FileLister class
 * implementation in Main when the game starts.
 * LevelList is used by HighScoreHandler, in order to get the names of all the levels.
 */
public class LevelList {
	
	private static LevelList instance;
	private List<String> levels;
	
    public static LevelList getInstance(){
    	if(instance == null){
    		instance = new LevelList();
    	}
        return instance;
    }

    public void setLevels(List<String> levels){
        this.levels = levels;
    }

	public List<String> getLevels(){
		return levels;
	}
}
