package edu.chl.morf.controllers;

import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.model.ActiveBlockPosition;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
	
    private GameLogic gameLogic;
    private boolean playerOnGround;

    
    public ContactListener(GameLogic gameLogic) {
        this.gameLogic=gameLogic;
        playerOnGround = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        UserData userDataA=new UserData(UserDataType.OTHER);
        UserData userDataB=new UserData(UserDataType.OTHER);

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
                
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeB == UserDataType.GHOST_LEFT) {
                
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeA == UserDataType.GHOST_RIGHT) {
                
                gameLogic.setGhostEmptyRight(false);
            } else if (userDataTypeB == UserDataType.GHOST_RIGHT) {
                
                gameLogic.setGhostEmptyRight(false);
            }
            //Active block setter
            else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_LEFT){
                
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.LEFT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_LEFT){
                
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.LEFT);
            } else if(userDataTypeA == UserDataType.ACTIVE_BLOCK_RIGHT){
                
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.RIGHT);
            } else if(userDataTypeB == UserDataType.ACTIVE_BLOCK_RIGHT){
                
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
                
            } else if ((userDataTypeA == UserDataType.GHOST_BOTTOM) && (userDataTypeB == UserDataType.SPIKE)) {
                gameLogic.killPlayer();
                
            }
            //Sets the jumping variable
            else if (userDataTypeA==UserDataType.GHOST_BOTTOM){
                gameLogic.setOnGround(true);
                gameLogic.stop(2);
                
            } else if (userDataTypeB==UserDataType.GHOST_BOTTOM){
                gameLogic.setOnGround(true);
                gameLogic.stop(2);
                
            }
            //Updates the Water isBottomBlock variable
            else if(userDataTypeA == UserDataType.WATER_SENSOR && userDataTypeB == UserDataType.WATER){
                gameLogic.setWaterBottom(fa.getBody(), true);
                gameLogic.setWaterTop(fb.getBody(),true);
                //if(gameLogic.getWaterState(fa.getBody()) != WaterState.SOLID){
                  //  fb.getBody().applyForceToCenter(120f, 0f, true);
                //}
            }
            else if(userDataTypeA == UserDataType.WATER && userDataTypeB == UserDataType.WATER_SENSOR){
                gameLogic.setWaterBottom(fb.getBody(), true);
                gameLogic.setWaterTop(fa.getBody(),true);
            }
            else if(userDataTypeA == UserDataType.FLOWER && userDataTypeB == UserDataType.WATER_FLOWER_INTERSECTION){
                if(gameLogic.getWaterState(fb.getBody()) == WaterState.LIQUID){
                    gameLogic.setLevelWon(true);
                }else {
                    gameLogic.setIntersectsFlower(fb.getBody(), true);
                }
            }
            else if(userDataTypeA == UserDataType.WATER_FLOWER_INTERSECTION && userDataTypeB == UserDataType.FLOWER){
                if(gameLogic.getWaterState(fa.getBody()) == WaterState.LIQUID){
                    gameLogic.setLevelWon(true);
                }else {
                    gameLogic.setIntersectsFlower(fa.getBody(), true);
                }
            }
            else if(userDataTypeA == UserDataType.GHOST_CORE || userDataTypeB == UserDataType.GHOST_CORE){
                gameLogic.setFlyingEnabled(true);
            }
            else if(userDataTypeA == UserDataType.GHOST_BOTTOM_ICE || userDataTypeB == UserDataType.GHOST_BOTTOM_ICE){
                gameLogic.setOnIce(true);
            }
        }
    }
    
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        UserData userDataA=new UserData(UserDataType.OTHER);
        UserData userDataB=new UserData(UserDataType.OTHER);

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
            
            if(userDataA.getNumOfContacts()==0) {
                //Jumping=true;
                gameLogic.setOnGround(false);
            }
        }
        if(userDataTypeB==UserDataType.GHOST_BOTTOM){
            
            if(userDataB.getNumOfContacts()==0) {
                //Jumping=true;
                gameLogic.setOnGround(false);
            }
        }

        //Sets activeBody to null when empty 
        if(userDataTypeA == UserDataType.GHOST_LEFT){
        	
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(userDataTypeA == UserDataType.GHOST_RIGHT){
        	
        	if(userDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_LEFT){
        	
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(userDataTypeB == UserDataType.GHOST_RIGHT){
        	
        	if(userDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }
        else if (userDataTypeA == UserDataType.ACTIVE_BLOCK_LEFT){
            
            if(userDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (userDataTypeB == UserDataType.ACTIVE_BLOCK_LEFT){
            
            if(userDataB.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (userDataTypeA == UserDataType.ACTIVE_BLOCK_RIGHT){
            if (userDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.RIGHT);
            }
        }
        else if (userDataTypeB == UserDataType.ACTIVE_BLOCK_RIGHT){
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
            if(!(fb.getBody() == null) && !(fa.getBody() == null)) {
                if (userDataA.getNumOfContacts() == 0) {
                    gameLogic.setWaterBottom(fa.getBody(), false);
                }
                if (userDataB.getNumOfContacts() == 0) {
                    gameLogic.setWaterTop(fb.getBody(), false);
                }
            }
        }
        else if (userDataTypeB == UserDataType.WATER_SENSOR && userDataTypeA == UserDataType.WATER){
            if(!(fb.getBody() == null) && !(fa.getBody() == null)){
                if(userDataB.getNumOfContacts() == 0){
                    gameLogic.setWaterBottom(fb.getBody(), false);
                }
                if (userDataA.getNumOfContacts() == 0){
                    gameLogic.setWaterTop(fa.getBody(), false);
                }
            }
        }
        else if (userDataTypeA == UserDataType.GHOST_CORE || userDataTypeB == UserDataType.GHOST_CORE){
            if(userDataTypeA == UserDataType.GHOST_CORE){
                
                if(userDataA.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
            }else{
                
                if (userDataB.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
            }
        }
        else if (userDataTypeA == UserDataType.GHOST_BOTTOM_ICE || userDataTypeB == UserDataType.GHOST_BOTTOM_ICE){
            if(userDataA.getNumOfContacts()==0 || userDataB.getNumOfContacts()==0){
                gameLogic.setOnIce(false);
            }
        }
        else if(userDataTypeA == UserDataType.FLOWER && userDataTypeB == UserDataType.WATER_FLOWER_INTERSECTION){
            gameLogic.setIntersectsFlower(fb.getBody(),false);
        }
        else if(userDataTypeA == UserDataType.WATER_FLOWER_INTERSECTION && userDataTypeB == UserDataType.FLOWER){
            gameLogic.setIntersectsFlower(fa.getBody(), false);
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
