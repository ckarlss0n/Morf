package edu.chl.morf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.chl.morf.handlers.KeyBindings;
import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.main.Main;
import static edu.chl.morf.main.Main.SCALING;

/**
 * This class represents the pause screen which is shown upon pausing the game.
 * The pause screen overlays the background screen, which in Morf is the PlayScreen.
 * The pause screen can be seen as a minimized version of the main menu.
 * <p>
 * Created by Christoffer on 2015-05-19.
 */
public class PauseScreen extends GameScreen {
	private PauseStage stage;
	private GameScreen backgroundScreen; //The background screen needs to be rendered as well
	private SoundHandler soundHandler = SoundHandler.getInstance();
	private KeyBindings keyBindings = KeyBindings.getInstance();

	public PauseScreen(ScreenManager sm, GameScreen backgroundScreen) {
		super(sm);
		this.stage = new PauseStage();
		this.backgroundScreen = backgroundScreen;
	}

	public void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}

	//Return to the screen shown behind the pause screen, and close the pause screen
	public void returnToParentScreen() {
		screenManager.popScreen();
	}

	@Override
	public void render(float delta) {
		backgroundScreen.render(0); //Delta zero makes the background screen render without flickering
		stage.act(delta);
		stage.draw();
		if (Gdx.input.isKeyJustPressed(keyBindings.getValue(keyBindings.getPauseKey()))) {
			returnToParentScreen();
		}
	}

	private class PauseStage extends Stage {
		private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		private Image background;
		private Table table;
		private TextButton resumeButton;
		private TextButton levelSelectionButton;
		private TextButton settingsButton;
		private TextButton mainMenuButton;

		public PauseStage() {
			Gdx.input.setInputProcessor(this);
			soundHandler.playMusic();
			background = new Image(new Texture("pauseBackground.png"));

			resumeButton = new TextButton("Resume", skin);
			levelSelectionButton = new TextButton("Level Selection", skin);
			settingsButton = new TextButton("Settings", skin);
			mainMenuButton = new TextButton("Main Menu", skin);

			resumeButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					returnToParentScreen();    //Close the pause screen, and return to the screen in the background (PlayScreen)
					super.clicked(event, x, y);
				}
			});

			levelSelectionButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.LEVEL_SELECTION, null); //Go to level selection
					super.clicked(event, x, y);
				}
			});

			settingsButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.OPTIONS, null); //Go to options
					super.clicked(event, x, y);
				}
			});

			mainMenuButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.MAIN_MENU, null); //Go to main menu
					super.clicked(event, x, y);
				}
			});

			table = new Table(skin);
			table.setSize(background.getWidth() * SCALING, background.getHeight() * SCALING);
			table.setPosition(Main.V_WIDTH / 2 - table.getWidth() / 2, Main.V_HEIGHT / 2 - table.getHeight() / 2);
			table.setBackground(background.getDrawable());
			table.defaults().padBottom(45).expandX().fillX().padLeft(30).padRight(30).height(50);

			table.add(resumeButton).row();
			table.add(levelSelectionButton).row();
			table.add(settingsButton).row();
			table.add(mainMenuButton).padBottom(0).row();

			this.addActor(table);
		}
	}
}
