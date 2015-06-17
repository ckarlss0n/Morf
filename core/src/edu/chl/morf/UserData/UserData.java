package edu.chl.morf.UserData;

/**
 * Created by Christoffer on 2015-04-17.
 */
public class UserData {
    private UserDataType userDataType;
    public UserData(){

    }
    public UserData(UserDataType userDataType){
        this.userDataType=userDataType;
    }

    public UserDataType getUserDataType(){
        return userDataType;
    }
}
