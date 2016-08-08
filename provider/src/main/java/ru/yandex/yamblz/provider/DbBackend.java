package ru.yandex.yamblz.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.yamblz.singerscontracts.Singer;
import ru.yandex.yamblz.singerscontracts.SingersContract;

import static ru.yandex.yamblz.provider.DbContract.*;
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
        String from = "(SELECT %s, %s, group_concat(%s, ',') AS %s, %s, %s, %s, %s, %s FROM %s LEFT JOIN " +
                "%s on %s=%s LEFT JOIN %s ON %s=%s GROUP BY %s)";
        /*
            (SELECT singers.id AS id, singers.name AS name, group_concat(genres.name, ',') AS genres,
             singers.tracks AS tracks, singers.albums AS albums, singers.description AS description,
             singers.cover_small AS cover_small, singers.cover_big AS cover_big FROM singers
             LEFT JOIN singers_genres on singers.id=singers_genres.singer_id LEFT JOIN genres
             ON singers_genres.genre_id=genres.id GROUP BY singers.id)
         */
        String fromFormatted = String.format(from,
                Singers.TABLE_NAME + "." + Singers.ID + " AS " + SingersContract.Singers.ID,
                Singers.TABLE_NAME + "." + Singers.NAME + " AS " + SingersContract.Singers.NAME,
                Genres.TABLE_NAME + "." + Genres.NAME,
                SingersContract.Singers.GENRES,
                Singers.TABLE_NAME + "." + Singers.TRACKS + " AS " + SingersContract.Singers.TRACKS,
                Singers.TABLE_NAME + "." + Singers.ALBUMS + " AS " + SingersContract.Singers.ALBUMS,
                Singers.TABLE_NAME + "." + Singers.DESCRIPTION + " AS " + SingersContract.Singers.DESCRIPTION,
                Singers.TABLE_NAME + "." + Singers.COVER_SMALL + " AS " + SingersContract.Singers.COVER_SMALL,
                Singers.TABLE_NAME + "." + Singers.COVER_BIG + " AS " + SingersContract.Singers.COVER_BIG,
                Singers.TABLE_NAME,
                SingersGenres.TABLE_NAME,
                Singers.TABLE_NAME + "." + Singers.ID,
                SingersGenres.TABLE_NAME + "." + SingersGenres.SINGER_ID,
                Genres.TABLE_NAME,
                SingersGenres.TABLE_NAME + "." + SingersGenres.GENRE_ID,
                Genres.TABLE_NAME + "." + Genres.ID,
                Singers.TABLE_NAME + "." + Singers.ID);
        SQLiteDatabase database = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(fromFormatted, projection, selection, selectionArgs, null,
                null, sortOrder);

        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean migrateSingers(List<Singer> singers) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        boolean success = true;

        truncateTable(Singers.TABLE_NAME);
        truncateTable(Genres.TABLE_NAME);
        truncateTable(SingersGenres.TABLE_NAME);

        success &= insertSingers(singers);
        success &= insertGenres(Singer.extractGenres(singers));
        insertArtistGenres(singers);
        if(success) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        return success;
    }

    void truncateTable(String tableName) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }

    public boolean insertGenre(String genre) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Genres.NAME, genre);
        return db.insert(Genres.TABLE_NAME, null, cv) != -1;
    }

    public boolean insertGenres(List<String> genres) {
        boolean success = true;
        for(String genre : genres) {
            success &= insertGenre(genre);
        }
        return success;
    }

    public boolean insertSinger(Singer singer) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Singers.ID, singer.getId());
        cv.put(Singers.NAME, singer.getName());
        cv.put(Singers.DESCRIPTION, singer.getDescription());
        cv.put(Singers.TRACKS, singer.getTracks());
        cv.put(Singers.ALBUMS, singer.getAlbums());
        cv.put(Singers.COVER_SMALL, singer.getCover().getSmall());
        cv.put(Singers.COVER_BIG, singer.getCover().getBig());
        return (db.insert(Singers.TABLE_NAME, null, cv) != -1);
    }

    public boolean insertSingers(List<Singer> singers) {
        boolean success = true;
        for(Singer singer : singers) {
            success &= insertSinger(singer);
        }
        return success;
    }

    public void insertArtistGenres(List<Singer> singers) {
        List<String> genres = Singer.extractGenres(singers);
        List<Long> ids = selectGenresIds(genres);
        Map<String, Long> genre2id = new HashMap<>();
        for(int i = 0; i < genres.size(); i++) {
            genre2id.put(genres.get(i), ids.get(i));
        }

        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        for(Singer singer : singers) {
            for(String genre : singer.getGenres()) {
                ContentValues cv = new ContentValues();
                cv.put(SingersGenres.SINGER_ID, singer.getId());
                cv.put(SingersGenres.GENRE_ID, genre2id.get(genre));
                db.insert(SingersGenres.TABLE_NAME, null, cv);
            }
        }
    }

    public List<Long> selectGenresIds(List<String> genres) {
        List<Long> ids = new ArrayList<>();
        for(String genre : genres) {
            ids.add(selectGenreId(genre));
        }
        return ids;
    }

    public long selectGenreId(String genre) {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Genres.TABLE_NAME, null, Genres.NAME + "=?",
                new String[] {genre}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(Genres.ID));
        }
        return -1;
    }
}
