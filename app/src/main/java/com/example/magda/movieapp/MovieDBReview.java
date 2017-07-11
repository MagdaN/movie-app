package com.example.magda.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDBReview implements Parcelable {

    private final String mAuthor;
    private final String mContent;

    public MovieDBReview(String author, String conent) {
        mAuthor = author;
        mContent = conent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
    }

    public static final Parcelable.Creator<MovieDBReview> CREATOR = new Parcelable.Creator<MovieDBReview>() {
        public MovieDBReview createFromParcel(Parcel in) {
            return new MovieDBReview(in);
        }

        public MovieDBReview[] newArray(int size) {
            return new MovieDBReview[size];
        }
    };

    private MovieDBReview(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }
}
