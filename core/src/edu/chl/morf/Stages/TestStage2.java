package edu.chl.morf.Stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.chl.morf.Actors.PlayerCharacter;

public class TestStage2 extends Stage{
	
	private PlayerCharacter playerCharacter;
    
    private World world;

    float accumulator;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
	
	public TestStage2(){
		camera = new OrthographicCamera();
	}
}
