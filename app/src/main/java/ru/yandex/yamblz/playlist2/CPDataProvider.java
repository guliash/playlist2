package ru.yandex.yamblz.playlist2;

import android.content.ContentUris;
import android.content.Context;
import android.support.annotation.AnyThread;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import ru.yandex.yamblz.playlist2.structures.Singer;

import static ru.yandex.yamblz.singerscontracts.SingersContract.Singers;

/**
 * Content provider based {@link DataProvider}
 */
public class CPDataProvider implements DataProvider {

    private Context mContext;
    private DataTransformer mDataTransformer;
    private Executor mWorker, mPoster;
    private Set<Callback> mCallbacks = new HashSet<>(0);

    @MainThread
    public CPDataProvider(Context context, DataTransformer dataTransformer, Executor worker,
                          Executor poster) {
        mContext = context;
        mDataTransformer = dataTransformer;
        mWorker = worker;
        mPoster = poster;
    }

    @MainThread
    private void persistCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @MainThread
    private boolean checkIfSubscribed(Callback callback) {
        return mCallbacks.contains(callback);
    }

    @MainThread
    @Override
    public void getSingers(final Callback<List<Singer>> callback) {
        persistCallback(callback);
        mWorker.execute(new Runnable() {
            @Override
            public void run() {
                List<Singer> singers = mDataTransformer.toSingers(mContext.getContentResolver().query(
                        Singers.CONTENT_URI, null, null, null, null));
                postResult(callback, singers);
            }
        });
    }

    @Override
    public void getSinger(final int singerId, @NonNull final Callback<Singer> callback) {
        persistCallback(callback);
        mWorker.execute(new Runnable() {
            @Override
            public void run() {
                Singer singer = mDataTransformer.toSinger(mContext.getContentResolver().query(
                        ContentUris.withAppendedId(Singers.CONTENT_URI, singerId),
                        null, null, null, null));
                postResult(callback, singer);
            }
        });
    }

    @MainThread
    @Override
    public void cancel(Callback callback) {
        mCallbacks.remove(callback);
    }

    @AnyThread
    private <T> void postResult(final Callback<T> callback, final T result) {
        mPoster.execute(new Runnable() {
            @Override
            public void run() {
                if(checkIfSubscribed(callback)) {
                    callback.postResult(result);
                    cancel(callback);
                }
            }
        });
    }
}
