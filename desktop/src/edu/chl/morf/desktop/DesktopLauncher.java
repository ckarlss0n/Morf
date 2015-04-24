package edu.chl.morf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.chl.morf.Main;

import static edu.chl.morf.Constants.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");	//Borderless
		config.width = GAME_WIDTH;
		config.height = GAME_HEIGHT;
        config.fullscreen = FULLSCREEN;
		new LwjglApplication(new Main(), config);
	}
}