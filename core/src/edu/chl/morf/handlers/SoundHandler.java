package edu.chl.morf.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * A singleton class taking care of sound effects and music.
 * It contains paths to all sound files, and methods to play them.
 * The class also contains methods to change the volume of music and sound effects.
 * <p>
 * Created by Christoffer on 2015-05-12.
 */
public class SoundHandler {

	private static SoundHandler instance = null;

	private Sound die;
	private Sound pour;
	private Sound freeze;
	private Sound heat;
	private Sound buttonForward;
	private Sound buttonReturn;
	private Sound saveSettings;
	private Sound buttonHover;
	private Music music;

	private float musicVolume = 1f;
	private float soundEffectsVolume = 1f;
	private boolean isMusicEnabled = true;
	private boolean isSoundEffectsEnabled = true;

	private SoundHandler() {
		//Game sounds
		die = bindPath("die.mp3");
		pour = bindPath("pour.mp3");
		freeze = bindPath("freeze.wav");
		heat = bindPath("heat.wav");

		//UI sounds
		buttonForward = bindPath("buttonForward.ogg");
		buttonReturn = bindPath("buttonReturn.ogg");
		saveSettings = bindPath("saveSettings.ogg");
		buttonHover = bindPath("buttonHover.wav");

		music = Gdx.audio.newMusic(Gdx.files.internal("Morf_Music_Theme.mp3"));
		music.setLooping(true);
		setMusicVolume(musicVolume);
	}

	public static synchronized SoundHandler getInstance() {
		if (instance == null) {
			instance = new SoundHandler();
		}
		return instance;
	}

	public void enableMusic() {
		isMusicEnabled = true;
		playMusic();
	}

	public void enabledSoundEffects() {
		isSoundEffectsEnabled = true;
	}

	public boolean isMusicEnabled() {
		return isMusicEnabled;
	}

	public boolean isSoundEffectsEnabled() {
		return isSoundEffectsEnabled;
	}

	public float getMusicVolume() {
		return music.getVolume();
	}

	public void setMusicVolume(float volume) {
		music.setVolume(volume);
	}

	public float getSoundEffectsVolume() {
		return soundEffectsVolume;
	}

	public void setSoundEffectsVolume(float volume) {
		soundEffectsVolume = volume;
	}

	private Sound bindPath(String path) {
		return Gdx.audio.newSound(Gdx.files.internal("sfx/" + path));
	}

	public void muteSoundEffects() {
		isSoundEffectsEnabled = false;
	}

	public void muteMusic() {
		isMusicEnabled = false;
		music.pause();
	}

	public void playSoundEffect(Sound sound) {
		if (isSoundEffectsEnabled) {
			sound.play(soundEffectsVolume);
		}
	}

	public void playMusic() {
		if (isMusicEnabled) {
			music.play();
		}
	}

	public Sound getDie() {
		return die;
	}

	public Sound getPour() {
		return pour;
	}

	public Sound getFreeze() {
		return freeze;
	}

	public Sound getHeat() {
		return heat;
	}

	public Sound getButtonForward() {
		return buttonForward;
	}

	public Sound getButtonReturn() {
		return buttonReturn;
	}

	public Sound getSaveSettings() {
		return saveSettings;
	}

	public Sound getButtonHover() {
		return buttonHover;
	}

}
