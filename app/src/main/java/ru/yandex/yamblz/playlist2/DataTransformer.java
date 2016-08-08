package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.yandex.yamblz.singerscontracts.Singer;

public interface DataTransformer {

    @NonNull List<Singer> toSingers(@Nullable Cursor cursor);

    @Nullable Singer toSinger(@Nullable Cursor cursor);

}
