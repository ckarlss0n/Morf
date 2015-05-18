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

	private boolean isKey(int keyCode, String keyName){
		return keyCode == Input.Keys.valueOf(keyName);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (isKey(keycode, keyBindings.getMoveLeftKey())) {
			gameLogic.setKeyState(keycode, true);
			gameLogic.moveLeft();
		} else if (isKey(keycode, keyBindings.getMoveRightKey())){
			gameLogic.setKeyState(keycode, true);
			gameLogic.moveRight();
		} else if(isKey(keycode, keyBindings.getJumpKey())) {
			gameLogic.setKeyState(keycode, true);
			gameLogic.jump();
		} else if(isKey(keycode, keyBindings.getFlyKey())) {
			gameLogic.fly();
		} else if(isKey(keycode, keyBindings.getPourKey())) {
			gameLogic.placeWater();
		} else if(isKey(keycode, keyBindings.getHeatKey())) {
			gameLogic.heatBlock();
		} else if(isKey(keycode, keyBindings.getCoolKey())) {
			gameLogic.coolBlock();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(isKey(keycode, keyBindings.getMoveLeftKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getMoveLeftKey()), false);
			gameLogic.moveLeft();
			gameLogic.stop();
		} else if(isKey(keycode, keyBindings.getMoveRightKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getMoveRightKey()), false);
			gameLogic.moveRight();
			gameLogic.stop();
		} else if(isKey(keycode, keyBindings.getJumpKey())) {
			gameLogic.setKeyState(Input.Keys.valueOf(keyBindings.getJumpKey()), false);
			gameLogic.stop();
		} else if(isKey(keycode, keyBindings.getFlyKey())) {
			gameLogic.stop();
		}
		return true;
	}
}
