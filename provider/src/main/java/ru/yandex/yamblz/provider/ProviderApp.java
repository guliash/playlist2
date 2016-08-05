package ru.yandex.yamblz.provider;

import android.app.Application;
import android.util.Log;

public class ProviderApp extends Application {

    public static final String PREFERENCES = "prefs";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "ON CREATE APP");
    }
}
