/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.magda.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.magda.movieapp.data.FavouriteMoviesContract;
import com.example.magda.movieapp.utilities.NetworkUtils;
import com.example.magda.movieapp.utilities.OpenMoviesJsonUtils;

import java.net.URL;

/**
 * The code is based on the code from udacity's sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private String mSorting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        getSupportActionBar().setTitle(getResources().getString(R.string.movie_list_title));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movies);

        int numberOfColumns = 4;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        }

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, this);

        mRecyclerView.setAdapter(mMovieAdapter);

        if (savedInstanceState != null) {
            mSorting = savedInstanceState.getString("CURRENT_SORTING");
        } else {
            mSorting = "popularity";
        }

        loadMovieData();
    }

    @Override
    public void onResume() {
        if(mSorting.equals("favourites")){
            loadMovieData();
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by_popularity) {
            mSorting = "popularity";
            loadMovieData();
        }

        if (id == R.id.sort_by_votes) {
            mSorting = "rates";
            loadMovieData();
        }

        if (id == R.id.favourites) {
            mSorting = "favourites";
            loadMovieData();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieDBEntry movie) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartMovieDetailActivity = new Intent(context, destinationClass);
        intentToStartMovieDetailActivity.putExtra("movie_detail", movie);
        startActivity(intentToStartMovieDetailActivity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CURRENT_SORTING", mSorting);
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMoviesTask().execute(mSorting);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private Cursor getFavouritesFromDb() {

        try {
            return getContentResolver().query(FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private class FetchMoviesTask extends AsyncTask<String, Void, MovieDBEntry[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(MovieDBEntry[] movieData) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                mMovieAdapter.setmMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }

        @Override
        protected MovieDBEntry[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            if (params[0].equals("favourites")) {

                MovieDBEntry[] retrievedMovieData;

                Cursor cursor = getFavouritesFromDb();
                int size = cursor != null ? cursor.getCount() : 0;
                retrievedMovieData = new MovieDBEntry[size];

                int i = 0;

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_TITLE));
                        String poster = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_POSTER));
                        String overview = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_OVERVIEW));
                        String voteAverage = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE));
                        String releaseDate = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_RELEASE_DATE));
                        String movieId = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID));

                        retrievedMovieData[i] = new MovieDBEntry(title, poster, overview, voteAverage, releaseDate, movieId);

                        i++;

                    } while (cursor.moveToNext());
                }

                if(cursor != null) {
                    cursor.close();
                }


                return retrievedMovieData;

            } else {

                String sortQuery = params[0];
                URL movieRequestUrl = NetworkUtils
                        .buildUrl(sortQuery);

                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieRequestUrl);

                    return OpenMoviesJsonUtils
                            .getSimpleMovieStringsFromJson(jsonMovieResponse);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

}
