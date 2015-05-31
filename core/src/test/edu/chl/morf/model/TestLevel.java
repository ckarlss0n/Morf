package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.model.blocks.Flower;
import edu.chl.morf.model.blocks.Water;

/**
 * Class for testing Level.
 * 
 * @author gustav
 */

public class TestLevel {
	private Level level;
	
	@Before
	public void initialize(){
		//Create Level with needed information
		level = new Level(null, null, new PlayerCharacter(10, 10, 10), new ArrayList<Water>(), null, 0);
	}
	
	@Test
	public void testAddAndRemoveWater(){
		Water water = new Water(0, 0, WaterState.LIQUID);
		level.addWater(water);
		assertTrue(level.getWaterBlocks().size() == 1);
		level.removeWater(water);
		assertTrue(level.getWaterBlocks().isEmpty());
	}
	
	@Test
	public void testPourWater(){
		//If ghostEmpty is false, water should not be poured
		level.getPlayer().setGhostEmpty(false);
		level.pourWater();
		assertTrue(level.getWaterBlocks().isEmpty());
		
		//If ghostEmpty is true, water should be poured
		level.getPlayer().setGhostEmpty(true);
		level.pourWater();
		assertTrue(level.getWaterBlocks().size() == 1);
		
		//If pouring water when player is inside the flower, level should be won
		level.setPlayerInsideFlower(true);
		level.pourWater();
		assertTrue(level.isLevelWon());
	}
}
