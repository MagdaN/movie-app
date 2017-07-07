package com.example.magda.movieapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.magda.movieapp.data.FavouriteMoviesContract.*;


public class FavouriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="movies.db";
    private static final int DATABASE_VERSION = 2;

    public FavouriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIELIST_TABLE = "CREATE TABLE " + FavouriteMoviesEntry.TABLE_NAME + " (" +
                FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavouriteMoviesEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                FavouriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavouriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_MOVIELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
