package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.chl.morf.model.Flower;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.PlayerCharacter;
import edu.chl.morf.model.Water;

public class TestLevel {
	@Test
	public void testAddAndRemoveWater(){
		Level level = new Level(null, null, null, new ArrayList<Water>(), null);
		Water water = new Water();
		level.addWater(water);
		assertTrue(level.getWaterBlocks().size() == 1);
		level.removeWater(water);
		assertTrue(level.getWaterBlocks().isEmpty());
	}
	
	@Test
	public void testPourWater(){
		Level level = new Level(null, null, new PlayerCharacter(10, 10), new ArrayList<Water>(), null);
		level.getPlayer().setGhostEmpty(false);
		level.pourWater();
		assertTrue(level.getWaterBlocks().isEmpty());
		level.getPlayer().setGhostEmpty(true);
		level.pourWater();
		assertTrue(level.getWaterBlocks().size() == 1);
		level.getPlayer().setActiveBlock(new Flower());
		level.pourWater();
		assertTrue(level.isLevelWon());
	}
}
