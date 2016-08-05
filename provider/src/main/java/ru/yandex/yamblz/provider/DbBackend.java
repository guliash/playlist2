package ru.yandex.yamblz.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import static ru.yandex.yamblz.provider.DbContract.Singers;

public class DbBackend {

    private DbOpenHelper mDbOpenHelper;

    public DbBackend(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    public DbBackend(DbOpenHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Nullable public Cursor getSingers(@Nullable String[] projection, @Nullable String selection,
                                       @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(Singers.TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertSingers(List<Singer> singers) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        boolean success = true;
        for(Singer singer : singers) {
            ContentValues cv = new ContentValues();
            cv.put(Singers.ID, singer.getId());
            cv.put(Singers.NAME, singer.getName());
            cv.put(Singers.DESCRIPTION, singer.getDescription());
            cv.put(Singers.TRACKS, singer.getTracks());
            cv.put(Singers.ALBUMS, singer.getAlbums());
            cv.put(Singers.COVER_SMALL, singer.getCover().getSmall());
            cv.put(Singers.COVER_BIG, singer.getCover().getBig());
            success &= (db.insert(Singers.TABLE_NAME, null, cv) != -1);
        }
        if(success) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
    }
}
