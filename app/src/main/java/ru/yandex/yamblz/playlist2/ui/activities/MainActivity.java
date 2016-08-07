package ru.yandex.yamblz.playlist2.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.playlist2.CPDataProvider;
import ru.yandex.yamblz.playlist2.CPDataTransformer;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.structures.Singer;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersAdapter;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersPagerAdapter;
import ru.yandex.yamblz.playlist2.ui.fragments.PhotoFragment;

public class MainActivity extends BaseActivity implements SingersAdapter.Callbacks {

    private boolean mPortrait;

    @Nullable
    @BindView(R.id.singers_pager)
    ViewPager singersPager;

    @Nullable
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Nullable
    @BindView(R.id.singers_list)
    RecyclerView singersList;

    @Inject
    DataProvider mDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getAppComponent().inject(this);

        mPortrait = findViewById(R.id.desc_holder) == null;

        if(!mPortrait) {
            singersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataProvider.getSingers(mSingersCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataProvider.cancel(mSingersCallback);
    }

    private DataProvider.Callback<List<Singer>> mSingersCallback = new DataProvider.Callback<List<Singer>>() {
        @Override
        public void postResult(List<Singer> result) {
            if(mPortrait) {
                singersPager.setAdapter(new SingersPagerAdapter(getSupportFragmentManager(), result));
                tabLayout.setupWithViewPager(singersPager);
            } else {
                singersList.setAdapter(new SingersAdapter(MainActivity.this, result));
                if(result != null && result.size() > 0) {
                    onSingerChosen(result.get(0));
                }
            }
        }
    };

    @Override
    public void onSingerChosen(Singer singer) {
        PhotoFragment photoFragment = (PhotoFragment)getSupportFragmentManager()
                .findFragmentById(R.id.desc_holder);
        photoFragment.setSinger(singer);
    }
}
