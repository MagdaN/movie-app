package com.example.magda.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MovieDetailActivity extends AppCompatActivity {

    private MovieDBEntry mMovie;
    private TextView mMovieTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieRatings;
    private TextView mMovieSynopsis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mMovieRatings = (TextView) findViewById(R.id.tv_movie_detail_ratings);
        mMovieSynopsis = (TextView) findViewById(R.id.tv_movie_detail_synopsis);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null) {
            if(intentThatStartedThisActivity.hasExtra("movie_detail"))
            {
                mMovie = (MovieDBEntry) intentThatStartedThisActivity.getParcelableExtra("movie_detail");
                mMovieTitle.setText(mMovie.getmTitle());
                mMovieReleaseDate.setText(mMovie.getmReleaseDate());
                mMovieRatings.setText(mMovie.getmVoteAverage());
                mMovieSynopsis.setText(mMovie.getmOverview());
            }
        }
    }
}
