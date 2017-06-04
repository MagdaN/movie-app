package com.example.magda.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    private MovieDBEntry mMovie;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieReleaseDate;
    private TextView mMovieRatings;
    private TextView mMovieSynopsis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mMovieRatings = (TextView) findViewById(R.id.tv_movie_detail_ratings);
        mMovieSynopsis = (TextView) findViewById(R.id.tv_movie_detail_synopsis);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null) {
            if(intentThatStartedThisActivity.hasExtra("movie_detail"))
            {
                mMovie = (MovieDBEntry) intentThatStartedThisActivity.getParcelableExtra("movie_detail");
                mMovieTitle.setText(mMovie.getmTitle());

                String url = mMovie.getmPoster();

                Picasso.with(getApplicationContext()).load(url).into(mMoviePoster);

                mMovieReleaseDate.setText((mMovie.getmReleaseDate()).split("-")[0]);
                mMovieRatings.setText(mMovie.getmVoteAverage() + "/10");
                mMovieSynopsis.setText(mMovie.getmOverview());
            }
        }
    }
}
