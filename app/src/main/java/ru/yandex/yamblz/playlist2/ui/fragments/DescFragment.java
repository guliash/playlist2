package ru.yandex.yamblz.playlist2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.structures.Singer;

public class DescFragment extends BaseFragment {

    private static final String ID_EXTRA = "index";
    private static final String SINGER_EXTRA = "singer";

    public static DescFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID_EXTRA, id);

        DescFragment descFragment = new DescFragment();
        descFragment.setArguments(bundle);

        return descFragment;
    }

    public static DescFragment newInstance(Singer singer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SINGER_EXTRA, singer);

        DescFragment descFragment = new DescFragment();
        descFragment.setArguments(bundle);

        return descFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desc_fragment, container, false);
        return view;
    }
}
