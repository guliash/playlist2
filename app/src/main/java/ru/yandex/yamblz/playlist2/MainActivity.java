package ru.yandex.yamblz.playlist2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private boolean mPortrait;

    @Nullable
    @BindView(R.id.singers_pager)
    ViewPager singersPager;

    @Nullable
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private DataProvider mDataProvider;

    private Handler mUiHandler;
    private DataProvider.Callback<List<Singer>> mSingersCallback = new DataProvider.Callback<List<Singer>>() {
        @Override
        public void postResult(List<Singer> result) {
            singersPager.setAdapter(new SingersPagerAdapter(getSupportFragmentManager(), result));
            tabLayout.setupWithViewPager(singersPager);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mUiHandler = new Handler();

        mDataProvider = new CPDataProvider(this, new CPDataTransformer(), worker(), poster());

        mPortrait = findViewById(R.id.desc_holder) == null;

        if(mPortrait) {
            mDataProvider.getSingers(mSingersCallback);
        }

    }

    private Executor worker() {
        return new ThreadPoolExecutor(4, 10, 120, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    private Executor poster() {
        return new Executor() {
            @Override
            public void execute(final Runnable command) {
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        command.run();
                    }
                });
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataProvider.cancel(mSingersCallback);
    }
}
