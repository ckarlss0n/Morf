package test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

public class TestWater {

	@Test
	public void testHeat() {
		Water water = new Water(WaterState.SOLID);
		water.heat();
		assertTrue(water.getState() == WaterState.LIQUID);
		water.heat();
		assertTrue(water.getState() == WaterState.GAS);
	}
	
	@Test
	public void testCool(){
		Water water = new Water(WaterState.GAS);
		water.cool();
		assertTrue(water.getState() == WaterState.LIQUID);
		water.cool();
		assertTrue(water.getState() == WaterState.SOLID);
	}

}
