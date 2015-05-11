package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chl.morf.Constants;
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
        TextButton playButton;
        TextButton exitButton;
        TextButton settingsButton;
        TextButton highscoreButton;
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font;
        public MainMenuStage() {
            Gdx.input.setInputProcessor(this);
            Skin skin = new Skin();
            font = new BitmapFont();
            TextureAtlas textureAtlas = new TextureAtlas(Constants.BUTTONS_ATLAS_PATH);
            skin.addRegions(textureAtlas);
            font.setColor(Color.WHITE);
            font.setScale(2);
            textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = font;
            textButtonStyle.up = skin.getDrawable(Constants.BUTTON_UNPRESSED_REGION_NAME);
            textButtonStyle.down = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);
            textButtonStyle.checked = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);

            //PlayButton
            playButton = new TextButton("Play", textButtonStyle);
            playButton.setPosition(50, 250);
            playButton.setSize(200, 200);
            playButton.addListener(new ChangeListener(  ) {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    screenManager.setState(ScreenManager.PLAY);
                }
            });
            this.addActor(playButton);
            //ExitButton
            exitButton=new TextButton("Exit", textButtonStyle);
            exitButton.setPosition(250,250);
            exitButton.setSize(200, 200);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            });
            this.addActor(exitButton);

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
