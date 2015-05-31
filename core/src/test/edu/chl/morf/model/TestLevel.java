package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.model.blocks.Flower;
import edu.chl.morf.model.blocks.Water;

public class TestLevel {
	@Test
	public void testAddAndRemoveWater(){
		Level level = new Level(null, null, null, new ArrayList<Water>(), null, 0);
		Water water = new Water(0, 0, WaterState.LIQUID);
		level.addWater(water);
		assertTrue(level.getWaterBlocks().size() == 1);
		level.removeWater(water);
		assertTrue(level.getWaterBlocks().isEmpty());
	}
	
	@Test
	public void testPourWater(){
		Level level = new Level(null, null, new PlayerCharacter(10, 10, 10), new ArrayList<Water>(), null, 0);
		level.getPlayer().setGhostEmpty(false);
		level.pourWater();
		assertTrue(level.getWaterBlocks().isEmpty());
		level.getPlayer().setGhostEmpty(true);
		level.pourWater();
		assertTrue(level.getWaterBlocks().size() == 1);
		level.getPlayer().setActiveBlock(new Flower(0, 0));
		level.pourWater();
		assertTrue(level.isLevelWon());
	}
}
