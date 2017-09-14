package com.sujan.mykitaab.POJOClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookpro on 4/19/17.
 */

public class FeedClass implements Parcelable{
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

    protected FeedClass(Parcel in) {
        story = in.readString();
        created_time = in.readString();
        id = in.readString();
        Url = in.readString();
    }

    public static final Creator<FeedClass> CREATOR = new Creator<FeedClass>() {
        @Override
        public FeedClass createFromParcel(Parcel in) {
            return new FeedClass(in);
        }

        @Override
        public FeedClass[] newArray(int size) {
            return new FeedClass[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(story);
        parcel.writeString(created_time);
        parcel.writeString(id);
        parcel.writeString(Url);
    }
}
