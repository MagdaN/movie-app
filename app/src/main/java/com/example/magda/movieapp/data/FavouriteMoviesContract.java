package com.example.magda.movieapp.data;

import android.provider.BaseColumns;

public class FavouriteMoviesContract {

    public static final class FavouriteMoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE  = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_MOVIE_ID = "movie_id";

    }
}
