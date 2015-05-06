package edu.chl.morf.controllers;
import com.badlogic.gdx.physics.box2d.*;
import edu.chl.morf.userdata.UserData;
import edu.chl.morf.userdata.UserDataType;
public class MyContactListener implements ContactListener{
    Body activeBodyLeft=null;
    Body activeBodyRight=null;
    boolean dead=false;
    boolean jumping=false;
    public MyContactListener () {
    }
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
        //boolean fallingBeforeTouch = false;
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        UserData userDataA=new UserData(UserDataType.OTHER);
        UserData userDataB=new UserData(UserDataType.OTHER);
        if(fa.getUserData()!=null){
            userDataA=(UserData)fa.getUserData();
        }
        if (fb.getUserData()!=null){
            userDataB=(UserData)fb.getUserData();
        }
        UserDataType userDataTypeB=userDataB.getUserDataType();
        UserDataType userDataTypeA=userDataA.getUserDataType();
        if (contact.isTouching()) {
            //These if cases are used to set the active block variable
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
            }
            //Sets the dead variable to true when contact between SPIKE and PLAYERCHARACTER occurs
            else if ((userDataTypeA == UserDataType.SPIKE) && (userDataTypeB == UserDataType.PLAYERCHARACTER)) {
                dead=true;
            } else if ((userDataTypeA== UserDataType.PLAYERCHARACTER) && (userDataTypeB == UserDataType.SPIKE)) {
                dead=true;
            }
            //Sets the jumping variable
            else if (userDataTypeA==UserDataType.GHOST_BOTTOM && userDataTypeB!=UserDataType.PLAYERCHARACTER){
                jumping=false;
            } else if (userDataTypeA!=UserDataType.PLAYERCHARACTER && userDataTypeB==UserDataType.GHOST_BOTTOM){
                jumping=false;
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
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        UserData userDataA=new UserData(UserDataType.OTHER);
        UserData userDataB=new UserData(UserDataType.OTHER);
        if(fa.getUserData()!=null){
            userDataA=(UserData)fa.getUserData();
        }
        if (fb.getUserData()!=null){
            userDataB=(UserData)fb.getUserData();
        }
        UserDataType userDataTypeB=userDataB.getUserDataType();
        UserDataType userDataTypeA=userDataA.getUserDataType();
        //Sets jumping to true
        if(userDataTypeA==UserDataType.GHOST_BOTTOM || userDataTypeB==UserDataType.GHOST_BOTTOM){
            jumping=true;
        }
        //Sets activeBody to null when empty
        if(userDataTypeA==UserDataType.GHOST_LEFT || userDataTypeA==UserDataType.GHOST_RIGHT){
            userDataA.decrement();
            if(userDataA.getNumOfContacts()==0){
                if(userDataTypeA==UserDataType.GHOST_LEFT){
                    activeBodyLeft=fa.getBody();
                }else{
                    activeBodyRight=fa.getBody();
                }
            }
        }
        else if(userDataTypeB==UserDataType.GHOST_LEFT || userDataTypeB==UserDataType.GHOST_RIGHT){
            userDataB.decrement();
            if(userDataB.getNumOfContacts()==0){
                if(userDataTypeB==UserDataType.GHOST_LEFT){
                    activeBodyLeft=fb.getBody();
                }else{
                    activeBodyRight=fb.getBody();
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
