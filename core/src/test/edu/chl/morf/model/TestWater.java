package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import edu.chl.morf.model.WaterState;
import edu.chl.morf.model.blocks.Water;

/**
 * Class for testing Water.
 * 
 * @author gustav
 */

public class TestWater {
	private Water water;
	
	@Before
	public void initialize(){
		//Create a Water with solid state.
		water = new Water(0, 0, WaterState.SOLID);
	}

	@Test
    public void testSetPosition(){
        //Set position to (2, 3)
    	water.setPosition(2, 3);
    	//Check that position is (2, 3)
        assertTrue(water.getPosition().x == 2 && water.getPosition().y == 3);
    }
    
	@Test
	public void testHeatAndCool() {
		water.heat();
		//Check that ice has melted to liquid water
		assertTrue(water.getState() == WaterState.LIQUID);
		water.heat();
		//Check that water has evaporated
		assertTrue(water.getState() == WaterState.GAS);
		water.cool();
		//Check that vapor has condensed
		assertTrue(water.getState() == WaterState.LIQUID);
		water.cool();
		//Check that water has frozen
		assertTrue(water.getState() == WaterState.SOLID);
	}
}
