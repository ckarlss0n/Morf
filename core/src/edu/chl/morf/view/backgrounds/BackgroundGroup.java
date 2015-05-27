package edu.chl.morf.view.backgrounds;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-07.
 */
public class BackgroundGroup {

    private ArrayList<BackgroundLayer> backgrounds;

    public BackgroundGroup(){
        this.backgrounds = new ArrayList<BackgroundLayer>();
    }

    public void addBackgroundLayer(BackgroundLayer background){
        this.backgrounds.add(background);
    }

    public ArrayList<BackgroundLayer> getBackgrounds(){
        return backgrounds;
    }

    public void setBackgroundSpeeds(float speed){
        for(BackgroundLayer backgroundLayer: backgrounds){
            backgroundLayer.setSpeed(speed);
        }
    }

    public void renderLayers(Batch batch, float delta){
        for(BackgroundLayer backgroundLayer: backgrounds){
            backgroundLayer.draw(batch,delta);
        }
    }
}
