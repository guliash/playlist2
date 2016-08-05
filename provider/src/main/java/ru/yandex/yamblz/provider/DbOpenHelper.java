package ru.yandex.yamblz.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import static ru.yandex.yamblz.provider.DbContract.*;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSingersTable(db);
        createGenresTable(db);
        createSingersGenresTable(db);
        addIndexes(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createSingersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Singers.TABLE_NAME + "(" +
            Singers.ID + " INTEGER PRIMARY KEY, " +
            Singers.NAME + " TEXT NOT NULL, " +
            Singers.DESCRIPTION + " TEXT, " +
            Singers.TRACKS + " INTEGER, " +
            Singers.ALBUMS + " INTEGER, " +
            Singers.COVER_SMALL + " TEXT, " +
            Singers.COVER_BIG + " TEXT)");
    }

    private void createGenresTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Genres.TABLE_NAME + "(" +
            Genres.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Genres.NAME + "TEXT)");
    }

    private void createSingersGenresTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SingersGenres.TABLE_NAME + "(" +
            SingersGenres.SINGER_ID + " INTEGER REFERENCES " + Singers.TABLE_NAME +
                "(" + Singers.ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
            SingersGenres.GENRE_ID + " INTEGER REFERENCES " + Genres.TABLE_NAME +
                "(" + Genres.ID + ") ON UPDATE CASCADE ON DELETE CASCADE)");
    }

    private void addIndexes(SQLiteDatabase db) {
        db.execSQL("CREATE INDEX " + Singers.NAME_INDEX + " ON " + Singers.TABLE_NAME +
                "(" + Singers.NAME + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
