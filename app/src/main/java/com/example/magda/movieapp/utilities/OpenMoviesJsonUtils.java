package com.example.magda.movieapp.utilities;

import com.example.magda.movieapp.MovieDBEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * These utilities will be used to parse the json response to an Array of MovieDBEntry objects.
 * The code is based on the code from udacities sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */


public final class OpenMoviesJsonUtils {

    public static MovieDBEntry[] getSimpleMovieStringsFromJson(String movieJsonStr)
            throws JSONException {

        final String MOVIE_POSTER_BASE = "http://image.tmdb.org/t/p/";
        final String MOVIE_POSTER_SIZE = "w185";
        final String MOVIE_LIST = "results";
        final String SUCCESS = "success";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        MovieDBEntry[] parsedMovieData;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(SUCCESS)) {
            if (movieJson.getString(SUCCESS).equals("false")) {
                return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);


        parsedMovieData = new MovieDBEntry[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {


            JSONObject movie = movieArray.getJSONObject(i);
            String title = movie.getString(ORIGINAL_TITLE);
            String poster = MOVIE_POSTER_BASE + MOVIE_POSTER_SIZE + movie.getString(POSTER_PATH);
            String overview = movie.getString(OVERVIEW);
            String voteAverage = movie.getString(VOTE_AVERAGE);
            String releaseDate = movie.getString(RELEASE_DATE);

            parsedMovieData[i] = new MovieDBEntry(title, poster, overview, voteAverage, releaseDate);
        }

        return parsedMovieData;
    }

}
