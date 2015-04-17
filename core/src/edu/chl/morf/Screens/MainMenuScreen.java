package edu.chl.morf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chl.morf.Constants;

/**
 * Created by Christoffer on 2015-04-13.
 */
public class MainMenuScreen extends ObservableScreen{

    private Stage stage;

    private class MainMenuStage extends Stage{

        TextButton playButton;
        TextButton exitButton;
        TextButton settingsButton;
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font;
        Skin skin;
        TextureAtlas buttonAtlas;

        public MainMenuStage(){
            super();
            Gdx.input.setInputProcessor(this);
            font = new BitmapFont();
            skin = new Skin();
            buttonAtlas = new TextureAtlas(Constants.BUTTONS_ATLAS_PATH);
            skin.addRegions(buttonAtlas);
            textButtonStyle = new TextButton.TextButtonStyle();
            font.setColor(Color.BLACK);
            font.setScale(2);
            textButtonStyle.font = font;
            textButtonStyle.up = skin.getDrawable(Constants.BUTTON_UNPRESSED_REGION_NAME);
            textButtonStyle.down = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);
            textButtonStyle.checked = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);

            //play button
            playButton = new TextButton("PLAY", textButtonStyle);
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showGame();
                }
            });
            playButton.setPosition(50, 250);
            playButton.setSize(200,100);
            this.addActor(playButton);

            //exit button
            exitButton = new TextButton("EXIT", textButtonStyle);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            exitButton.setPosition(300, 250);
            exitButton.setSize(200,100);
            this.addActor(exitButton);

            //exit button
            exitButton = new TextButton("SETTINGS", textButtonStyle);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    
                }
            });
            exitButton.setPosition(550,250);
            exitButton.setSize(200,100);
            this.addActor(exitButton);
        }
    }


    public MainMenuScreen(){
        super();
        stage = new MainMenuStage();
    }


    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void showGame(){
        System.out.println("Event reggat!");
        try {
            changeToScreen("gamescreen");
        }catch (IllegalArgumentException e){

        }
    }
}
