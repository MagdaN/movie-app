package com.example.magda.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magda.movieapp.data.FavouriteMoviesContract;
import com.example.magda.movieapp.data.FavouriteMoviesDbHelper;
import com.squareup.picasso.Picasso;

/**
 * The code is based on the code from udacities sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */


public class MovieDetailActivity extends AppCompatActivity {

    MovieDBEntry mMovie;
    private SQLiteDatabase mDb;


    @Override
    public void onCreate(Bundle savedInstanceState) {

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

        FavouriteMoviesDbHelper dbHelper = new FavouriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        if(intentThatStartedThisActivity != null) {
            if(intentThatStartedThisActivity.hasExtra("movie_detail"))
            {
                mMovie = intentThatStartedThisActivity.getParcelableExtra("movie_detail");
                mMovieTitle.setText(mMovie.getmTitle());

                getSupportActionBar().setTitle(getResources().getString(R.string.movie_detail_title));

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_to_favourite_movies) {
            long value = addMovieToFavourites(mMovie);
            Log.v("ADDED_TO_DB", Long.toString(value));
        }

        return super.onOptionsItemSelected(item);
    }

    public long addMovieToFavourites(MovieDBEntry movieDBEntry) {
        ContentValues cv = new ContentValues();
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_TITLE, movieDBEntry.getmTitle());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_POSTER, movieDBEntry.getmPoster());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_RELEASE_DATE, movieDBEntry.getmReleaseDate());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE, movieDBEntry.getmVoteAverage());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_OVERVIEW, movieDBEntry.getmOverview());
        return mDb.insert(FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME, null, cv);
    }
}
