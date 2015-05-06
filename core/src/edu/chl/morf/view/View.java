package edu.chl.morf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.chl.morf.model.Level;
import edu.chl.morf.model.TileType;

/**
 * Created by Lage on 2015-05-06.
 */
public class View {

    private Level level;
    private Batch batch;

    public View(){
        batch = new SpriteBatch();
    }

    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Clears the screen.
        batch.begin();
        for(TileType[] tileRow: level.getMatrix()){
            for(TileType tile: tileRow){
                switch (tile){
                    case FLOWER:

                        break;
                }
            }
        }
        batch.end();
    }
}
