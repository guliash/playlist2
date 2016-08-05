package ru.yandex.yamblz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import static ru.yandex.yamblz.provider.SingersContract.AUTHORITY;
import static ru.yandex.yamblz.provider.SingersContract.Singers;

public class SingersContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int GET_SINGERS = 1;
    private static final int GET_SINGERS_BY_ID = 2;

    static {
        sUriMatcher.addURI(AUTHORITY, Singers.TABLE_NAME, GET_SINGERS);

        sUriMatcher.addURI(AUTHORITY, Singers.TABLE_NAME + "/#", GET_SINGERS_BY_ID);
    }

    private static final String SINGERS_INSERTED_KEY = "singers_inserted";

    private DbBackend mDbBackend;

    @Override
    public boolean onCreate() {
        Log.e("TAG", "ON CREATE CP");
        mDbBackend = new DbBackend(new DbOpenHelper(getContext()));
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(!checkIfSingersWereInserted()) {
            mDbBackend.insertSingers(retrieveSingers());
            setSingersInserted();
        }
        switch (sUriMatcher.match(uri)) {
            case GET_SINGERS:
                return mDbBackend.getSingers();
            case GET_SINGERS_BY_ID:
                throw new IllegalArgumentException("Not implemented- " + uri.toString());
            default:
                throw new IllegalArgumentException("Wrong query - " + uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case GET_SINGERS:
                return Singers.CONTENT_TYPE;
            case GET_SINGERS_BY_ID:
                return Singers.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private boolean checkIfSingersWereInserted() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ProviderApp.PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SINGERS_INSERTED_KEY, false);
    }

    private void setSingersInserted() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(
                ProviderApp.PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SINGERS_INSERTED_KEY, true);
        editor.apply();
    }

    private List<Singer> retrieveSingers() {
        return new SingersProvider(getContext()).getSingers();
    }
}
