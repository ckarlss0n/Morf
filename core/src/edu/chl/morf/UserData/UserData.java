package edu.chl.morf.UserData;

/**
 * Created by Christoffer on 2015-04-17.
 */
public class UserData {
    private UserDataType userDataType;
    private int numOfContacts;
    public UserData(){

    }
    public UserData(UserDataType userDataType){
        this.userDataType=userDataType;
    }

    public UserDataType getUserDataType(){
        return userDataType;
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
