package edu.chl.morf.handlers.controllers.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import edu.chl.morf.handlers.controllers.GameLogic;
import edu.chl.morf.model.ActiveBlockPosition;
import edu.chl.morf.model.WaterState;

/**
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
 */

public class CollisionListener implements com.badlogic.gdx.physics.box2d.ContactListener {
	
    private GameLogic gameLogic;

    
    public CollisionListener(GameLogic gameLogic) {
        this.gameLogic=gameLogic;
    }


    /**
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

        //Make sure collisionDataA and B can never be null
        CollisionData collisionDataA =new CollisionData(CollisionType.OTHER);
        CollisionData collisionDataB =new CollisionData(CollisionType.OTHER);
        if(fa.getUserData()!=null){
            collisionDataA = (CollisionData)fa.getUserData();
        }
        if (fb.getUserData()!=null){
            collisionDataB = (CollisionData)fb.getUserData();
        }

        //Sets collisionTypeA and B to the type of collisionDataA and B respectively
        CollisionType collisionTypeB = collisionDataB.getCollisionType();
        CollisionType collisionTypeA = collisionDataA.getCollisionType();

        /* Adds 1 to the numOfContacts of each fixtures CollisionData, which is important
         in the endContact method where there are checks to see if a fixture has
         numOfContacts == 0 */
        collisionDataA.increment();
        collisionDataB.increment();

