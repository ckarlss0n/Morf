package edu.chl.morf.controllers;

import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.model.ActiveBlockPosition;
import edu.chl.morf.model.WaterState;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;

public class CollisionListener implements com.badlogic.gdx.physics.box2d.ContactListener {
	
    private GameLogic gameLogic;

    
    public CollisionListener(GameLogic gameLogic) {
        this.gameLogic=gameLogic;
    }


    /*
     * Whenever anything collides in the game world, a contact is sent to
     * the CollisionListeners beginContact method.
     * Whenever two bodies stop being in contact with each other, a contact
     * is sent to the endContact method.
     * A contact consists of the two fixtures that collided or stopped
     * being in contact with each other.
     * The beginContact and endContact methods check which fixtures collided
     * and forwards the information to gameLogic through for example setting
     * relevant variables.
     *
     * The CollisionListener is only responsible for "extraordinary" events.
     * The box2D physics engine handles things like two objects bouncing off each other
     * on contact automatically.
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //Make sure userDataA and B can never be null
        UserData userDataA=new UserData(UserDataType.OTHER);
        UserData userDataB=new UserData(UserDataType.OTHER);
        if(fa.getUserData()!=null){
            userDataA = (UserData)fa.getUserData();
        }
        if (fb.getUserData()!=null){
            userDataB = (UserData)fb.getUserData();
        }

        //Sets userDataTypeA and B to the type of userDataA and B respectively
        UserDataType userDataTypeB = userDataB.getUserDataType();
        UserDataType userDataTypeA = userDataA.getUserDataType();

        /* Adds 1 to the numOfContacts of each fixtures UserData, which is important
         in the endContact method where there are checks to see if a fixture has
         numOfContacts == 0 */
        userDataA.increment();
        userDataB.increment();

