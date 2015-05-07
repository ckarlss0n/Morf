package edu.chl.morf.handlers;

import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.Constants.PPM;

import java.awt.Point;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.chl.morf.model.Level;
import edu.chl.morf.model.LevelObject;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.TileType;

public class LevelFactory {

	private TiledMap tileMap;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer spikeLayer;

	public Level getLevel(String name){

		tileMap = new TmxMapLoader().load(LEVEL_PATH + name);
		groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");

		Matrix matrix = new Matrix(groundLayer.getHeight(), groundLayer.getWidth());

		for (int row = 0; row < groundLayer.getHeight(); row++) {
			for (int col = 0; col < groundLayer.getWidth(); col++) {
				TiledMapTileLayer.Cell cell = groundLayer.getCell(col, row);

				if (cell == null) continue;
				if (cell.getTile() == null) continue;

				matrix.getObjects().add(new LevelObject(TileType.GROUND, new Point(groundLayer.getHeight() - 1 - row, col)));
			}
		}
		return new Level(matrix, name);

	}
}
