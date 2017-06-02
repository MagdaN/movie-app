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

    public static String[] getSimpleMovieStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_TEMPERATURE = "temp";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";

        final String OWM_MESSAGE_CODE = "cod";

        String[] parsedWeatherData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        parsedWeatherData = new String[weatherArray.length()];

        for (int i = 0; i < weatherArray.length(); i++) {

            String description;

            JSONObject dayForecast = weatherArray.getJSONObject(i);

            JSONObject weatherObject =
                    dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            parsedWeatherData[i] = description;
        }

        return parsedWeatherData;
    }

    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        return null;
    }

}
