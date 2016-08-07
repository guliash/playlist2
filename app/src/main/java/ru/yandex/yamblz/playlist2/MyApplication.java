package ru.yandex.yamblz.playlist2;

import android.app.Application;

import ru.yandex.yamblz.playlist2.di.components.AppComponent;
import ru.yandex.yamblz.playlist2.di.components.DaggerAppComponent;
import ru.yandex.yamblz.playlist2.di.modules.AppModule;

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
