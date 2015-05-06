package edu.chl.morf.controllers;

import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.model.PlayerCharacterModel;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;
import edu.chl.morf.model.Level;

public class MyContactListener implements ContactListener{
	Level level;
	Body activeBodyLeft=null;
	Body activeBodyRight=null;
	boolean dead=false;
	boolean jumping=false;
	public MyContactListener (Level level){
		this.level=level;
	}
	PlayerCharacterModel player=level.getPlayer();

	public boolean isDead(){return dead;}

	public boolean isJumping() {return jumping;}

	public Body getActiveBodyLeft(){
		return activeBodyLeft;
	}

	public Body getActiveBodyRight() {
		return activeBodyRight;
	}

	@Override
	public void beginContact(Contact contact) {
		boolean fallingBeforeTouch = false;
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		UserData userDataA=new UserData(UserDataType.OTHER);
		UserData userDataB=new UserData(UserDataType.OTHER);
		if(fa.getUserData()!=null){
			userDataA=(UserData) fa.getUserData();
		}
		if (fb.getUserData()!=null){
			userDataB=(UserData) fb.getUserData();
		}

		UserDataType userDataTypeB=userDataB.getUserDataType();
		UserDataType userDataTypeA=userDataA.getUserDataType();

		if (contact.isTouching()) {
			if (userDataTypeA == UserDataType.GHOST_LEFT) {
				activeBodyLeft=fb.getBody();
				userDataA.increment();
			} else if (userDataTypeB== UserDataType.GHOST_LEFT) {
				activeBodyLeft=fa.getBody();
				userDataB.increment();
			} else if (userDataTypeA== UserDataType.GHOST_RIGHT) {
				activeBodyRight=fb.getBody();
				userDataA.increment();
			} else if (userDataTypeB == UserDataType.GHOST_RIGHT) {
				activeBodyRight=fa.getBody();
				userDataB.increment();
			} else if ((userDataTypeA == UserDataType.SPIKE) && (userDataTypeB == UserDataType.PLAYERCHARACTER)) {
				dead=true;
			} else if ((userDataTypeA== UserDataType.PLAYERCHARACTER) && (userDataTypeB == UserDataType.SPIKE)) {
				dead=true;
			}
		}
		else {
			System.out.println("No touch!");
		}
		/*
		if (player.getVelocity().y < -10) {
			fallingBeforeTouch = true;
		}
		if (fallingBeforeTouch && contact.isTouching()) {
			System.out.println("DIE");
			playerCharacter.die();
		}
		*/

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
