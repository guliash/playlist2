package ru.yandex.yamblz.playlist2.di.modules;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.playlist2.CPDataProvider;
import ru.yandex.yamblz.playlist2.CPDataTransformer;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.DataTransformer;

@Module
public class AppModule {

    public static final String UI_HANDLER = "ui_handler";
    public static final String WORK_EXECUTOR = "worker";
    public static final String POST_EXECUTOR = "poster";

    private Context mContext;


    public AppModule(@NonNull Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    @Named(UI_HANDLER)
    Handler provideUiHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @Singleton
    @Named(WORK_EXECUTOR)
    Executor provideWorker() {
        return new ThreadPoolExecutor(4, 10, 120, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    @Provides
    @Singleton
    @Named(POST_EXECUTOR)
    Executor providePoster(@Named(UI_HANDLER) final Handler uiHandler) {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                uiHandler.post(command);
            }
        };
    }

    @Provides
    @Singleton
    DataTransformer provideDataTransformer() {
        return new CPDataTransformer();
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider(Context context, DataTransformer dataTransformer,
                                     @Named(WORK_EXECUTOR) Executor worker,
                                     @Named(POST_EXECUTOR) Executor poster) {
        return new CPDataProvider(context, dataTransformer, worker, poster);
    }

}
