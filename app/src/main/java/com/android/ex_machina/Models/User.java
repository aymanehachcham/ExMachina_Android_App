package com.android.ex_machina.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name;
    private String email;
    private String imageurl;
    private String provider;
    private String uid;
    private String datecreation;

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        imageurl = in.readString();
        provider = in.readString();
        uid = in.readString();
        datecreation = in.readString();
    }

    public User()
    {

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setImageurl(String imageurl){
        this.imageurl = imageurl;
    }

    public String getImageurl(){
        return imageurl;
    }

    public void setProvider(String provider){
        this.provider = provider;
    }

    public String getProvider(){
        return provider;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDatecreation() {
        return datecreation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(imageurl);
        dest.writeString(provider);
        dest.writeString(uid);
        dest.writeString(datecreation);
    }
}
