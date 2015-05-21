package edu.chl.morf.screens2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.chl.morf.handlers.HighScoreHandler;
import edu.chl.morf.handlers.LevelFactory;
import edu.chl.morf.handlers.ScreenManager;
import edu.chl.morf.handlers.ScreenManager.ScreenType;
import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.main.Main;
import edu.chl.morf.model.Level;

import java.util.ArrayList;

/**
 * Created by Lage on 2015-05-08.
 */
public class LevelSelectionScreen extends GameScreen{
    private SoundHandler soundHandler = SoundHandler.getInstance();

    private class LevelSelectionStage extends Stage{

        private class BackButton extends Image{
            private Texture backButtonTexture;
            private BackButton(){
                this.backButtonTexture = new Texture("menu/Btn_Exit.png");
                this.addListener(new ClickListener(){
                   @Override
                   public void clicked(InputEvent event, float x, float y){
                       screenManager.popScreen();
                       soundHandler.playSoundEffect(soundHandler.getButtonReturn());
                   }
                });
            }
            @Override
            public void draw(Batch batch, float parentAlpha){
                super.draw(batch,parentAlpha);
                batch.draw(backButtonTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
            }
        }

        private class LevelPreview extends Image{

            private class Star extends Image{
                private Texture starTexture;
                private Star(){
                    this.starTexture = new Texture("levelselection/level_selection_star.png");
                }
                @Override
                public void draw(Batch batch, float parentAlpha){
                    super.draw(batch,parentAlpha);
                    batch.draw(starTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
                }
            }

            ArrayList<Star> stars;
            private Texture normalTexture;
            private Texture highlightTexture;
            private boolean highlighted;
            private Level level;

            private LevelPreview(Level level) {
                super();
                this.level = level;
                this.setSize(0.3f * Main.V_WIDTH, 0.25f *  Main.V_HEIGHT);
                this.setPosition(0.2f * Main.V_WIDTH, (20 * 6 / 4) / 100f * Main.V_HEIGHT);
                this.normalTexture = new Texture("levelselection/Level_1_Thumb.png");
                this.highlightTexture = new Texture("levelselection/Level_1_Thumb_Focus.png");

                Integer levelScore = HighScoreHandler.getInstance().getHighScore(this.level);

                stars = new ArrayList<Star>();
                if(levelScore != null) {
                    for (int i = 0; i < 8; i++) {
                        if (levelScore >= (i+1) * (30 / 8)) {
                            Star star = new Star();
                            float starSize = 71f / 1920f * Main.V_WIDTH;
                            star.setSize(starSize, starSize);
                            star.setPosition(this.getX() + 19 + 42.5f * i, this.getY() + 14);
                            stars.add(star);
                        }
                    }
                }
                this.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        screenManager.pushScreen(ScreenType.PLAY, "Level_1.tmx");
                    }
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                        highlighted = true;
                        soundHandler.playSoundEffect(soundHandler.getButtonHover());
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                        highlighted = false;
                    }
                });

            }
            @Override
            public void draw(Batch batch,float parentAlpha) {
                super.draw(batch, parentAlpha);
                if(this.highlighted){
                    batch.draw(highlightTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
                }else{
                    batch.draw(normalTexture,this.getX(),this.getY(),this.getWidth(),this.getHeight());
                }
                for (Star star: stars) {
                    star.draw(batch, parentAlpha);
                }
            }
        }

        private LevelSelectionStage(){
            super();
            LevelFactory levelFactory = LevelFactory.getInstace();
            this.addActor(new LevelPreview(levelFactory.getLevel("Level_1.tmx", false)));
            BackButton backButton = new BackButton();
            backButton.setPosition(30,600);
            backButton.setSize(300,100);
            this.addActor(backButton);
        }
    }

    public LevelSelectionScreen(ScreenManager sm) {
        super(sm);
        stage = new LevelSelectionStage();
        setFocus();
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

	@Override
	public void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
