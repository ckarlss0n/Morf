package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.chl.morf.handlers.KeyBindings;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.handlers.ScreenManager.ScreenType;
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
		private CheckBox musicCheckBox;
		private CheckBox soundEffectsCheckBox;
		private Slider musicVolumeSlider;
		private Slider soundEffectsVolumeSlider;
		private KeyBindings keyBindings = KeyBindings.getInstance();

		private void bindListener(final TextField textField, final String keyName){
			textField.addListener(new ClickListener(){
				@Override
				public boolean keyDown(InputEvent event, int keycode) {
					String oldKey = textField.getText();
					String newKey = Input.Keys.toString(keycode);
					if(!keyBindings.isUsed(newKey)) {
						keyBindings.removeKey(oldKey);
						keyBindings.addKey(newKey);
						textField.setText(newKey);
						keyBindings.setKey(keyName, newKey);
						textField.setColor(Color.GREEN);
					} else {
						textField.setColor(Color.RED);
					}
					System.out.println(keyBindings.getUsedKeys());
					textField.setDisabled(true);
					return super.keyDown(event, keycode);
				}

				@Override
				public boolean keyUp(InputEvent event, int keycode) {
					textField.setColor(Color.WHITE);
					return super.keyUp(event, keycode);
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					textField.setDisabled(false);
					super.enter(event, x, y, pointer, fromActor);
				}
			});
		}

		private OptionsScreenStage(){
			soundHandler.playMusic();
			//Background
			background=new Image(new Texture("levelselection/Level_Selection_Background.png"));
			background.setScale(scaling);
			background.setPosition(0, 0);
			addActor(background);

			Label controlsLabel = new Label("Keyboard controls", skin);
			Label moveLeftLabel = new Label("Move left: ", skin);
			Label moveRightLabel = new Label("Move right: ", skin);
			Label jumpLabel = new Label("Jump: ", skin);
			Label flyLabel = new Label("Fly: ", skin);
			Label pourLabel = new Label("Pour water: ", skin);
			Label coolLabel = new Label("Cool: ", skin);
			Label heatLabel = new Label("Heat: ", skin);

			final TextField moveLeftField = new TextField(keyBindings.getMoveLeftKey(), skin);
            final TextField moveRightField = new TextField(keyBindings.getMoveRightKey(), skin);
            final TextField jumpField = new TextField(keyBindings.getJumpKey(), skin);
            final TextField flyField = new TextField(keyBindings.getFlyKey(), skin);
            final TextField pourField = new TextField(keyBindings.getPourKey(), skin);
            final TextField coolField = new TextField(keyBindings.getCoolKey(), skin);
            final TextField heatField = new TextField(keyBindings.getHeatKey(), skin);

			bindListener(moveLeftField, "MOVE_LEFT_KEY");
			bindListener(moveRightField, "MOVE_RIGHT_KEY");
			bindListener(jumpField, "JUMP_KEY");
			bindListener(flyField, "FLY_KEY");
			bindListener(pourField, "POUR_KEY");
			bindListener(coolField, "COOL_KEY");
			bindListener(heatField, "HEAT_KEY");

			//Components
			Label title = new Label("Options", skin);
			musicCheckBox = new CheckBox(" Enable music", skin);
			musicCheckBox.setChecked(soundHandler.isMusicEnabled());
			musicCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
					if (musicCheckBox.isChecked()) {
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
					screenManager.setScreen(ScreenType.MAIN_MENU, null);
					soundHandler.playSoundEffect(soundHandler.getButtonReturn());
					soundHandler.playSoundEffect(soundHandler.getSaveSettings());
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
				}
			});
			TextButton resetButton = new TextButton("Restore Defaults", skin);
			resetButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					soundEffectsCheckBox.setChecked(true);
					soundEffectsVolumeSlider.setValue(1f);
					musicCheckBox.setChecked(true);
					musicVolumeSlider.setValue(1f);
					keyBindings.resetDefaults();
					moveLeftField.setText(keyBindings.getMoveLeftKey());
					moveRightField.setText(keyBindings.getMoveRightKey());
					jumpField.setText(keyBindings.getJumpKey());
					flyField.setText(keyBindings.getFlyKey());
					pourField.setText(keyBindings.getPourKey());
					coolField.setText(keyBindings.getCoolKey());
					heatField.setText(keyBindings.getHeatKey());
					System.out.println("RESET " + keyBindings.getUsedKeys());
					soundHandler.playSoundEffect(soundHandler.getSaveSettings());
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					soundHandler.playSoundEffect(soundHandler.getButtonHover());
				}
			});

			//Table
			table = new Table(skin);
			table.setDebug(false);
			table.setFillParent(true);
			table.defaults().padBottom(10);
			table.pad(getHeight() * 0.35f, getWidth() * 0.2f, getHeight() * 0.1f, getWidth() * 0.2f);

			table.add(title).padBottom(20);
			table.add(controlsLabel).padBottom(20).padLeft(60).colspan(2).center();
			table.row();

			table.add(musicCheckBox).left();

			//Move left
			table.add(moveLeftLabel).right().padLeft(60);
			table.add(moveLeftField).padLeft(10).left();
			table.row();

			table.add(musicVolumeTitle).center();

			//Move right
			table.add(moveRightLabel).right().padLeft(60);
			table.add(moveRightField).padLeft(10).left();
			table.row();

			table.add(musicVolumeSlider).fillX();

			//Jump
			table.add(jumpLabel).right().padLeft(60);
			table.add(jumpField).padLeft(10).left();
			table.row();

			table.add(soundEffectsCheckBox).left();

			//Fly
			table.add(flyLabel).right().padLeft(60);
			table.add(flyField).padLeft(10).left();
			table.row();

			table.add(soundEffectsVolumeTitle).center();

			//Place block/Pour
			table.add(pourLabel).right().padLeft(60);
			table.add(pourField).padLeft(10).left();
			table.row();

			table.add(soundEffectsVolumeSlider).fillX().expandX();

			//Cool/Freeze
			table.add(coolLabel).right().padLeft(60);
			table.add(coolField).padLeft(10).left();
			table.row();


			//Heat/Warm
			table.add(heatLabel).right().padLeft(60).colspan(2);
			table.add(heatField).padLeft(10).left();
			table.row();

			table.add(returnButton).size(returnButton.getWidth()+20, returnButton.getHeight()+10).bottom().expandX().left();
			table.add(resetButton).size(resetButton.getWidth() + 20, resetButton.getHeight()+10).bottom().right().colspan(2);
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
