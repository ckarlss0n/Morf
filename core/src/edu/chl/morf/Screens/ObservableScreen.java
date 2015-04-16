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

    public void addObserver(PropertyChangeListener pcl){
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
