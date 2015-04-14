package edu.chl.morf;

import com.badlogic.gdx.Game;
import edu.chl.morf.Screens.GameScreen;

public class Morf extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}