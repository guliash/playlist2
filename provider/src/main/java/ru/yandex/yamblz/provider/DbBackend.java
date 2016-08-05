package ru.yandex.yamblz.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ru.yandex.yamblz.provider.DbContract.Singers;

public class DbBackend {

    private DbOpenHelper mDbOpenHelper;

    public DbBackend(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    public DbBackend(DbOpenHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    public Cursor getSingers() {
        return getSingers("");
    }

    @Nullable public Cursor getSingers(@NonNull String prefixName) {
        SQLiteDatabase database = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(Singers.TABLE_NAME, null, Singers.NAME + " LIKE ?", new String[] {
                prefixName + "%"}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
