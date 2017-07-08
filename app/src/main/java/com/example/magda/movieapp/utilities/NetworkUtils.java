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
package com.example.magda.movieapp.utilities;

import android.net.Uri;

import com.example.magda.movieapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the movie database servers.
 * The code is based on the code from udacities sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */

public final class NetworkUtils {

    private static final String YOUTUBE_BASE_URL =
            "https://www.youtube.com/watch";

    private static final  String YOUTUBE_PARAM = "v";

    private static final String MOVIE_DB_URL_MOVIE_DETAIL =
            "https://api.themoviedb.org/3/movie/";

    private static final String MOVIE_DB_URL_POPULAR =
            "https://api.themoviedb.org/3/movie/popular";

    private static final String MOVIE_DB_URL_TOP_RATED =
            "https://api.themoviedb.org/3/movie/top_rated";

    private static final String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    private final static String API_PARAM = "api_key";
    private final static String LANGUAGE_PARAM = "language";


    public static URL buildUrl(String sortByQuery) {

        String MOVIE_DB_BASE_URL = MOVIE_DB_URL_POPULAR;

        if(sortByQuery.equals("rates")) {
            MOVIE_DB_BASE_URL = MOVIE_DB_URL_TOP_RATED;
        }

        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildMovieReviewsUrl(String id) {

        String MOVIE_DB_BASE_URL = MOVIE_DB_URL_MOVIE_DETAIL + id + "/reviews";

        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static URL builYoutubeUrl(String key) {

        String YOUTUBE_URL = YOUTUBE_BASE_URL;

        Uri builtUri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_PARAM, key)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static URL buildMovieTrailersUrl(String id) {

        String MOVIE_DB_BASE_URL = MOVIE_DB_URL_MOVIE_DETAIL + id + "/videos";

        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}