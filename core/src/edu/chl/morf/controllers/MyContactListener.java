package edu.chl.morf.controllers;

import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.model.ActiveBlockPosition;
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

        userDataA.increment();
        userDataB.increment();
        if (contact.isTouching()) {
            //These if cases are used to check if ghost is empty
            if (userDataTypeA == UserDataType.GHOST_LEFT) {
                userDataA.increment();
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeB == UserDataType.GHOST_LEFT) {
                userDataB.increment();
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeA == UserDataType.GHOST_RIGHT) {
                userDataA.increment();
                gameLogic.setGhostEmptyRight(false);
            } else if (userDataTypeB == UserDataType.GHOST_RIGHT) {
                userDataB.increment();
                gameLogic.setGhostEmptyRight(false);
            }
            //Active block setter
            else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_LEFT){
                userDataA.increment();
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.LEFT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_LEFT){
                userDataB.increment();
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.LEFT);
            } else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_RIGHT){
                userDataA.increment();
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.RIGHT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_RIGHT){
                userDataB.increment();
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.RIGHT);
            } else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_BOTTOM_LEFT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.BOTTOM_LEFT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_BOTTOM_LEFT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.BOTTOM_LEFT);
            } else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_BOTTOM_RIGHT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.BOTTOM_RIGHT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_BOTTOM_RIGHT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.BOTTOM_RIGHT);
            }
            
            //Sets the dead variable to true when contact between SPIKE and PLAYERCHARACTER occurs
            else if ((userDataTypeA == UserDataType.SPIKE) && (userDataTypeB == UserDataType.GHOST_BOTTOM)) {
                gameLogic.killPlayer();
                userDataB.increment();
            } else if ((userDataTypeA == UserDataType.GHOST_BOTTOM) && (userDataTypeB == UserDataType.SPIKE)) {
                gameLogic.killPlayer();
                userDataA.increment();
            }
            //Sets the jumping variable
            else if (userDataTypeA==UserDataType.GHOST_BOTTOM){
                gameLogic.setOnGround(true);
                gameLogic.stop(2);
                userDataA.increment();
            } else if (userDataTypeB==UserDataType.GHOST_BOTTOM){
                gameLogic.setOnGround(true);
                gameLogic.stop(2);
                userDataB.increment();
            }
            //Updates the Water isBottomBlock variable
            else if(userDataTypeA == UserDataType.WATER_SENSOR && userDataTypeB == UserDataType.WATER){
                userDataA.increment();
                userDataB.increment();
                gameLogic.setWaterBottom(fa.getBody(), true);
                gameLogic.setWaterTop(fb.getBody(),true);
                //if(gameLogic.getWaterState(fa.getBody()) != WaterState.SOLID){
                  //  fb.getBody().applyForceToCenter(120f, 0f, true);
                //}
            }
            else if(userDataTypeA == UserDataType.WATER && userDataTypeB == UserDataType.WATER_SENSOR){
                userDataB.increment();
                userDataA.increment();
                gameLogic.setWaterBottom(fb.getBody(), true);
                gameLogic.setWaterTop(fa.getBody(),true);
            }
            else if(userDataTypeA == UserDataType.GHOST_CORE || userDataTypeB == UserDataType.GHOST_CORE){
                if(userDataTypeA == UserDataType.GHOST_CORE){
                    userDataA.increment();
                } else{
                    userDataB.increment();
                }
                gameLogic.setFlyingEnabled(true);
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

        userDataA.decrement();
        userDataB.decrement();
        
        //Sets jumping to true
        if(userDataTypeA==UserDataType.GHOST_BOTTOM) {
            userDataA.decrement();
            if(userDataA.getNumOfContacts()==0) {
                //Jumping=true;
                gameLogic.setOnGround(false);
            }
        }
        if(userDataTypeB==UserDataType.GHOST_BOTTOM){
            userDataB.decrement();
            if(userDataB.getNumOfContacts()==0) {
                //Jumping=true;
                gameLogic.setOnGround(false);
            }
        }

        //Sets activeBody to null when empty 
        if(userDataTypeA == UserDataType.GHOST_LEFT){
        	userDataA.decrement();
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(userDataTypeA == UserDataType.GHOST_RIGHT){
        	userDataA.decrement();
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_LEFT){
        	userDataB.decrement();
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_RIGHT){
        	userDataB.decrement();
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }
        else if (userDataTypeA == UserDataType.ACTIVE_BLOCK_LEFT){
            userDataA.decrement();
            if(userDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (userDataTypeB == UserDataType.ACTIVE_BLOCK_LEFT){
            userDataB.decrement();
            if(userDataB.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (userDataTypeA == UserDataType.ACTIVE_BLOCK_RIGHT){
            userDataA.decrement();
            if (userDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.RIGHT);
            }
        }
        else if (userDataTypeB == UserDataType.ACTIVE_BLOCK_RIGHT){
            userDataB.decrement();
            if (userDataB.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.RIGHT);
            }
        }
        else if ((userDataTypeA == UserDataType.ACTIVE_BLOCK_BOTTOM_LEFT && userDataA.getNumOfContacts()==0) ||
                userDataTypeB == UserDataType.ACTIVE_BLOCK_BOTTOM_LEFT && userDataB.getNumOfContacts()==0) {
            gameLogic.setActiveBody(null, ActiveBlockPosition.BOTTOM_LEFT);
        }
        else if((userDataTypeB == UserDataType.ACTIVE_BLOCK_BOTTOM_RIGHT && userDataB.getNumOfContacts()==0) ||
                userDataTypeA == UserDataType.ACTIVE_BLOCK_BOTTOM_RIGHT && userDataA.getNumOfContacts()==0){
            gameLogic.setActiveBody(null, ActiveBlockPosition.BOTTOM_RIGHT);
        }
        else if (userDataTypeA == UserDataType.WATER_SENSOR && userDataTypeB == UserDataType.WATER){
            userDataA.decrement();
            userDataB.decrement();
            if(userDataA.getNumOfContacts() == 0){
                gameLogic.setWaterBottom(fa.getBody(), false);
            }
            if (userDataB.getNumOfContacts() == 0){
                gameLogic.setWaterTop(fb.getBody(), false);
            }
        }
        else if (userDataTypeB == UserDataType.WATER_SENSOR && userDataTypeA == UserDataType.WATER){
            userDataA.decrement();
            userDataB.decrement();
            if(userDataB.getNumOfContacts() == 0){
                gameLogic.setWaterBottom(fb.getBody(), false);
            }
            if (userDataA.getNumOfContacts() == 0){
                gameLogic.setWaterTop(fa.getBody(), false);
            }
        }
        else if (userDataTypeA == UserDataType.GHOST_CORE || userDataTypeB == UserDataType.GHOST_CORE){
            if(userDataTypeA == UserDataType.GHOST_CORE){
                userDataA.decrement();
                if(userDataA.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
            }else{
                userDataB.decrement();
                if (userDataB.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
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
