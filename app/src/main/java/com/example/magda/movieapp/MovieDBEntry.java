package com.example.magda.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDBEntry implements Parcelable {

    private final String mTitle;
    private final String mPoster;
    private final String mOverview;
    private final String mVoteAverage;
    private final String mReleaseDate;

    public MovieDBEntry(String title, String poster, String overview,
                 String voteAverage, String releaseDate){
        mTitle = title;
        mPoster = poster;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mOverview);
        dest.writeString(mVoteAverage);
        dest.writeString(mReleaseDate);
    }

    private MovieDBEntry(Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    public static final Parcelable.Creator<MovieDBEntry> CREATOR = new Parcelable.Creator<MovieDBEntry>() {
        public MovieDBEntry createFromParcel(Parcel in) {
            return new MovieDBEntry(in);
        }

        public MovieDBEntry[] newArray(int size) {
            return new MovieDBEntry[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

}
