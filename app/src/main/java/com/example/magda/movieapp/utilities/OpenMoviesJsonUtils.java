package com.example.magda.movieapp.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by magda on 02.06.17.
 */

public final class OpenMoviesJsonUtils {

    public static String[] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String MOVIE_LIST = "results";
        final String MESSAGE_CODE = "status_code";
        final String SUCCESS = "success";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(SUCCESS)) {
            if (movieJson.getString(SUCCESS) == "false") {
                return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            String title;

            JSONObject movie = movieArray.getJSONObject(i);
            title = movie.getString("title");

            parsedMovieData[i] = title;
        }

        return parsedMovieData;
    }

}
