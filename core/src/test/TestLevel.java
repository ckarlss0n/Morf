package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import edu.chl.morf.model.Level;
import edu.chl.morf.model.Water;

public class TestLevel {
	@Test
	public void testAddWater(){
		Level level = new Level(null);
		level.addWater(new Water(0, 0));
		assertTrue(level.getWaterBlocks().size() == 1);
	}
	
	@Test
	public void testPourWater(){
		Level level = new Level(null);
		level.pourWater();
		assertTrue(level.getWaterBlocks().get(0).getPosition().equals(level.getPlayer().getPosition()));
	}
}
