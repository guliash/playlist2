package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    void onClick() {
        Log.e("TAG", "CLICK");
        Cursor cursor = getContentResolver().query(SingersContract.Singers.CONTENT_URI, null, null, null, null);
        Log.e("TAG", cursor.getCount() + " ");
    }
}
