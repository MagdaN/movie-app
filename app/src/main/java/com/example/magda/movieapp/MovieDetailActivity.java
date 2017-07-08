package com.example.magda.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magda.movieapp.data.FavouriteMoviesContract;
import com.example.magda.movieapp.data.FavouriteMoviesDbHelper;
import com.example.magda.movieapp.utilities.NetworkUtils;
import com.example.magda.movieapp.utilities.OpenMoviesJsonUtils;

import com.squareup.picasso.Picasso;

import java.net.URL;

import static java.lang.Long.parseLong;

/**
 * The code is based on the code from udacities sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */


public class MovieDetailActivity extends AppCompatActivity {

    MovieDBEntry mMovie;
    private SQLiteDatabase mDb;
    private ReviewAdapter mReviewAdapter;
    LinearLayout mReviews;
    boolean mIsFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        TextView mMovieTitle;
        ImageView mMoviePoster;
        TextView mMovieReleaseDate;
        TextView mMovieRatings;
        TextView mMovieSynopsis;
        RecyclerView mRecyclerView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mMovieRatings = (TextView) findViewById(R.id.tv_movie_detail_ratings);
        mMovieSynopsis = (TextView) findViewById(R.id.tv_movie_detail_synopsis);
        mReviews = (LinearLayout) findViewById(R.id.lv_reviews);

        Intent intentThatStartedThisActivity = getIntent();

        FavouriteMoviesDbHelper dbHelper = new FavouriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_reviews);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter(this);

        mRecyclerView.setAdapter(mReviewAdapter);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie_detail")) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra("movie_detail");

                mIsFavourite = hasFavourite(mMovie);

                mMovieTitle.setText(mMovie.getmTitle());

                getSupportActionBar().setTitle(getResources().getString(R.string.movie_detail_title));

                String url = mMovie.getmPoster();

                Picasso.with(getApplicationContext()).load(url).into(mMoviePoster);

                mMovieReleaseDate.setText((mMovie.getmReleaseDate()).split("-")[0]);
                String ratings = mMovie.getmVoteAverage();
                Resources res = getResources();
                mMovieRatings.setText(String.format(res.getString(R.string.votes), ratings));
                mMovieSynopsis.setText(mMovie.getmOverview());

                new FetchMovieDetailTask().execute(mMovie.getmMovieDbId());
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(mIsFavourite) {
            menu.removeItem(R.id.add_to_favourite_movies);
        } else {
            menu.removeItem(R.id.remove_from_favourite_movies);
        }
        return true;
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
            addMovieToFavouritesIfNotExists(mMovie);
        }

        if (id == R.id.remove_from_favourite_movies) {
            removeMovieFromFavourites(mMovie);
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean hasFavourite(MovieDBEntry movieDBEntry) {

        String id = movieDBEntry.getmMovieDbId();
        String selectQuery = "SELECT  * FROM " + FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME + " WHERE "
                + FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " =?";

        Cursor cursor = mDb.rawQuery(selectQuery, new String[]{id});

        boolean hasObject = false;

        if (cursor.moveToFirst()) {
            hasObject = true;
        }

        cursor.close();
        return hasObject;
    }

    public int removeMovieFromFavourites(MovieDBEntry movieDBEntry) {

        String id = movieDBEntry.getmMovieDbId();

        int result = mDb.delete(FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + "=" + id, null);

        if (result > 0) {

            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_removed_favourite);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            mIsFavourite = false;
            invalidateOptionsMenu();
        }

        return result;

    }

    public long addMovieToFavouritesIfNotExists(MovieDBEntry movieDBEntry) {

        if (!mIsFavourite) {
            ContentValues cv = new ContentValues();
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_TITLE, movieDBEntry.getmTitle());
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_POSTER, movieDBEntry.getmPoster());
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_RELEASE_DATE, movieDBEntry.getmReleaseDate());
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE, movieDBEntry.getmVoteAverage());
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_OVERVIEW, movieDBEntry.getmOverview());
            cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID, movieDBEntry.getmMovieDbId());
            long result = mDb.insert(FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME, null, cv);

            if (result != -1) {
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.toast_added_favourite);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                mIsFavourite = true;
                invalidateOptionsMenu();
            }

            return result;
        } else {
            return -1;
        }
    }

    private class FetchMovieDetailTask extends AsyncTask<String, Void, MovieDBReview[]> {

        @Override
        protected void onPostExecute(MovieDBReview[] reviewData) {
            if (reviewData.length != 0 & reviewData != null) {
                mReviews.setVisibility(View.VISIBLE);
                mReviewAdapter.setmReviewData(reviewData);
            }
        }

        @Override
        protected MovieDBReview[] doInBackground(String... params) {

            String id = params[0];

            URL movieRequestUrl = NetworkUtils
                    .buildMovieReviewsUrl(id);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                return OpenMoviesJsonUtils.getReviewsStringsFromJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
    }

}
