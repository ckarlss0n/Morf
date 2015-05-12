package edu.chl.morf.controllers;
import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

public class MyContactListener implements ContactListener{
	
    private GameLogic gameLogic;
    private boolean playerOnGround;
    
    public MyContactListener (GameLogic gameLogic) {
        this.gameLogic=gameLogic;
        playerOnGround = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        UserData userDataA = new UserData(UserDataType.OTHER);
        UserData userDataB = new UserData(UserDataType.OTHER);
        
        if(fa.getUserData()!=null){
            userDataA = (UserData)fa.getUserData();
        }
        if (fb.getUserData()!=null){
            userDataB = (UserData)fb.getUserData();
        }
        
        UserDataType userDataTypeB = userDataB.getUserDataType();
        UserDataType userDataTypeA = userDataA.getUserDataType();
        
        if (contact.isTouching()) {
            //These if cases are used to set the active block variable
            if (userDataTypeA == UserDataType.GHOST_LEFT) {
                userDataA.increment();
                gameLogic.setActiveBodyLeft(fb.getBody());
            } else if (userDataTypeB == UserDataType.GHOST_LEFT) {
                userDataB.increment();
                gameLogic.setActiveBodyLeft(fa.getBody());
            } else if (userDataTypeA == UserDataType.GHOST_RIGHT) {
                userDataA.increment();
                gameLogic.setActiveBodyRight(fb.getBody());
            } else if (userDataTypeB == UserDataType.GHOST_RIGHT) {
                userDataB.increment();
                gameLogic.setActiveBodyRight(fa.getBody());
            }
            
            //Sets the dead variable to true when contact between SPIKE and PLAYERCHARACTER occurs
            else if ((userDataTypeA == UserDataType.SPIKE) && (userDataTypeB == UserDataType.PLAYERCHARACTER)) {
                gameLogic.killPlayer();
            } else if ((userDataTypeA == UserDataType.PLAYERCHARACTER) && (userDataTypeB == UserDataType.SPIKE)) {
                gameLogic.killPlayer();
            }
            //Sets the jumping variable
            else if (userDataTypeA == UserDataType.GHOST_BOTTOM && userDataTypeB != UserDataType.PLAYERCHARACTER){
                playerOnGround = false;
            } else if (userDataTypeA != UserDataType.PLAYERCHARACTER && userDataTypeB == UserDataType.GHOST_BOTTOM){
                playerOnGround = false;
            }
        }
    }
    
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        UserData userDataA = new UserData(UserDataType.OTHER);
        UserData userDataB = new UserData(UserDataType.OTHER);
        
        if(fa.getUserData() != null){
            userDataA=(UserData)fa.getUserData();
        }
        if (fb.getUserData() != null){
            userDataB = (UserData)fb.getUserData();
        }
        
        UserDataType userDataTypeB = userDataB.getUserDataType();
        UserDataType userDataTypeA = userDataA.getUserDataType();
        
        //Sets jumping to true
        if(userDataTypeA==UserDataType.GHOST_BOTTOM || userDataTypeB==UserDataType.GHOST_BOTTOM){
            playerOnGround=true;
        }
        
        //Sets activeBody to null when empty 
        if(userDataTypeA == UserDataType.GHOST_LEFT){
        	userDataA.decrement();
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setActiveBodyLeft(null);
        	}
        }
        else if(userDataTypeA == UserDataType.GHOST_RIGHT){
        	userDataA.decrement();
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setActiveBodyRight(null);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_LEFT){
        	userDataB.decrement();
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setActiveBodyLeft(null);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_RIGHT){
        	userDataB.decrement();
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setActiveBodyRight(null);
        	}
        }
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
