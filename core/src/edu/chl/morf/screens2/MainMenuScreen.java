package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chl.morf.handlers.ScreenManager;

/**
 * Responsible for showing the main menu screen on start-up.
 * Created by Harald Brorsson on 5/11/15.
 */
public class MainMenuScreen extends GameScreen{
    MainMenuStage stage;
    public MainMenuScreen(ScreenManager sm){
        super(sm);
        this.stage=new MainMenuStage();
    }

    public class MainMenuStage extends Stage{
        float scaling=0.6666f;
        Image background;
        ImageButton settingsButton;
        TextButton playButton;
        TextButton exitButton;
        TextButton highscoreButton;
        ImageButton.ImageButtonStyle settingsButtonStyle;
        TextButton.TextButtonStyle playTextButtonStyle;
        TextButton.TextButtonStyle exitTextButtonStyle;
        BitmapFont font;
        public MainMenuStage() {
            Gdx.input.setInputProcessor(this);
            //Skin skin = new Skin();
            font = new BitmapFont();
            //TextureAtlas textureAtlas = new TextureAtlas(Constants.BUTTONS_ATLAS_PATH);
            //skin.addRegions(textureAtlas);
            font.setColor(Color.WHITE);
            font.setScale(2);
            playTextButtonStyle = new TextButton.TextButtonStyle();
            playTextButtonStyle.font = font;

            //background
            background=new Image(new Texture("menu/MainMenu_Background.png"));
            //background.setScaling(Scaling.fit);
            background.setScale(scaling);
            background.setPosition(0, 0);
            this.addActor(background);


            //PlayButton
            playTextButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_LevelSelection.png")));
            playTextButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_LevelSelection_Focus.png")));
            playTextButtonStyle.over=playTextButtonStyle.down;
            playButton = new TextButton("",playTextButtonStyle);
            playButton.setPosition(557 * scaling, 89 + (146 + 16) * 3 * scaling);
            playButton.setSize(806 * scaling, 146 * scaling);
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    screenManager.setState(ScreenManager.PLAY);
                }
            });
            this.addActor(playButton);

            //ExitButton
            exitTextButtonStyle=new TextButton.TextButtonStyle(playTextButtonStyle);
            exitTextButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Exit.png")));
            exitTextButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Exit_Focus.png")));
            exitTextButtonStyle.over = exitTextButtonStyle.down;
            exitButton=new TextButton("", exitTextButtonStyle);
            exitButton.setPosition(557 * scaling, 89 * scaling);
            exitButton.setSize(806 * scaling, 146 * scaling);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            this.addActor(exitButton);

            //settingsButton
            settingsButtonStyle=new ImageButton.ImageButtonStyle();
            settingsButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Settings.png")));
            settingsButtonStyle.down=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Settings_Focus.png")));
            settingsButtonStyle.over=settingsButtonStyle.down;
            settingsButton=new ImageButton(settingsButtonStyle);
            settingsButton.setPosition(557 * scaling, (89 + 146 + 16) * scaling);
            settingsButton.setSize(806 * scaling, 146 * scaling);
            settingsButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {Gdx.app.exit();}
            });
            this.addActor(settingsButton);
        }
    }

    @Override
    public void dispose() {
    }
    @Override
    /** Called when this screen becomes the current screen. */
    public void show () {
    }

    @Override
    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render (float delta){
        stage.act(delta);
        stage.draw();
    }

    @Override
    /** @see ApplicationListener#resize(int, int) */
    public void resize (int width, int height){}

    @Override
    /** @see ApplicationListener#pause() */
    public void pause (){}

    @Override
    /** @see ApplicationListener#resume() */
    public void resume (){}

    @Override
    /** Called when this screen is no longer the current screen. */
    public void hide (){}

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }
}
