package edu.chl.morf.handlers;

import static edu.chl.morf.Constants.LEVEL_PATH;
import static edu.chl.morf.handlers.Constants.*;
import static edu.chl.morf.Constants.GROUND_FRICTION;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.chl.morf.model.Level;

public class LevelGenerator {

    private TiledMapTileLayer groundLayer;
	public static float TILE_SIZE;
	public void generateLevel(Level level, World world){
		
		TiledMap tileMap = new TmxMapLoader().load(LEVEL_PATH + level.getName());
		groundLayer = (TiledMapTileLayer) tileMap.getLayers().get("Ground");
		TILE_SIZE = groundLayer.getTileHeight();
		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		FixtureDef fixDef = new FixtureDef();

		for (int row = 0; row < level.getMatrix().getRows(); row++) {
			for (int col = 0; col < level.getMatrix().getColumns(); col++) {
				TiledMapTileLayer.Cell cell = groundLayer.getCell(col, row);


				if (cell == null) continue;
				if (cell.getTile() == null) continue;

				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((col + 0.5f) * TILE_SIZE / PPM, (row + 0.5f) * TILE_SIZE / PPM);

				ChainShape chainShape = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[1] = new Vector2(-TILE_SIZE / 2 / PPM, TILE_SIZE / 2 / PPM);
				v[2] = new Vector2(TILE_SIZE / 2 / PPM, TILE_SIZE / 2 / PPM);
				v[3] = new Vector2(TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);
				v[4] = new Vector2(-TILE_SIZE / 2 / PPM, -TILE_SIZE / 2 / PPM);

				chainShape.createChain(v);
				fixDef.friction = GROUND_FRICTION;
				fixDef.shape = chainShape;
				fixDef.filter.categoryBits = BIT_GROUND;
				fixDef.filter.maskBits = -1;
				fixDef.isSensor = false;
				world.createBody(bodyDef).createFixture(fixDef);
			}
		}

	}
}
