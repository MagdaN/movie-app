package com.example.magda.movieapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.magda.movieapp.MovieDBEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by magda on 02.06.17.
 */

public final class OpenMoviesJsonUtils {

    private static final String TAG = OpenMoviesJsonUtils.class.getSimpleName();

    public static MovieDBEntry[] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String MOVIE_LIST = "results";
        final String SUCCESS = "success";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        MovieDBEntry[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(SUCCESS)) {
            if (movieJson.getString(SUCCESS) == "false") {
                return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);


        parsedMovieData = new MovieDBEntry[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {


            JSONObject movie = movieArray.getJSONObject(i);
            String title = movie.getString(ORIGINAL_TITLE);
            String poster = movie.getString(POSTER_PATH);
            String overview = movie.getString(OVERVIEW);
            String voteAverage = movie.getString(VOTE_AVERAGE);
            String releaseDate = movie.getString(RELEASE_DATE);

            parsedMovieData[i] = new MovieDBEntry(title, poster, overview, voteAverage, releaseDate);
        }

        return parsedMovieData;
    }

}
