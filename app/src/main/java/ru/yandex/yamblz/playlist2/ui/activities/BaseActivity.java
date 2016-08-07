package ru.yandex.yamblz.playlist2.ui.activities;

import android.support.v7.app.AppCompatActivity;

import ru.yandex.yamblz.playlist2.MyApplication;
import ru.yandex.yamblz.playlist2.di.components.AppComponent;

public class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return ((MyApplication)getApplicationContext()).getAppComponent();
    }

}
