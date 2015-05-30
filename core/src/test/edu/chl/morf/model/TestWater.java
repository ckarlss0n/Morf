package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

public class TestWater {

	@Test
    public void testSetPosition(){
    	//Create Water with position (1, 1)
    	Water water = new Water(1, 1, WaterState.LIQUID);
        //Set position to (2, 3)
    	water.setPosition(2, 3);
    	//Check that position is (2, 3)
        assertTrue(water.getPosition().x == 2 && water.getPosition().y == 3);
    }
    
	@Test
	public void testHeat() {
		//Create solid Water (ice)
		Water water = new Water(WaterState.SOLID);
		water.heat();
		//Check that ice has melted to liquid water
		assertTrue(water.getState() == WaterState.LIQUID);
		water.heat();
		//Check that water has evaporated
		assertTrue(water.getState() == WaterState.GAS);
	}
	
	@Test
	public void testCool(){
		//Create Water in gas form (vapor)
		Water water = new Water(WaterState.GAS);
		water.cool();
		//Check that vapor has condensed
		assertTrue(water.getState() == WaterState.LIQUID);
		water.cool();
		//Check that water has frozen
		assertTrue(water.getState() == WaterState.SOLID);
	}

}
