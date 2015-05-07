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
    }

    public Matrix(int maxRows, int maxColumns, ArrayList<LevelObject> objects){
        this(maxRows,maxColumns);
        this.objects = objects;
    }

    public void addLevelObject(LevelObject object){
        int x = object.getPosition().x;
        int y = object.getPosition().y;
        if(x >= 0 && x <= maxColumns && y >= 0 && y <= maxRows){
            this.objects.add(object);
        }
    }

    public ArrayList<LevelObject> getLevelObjects(){
        return this.objects;
    }
}
