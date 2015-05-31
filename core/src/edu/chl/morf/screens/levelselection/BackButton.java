package edu.chl.morf.screens.levelselection;

import com.badlogic.gdx.graphics.g2d.Batch;
import edu.chl.morf.handlers.SoundHandler;
import edu.chl.morf.screens.ScreenManager;

/**
 * Created by Lage on 2015-05-21.
 */
public class BackButton extends SelectionComponent {
    private ScreenManager screenManager = ScreenManager.getInstance();
    private SoundHandler soundHandler = SoundHandler.getInstance();
    public BackButton(){
        super("levelselection/Btn_Return.png", "levelselection/Btn_Return_Focus.png");

    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
    }

    @Override
    public void clickAction() {
        screenManager.popScreen();
        soundHandler.playSoundEffect(soundHandler.getButtonReturn());
    }
}
