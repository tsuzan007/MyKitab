package com.sujan.mykitaab.POJOClasses;

/**
 * Created by macbookpro on 4/19/17.
 */

public class FeedClass {
    String story;
    String created_time;
    String id;
    String Url;

    public FeedClass(String story, String created_time, String id, String Url) {
        this.story = story;
        this.created_time = created_time;
        this.id = id;
        this.Url=Url;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
