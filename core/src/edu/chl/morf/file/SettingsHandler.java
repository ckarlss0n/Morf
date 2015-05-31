package edu.chl.morf.file;

import edu.chl.morf.handlers.KeyBindings;
import edu.chl.morf.handlers.SoundHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A singleton class for handling the saving and reading of settings.
 * Extends the abstract class FileHandler.
 * <p>
 * Created by Lage on 2015-05-17.
 */
public class SettingsHandler extends AbstractFileHandler implements FileHandler{
	public static final String FILE_PATH = "/.morf/.settings.txt";

	/*
	Each setting value is written to the settings text file, on a separate row, in a known order.
	 */
	@Override
	public void write(PrintWriter printWriter) {
		SoundHandler soundHandler = SoundHandler.getInstance();
		KeyBindings keyBindings = KeyBindings.getInstance();

		//Sound preferences
		printWriter.println(soundHandler.getMusicVolume());
		printWriter.println(soundHandler.getSoundEffectsVolume());
		printWriter.println(soundHandler.isMusicEnabled());
		printWriter.println(soundHandler.isSoundEffectsEnabled());

		//Key bindings
		printWriter.println(keyBindings.getMoveLeftKey());
		printWriter.println(keyBindings.getMoveRightKey());
		printWriter.println(keyBindings.getJumpKey());
		printWriter.println(keyBindings.getFlyKey());
		printWriter.println(keyBindings.getPourKey());
		printWriter.println(keyBindings.getCoolKey());
		printWriter.println(keyBindings.getHeatKey());
	}

	/*
	 Each row contains a value of a setting.
	 The order is the same as the one used in the write-method.
	 On each row a method is called, updating the setting with the read value.
	 */
	@Override
	public void read(BufferedReader bufferedReader) throws IOException {
		KeyBindings keyBindings = KeyBindings.getInstance();
		try {
			SoundHandler soundHandler = SoundHandler.getInstance();
			String line = bufferedReader.readLine();
			if (line != null) {
				try {
					soundHandler.setMusicVolume(Float.parseFloat(line));
				} catch (NumberFormatException e) {
					System.out.println("Unable to parse string");
				}
				line = bufferedReader.readLine();
				try {
					soundHandler.setSoundEffectsVolume(Float.parseFloat(line));
				} catch (NumberFormatException e) {
					System.out.println("Unable to parse string");
				}
				line = bufferedReader.readLine();
				if (line.equals("true")) {
					soundHandler.enableMusic();
				} else {
					soundHandler.muteMusic();
				}
				line = bufferedReader.readLine();
				if (line.equals("true")) {
					soundHandler.enabledSoundEffects();
				} else {
					soundHandler.muteSoundEffects();
				}

				//Key bindings
				keyBindings.setMoveLeftKey(bufferedReader.readLine());
				keyBindings.setMoveRightKey(bufferedReader.readLine());
				keyBindings.setJumpKey(bufferedReader.readLine());
				keyBindings.setFlyKey(bufferedReader.readLine());
				keyBindings.setPourKey(bufferedReader.readLine());
				keyBindings.setCoolKey(bufferedReader.readLine());
				keyBindings.setHeatKey(bufferedReader.readLine());
				keyBindings.bindKeys();
			}
		} catch (NullPointerException e) {
			keyBindings.bindKeys();
		}
	}

	@Override
	public String getFilePath() {
		return FILE_PATH;
	}
}
