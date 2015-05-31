package edu.chl.morf.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing a 2D-matrix with a list.
 * A Matrix has a number of rows and columns and a list of LevelObjects.
 * 
 * @author Lage
 */
public class Matrix {

    private int rows;
    private int columns;
    private List<LevelObject> objects;

    //Constructors
    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        objects = new ArrayList<LevelObject>();
    }
    public Matrix(int maxRows, int maxColumns, List<LevelObject> objects){
        this(maxRows,maxColumns);
        this.objects = objects;
    }

    //Getters
    public int getRows(){
    	return rows;
    }
    public int getColumns(){
    	return columns;
    }
    public List<LevelObject> getLevelObjects(){
        return objects;
    }
    
    //Method for adding a LevelObject to the Matrix.
    public void addLevelObject(LevelObject object){
        float x = object.getPosition().x;
        float y = object.getPosition().y;
        //Check that object position is within matrix size.
        if(x >= 0 && x <= rows && y >= 0 && y <= columns){
            objects.add(object);
        }
    }
}
