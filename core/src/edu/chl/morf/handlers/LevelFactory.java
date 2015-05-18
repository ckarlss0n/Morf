package edu.chl.morf.handlers;

import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.Constants.PPM;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.chl.morf.model.Flower;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.LevelObject;
import edu.chl.morf.model.Matrix;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.model.TileType;
import edu.chl.morf.model.Water;
import edu.chl.morf.model.WaterState;

public class LevelFactory {

	private TiledMap tileMap;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer spikeLayer;
	private MapLayer waterLayer;
	private MapLayer iceLayer;
	private MapLayer vaporLayer;
	private MapLayer flowerLayer;
	
	public static float TILE_SIZE;

	public Level getLevel(String name){

		tileMap = new TmxMapLoader().load(LEVEL_PATH + name);
		groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Ground");
		spikeLayer = (TiledMapTileLayer) tileMap.getLayers().get("Spikes");
		waterLayer = tileMap.getLayers().get("Water");
		TILE_SIZE = groundLayer.getTileHeight();

		Matrix matrix = new Matrix(groundLayer.getHeight(), groundLayer.getWidth());
		ArrayList<Water> waterBlocks = new ArrayList<Water>();
		Flower flower;

		if (groundLayer != null){
			for (int row = 0; row < groundLayer.getHeight(); row++) {
				for (int col = 0; col < groundLayer.getWidth(); col++) {
					TiledMapTileLayer.Cell cell = groundLayer.getCell(col, row);
					
					if (cell == null) continue;
					if (cell.getTile() == null) continue;

					matrix.addLevelObject(new LevelObject(TileType.GROUND, new Point2D.Float(row, col)));
				}
			}
		}
		
		if (spikeLayer != null){
			for (int row = 0; row < spikeLayer.getHeight(); row++){
				for (int col = 0; col < spikeLayer.getWidth(); col++) {
					TiledMapTileLayer.Cell cell = spikeLayer.getCell(col, row);
					
					if (cell == null) continue;
					if (cell.getTile() == null) continue;

					matrix.addLevelObject(new LevelObject(TileType.SPIKES, new Point2D.Float(row, col)));
				}
			}
		}

		if (waterLayer != null){
			for (MapObject water : waterLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)water.getProperties().get("x") - TILE_SIZE / 2,
						(Float)water.getProperties().get("y") - TILE_SIZE / 2);
				waterBlocks.add(new Water(position, WaterState.LIQUID));
			}
		}
		
		if (flowerLayer != null){
			for (MapObject flowerObject : flowerLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)flowerObject.getProperties().get("x") - TILE_SIZE / 2,
						(Float)flowerObject.getProperties().get("y") - TILE_SIZE / 2);
				flower = new Flower(position);
			}
		}

		PlayerCharacterModel player = new PlayerCharacterModel(500, 500);
		return new Level(matrix, name, player, waterBlocks);
	}
}
