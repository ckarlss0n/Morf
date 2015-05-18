package edu.chl.morf.handlers;

import java.io.*;

/**
 * Created by Lage on 2015-05-17.
 */
public class SettingsHandler extends FileHandler{
    private static final SettingsHandler instance = new SettingsHandler();

    public static synchronized SettingsHandler getInstance(){
        return instance;
    }

    public void write(PrintWriter printWriter){
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
        } catch (NullPointerException e){
            keyBindings.bindKeys();
        }
    }

    @Override
    public String getFilePath() {
        return "/.morf/.settings.txt";
    }
}
