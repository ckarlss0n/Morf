package edu.chl.morf.handlers;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Christoffer on 2015-05-17.
 */
public class KeyBindings {

	private static KeyBindings instance = null;

	private String MOVE_LEFT_KEY;
	private String MOVE_RIGHT_KEY;
	private String JUMP_KEY;
	private String FLY_KEY;
	private String POUR_KEY;
	private String COOL_KEY;
	private String HEAT_KEY;

	private String MOVE_LEFT_KEY_DEFAULT;
	private String MOVE_RIGHT_KEY_DEFAULT;
	private String JUMP_KEY_DEFAULT;
	private String FLY_KEY_DEFAULT;
	private String POUR_KEY_DEFAULT;
	private String COOL_KEY_DEFAULT;
	private String HEAT_KEY_DEFAULT;

	public List<String> usedKeys = new LinkedList<String>();

	private KeyBindings(){
		MOVE_LEFT_KEY_DEFAULT = "Left";
		MOVE_LEFT_KEY = MOVE_LEFT_KEY_DEFAULT;

		MOVE_RIGHT_KEY_DEFAULT = "Right";
		MOVE_RIGHT_KEY = MOVE_RIGHT_KEY_DEFAULT;

		JUMP_KEY_DEFAULT = "Up";
		JUMP_KEY = JUMP_KEY_DEFAULT;

		FLY_KEY_DEFAULT = "L-Shift";
		FLY_KEY = FLY_KEY_DEFAULT;

		POUR_KEY_DEFAULT = "X";
		POUR_KEY = POUR_KEY_DEFAULT;

		COOL_KEY_DEFAULT = "Z";
		COOL_KEY = COOL_KEY_DEFAULT;

		HEAT_KEY_DEFAULT = "C";
		HEAT_KEY = HEAT_KEY_DEFAULT;
	}

	public static synchronized KeyBindings getInstance(){
		if(instance == null){
			instance = new KeyBindings();
		}
		return instance;
	}

	public void bindKeys(){
		usedKeys.add(MOVE_LEFT_KEY);
		usedKeys.add(MOVE_RIGHT_KEY);
		usedKeys.add(JUMP_KEY);
		usedKeys.add(FLY_KEY);
		usedKeys.add(POUR_KEY);
		usedKeys.add(COOL_KEY);
		usedKeys.add(HEAT_KEY);
	}

	public List<String> getUsedKeys(){
		return usedKeys;
	}

	public boolean isUsed(String key){
		return usedKeys.contains(key);
	}

	public void removeKey(String key){
		usedKeys.remove(key);
	}

	public void addKey(String key){
		usedKeys.add(key);
	}

	public void setMoveLeftKey(String key){
		MOVE_LEFT_KEY = key;
	}

	public void setMoveRightKey(String key){
		MOVE_RIGHT_KEY = key;
	}

	public void setJumpKey(String key){
		JUMP_KEY = key;
	}

	public void setFlyKey(String key){
		FLY_KEY = key;
	}

	public void setPourKey(String key){
		POUR_KEY = key;
	}

	public void setCoolKey(String key){
		COOL_KEY = key;
	}

	public void setHeatKey(String key){
		HEAT_KEY = key;
	}

	public void setKey(String keyName, String keyValue){
		switch(keyName){
			case "MOVE_LEFT_KEY":
				setMoveLeftKey(keyValue);
				break;
			case "MOVE_RIGHT_KEY":
				setMoveRightKey(keyValue);
				break;
			case "JUMP_KEY":
				setJumpKey(keyValue);
				break;
			case "FLY_KEY":
				setFlyKey(keyValue);
				break;
			case "POUR_KEY":
				setPourKey(keyValue);
				break;
			case "COOL_KEY":
				setCoolKey(keyValue);
				break;
			case "HEAT_KEY":
				setHeatKey(keyValue);
				break;
		}
	}

	public String getMoveLeftKey() {
		return MOVE_LEFT_KEY;
	}

	public String getMoveRightKey() {
		return MOVE_RIGHT_KEY;
	}

	public String getJumpKey(){
		return JUMP_KEY;
	}

	public String getFlyKey(){
		return FLY_KEY;
	}

	public String getPourKey() {
		return POUR_KEY;
	}

	public String getCoolKey() {
		return COOL_KEY;
	}

	public String getHeatKey() {
		return HEAT_KEY;
	}

	public void resetDefaults(){
		setMoveLeftKey(MOVE_LEFT_KEY_DEFAULT);
		setMoveRightKey(MOVE_RIGHT_KEY_DEFAULT);
		setJumpKey(JUMP_KEY_DEFAULT);
		setFlyKey(FLY_KEY_DEFAULT);
		setPourKey(POUR_KEY_DEFAULT);
		setCoolKey(COOL_KEY_DEFAULT);
		setHeatKey(HEAT_KEY_DEFAULT);
		usedKeys.clear();
		bindKeys();
	}
}
