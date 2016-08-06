package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface DataTransformer {

    @NonNull List<Singer> toSingers(@Nullable Cursor cursor);

}
