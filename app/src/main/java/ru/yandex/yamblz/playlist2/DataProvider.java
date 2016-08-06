package ru.yandex.yamblz.playlist2;

import java.util.List;

public interface DataProvider {

    interface Callback<T>{
        void postResult(T result);
    }

    void getSingers(Callback<List<Singer>> callback);

    void cancel(Callback callback);

}
