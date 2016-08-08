package ru.yandex.yamblz.playlist2.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.singerscontracts.Singer;

public class PreviewFragment extends BaseFragment {

    private static final String SINGER_EXTRA = "singer";
    private static final String SINGER_ID_EXTRA = "singer_id";

    private static final int NO_SINGER = -1;

    private int mSingerId = NO_SINGER;
    private Singer mSinger;

    @BindView(R.id.photo)
    ImageView photo;

    @Inject
    DataProvider dataProvider;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onMoreChosen(Singer singer);
    }

    private DataProvider.Callback<Singer> mSingerCallback = new DataProvider.Callback<Singer>() {
        @Override
        public void postResult(@Nullable Singer result) {
            if(result == null) {
                showSingerLoadError();
            } else {
                setSinger(result);
            }
        }
    };

    public static PreviewFragment newInstance(Singer singer) {
        Log.e("TAG", "SINGER " + singer);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SINGER_EXTRA, singer);

        PreviewFragment previewFragment = new PreviewFragment();
        previewFragment.setArguments(bundle);

        return previewFragment;
    }

    public static PreviewFragment newInstance(int singerId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SINGER_ID_EXTRA, singerId);

        PreviewFragment previewFragment = new PreviewFragment();
        previewFragment.setArguments(bundle);

        return previewFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks) {
            mCallbacks = (Callbacks)context;
        } else {
            throw new RuntimeException("Should implement PreviewFragment#Callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        Bundle bundle = (savedInstanceState != null ? savedInstanceState : getArguments());

        if(bundle != null) {
            if (bundle.containsKey(SINGER_EXTRA)) {
                mSinger = bundle.getParcelable(SINGER_EXTRA);
            } else {
                mSingerId = bundle.getInt(SINGER_ID_EXTRA);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mSinger != null) {
            outState.putParcelable(SINGER_EXTRA, mSinger);
        } else if(mSingerId != NO_SINGER) {
            outState.putInt(SINGER_ID_EXTRA, mSingerId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "ON RESUME " + mSinger);
        if(mSinger != null) {
            onSingerSet();
        } else if(mSingerId != NO_SINGER) {
            dataProvider.getSinger(mSingerId, mSingerCallback);
        }
    }

    public void setSinger(@Nullable Singer singer) {
        mSinger = singer;
        onSingerSet();
    }

    @OnClick(R.id.more)
    void onMoreClick() {
        mCallbacks.onMoreChosen(mSinger);
    }

    public void setSingerId(int singerId) {
        mSingerId = singerId;
        mSinger = null;

        dataProvider.getSinger(mSingerId, mSingerCallback);
    }

    private void onSingerSet() {
        if(mSinger == null) {
            hideInfo();
        } else {
            showSingerInfo();
        }
    }

    private void showSingerLoadError() {
        Snackbar.make(photo, getString(R.string.error), Snackbar.LENGTH_LONG).show();
    }

    private void hideInfo() {
        photo.setImageDrawable(null);
    }

    private void showSingerInfo() {
        photo.setImageDrawable(null);
        Picasso.with(getContext()).load(mSinger.getCover().getSmall()).fit().centerInside().into(photo);
    }


}
