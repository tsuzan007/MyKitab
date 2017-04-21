package com.sujan.mykitaab.HelperClass;

/**
 * Created by macbookpro on 4/17/17.
 */

public class User_WithFacebook {

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
}