        //isTouching is used to weed out false-positives as specified in the box2D documentation
        if (contact.isTouching()) {

            /* These if cases are used to set whether the sensor blocks, also called ghost blocks,
               are empty or not. They are used to determine whether the player can place new water
               blocks or not.
               If either userDataType is a ghost block it means the ghost is in contact with something
               and the GhostEmpty variable is set to false. */
            if (collisionTypeA == CollisionType.GHOST_LEFT) {
                gameLogic.setGhostEmptyLeft(false);
            } else if (collisionTypeB == CollisionType.GHOST_LEFT) {
                gameLogic.setGhostEmptyLeft(false);
            } else if (collisionTypeA == CollisionType.GHOST_RIGHT) {
                gameLogic.setGhostEmptyRight(false);
            } else if (collisionTypeB == CollisionType.GHOST_RIGHT) {
                gameLogic.setGhostEmptyRight(false);
            }

            /*  When a block comes in contact with the ACTIVE_BLOCK_LEFT fixture the block is
                set to the active block at position LEFT. The model handles which of the positions
                is currently active. */
            else if(collisionTypeA == CollisionType.ACTIVE_BLOCK_LEFT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.LEFT);
            } else if(collisionTypeB == CollisionType.ACTIVE_BLOCK_LEFT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.LEFT);
            } else if(collisionTypeA == CollisionType.ACTIVE_BLOCK_RIGHT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.RIGHT);
            } else if(collisionTypeB == CollisionType.ACTIVE_BLOCK_RIGHT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.RIGHT);
            } else if(collisionTypeA == CollisionType.ACTIVE_BLOCK_BOTTOM_LEFT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.BOTTOM_LEFT);
            } else if(collisionTypeB == CollisionType.ACTIVE_BLOCK_BOTTOM_LEFT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.BOTTOM_LEFT);
            } else if(collisionTypeA == CollisionType.ACTIVE_BLOCK_BOTTOM_RIGHT){
                gameLogic.setActiveBody(fb.getBody(), ActiveBlockPosition.BOTTOM_RIGHT);
            } else if(collisionTypeB == CollisionType.ACTIVE_BLOCK_BOTTOM_RIGHT){
                gameLogic.setActiveBody(fa.getBody(), ActiveBlockPosition.BOTTOM_RIGHT);
            }
            
            //Sets the dead variable to true when contact between SPIKE and PLAYERCHARACTER occurs
            else if ((collisionTypeA == CollisionType.SPIKE) && (collisionTypeB == CollisionType.GHOST_BOTTOM)) {
                gameLogic.killPlayer();
            } else if ((collisionTypeA == CollisionType.GHOST_BOTTOM) && (collisionTypeB == CollisionType.SPIKE)) {
                gameLogic.killPlayer();
            }

            //Sets the jumping variable
            else if (collisionTypeA == CollisionType.GHOST_BOTTOM || collisionTypeB == CollisionType.GHOST_BOTTOM) {
                gameLogic.setOnGround(true);
                //stop(2) makes sure the character doesn't slow down too much when it lands.
                gameLogic.stop(2);
            }
            /*  Each water block contains whether it is on top of or under another water block.
                This is useful for displaying different textures in the view.
                The WATER_SENSOR fixture is a fixture on top of every water block that can sense
                other water blocks.
                These if cases set the waterBottom and waterTop variables to true. */
            else if(collisionTypeA == CollisionType.WATER_SENSOR && collisionTypeB == CollisionType.WATER){
                gameLogic.setWaterBottom(fa.getBody(), true);
            }
            else if(collisionTypeA == CollisionType.WATER && collisionTypeB == CollisionType.WATER_SENSOR){
                gameLogic.setWaterBottom(fb.getBody(), true);
            }

            /*  WATER_FLOWER_INTERSECTION is a fixture on every water block. If it comes in contact with the flower
                the level is won if the water block is in WaterState LIQUID. */
            else if(collisionTypeA == CollisionType.FLOWER && collisionTypeB == CollisionType.WATER_FLOWER_INTERSECTION){
                if(gameLogic.getWaterState(fb.getBody()) == WaterState.LIQUID){
                    gameLogic.setLevelWon(true);
                }else {
                    gameLogic.setIntersectsFlower(fb.getBody(), true);
                }
            }
            else if(collisionTypeA == CollisionType.WATER_FLOWER_INTERSECTION && collisionTypeB == CollisionType.FLOWER){
                if(gameLogic.getWaterState(fa.getBody()) == WaterState.LIQUID){
                    gameLogic.setLevelWon(true);
                }else {
                    gameLogic.setIntersectsFlower(fa.getBody(), true);
                }
            }

            //When the playerCharacter is inside the flower, the level can be won by pouring water.
            else if(collisionTypeA == CollisionType.GHOST_CORE && collisionTypeB == CollisionType.FLOWER){
                gameLogic.setPlayerInsideFlower(true);
                collisionDataA.decrement();
            }else if(collisionTypeA == CollisionType.FLOWER && collisionTypeB == CollisionType.GHOST_CORE){
                gameLogic.setPlayerInsideFlower(true);
                collisionDataB.decrement();
            }

            /*  The GHOST_CORE fixture is a player fixture that only senses the flower and water blocks in
                the gas state. Since collision with flower is checked above, the other fixture here is water vapor.
                Sets flyingEnabled to true when in contact with water vapor. */
            else if(collisionTypeA == CollisionType.GHOST_CORE || collisionTypeB == CollisionType.GHOST_CORE){
                gameLogic.setFlyingEnabled(true);
            }

            /*  GHOST_BOTTOM_ICE is a player fixture that can only register contact with water block in state ICE.
                When onIce is true, the player slides more. */
            else if(collisionTypeA == CollisionType.GHOST_BOTTOM_ICE || collisionTypeB == CollisionType.GHOST_BOTTOM_ICE){
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

        //Make sure collisionDataA and B can never be null
        CollisionData collisionDataA =new CollisionData(CollisionType.OTHER);
        CollisionData collisionDataB =new CollisionData(CollisionType.OTHER);
        if(fa.getUserData() != null){
            collisionDataA =(CollisionData)fa.getUserData();
        }
        if (fb.getUserData() != null){
            collisionDataB = (CollisionData)fb.getUserData();
        }
        
        CollisionType collisionTypeB = collisionDataB.getCollisionType();
        CollisionType collisionTypeA = collisionDataA.getCollisionType();

        //Reduces numbOfContacts of the fixtures by 1.
        collisionDataA.decrement();
        collisionDataB.decrement();

          /* These if cases are used to set whether the sensor blocks, also called ghost blocks,
               are empty or not. They are used to determine whether the player can place new water
               blocks.
               If either userDataType is a ghost block it means the ghost is no longer in contact with
               another object in the game world and if the ghost is no longer in contact with any object,
               the GhostEmpty variable is set to false. */
        if(collisionTypeA == CollisionType.GHOST_LEFT){
        	if(collisionDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(collisionTypeA == CollisionType.GHOST_RIGHT){
        	if(collisionDataA.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }
        else if(collisionTypeB == CollisionType.GHOST_LEFT){
        	if(collisionDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyLeft(true);
        	}
        }
        else if(collisionTypeB == CollisionType.GHOST_RIGHT){
        	if(collisionDataB.getNumOfContacts() == 0){
        		gameLogic.setGhostEmptyRight(true);
        	}
        }

        /*  When a block is no longer in contact with the ACTIVE_BLOCK_LEFT fixture, the
            activeBlock at position LEFT is set to null. The model handles which of the positions
            is currently active. */
        else if (collisionTypeA == CollisionType.ACTIVE_BLOCK_LEFT){
            if(collisionDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (collisionTypeB == CollisionType.ACTIVE_BLOCK_LEFT){
            if(collisionDataB.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.LEFT);
            }
        }
        else if (collisionTypeA == CollisionType.ACTIVE_BLOCK_RIGHT){
            if (collisionDataA.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.RIGHT);
            }
        }
        else if (collisionTypeB == CollisionType.ACTIVE_BLOCK_RIGHT){
            if (collisionDataB.getNumOfContacts() == 0){
                gameLogic.setActiveBody(null, ActiveBlockPosition.RIGHT);
            }
        }
        else if ((collisionTypeA == CollisionType.ACTIVE_BLOCK_BOTTOM_LEFT && collisionDataA.getNumOfContacts()==0) ||
                collisionTypeB == CollisionType.ACTIVE_BLOCK_BOTTOM_LEFT && collisionDataB.getNumOfContacts()==0) {
            gameLogic.setActiveBody(null, ActiveBlockPosition.BOTTOM_LEFT);
        }
        else if((collisionTypeB == CollisionType.ACTIVE_BLOCK_BOTTOM_RIGHT && collisionDataB.getNumOfContacts()==0) ||
                collisionTypeA == CollisionType.ACTIVE_BLOCK_BOTTOM_RIGHT && collisionDataA.getNumOfContacts()==0){
            gameLogic.setActiveBody(null, ActiveBlockPosition.BOTTOM_RIGHT);
        }

        //Sets jumping to true if GHOST_BOTTOM has no contacts.
        if(collisionTypeA == CollisionType.GHOST_BOTTOM) {
            if(collisionDataA.getNumOfContacts()==0) {
                gameLogic.setOnGround(false);
            }
        }
        if(collisionTypeB == CollisionType.GHOST_BOTTOM){
            if(collisionDataB.getNumOfContacts()==0) {
                gameLogic.setOnGround(false);
            }
        }

        /*  Each water block contains whether it is on top of or under another water block.
            This is useful for displaying different textures in the view.
            The WATER_SENSOR fixture is a fixture on top of every water block that can sense
            other water blocks.
            These if cases set the waterBottom and waterTop variables to false. */
        else if (collisionTypeA == CollisionType.WATER_SENSOR && collisionTypeB == CollisionType.WATER){
            if(!(fb.getBody() == null) && !(fa.getBody() == null)) {
                if (collisionDataA.getNumOfContacts() == 0) {
                    gameLogic.setWaterBottom(fa.getBody(), false);
                }
            }
        }
        else if (collisionTypeB == CollisionType.WATER_SENSOR && collisionTypeA == CollisionType.WATER){
            if(!(fb.getBody() == null) && !(fa.getBody() == null)){
                if(collisionDataB.getNumOfContacts() == 0){
                    gameLogic.setWaterBottom(fb.getBody(), false);
                }
            }
        }
        /*  WATER_FLOWER_INTERSECTION is a fixture on every water block. If it comes in contact with the flower
            the level is won if the water block is in WaterState LIQUID. These if cases are relevant when
            a water block in WaterState SOLID stops being in contact with the flower before being melted. */
        else if(collisionTypeA == CollisionType.FLOWER && collisionTypeB == CollisionType.WATER_FLOWER_INTERSECTION){
            gameLogic.setIntersectsFlower(fb.getBody(), false);
        }
        else if(collisionTypeA == CollisionType.WATER_FLOWER_INTERSECTION && collisionTypeB == CollisionType.FLOWER){
            gameLogic.setIntersectsFlower(fa.getBody(), false);
        }
        
        //When the playerCharacter is inside the flower, the level can be won by pouring water.
        else if(collisionTypeA == CollisionType.GHOST_CORE && collisionTypeB == CollisionType.FLOWER){
            gameLogic.setPlayerInsideFlower(false);
            collisionDataA.increment();
        }else if(collisionTypeA == CollisionType.FLOWER && collisionTypeB == CollisionType.GHOST_CORE){
            gameLogic.setPlayerInsideFlower(false);
            collisionDataB.increment();
        }

        /*  The GHOST_CORE fixture is a player fixture that only senses the flower and water blocks in
            the gas state. Since collision with flower is checked above, the other fixture here is water vapor.
            Sets flyingEnabled to false when no longer in contact with water vapor. */
        else if (collisionTypeA == CollisionType.GHOST_CORE || collisionTypeB == CollisionType.GHOST_CORE){
            if(collisionTypeA == CollisionType.GHOST_CORE){
                if(collisionDataA.getNumOfContacts()==0){
                    gameLogic.setFlyingEnabled(false);
                }
            }else if (collisionDataB.getNumOfContacts()==0){
                gameLogic.setFlyingEnabled(false);
            }
        }

        /*  GHOST_BOTTOM_ICE is a player fixture that can only register contact with water block in state ICE.
            When onIce is true, the player slides more. */
        else if (collisionTypeA == CollisionType.GHOST_BOTTOM_ICE || collisionTypeB == CollisionType.GHOST_BOTTOM_ICE){
            if(collisionDataA.getNumOfContacts()==0 || collisionDataB.getNumOfContacts()==0){
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
