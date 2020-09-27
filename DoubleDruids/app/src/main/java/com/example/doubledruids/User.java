package com.example.doubledruids;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class User implements Parcelable {
    String name;
    String email;
    String password;
    String userUniqueID;
    String key;
//    ArrayList<String> teams;
//    String icon;
//    String preferredPosition;
//    ArrayList<String> otherPositions;

    public User(){

    }

    public User(Parcel in){
        this();
        readFromParcel(in);
    }
    public void readFromParcel(Parcel in){
        name=in.readString();
        email=in.readString();
        password=in.readString();
        userUniqueID=in.readString();
        key=in.readString();
        //teams= new ArrayList<>();
        //in.readList(teams, User.class.getClassLoader());
        //in.readList(otherPositions, User.class.getClassLoader());

    }

    public User(String name, String email, String password, String athleteUniqueID, String key) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userUniqueID = athleteUniqueID;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserUniqueID() {
        return userUniqueID;
    }

    public void setUserUniqueID(String userUniqueID) {
        this.userUniqueID = userUniqueID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v("", "write to parcel..."+ flags);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(userUniqueID);
        dest.writeString(key);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
