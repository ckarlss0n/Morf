package edu.chl.morf.backgrounds;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-07.
 */
public class BackgroundGroup {

    private ArrayList<BackgroundLayer> backgrounds;

    public void addBackgroundLayer(BackgroundLayer background){
        this.backgrounds.add(background);
    }

    public ArrayList<BackgroundLayer> getBackgrounds(){
        return backgrounds;
    }
}
