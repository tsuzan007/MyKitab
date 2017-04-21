package com.sujan.mykitaab.HelperClass;

/**
 * Created by macbookpro on 4/19/17.
 */

public class FeedClass {
    String story;
    String created_time;
    String id;

    public FeedClass(String story, String created_time, String id) {
        this.story = story;
        this.created_time = created_time;
        this.id = id;
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
}
