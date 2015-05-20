package edu.chl.morf.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import edu.chl.morf.handlers.KeyBindings;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameController extends InputAdapter{

	private GameLogic gameLogic;
	private KeyBindings keyBindings = KeyBindings.getInstance();

	public GameController(GameLogic gameLogic){
		this.gameLogic = gameLogic;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keyBindings.isKey(keycode, keyBindings.getMoveLeftKey())) {
			gameLogic.setKeyState(keycode, true);
			gameLogic.moveLeft();
		} else if (keyBindings.isKey(keycode, keyBindings.getMoveRightKey())){
			gameLogic.setKeyState(keycode, true);
			gameLogic.moveRight();
		} else if(keyBindings.isKey(keycode, keyBindings.getJumpKey())) {
			gameLogic.setKeyState(keycode, true);
			gameLogic.jump();
		} else if(keyBindings.isKey(keycode, keyBindings.getFlyKey())) {
			gameLogic.fly();
		} else if(keyBindings.isKey(keycode, keyBindings.getPourKey())) {
			gameLogic.placeWater();
		} else if(keyBindings.isKey(keycode, keyBindings.getHeatKey())) {
			gameLogic.heatBlock();
		} else if(keyBindings.isKey(keycode, keyBindings.getCoolKey())) {
			gameLogic.coolBlock();
		} else if(keyBindings.isKey(keycode, keyBindings.getPauseKey())){
			gameLogic.pauseGame();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keyBindings.isKey(keycode, keyBindings.getMoveLeftKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getMoveLeftKey()), false);
			gameLogic.moveLeft();
			gameLogic.stop();
		} else if(keyBindings.isKey(keycode, keyBindings.getMoveRightKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getMoveRightKey()), false);
			gameLogic.moveRight();
			gameLogic.stop();
		} else if(keyBindings.isKey(keycode, keyBindings.getJumpKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getJumpKey()), false);
			gameLogic.stop();
		} else if(keyBindings.isKey(keycode, keyBindings.getFlyKey())) {
			gameLogic.stop();
		}
		return true;
	}
}
