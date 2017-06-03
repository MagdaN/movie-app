package com.example.magda.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by magda on 03.06.17.
 */

public class MovieDBEntry implements Parcelable {

    private String mTitle;
    private String mPoster;
    private String mOverview;
    private String mVoteAverage;
    private String mReleaseDate;

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

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }
}
