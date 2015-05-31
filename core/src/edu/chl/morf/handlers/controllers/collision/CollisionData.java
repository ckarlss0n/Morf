package edu.chl.morf.handlers.controllers.collision;

/**
 * All fixtures (Box2D) are assigned an instance of CollisionData upon creation.
 * The instance contains information about what type of fixture that
 * holds it (CollisionType).
 * CollisionData also stores how many other world objects it's fixture
 * is in contact with.
 *
 * @author Harald Brorsson
 */
public class CollisionData {
    private CollisionType collisionType;
    private int numOfContacts;

    public CollisionData(CollisionType collisionType){
        this.collisionType = collisionType;
    }

    public CollisionType getCollisionType(){
        return collisionType;
    }
    public int getNumOfContacts(){
        return numOfContacts;
    }
    public void increment(){
        numOfContacts=numOfContacts+1;
    }
    public void decrement(){
        numOfContacts=numOfContacts-1;
    }
}
