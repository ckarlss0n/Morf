package edu.chl.morf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.chl.morf.Main;

import static edu.chl.morf.Constants.*;

/**
 * This class is used to run Morf in a desktop environment.
 * <p>
 * <li><b>Responsible for: </b>
 * Starting and running Morf in a desktop environment.
 * <p>
 * <li><b>Used by: </b>
 * None.
 * <p>
 * <li><b>Using: </b>
 * <li>{@link edu.chl.morf.Constants}
 * <li>{@link Main}
 * <p>
 * @author Christoffer Karlsson
 */
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