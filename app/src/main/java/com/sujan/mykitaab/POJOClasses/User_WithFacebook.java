package com.sujan.mykitaab.POJOClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookpro on 4/17/17.
 */

public class User_WithFacebook implements Parcelable{

    String name;
    String email_id;
    String id;
    String date_of_birth;




    public User_WithFacebook(String name, String email_id, String id, String date_of_birth) {
        this.name = name;
        this.email_id = email_id;
        this.id = id;
        this.date_of_birth = date_of_birth;

    }

    protected User_WithFacebook(Parcel in) {
        name = in.readString();
        email_id = in.readString();
        id = in.readString();
        date_of_birth = in.readString();

    }

    public static final Creator<User_WithFacebook> CREATOR = new Creator<User_WithFacebook>() {
        @Override
        public User_WithFacebook createFromParcel(Parcel in) {
            return new User_WithFacebook(in);
        }

        @Override
        public User_WithFacebook[] newArray(int size) {
            return new User_WithFacebook[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getid() {
        return id;
    }

    public void setid(String location) {
        this.id = location;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email_id);
        parcel.writeString(id);
        parcel.writeString(date_of_birth);

    }

}
