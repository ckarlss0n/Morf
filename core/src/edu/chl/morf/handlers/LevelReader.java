package edu.chl.morf.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class LevelReader {
	
	private static LevelReader instance;
	private List<String> levels;
	
	private LevelReader(){
		File folder = new File("C:\\Users\\gustav\\Documents\\MorfWorkspace\\Morf\\core\\assets\\levels");
		File[] listOfFiles = folder.listFiles();
		List<String> levels = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				levels.add(listOfFiles[i].getName());
			}
		}
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
