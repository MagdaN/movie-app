package com.example.magda.movieapp;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieDBTrailer implements Parcelable {

    private final String mName;
    private final String mKey;

    public MovieDBTrailer(String name, String key) {
        mName = name;
        mKey = key;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mKey);
    }

    private MovieDBTrailer(Parcel in) {
        mName = in.readString();
        mKey = in.readString();
    }

    public static final Parcelable.Creator<MovieDBTrailer> CREATOR = new Parcelable.Creator<MovieDBTrailer>() {
        public MovieDBTrailer createFromParcel(Parcel in) {
            return new MovieDBTrailer(in);
        }

        public MovieDBTrailer[] newArray(int size) {
            return new MovieDBTrailer[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public String getmKey() {
        return mKey;
    }
}
