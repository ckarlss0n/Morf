package test.edu.chl.morf.model;

import static edu.chl.morf.Constants.WORLD_GRAVITY;
import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.handlers.BodyFactory;

public class TestBodyFactory {

	@Test
	public void testCreatePlayerBody() {
		BodyFactory bf = new BodyFactory();
		Vector2 playerPos = new Vector2(0, 0);
		Body body = bf.createPlayerBody(new World(new Vector2(0, 0), true), playerPos);

		assertTrue(body != null);
	}

}
