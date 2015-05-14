package edu.chl.morf.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Christoffer on 2015-05-12.
 */
public class SoundHandler {

	private static SoundHandler instance = null;

	private Sound hit;
	private Sound die;
	private Sound jump;
	private Sound walkLeft;
	private Sound walkRight;
	private Sound swim;
	private Sound pour;
	private Sound freeze;
	private Sound heat;
	private Sound unfreeze;
	private Sound buttonForward;
	private Sound buttonReturn;
	private Sound saveSettings;
	private Sound buttonHover;
	private Music music;

	private float musicVolume = 1f;
	private float soundEffectsVolume = 1f;
	private boolean isMusicEnabled = true;
	private boolean isSoundEffectsEnabled = true;

	private SoundHandler(){
		//Game sounds
		hit = bindPath("hit.mp3");
		die = bindPath("die.mp3");
		//jump = bindPath("jump.mp3");
		walkLeft = bindPath("walkLeft.mp3");
		walkRight = bindPath("walkRight.mp3");
		swim = bindPath("swim.mp3");
		pour = bindPath("pour.mp3");
		freeze = bindPath("freeze.wav");
		heat = bindPath("heat.wav");
		unfreeze = bindPath("unfreeze.wav");

		//UI sounds
		buttonForward = bindPath("buttonForward.ogg");
		buttonReturn = bindPath("buttonReturn.ogg");
		saveSettings = bindPath("saveSettings.ogg");
		buttonHover = bindPath("buttonHover.wav");

		music = Gdx.audio.newMusic(Gdx.files.internal("Morf_Music_Theme.mp3"));
		music.setLooping(true);
		setMusicVolume(musicVolume);
	}

	public static synchronized SoundHandler getInstance(){
		if(instance == null){
			instance = new SoundHandler();
		}
		return instance;
	}

	public void enableMusic(){
		isMusicEnabled = true;
		playMusic();
	}

	public void enabledSoundEffects(){
		isSoundEffectsEnabled = true;
	}

	public boolean isMusicEnabled(){
		return isMusicEnabled;
	}

	public boolean isSoundEffectsEnabled(){
		return isSoundEffectsEnabled;
	}

	public float getMusicVolume(){
		return music.getVolume();
	}

	public float getSoundEffectsVolume(){
		return soundEffectsVolume;
	}

	private Sound bindPath(String path){
		return Gdx.audio.newSound(Gdx.files.internal("sfx/"+path));
	}

	public void setMusicVolume(float volume){
		music.setVolume(volume);
	}

	public void setSoundEffectsVolume(float volume) {
		soundEffectsVolume = volume;
	}

	public void muteSoundEffects() {
		isSoundEffectsEnabled = false;
	}

	public void muteMusic(){
		isMusicEnabled = false;
		music.pause();
	}

	public void playSoundEffect(Sound sound){
		if(isSoundEffectsEnabled) {
			sound.play(soundEffectsVolume);
		}
	}

	public void playMusic(){
		if(isMusicEnabled) {
			music.play();
		}
	}

	public void stopMusic(){
		music.stop();
	}

	public Sound getHit() {
		return hit;
	}

	public Sound getDie() {
		return die;
	}

	public Sound getJump() {
		return jump;
	}

	public Sound getWalkLeft() {
		return walkLeft;
	}

	public Sound getWalkRight() {
		return walkRight;
	}

	public Sound getSwim() {
		return swim;
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

	public Sound getUnfreeze() {
		return unfreeze;
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
