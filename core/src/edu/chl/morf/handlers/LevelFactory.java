package edu.chl.morf.handlers;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import edu.chl.morf.model.*;
import edu.chl.morf.model.blocks.Flower;
import edu.chl.morf.model.blocks.Water;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class used for creating new levels.
 * The class reads different layers from a map created in Tiled Map Editor.
 * Each layer is converted to level objects and placed in a matrix, which is used when creating a new level.
 * The class also creates water blocks, the flower block, and the player character.
 */
public class LevelFactory {

	private TiledMap tileMap;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer spikeLayer;
	private MapLayer waterLayer;
	private MapLayer iceLayer;
	private MapLayer vaporLayer;
	private MapLayer flowerLayer;
	private MapLayer playerLayer;
    private static LevelFactory instance = new LevelFactory();
    private Map<String,Level> nameLevelMap;
	
	public static float TILE_SIZE;

    private LevelFactory(){
        nameLevelMap = new HashMap<String, Level>();
    }

    public static LevelFactory getInstance(){
        return instance;
    }

	public Level getLevel(String name, boolean reset){

		tileMap = new TmxMapLoader().load("levels/" + name);
		groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Ground");
		spikeLayer = (TiledMapTileLayer) tileMap.getLayers().get("Spikes");
		waterLayer = tileMap.getLayers().get("Water");
		iceLayer = tileMap.getLayers().get("Ice");
		vaporLayer = tileMap.getLayers().get("Vapor");
		flowerLayer = tileMap.getLayers().get("Flower");
		playerLayer = tileMap.getLayers().get("Player"); //Used to get the starting position of the player character
		TILE_SIZE = groundLayer.getTileHeight();

		Matrix matrix = new Matrix(groundLayer.getHeight(), groundLayer.getWidth());
		ArrayList<Water> waterBlocks = new ArrayList<Water>();
		Flower flower = null;

		//Add ground objects to level
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
		
		//Add spike objects to level
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

		//Add water blocks to level
		if (waterLayer != null){
			for (MapObject water : waterLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)water.getProperties().get("x") + TILE_SIZE / 2,
						(Float)water.getProperties().get("y") + TILE_SIZE / 2);
				waterBlocks.add(new Water(position, WaterState.LIQUID));
			}
		}
		
		//Add ice blocks to level
		if (iceLayer != null){
			for (MapObject ice : iceLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)ice.getProperties().get("x") + TILE_SIZE / 2,
						(Float)ice.getProperties().get("y") + TILE_SIZE / 2);
				waterBlocks.add(new Water(position, WaterState.SOLID));
			}
		}
		
		//Add vapor blocks to level
		if (vaporLayer != null){
			for (MapObject vapor : vaporLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)vapor.getProperties().get("x") + TILE_SIZE / 2,
						(Float)vapor.getProperties().get("y") + TILE_SIZE / 2);
				waterBlocks.add(new Water(position, WaterState.GAS));
			}
		}

		int waterLevel = 10;

		//Add flower block to level
		if (flowerLayer != null){
			for (MapObject flowerObject : flowerLayer.getObjects()) {
				Point2D.Float position = new Point2D.Float(
						(Float)flowerObject.getProperties().get("x") + TILE_SIZE / 2,
						(Float)flowerObject.getProperties().get("y") + TILE_SIZE / 2);
				waterLevel = Integer.parseInt(flowerObject.getProperties().get("waterLevel").toString()); //The unique flower object has got a custom waterLevel property
				flower = new Flower(position);
			}
		} else {
			flower = null;
		}

		int playerStartPosX = 500;
		int playerStartPosY = 500;

		if(playerLayer != null){
			MapObject playerPos = playerLayer.getObjects().get(0);
			if(playerPos != null) {
				playerStartPosX = (int) ((Float) playerPos.getProperties().get("x") + TILE_SIZE / 2);
				playerStartPosY = (int) ((Float) playerPos.getProperties().get("y") + TILE_SIZE / 2);
			}
		}

		PlayerCharacter player = new PlayerCharacter(playerStartPosX, playerStartPosY, waterLevel);

        if(nameLevelMap.containsKey(name) && !reset){
            return nameLevelMap.get(name);
        }else {
            Level level = new Level(matrix, name, player, waterBlocks, flower, waterLevel);
            nameLevelMap.put(name,level);
            return level;
        }
	}
}
