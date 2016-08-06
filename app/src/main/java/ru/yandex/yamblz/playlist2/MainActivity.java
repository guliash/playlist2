package ru.yandex.yamblz.playlist2;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static ru.yandex.yamblz.playlist2.SingersContract.*;

public class MainActivity extends AppCompatActivity {

    private boolean mPortrait;

    @BindView(R.id.singers_pager)
    ViewPager singersPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPortrait = findViewById(R.id.desc_holder) == null;
        Cursor cursor = getContentResolver().query(Singers.CONTENT_URI, null, null, null, null);
        Log.e("TAG", "COUNT " + cursor.getCount());
        List<Singer> singers = transformCursor(cursor);


        if(mPortrait) {
            singersPager.setAdapter(new SingersPagerAdapter(getSupportFragmentManager(), singers));
            tabLayout.setupWithViewPager(singersPager);
        }

    }

    private List<Singer> transformCursor(Cursor cursor) {
        List<Singer> singers = new ArrayList<>();
        if(cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Singer.Builder builder = new Singer.Builder();
                builder.id(cursor.getInt(cursor.getColumnIndex(Singers.ID)))
                        .name(cursor.getString(cursor.getColumnIndex(Singers.NAME)))
                        .tracks(cursor.getInt(cursor.getColumnIndex(Singers.TRACKS)))
                        .albums(cursor.getInt(cursor.getColumnIndex(Singers.ALBUMS)))
                        .cover(new Cover(
                                cursor.getString(cursor.getColumnIndex(Singers.COVER_SMALL)),
                                cursor.getString(cursor.getColumnIndex(Singers.COVER_BIG))
                        ))
                        .description(cursor.getString(cursor.getColumnIndex(Singers.DESCRIPTION)));
                singers.add(builder.build());
                cursor.moveToNext();
            }
        }
        return singers;
    }
}
