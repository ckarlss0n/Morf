package edu.chl.morf.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameController extends InputAdapter{

	private GameLogic gameLogic;

	public GameController(GameLogic gameLogic){
		this.gameLogic = gameLogic;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.LEFT:
				gameLogic.moveLeft();
				break;
			case Input.Keys.RIGHT:
				gameLogic.moveRight();
				break;
			case Input.Keys.UP:
				gameLogic.jump();
				break;
			case Input.Keys.SPACE:
				gameLogic.resetGame();
				break;
			case Input.Keys.SHIFT_LEFT:
				gameLogic.fly();
				break;
			case Input.Keys.X:
				gameLogic.placeWater();
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		gameLogic.stop();
		return true;
	}
}
