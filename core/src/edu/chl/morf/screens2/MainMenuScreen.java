package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.screens.ScreenManager;
import edu.chl.morf.screens.ScreenManager.ScreenType;

import java.awt.*;
import java.net.URI;

/**
 * Responsible for showing the main menu screen on start-up.
 * Created by Harald Brorsson on 5/11/15.
 */
public class MainMenuScreen extends GameScreen{
    MainMenuStage stage;
    private SoundHandler soundHandler = SoundHandler.getInstance();
    public MainMenuScreen(ScreenManager sm){
        super(sm);
        this.stage=new MainMenuStage();
        setFocus();
    }

    public class MainMenuStage extends Stage{
        float scaling=0.666f;
        Image background;
        ImageButton settingsButton;
        ImageButton playButton;
        ImageButton exitButton;
        ImageButton aboutButton;
        ImageButton.ImageButtonStyle aboutButtonStyle;
        ImageButton.ImageButtonStyle settingsButtonStyle;
        ImageButton.ImageButtonStyle playButtonStyle;
        ImageButton.ImageButtonStyle exitButtonStyle;
        public MainMenuStage() {
            soundHandler.playMusic();
            //background
            background=new Image(new Texture("menu/MainMenu_Background.png"));
            background.setScale(scaling);
            background.setPosition(0, 0);
            this.addActor(background);

            //PlayButton
            playButtonStyle=new ImageButton.ImageButtonStyle();
            playButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_LevelSelection.png")));
            playButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_LevelSelection_Focus.png")));
            playButtonStyle.over= playButtonStyle.down;
            playButton = new ImageButton(playButtonStyle);
            playButton.setPosition(557 * scaling, (89 + (146 + 16) * 3) * scaling);
            playButton.setSize(806 * scaling, 146 * scaling);
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    screenManager.pushScreen(ScreenType.LEVEL_SELECTION, null);
                    soundHandler.playSoundEffect(soundHandler.getButtonForward());
                }
            });
            playButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    soundHandler.playSoundEffect(soundHandler.getButtonHover());
                }
            });
            this.addActor(playButton);

            //ExitButton
            exitButtonStyle =new ImageButton.ImageButtonStyle();
            exitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Exit.png")));
            exitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Exit_Focus.png")));
            exitButtonStyle.over = exitButtonStyle.down;
            exitButton=new ImageButton(exitButtonStyle);
            exitButton.setPosition(557 * scaling, 89 * scaling);
            exitButton.setSize(806 * scaling, 146 * scaling);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            exitButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    soundHandler.playSoundEffect(soundHandler.getButtonHover());
                }
            });
            this.addActor(exitButton);

            //settingsButton
            settingsButtonStyle=new ImageButton.ImageButtonStyle();
            settingsButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Settings.png")));
            settingsButtonStyle.down=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_Settings_Focus.png")));
            settingsButtonStyle.over=settingsButtonStyle.down;
            settingsButton=new ImageButton(settingsButtonStyle);
            settingsButton.setPosition(557 * scaling, (89 + (146 + 16) * 2) * scaling);
            settingsButton.setSize(806 * scaling, 146 * scaling);
            settingsButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    screenManager.pushScreen(ScreenType.OPTIONS, null);
                }
            });
            settingsButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    soundHandler.playSoundEffect(soundHandler.getButtonHover());
                }
            });
            this.addActor(settingsButton);

            //aboutButton
            aboutButtonStyle =new ImageButton.ImageButtonStyle();
            aboutButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_About.png")));
            aboutButtonStyle.down=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_About_Focus.png")));
            aboutButtonStyle.over= aboutButtonStyle.down;
            aboutButton =new ImageButton(aboutButtonStyle);
            aboutButton.setPosition(557 * scaling, (89 + 146 + 16) * scaling);
            aboutButton.setSize(806 * scaling, 146 * scaling);
            aboutButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI oURL = new URI("https://github.com/ckarlss0n/Morf");
                        desktop.browse(oURL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            aboutButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    soundHandler.playSoundEffect(soundHandler.getButtonHover());
                }
            });
            this.addActor(aboutButton);
        }
    }
    @Override
    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render (float delta){
        stage.act(delta);
        stage.draw();
    }
	@Override
	public void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
