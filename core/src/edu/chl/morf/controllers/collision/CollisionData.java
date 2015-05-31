package edu.chl.morf.controllers.collision;

/**
 * Created by Christoffer on 2015-04-17.
 */
public class CollisionData {
    private CollisionType collisionType;
    private int numOfContacts;
    public CollisionData(){

    }
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
