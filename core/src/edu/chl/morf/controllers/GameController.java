package edu.chl.morf.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christoffer on 2015-05-06.
 */
public class GameController extends InputAdapter{

	private Map<Integer, Boolean> pressedKeys;

	public GameController(){
		pressedKeys = new HashMap<Integer, Boolean>();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		setKeys(keycode, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		setKeys(keycode, false);
		return true;
	}

	public Map<Integer, Boolean> getPressedKeys(){
		return pressedKeys;
	}

	public void setKeys(int keycode, boolean bool){
		switch (keycode) {
			case Input.Keys.LEFT:
				pressedKeys.put(Input.Keys.LEFT, bool);
				break;
			case Input.Keys.RIGHT:
				pressedKeys.put(Input.Keys.RIGHT, bool);
				break;
			case Input.Keys.UP:
				pressedKeys.put(Input.Keys.UP, bool);
				break;
			case Input.Keys.SPACE:
				pressedKeys.put(Input.Keys.SPACE, bool);
				break;
			case Input.Keys.SHIFT_LEFT:
				pressedKeys.put(Input.Keys.SHIFT_LEFT, bool);
				break;
			case Input.Keys.X:
				pressedKeys.put(Input.Keys.X, bool);
				break;
			case Input.Keys.C:
				pressedKeys.put(Input.Keys.C, bool);
				break;
		}
	}

}
