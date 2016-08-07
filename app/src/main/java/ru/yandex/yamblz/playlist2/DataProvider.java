package ru.yandex.yamblz.playlist2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.yandex.yamblz.playlist2.structures.Singer;

public interface DataProvider {

    interface Callback<T>{
        void postResult(@Nullable T result);
    }

    void getSingers(@NonNull Callback<List<Singer>> callback);

    void getSinger(int singerId, @NonNull Callback<Singer> callback);

    void cancel(@NonNull Callback callback);

}
