package test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.chl.morf.model.Level;
import edu.chl.morf.model.Water;

public class TestLevel {
	@Test
	public void testAddWater(){
		Level level = new Level(null);
		level.addWater(new Water());
		assertTrue(level.getWaterBlocks().size() == 1);
	}
}
