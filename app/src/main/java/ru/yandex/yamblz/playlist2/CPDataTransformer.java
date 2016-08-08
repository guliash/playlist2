package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.yandex.yamblz.singerscontracts.Cover;
import ru.yandex.yamblz.singerscontracts.Singer;

import static ru.yandex.yamblz.singerscontracts.SingersContract.*;

public class CPDataTransformer implements DataTransformer {

    @Override
    @NonNull public List<Singer> toSingers(@Nullable Cursor cursor) {
        List<Singer> singers = new ArrayList<>();
        if(cursor == null) {
            return singers;
        }
        cursor.moveToFirst();
        do {
            singers.add(Singer.readSinger(cursor));
        } while(cursor.moveToNext());
        cursor.close();
        return singers;
    }

    @Nullable
    @Override
    public Singer toSinger(@Nullable Cursor cursor) {
        if(cursor == null) {
            return null;
        }
        cursor.moveToFirst();

        Singer singer = Singer.readSinger(cursor);
        cursor.close();
        return singer;
    }
}
