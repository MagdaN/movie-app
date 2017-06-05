package com.example.magda.movieapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        MovieDBEntry mMovie;
        TextView mMovieTitle;
        ImageView mMoviePoster;
        TextView mMovieReleaseDate;
        TextView mMovieRatings;
        TextView mMovieSynopsis;

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
                mMovie = intentThatStartedThisActivity.getParcelableExtra("movie_detail");
                mMovieTitle.setText(mMovie.getmTitle());

                String url = mMovie.getmPoster();

                Picasso.with(getApplicationContext()).load(url).into(mMoviePoster);

                mMovieReleaseDate.setText((mMovie.getmReleaseDate()).split("-")[0]);
                String ratings = mMovie.getmVoteAverage();
                Resources res = getResources();
                mMovieRatings.setText(String.format(res.getString(R.string.votes), ratings));
                mMovieSynopsis.setText(mMovie.getmOverview());
            }
        }
    }
}
