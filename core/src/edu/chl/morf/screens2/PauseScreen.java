package edu.chl.morf.screens2;

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
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.main.Main;

/**
 * Created by Christoffer on 2015-05-19.
 */
public class PauseScreen extends GameScreen{
	private PauseStage stage;
	private GameScreen backgroundScreen;
	private SoundHandler soundHandler = SoundHandler.getInstance();
	private KeyBindings keyBindings = KeyBindings.getInstance();

	public PauseScreen(ScreenManager sm, GameScreen backgroundScreen){
		super(sm);
		this.stage=new PauseStage();
		this.backgroundScreen = backgroundScreen;
	}

	public void setFocus(){
		Gdx.input.setInputProcessor(stage);
	}

	private class PauseStage extends Stage {
		float scaling= 0.666f;
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
					returnToParentScreen();
					super.clicked(event, x, y);
				}
			});

			levelSelectionButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.LEVEL_SELECTION, null);
					super.clicked(event, x, y);
				}
			});

			settingsButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.OPTIONS, null);
					super.clicked(event, x, y);
				}
			});

			mainMenuButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					screenManager.pushScreen(ScreenManager.ScreenType.MAIN_MENU, null);
					super.clicked(event, x, y);
				}
			});

			table = new Table(skin);
			table.setSize(background.getWidth() * scaling, background.getHeight() * scaling);
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

	public void returnToParentScreen(){
		screenManager.popScreen();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void show () {
	}

	@Override
	public void render (float delta){
		backgroundScreen.render(0); //Render the background screen without flickering
		stage.act(delta);
		stage.draw();
		if(Gdx.input.isKeyJustPressed(keyBindings.getValue(keyBindings.getPauseKey()))){
			returnToParentScreen();
		}
	}

	@Override
	public void resize (int width, int height){
	}

	@Override
	public void pause (){}

	@Override
	public void resume (){}

	@Override
	public void hide (){}

	@Override
	public void handleInput() {}

	@Override
	public void update(float dt) {}
}
