package test.edu.chl.morf.model;

import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.model.blocks.Water;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertTrue;

/**
 * Class for testing PlayerCharacter.
 * 
 * @author gustav
 */

public class TestPlayerCharacter {
	private PlayerCharacter player;
	private Water water;
	
	@Before
	public void initialize(){
		//Create player character with waterAmount 2
		player = new PlayerCharacter(0, 0, 2);

		//Set active block to a Water for testing of heating and cooling
		water = new Water(0, 0, WaterState.LIQUID);
		player.setActiveBlock(water);
	}

	@Test
	public void testMoveLeft(){
		player.moveLeft();
		assertTrue(player.isMoving() && !player.isFacingRight());
	}

	@Test
	public void testMoveRight(){
		player.moveRight();
		assertTrue(player.isMoving() && player.isFacingRight());
	}

	@Test
	public void testPourWater(){
		Water water = player.pourWater();
		//Check that a Water was returned and used by player.
		assertTrue(water != null && player.getWaterAmount() == 1);
	}
	
	@Test
	public void testDecreaseWaterLevel(){
		player.decreaseWaterAmount();
		assertTrue(player.getWaterAmount() == 1);
		player.decreaseWaterAmount();
		//Check that player dies when water amount is decreased to 0.
		assertTrue(player.isDead());
	}
	
	@Test
	public void testHeatActiveBlock(){
		player.heatActiveBlock();
		//Check that liquid water was vaporized.
		assertTrue(water.getState() == WaterState.GAS);
	}
	
	@Test
	public void testCoolActiveBlock(){
		player.coolActiveBlock();
		//Check that liquid water was frozen.
		assertTrue(water.getState() == WaterState.SOLID);
	}
}
