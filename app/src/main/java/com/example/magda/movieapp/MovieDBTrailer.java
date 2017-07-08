package com.example.magda.movieapp;


public class MovieDBTrailer {

    String mName;
    String mSite;
    String mKey;
    String mType;

    public MovieDBTrailer(String name, String site, String key, String type) {
        mName = name;
        mSite = site;
        mKey = key;
        mType = type;
    }

    public String getmName() {
        return mName;
    }

    public String getmSite() {
        return mSite;
    }

    public String getmKey() {
        return mKey;
    }

    public String getmType() {
        return mType;
    }
}
