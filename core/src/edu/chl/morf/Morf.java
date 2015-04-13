package edu.chl.morf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.chl.morf.Screens.GameScreen;

public class Morf extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}