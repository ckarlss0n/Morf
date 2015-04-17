package edu.chl.morf.Screens;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class MainMenuScreen extends ObservableScreen{

    private Stage stage;

    public MainMenuScreen(){
        super();
    }


    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void showGame(){
        try {
            changeToScreen("gamescreen");
        }catch (IllegalArgumentException e){

        }
    }
}
