package edu.chl.morf.model;

import java.util.ArrayList;

import edu.chl.morf.stages.Block;
import edu.chl.morf.stages.WaterBlock;

/*
 * Class for representing a level.
 * A level consists of a matrix containing solid tiles and a list containing the water in the level.
 */
public class Level {
	
	private TileType[][] levelMatrix;
	private ArrayList<Water> waterBlocks;

	public Level(TileType[][] matrix){
		levelMatrix = matrix;
	}
	
	public TileType[][] getMatrix(){
		return levelMatrix;
	}
	
	public void addWater(Water w){
		waterBlocks.add(w);
	}
	
	public void removeWater(Water w){
		waterBlocks.remove(w);
	}
}
