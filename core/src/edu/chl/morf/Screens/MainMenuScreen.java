package edu.chl.morf.Screens;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class MainMenuScreen extends ObservableScreen{

    public void showGame(){
        try {
            changeToScreen("gamescreen");
        }catch (IllegalArgumentException e){

        }
    }
}
