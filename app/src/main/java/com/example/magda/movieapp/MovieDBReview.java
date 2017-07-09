package com.example.magda.movieapp;

public class MovieDBReview {

    private final String mAuthor;
    private final String mContent;

    public MovieDBReview(String author, String conent) {
        mAuthor = author;
        mContent = conent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

}
