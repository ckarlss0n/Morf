package edu.chl.morf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.chl.morf.Morf;
import static edu.chl.morf.Constants.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GAME_WIDTH;
		config.height = GAME_HEIGHT;
		new LwjglApplication(new Morf(), config);
	}
}