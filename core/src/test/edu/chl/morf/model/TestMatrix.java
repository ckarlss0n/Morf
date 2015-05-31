package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import edu.chl.morf.model.LevelObject;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.TileType;

/**
 * Class for testing Matrix.
 * 
 * @author gustav
 */

public class TestMatrix {

	@Test
	public void testAddLevelObject() {
		//Create Matrix with size 5x5
		Matrix matrix  = new Matrix(5, 5);
		
		//Create and add levelObject to a valid position in the Matrix
		Point2D.Float pos = new Point2D.Float(1, 1);
		LevelObject object1 = new LevelObject(TileType.GROUND, pos);
		matrix.addLevelObject(object1);
		
		//Check that the levelObject is now in the Matrix
		assertTrue(matrix.getLevelObjects().get(0) == object1);
		
		//Create and add levelObject to a non-valid position in the Matrix
		pos.setLocation(10, 10);
		LevelObject object2 = new LevelObject(TileType.GROUND, pos);
		matrix.addLevelObject(object2);
		
		//Check that no levelObject was added to the Matrix (size is still 1)
		assertTrue(matrix.getLevelObjects().size() == 1);
	}

}
