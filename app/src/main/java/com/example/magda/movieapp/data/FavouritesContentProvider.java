package com.example.magda.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.magda.movieapp.data.FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME;

public class FavouritesContentProvider extends ContentProvider {

    private FavouriteMoviesDbHelper mFavouriteMoviesHelper;

    private static final int FAVOURITES = 100;
    private static final int FAVOURITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavouriteMoviesContract.AUTHORITY, FavouriteMoviesContract.PATH_FAVOURITES, FAVOURITES);
        uriMatcher.addURI(FavouriteMoviesContract.AUTHORITY, FavouriteMoviesContract.PATH_FAVOURITES + "/#", FAVOURITES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavouriteMoviesHelper = new FavouriteMoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mFavouriteMoviesHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor cursor;

        switch (match) {
            case FAVOURITES:
                cursor = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE
                );
                break;
            case FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " =?";
                String[] mSelectionArgs = new String[]{id};
                cursor = db.query(
                        TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_VOTE_AVERAGE
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mFavouriteMoviesHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case FAVOURITES:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mFavouriteMoviesHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int favouritesDeleted;

        switch (match) {
            case FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                favouritesDeleted = db.delete(
                    TABLE_NAME,
                    FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " =?",
                    new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(favouritesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return favouritesDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
