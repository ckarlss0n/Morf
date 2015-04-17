package edu.chl.morf.Screens;

import com.badlogic.gdx.Gdx;
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

        TextButton button;
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font;
        Skin skin;
        TextureAtlas buttonAtlas;

        public MainMenuStage(){
            super();
            Gdx.input.setInputProcessor(this);
            font = new BitmapFont();
            skin = new Skin();
            buttonAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
            skin.addRegions(buttonAtlas);
            textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = font;
            textButtonStyle.up = skin.getDrawable(Constants.PLAYERCHARACTER_IDLE_REGION_NAME);
            textButtonStyle.down = skin.getDrawable(Constants.PLAYERCHARACTER_RUNNINGLEFT_REGION_NAMES[0]);
            textButtonStyle.checked = skin.getDrawable(Constants.PLAYERCHARACTER_IDLE_REGION_NAME);
            button = new TextButton("Button1", textButtonStyle);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Event!");
                    showGame();
                }
            });
            this.addActor(button);
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
