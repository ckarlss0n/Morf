package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chl.morf.Constants;
import edu.chl.morf.handlers.ScreenManager;

/**
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen implements EventListener{

    private class LevelSelectionStage extends Stage{

        private class LevelPreview extends Table{
            TextButton playButton;
            TextButton.TextButtonStyle textButtonStyle;
            Label.LabelStyle labelStyle;
            BitmapFont font;
            Skin skin;
            TextureAtlas buttonAtlas;

            private LevelPreview(){
                super();
                font = new BitmapFont();
                skin = new Skin();
                buttonAtlas = new TextureAtlas(Constants.BUTTONS_ATLAS_PATH);
                skin.addRegions(buttonAtlas);
                textButtonStyle = new TextButton.TextButtonStyle();
                labelStyle = new Label.LabelStyle();
                font.setColor(Color.BLACK);
                font.setScale(2);
                labelStyle.font = font;
                textButtonStyle.font = font;
                textButtonStyle.up = skin.getDrawable(Constants.BUTTON_UNPRESSED_REGION_NAME);
                textButtonStyle.down = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);
                textButtonStyle.checked = skin.getDrawable(Constants.BUTTON_PRESSED_REGION_NAME);

                //play button
                playButton = new TextButton("Play", textButtonStyle);
                playButton.setDebug(true);

                this.setDebug(true);
                this.add(new Label("Image here", labelStyle));
                this.row();
                this.add(new Label("*", labelStyle));
                this.add(new Label("*", labelStyle));
                this.add(new Label("*", labelStyle));
                this.row();
                this.add(new Label("Points here ", labelStyle));
                this.row();
                this.add(playButton);
            }
        }

        private Table table;
        private LevelSelectionStage(){
            super();
            table = new Table();
            table.setPosition(new LevelPreview().getMinWidth() + new LevelPreview().getMinWidth() / 2 + 1, Gdx.graphics.getHeight() - new LevelPreview().getMinHeight() * 1,5 - 1);
            System.out.println(new LevelPreview().getMinHeight());
            table.add(new LevelPreview());
            table.add(new LevelPreview());
            table.add(new LevelPreview());
            table.row();
            table.add(new LevelPreview());
            table.add(new LevelPreview());
            table.add(new LevelPreview());
            addActor(table);
        }
    }

    public LevelSelectionScreen(ScreenManager sm) {
        super(sm);
        stage = new LevelSelectionStage();
        Gdx.input.setInputProcessor(stage);
    }
    private Stage stage;

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean handle(Event event) {
        if(event instanceof ChangeListener.ChangeEvent){
            ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)event;
            if(changeEvent.getTarget() instanceof TextButton){
                TextButton button = (TextButton)changeEvent.getTarget();
                String buttonText = button.getLabel().getText().toString();
                if(buttonText.equals("PLAY")){
                    /*sm.setStageInputHandler();
                    gameScreen.show();
                    setScreen(gameScreen);*/
                }else if(buttonText.equals("SETTINGS")){

                }
            }
        }
        return true;
    }
}
