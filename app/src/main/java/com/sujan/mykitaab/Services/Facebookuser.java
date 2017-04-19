package com.sujan.mykitaab.Services;

/**
 * Created by macbookpro on 4/18/17.
 */

public class Facebookuser {

    int id;
    String name;

    public Facebookuser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
