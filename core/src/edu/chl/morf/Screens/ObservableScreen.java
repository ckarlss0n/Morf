package edu.chl.morf.Screens;

import com.badlogic.gdx.Screen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Lage on 2015-04-16.
 */
public abstract class ObservableScreen implements Screen{

    private PropertyChangeSupport pcs;

    public ObservableScreen(){
        pcs = new PropertyChangeSupport(this);
    }

    public void notifyObservers(String propertyName, boolean oldValue, boolean newValue){
        pcs.firePropertyChange(propertyName,oldValue,newValue);
    }

    public void changeToScreen(String screenName) throws IllegalArgumentException{
        if(screenName.equals("gamescreen")||screenName.equals("mainmenuscreen")){
            notifyObservers(screenName,false,true);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void addObserver(PropertyChangeListener pcl){
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
