package test.edu.chl.morf.model;

import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertTrue;

/**
 * Created by Christoffer on 2015-05-04.
 */
public class TestPlayerCharacter {

	@Test
	public void testMoveLeft(){
		PlayerCharacter player = new PlayerCharacter();
		player.moveLeft();
		assertTrue(player.isMoving() && !player.isFacingRight());
	}

	@Test
	public void testMoveRight(){
		PlayerCharacter player = new PlayerCharacter();
		player.moveRight();
		assertTrue(player.isMoving() && player.isFacingRight());
	}

	@Test
	public void testPourWater(){
		PlayerCharacter player = new PlayerCharacter(10, 10);
		Water water = player.pourWater();
		assertTrue(water != null && player.getWaterLevel() == 29);
	}
	
	@Test
	public void testDecreaseWaterLevel(){
		PlayerCharacter player = new PlayerCharacter(10, 10);
		player.setWaterLevel(2);
		player.decreaseWaterLevel();
		assertTrue(player.getWaterLevel() == 1);
		player.decreaseWaterLevel();
		assertTrue(player.isDead() == true);
	}
	
	@Test
	public void testHeatActiveBlock(){
		PlayerCharacter player = new PlayerCharacter();
		Water water = new Water(WaterState.LIQUID);
		player.setActiveBlock(water);
		player.heatActiveBlock();
		assertTrue(water.getState() == WaterState.GAS);
	}
	
	@Test
	public void testCoolActiveBlock(){
		PlayerCharacter player = new PlayerCharacter();
		Water water = new Water(WaterState.LIQUID);
		player.setActiveBlock(water);
		player.coolActiveBlock();
		assertTrue(water.getState() == WaterState.SOLID);
	}
}