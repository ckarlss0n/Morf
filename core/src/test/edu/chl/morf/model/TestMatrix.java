package test.edu.chl.morf.model;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import edu.chl.morf.model.LevelObject;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.TileType;

public class TestMatrix {

	@Test
	public void testAddLevelObject() {
		Matrix matrix  = new Matrix(5, 5);
		Point2D.Float pos = new Point2D.Float(1, 1);
		LevelObject object = new LevelObject(TileType.GROUND, pos);
		matrix.addLevelObject(object);
		assertTrue(matrix.getLevelObjects().get(matrix.getLevelObjects().size() - 1).equals(object));
		pos.setLocation(10, 10);
		object = new LevelObject(TileType.GROUND, pos);
		matrix.addLevelObject(object);
		assertTrue(matrix.getLevelObjects().size() == 1);
	}

}
