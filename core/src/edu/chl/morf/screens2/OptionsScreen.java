package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.handlers.SoundHandler;

/**
 * Created by Christoffer on 2015-05-14.
 */
public class OptionsScreen extends GameScreen{
	private SoundHandler soundHandler = SoundHandler.getInstance();
	private Stage stage;

	public OptionsScreen(ScreenManager screenManager){
		super(screenManager);
		stage = new OptionsScreenStage();
		Gdx.input.setInputProcessor(stage);
	}

	private class OptionsScreenStage extends Stage{
		float scaling= 0.666f;
		private Image background;
		private Table table;
		private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		CheckBox musicCheckBox;
		CheckBox soundEffectsCheckBox;
		Slider musicVolumeSlider;
		Slider soundEffectsVolumeSlider;

		private OptionsScreenStage(){
			soundHandler.playMusic();
			//Background
			background=new Image(new Texture("menu/MainMenu_Background.png"));
			background.setScale(scaling);
			background.setPosition(0, 0);
			addActor(background);

			//Components
			Label title = new Label("Options", skin);
			title.setFontScale(2f);
			musicCheckBox = new CheckBox(" Enable music", skin);
			musicCheckBox.setChecked(soundHandler.isMusicEnabled());
			musicCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
					if(musicCheckBox.isChecked()){
						soundHandler.enableMusic();
					} else {
						soundHandler.muteMusic();
					}
				}
			});

			Label musicVolumeTitle = new Label("Music volume", skin);
			musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
			musicVolumeSlider.setValue(soundHandler.getMusicVolume());
			musicVolumeSlider.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(!musicCheckBox.isChecked()){
						musicCheckBox.setChecked(true);
					}
					soundHandler.setMusicVolume(musicVolumeSlider.getValue());
				}
			});

			soundEffectsCheckBox = new CheckBox(" Enable sound effects", skin);
			soundEffectsCheckBox.setChecked(soundHandler.isSoundEffectsEnabled());
			soundEffectsCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
					if (soundEffectsCheckBox.isChecked()) {
						soundHandler.enabledSoundEffects();
					} else {
						soundHandler.muteSoundEffects();
					}
				}
			});

			Label soundEffectsVolumeTitle = new Label("Sound effects volume", skin);
			soundEffectsVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
			soundEffectsVolumeSlider.setValue(soundHandler.getSoundEffectsVolume());
			soundEffectsVolumeSlider.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (!soundEffectsCheckBox.isChecked()) {
						soundEffectsCheckBox.setChecked(true);
					}
					soundHandler.setSoundEffectsVolume(soundEffectsVolumeSlider.getValue());
				}
			});

			TextButton returnButton = new TextButton("Return", skin);
			returnButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					screenManager.setState(ScreenManager.MAINMENU);
					soundHandler.playSoundEffect(soundHandler.getButtonReturn());
					soundHandler.playSoundEffect(soundHandler.getSaveSettings());
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
				}
			});
			TextButton saveSettingsButton = new TextButton("Restore Defaults", skin);
			saveSettingsButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					soundEffectsCheckBox.setChecked(true);
					soundEffectsVolumeSlider.setValue(1f);
					musicCheckBox.setChecked(true);
					musicVolumeSlider.setValue(1f);
					soundHandler.playSoundEffect(soundHandler.getSaveSettings());
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
				}
			});

			//Table
			table = new Table();
			table.setFillParent(true);
			table.pad(getHeight() * 0.35f, getWidth() * 0.3f, getHeight() * 0.1f, getWidth() * 0.3f);
			table.setDebug(false);
			table.add(title).padBottom(30).top().left().row();

			table.add(musicCheckBox).left().padBottom(10).row();
			table.add(musicVolumeTitle).colspan(2).center().padBottom(10).row();
			table.add(musicVolumeSlider).colspan(2).fillX().padBottom(30).row();

			table.add(soundEffectsCheckBox).left().padBottom(10).row();
			table.add(soundEffectsVolumeTitle).colspan(2).center().padBottom(10).row();
			table.add(soundEffectsVolumeSlider).colspan(2).fillX().expandX().padBottom(50).row();

			table.add(returnButton).size(200, 75).bottom().expandY().left();
			table.add(saveSettingsButton).size(200, 75).bottom().expandY().right();
			addActor(table);
		}

	}

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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //Clears the screen.
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
}
