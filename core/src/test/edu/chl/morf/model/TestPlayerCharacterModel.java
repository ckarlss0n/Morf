package test.edu.chl.morf.model;

import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.Water;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christoffer on 2015-05-04.
 */
public class TestPlayerCharacterModel {
	static PlayerCharacterModel playerCharacterModel;

	@Test
	public void testMoveLeft(){
		playerCharacterModel = new PlayerCharacterModel();
		playerCharacterModel.moveLeft();
		assertTrue(playerCharacterModel.isMoving() && !playerCharacterModel.isFacingRight());
	}

	@Test
	public void testMoveRight(){
		playerCharacterModel = new PlayerCharacterModel();
		playerCharacterModel.moveRight();
		assertTrue(playerCharacterModel.isMoving() && playerCharacterModel.isFacingRight());
	}

	@Test
	public void testPourWater(){
		playerCharacterModel = new PlayerCharacterModel(10, 10);
		Point point = new Point(10, 10);
		Water water = new Water(point);
		assertTrue(water.getPosition().equals(playerCharacterModel.pourWater().getPosition())); //Returns a water block
	}
}
