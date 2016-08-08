package ru.yandex.yamblz.playlist2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersPagerAdapter;
import ru.yandex.yamblz.singerscontracts.Singer;

public class TabsFragment extends BaseFragment {

    @BindView(R.id.singers_pager)
    ViewPager singersPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    DataProvider mDataProvider;

    public static TabsFragment newInstance() {

        Bundle args = new Bundle();

        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mDataProvider.getSingers(mSingersCallback);
    }

    private DataProvider.Callback<List<Singer>> mSingersCallback = new DataProvider.Callback<List<Singer>>() {
        @Override
        public void postResult(List<Singer> result) {
            //TODO why need child FM?
            singersPager.setAdapter(new SingersPagerAdapter(getChildFragmentManager(), result));
            tabLayout.setupWithViewPager(singersPager);
        }
    };
}
