package edu.chl.morf.Stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.chl.morf.PlayerCharacter;

/**
 * Created by Lage on 2015-04-13.
 */
public class GameStage extends Stage {

    public GameStage(){
        addActor(new PlayerCharacter());
    }
}
