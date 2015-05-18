package edu.chl.morf.model;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-07.
 */
public class Matrix {

    private int maxRows;
    private int maxColumns;
    private ArrayList<LevelObject> objects;

    public Matrix(int maxRows, int maxColumns){
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        objects = new ArrayList<LevelObject>();
    }

    public Matrix(int maxRows, int maxColumns, ArrayList<LevelObject> objects){
        this(maxRows,maxColumns);
        this.objects = objects;
    }

    public void addLevelObject(LevelObject object){
        float x = object.getPosition().x;
        float y = object.getPosition().y;
        if(x >= 0 && x <= maxRows && y >= 0 && y <= maxColumns){
            this.objects.add(object);
        }
    }

    public int getRows(){
    	return maxRows;
    }
    public int getColumns(){
    	return maxColumns;
    }
    
    public ArrayList<LevelObject> getLevelObjects(){
        return this.objects;
    }
}
