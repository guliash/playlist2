package ru.yandex.yamblz.playlist2.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.structures.Singer;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersAdapter;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersPagerAdapter;
import ru.yandex.yamblz.playlist2.ui.fragments.DescFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.ListFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.PreviewFragment;
import ru.yandex.yamblz.playlist2.ui.fragments.TabsFragment;

public class MainActivity extends BaseActivity implements PreviewFragment.Callbacks, ListFragment.Callbacks {

    private boolean mPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getAppComponent().inject(this);

        mPortrait = findViewById(R.id.preview_fragment) == null;
        Log.e("TAG", "PORTRAIT " + mPortrait);

        if(mPortrait && savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, TabsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onMoreChosen(Singer singer) {
        if(mPortrait) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, DescFragment.newInstance(singer))
                    .addToBackStack(null)
                    .commit();
        } else {
            DescFragment descFragment = DescFragment.newInstance(singer);
            descFragment.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onSingerChosen(Singer singer) {
        PreviewFragment previewFragment = (PreviewFragment)getSupportFragmentManager()
                .findFragmentById(R.id.preview_fragment);
        previewFragment.setSinger(singer);
    }

    @Override
    public void onSingersShown(@NonNull List<Singer> singers) {
        PreviewFragment previewFragment = (PreviewFragment)getSupportFragmentManager()
                .findFragmentById(R.id.preview_fragment);
        previewFragment.setSinger(singers.get(0));
    }
}
