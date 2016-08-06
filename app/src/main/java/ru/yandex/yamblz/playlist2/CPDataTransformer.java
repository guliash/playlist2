package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.yamblz.playlist2.SingersContract.*;

public class CPDataTransformer implements DataTransformer {
    @Override
    @NonNull public List<Singer> toSingers(@Nullable Cursor cursor) {
        List<Singer> singers = new ArrayList<>();
        if(cursor == null) {
            return singers;
        }
        cursor.moveToFirst();
        do {
            Singer.Builder builder = new Singer.Builder();
            builder.id(cursor.getInt(cursor.getColumnIndex(Singers.ID)))
                    .name(cursor.getString(cursor.getColumnIndex(Singers.NAME)))
                    .tracks(cursor.getInt(cursor.getColumnIndex(Singers.TRACKS)))
                    .albums(cursor.getInt(cursor.getColumnIndex(Singers.ALBUMS)))
                    .cover(new Cover(
                            cursor.getString(cursor.getColumnIndex(Singers.COVER_SMALL)),
                            cursor.getString(cursor.getColumnIndex(Singers.COVER_BIG))
                    ))
                    .description(cursor.getString(cursor.getColumnIndex(Singers.DESCRIPTION)));
            singers.add(builder.build());
        } while(cursor.moveToNext());
        cursor.close();
        return singers;
    }
}
