package edu.chl.morf.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
	
	private LevelReader(){
		String path = System.getProperty("user.dir") + File.separator + "levels";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> levels = new ArrayList<String>();

		for(File file : listOfFiles){
			if(file.isFile()){
				levels.add(file.getName());
			}
		}

        Collections.sort(levels); //Sort the list alphabetically
		this.levels = levels;
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
