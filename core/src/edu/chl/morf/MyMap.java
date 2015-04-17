package edu.chl.morf;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyMap {

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;
    private OrthographicCamera cam;
    private TiledMapTileLayer layer;
    
    public MyMap(){
        tileMap = new TmxMapLoader().load("testmap.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        
        layer = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");

        tileSize = layer.getTileWidth();
        
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    }
    
//    public OrthographicCamera getCam(){
//    	return cam;
//    }
    
    public float getTileSize(){
    	return tileSize;
    }
    
    public OrthogonalTiledMapRenderer getRenderer(){
    	return tmr;
    }
    
    public TiledMapTileLayer getLayer(){
    	return layer;
    }
    
    public void render(){
      tmr.setView(cam);
      tmr.render();
    }
    
    
}
