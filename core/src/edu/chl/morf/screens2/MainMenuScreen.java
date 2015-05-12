package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
        float scaling=0.666f;
        Image background;
        ImageButton settingsButton;
        ImageButton playButton;
        ImageButton exitButton;
        ImageButton highScoreButton;
        ImageButton.ImageButtonStyle highScoreButtonStyle;
        ImageButton.ImageButtonStyle settingsButtonStyle;
        ImageButton.ImageButtonStyle playButtonStyle;
        ImageButton.ImageButtonStyle exitButtonStyle;
        public MainMenuStage() {
            Gdx.input.setInputProcessor(this);

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
                    screenManager.setState(ScreenManager.LEVELSELECTION);
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
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            this.addActor(settingsButton);

            //highScoreButton
            highScoreButtonStyle =new ImageButton.ImageButtonStyle();
            highScoreButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_HighScores.png")));
            highScoreButtonStyle.down=new TextureRegionDrawable(new TextureRegion(new Texture("menu/Btn_HighScores_Focus.png")));
            highScoreButtonStyle.over= highScoreButtonStyle.down;
            highScoreButton =new ImageButton(highScoreButtonStyle);
            highScoreButton.setPosition(557 * scaling, (89 + (146 + 16) * 2) * scaling);
            highScoreButton.setSize(806 * scaling, 146 * scaling);
            highScoreButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            this.addActor(highScoreButton);
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
    public void resize (int width, int height){
    }

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