        //isTouching is used to weed out false-positives as specified in the box2D documentation
        if (contact.isTouching()) {

            /* These if cases are used to set whether the sensor blocks, also called ghost blocks,
               are empty or not. They are used to determine whether the player can place new water
               blocks or not.
               If either userDataType is a ghost block it means the ghost is in contact with something
               and the GhostEmpty variable is set to false. */
            if (userDataTypeA == UserDataType.GHOST_LEFT) {
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeB == UserDataType.GHOST_LEFT) {
                gameLogic.setGhostEmptyLeft(false);
            } else if (userDataTypeA == UserDataType.GHOST_RIGHT) {
                gameLogic.setGhostEmptyRight(false);
            } else if (userDataTypeB == UserDataType.GHOST_RIGHT) {
                gameLogic.setGhostEmptyRight(false);
            }

            /*  When a block comes in contact with the ACTIVE_BLOCK_LEFT fixture the block is
                set to the active block at position LEFT. The model handles which of the positions
                is currently active. */
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
            else if (userDataTypeA==UserDataType.GHOST_BOTTOM || userDataTypeB == UserDataType.GHOST_BOTTOM) {
                gameLogic.setOnGround(true);
                //stop(2) makes sure the character doesn't slow down too much when it lands.
                gameLogic.stop(2);
            }
            /*  Each water block contains whether it is on top of or under another water block.
                This is useful for displaying different textures in the view.
                The WATER_SENSOR fixture is a fixture on top of every water block that can sense
                other water blocks.
                These if cases set the waterBottom and waterTop variables to true. */
            else if(userDataTypeA == UserDataType.WATER_SENSOR && userDataTypeB == UserDataType.WATER){
                gameLogic.setWaterBottom(fa.getBody(), true);
                gameLogic.setWaterTop(fb.getBody(),true);
            }
            else if(userDataTypeA == UserDataType.WATER && userDataTypeB == UserDataType.WATER_SENSOR){
                gameLogic.setWaterBottom(fb.getBody(), true);
                gameLogic.setWaterTop(fa.getBody(),true);
            }

            /*  WATER_FLOWER_INTERSECTION is a fixture on every water block. If it comes in contact with the flower
                the level is won if the water block is in WaterState LIQUID. */
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

            //When the playerCharacter is inside the flower, the level can be won by pouring water.
            else if(userDataTypeA == UserDataType.GHOST_CORE && userDataTypeB == UserDataType.FLOWER){
                gameLogic.setPlayerInsideFlower(true);
            }else if(userDataTypeA == UserDataType.FLOWER && userDataTypeB == UserDataType.GHOST_CORE){
                gameLogic.setPlayerInsideFlower(true);
            }

            /*  The GHOST_CORE fixture is a player fixture that only senses the flower and water blocks in
                the gas state. Since collision with flower is checked above, the other fixture here is water vapor.
                Sets flyingEnabled to true when in contact with water vapor. */
            else if(userDataTypeA == UserDataType.GHOST_CORE){
                gameLogic.setFlyingEnabled(true);
            }

            /*  GHOST_BOTTOM_ICE is a player fixture that can only register contact with water block in state ICE.
                When onIce is true, the player slides more. */
            else if(userDataTypeA == UserDataType.GHOST_BOTTOM_ICE || userDataTypeB == UserDataType.GHOST_BOTTOM_ICE){
                gameLogic.setOnIce(true);
            }
        }
    }

    /*********'
     * Is called when two objects in the game world are no longer in contact
     * with each other.
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //Make sure userDataA and B can never be null
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

        //Reduces numbOfContacts of the fixtures by 1.
        userDataA.decrement();
        userDataB.decrement();

          /* These if cases are used to set whether the sensor blocks, also called ghost blocks,
               are empty or not. They are used to determine whether the player can place new water
               blocks.
               If either userDataType is a ghost block it means the ghost is no longer in contact with
               another object in the game world and if the ghost is no longer in contact with any object,
               the GhostEmpty variable is set to false. */
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

        /*  When a block is no longer in contact with the ACTIVE_BLOCK_LEFT fixture, the
            activeBlock at position LEFT is set to null. The model handles which of the positions
            is currently active. */
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

        //Sets jumping to true if GHOST_BOTTOM has no contacts.
        if(userDataTypeA==UserDataType.GHOST_BOTTOM) {
            if(userDataA.getNumOfContacts()==0) {
                gameLogic.setOnGround(false);
            }
        }
        if(userDataTypeB==UserDataType.GHOST_BOTTOM){
            if(userDataB.getNumOfContacts()==0) {
                gameLogic.setOnGround(false);
            }
        }

        /*  Each water block contains whether it is on top of or under another water block.
            This is useful for displaying different textures in the view.
            The WATER_SENSOR fixture is a fixture on top of every water block that can sense
            other water blocks.
            These if cases set the waterBottom and waterTop variables to false. */
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
        /*  WATER_FLOWER_INTERSECTION is a fixture on every water block. If it comes in contact with the flower
            the level is won if the water block is in WaterState LIQUID. These if cases are relevant when
            a water block in WaterState SOLID stops being in contact with the flower before being melted. */
        else if(userDataTypeA == UserDataType.FLOWER && userDataTypeB == UserDataType.WATER_FLOWER_INTERSECTION){
            gameLogic.setIntersectsFlower(fb.getBody(), false);
        }
        else if(userDataTypeA == UserDataType.WATER_FLOWER_INTERSECTION && userDataTypeB == UserDataType.FLOWER){
            gameLogic.setIntersectsFlower(fa.getBody(), false);
        }
        
        //When the playerCharacter is inside the flower, the level can be won by pouring water.
        else if(userDataTypeA == UserDataType.GHOST_CORE && userDataTypeB == UserDataType.FLOWER){
            gameLogic.setPlayerInsideFlower(false);
        }else if(userDataTypeA == UserDataType.FLOWER && userDataTypeB == UserDataType.GHOST_CORE){
            gameLogic.setPlayerInsideFlower(false);
        }

        /*  The GHOST_CORE fixture is a player fixture that only senses the flower and water blocks in
            the gas state. Since collision with flower is checked above, the other fixture here is water vapor.
            Sets flyingEnabled to false when no longer in contact with water vapor. */
        else if (userDataTypeA == UserDataType.GHOST_CORE || userDataTypeB == UserDataType.GHOST_CORE){
            if(userDataTypeA == UserDataType.GHOST_CORE){
                if(userDataA.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
            }else if (userDataB.getNumOfContacts()==0){
                gameLogic.setFlyingEnabled(false);
            }
        }

        /*  GHOST_BOTTOM_ICE is a player fixture that can only register contact with water block in state ICE.
            When onIce is true, the player slides more. */
        else if (userDataTypeA == UserDataType.GHOST_BOTTOM_ICE || userDataTypeB == UserDataType.GHOST_BOTTOM_ICE){
            if(userDataA.getNumOfContacts()==0 || userDataB.getNumOfContacts()==0){
                gameLogic.setOnIce(false);
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
