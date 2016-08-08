package ru.yandex.yamblz.playlist2.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.playlist2.DataProvider;
import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.ui.adapters.SingersAdapter;
import ru.yandex.yamblz.singerscontracts.Singer;

public class ListFragment extends BaseFragment {

    @BindView(R.id.singers_list)
    RecyclerView singersList;

    @Inject
    DataProvider dataProvider;

    private Callbacks mCallbacks;

    public interface Callbacks extends SingersAdapter.Callbacks {
        void onSingersShown(@NonNull List<Singer> singers);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks) {
            mCallbacks = (Callbacks)context;
        } else {
            throw new RuntimeException("Must implement ListFragment#Callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        ButterKnife.bind(this, view);

        singersList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataProvider.getSingers(mSingersCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        dataProvider.cancel(mSingersCallback);
    }

    private DataProvider.Callback<List<Singer>> mSingersCallback = new DataProvider.Callback<List<Singer>>() {
        @Override
        public void postResult(List<Singer> result) {
            singersList.setAdapter(new SingersAdapter(mCallbacks, result));
            if(result != null && result.size() != 0) {
                mCallbacks.onSingersShown(result);
            }
        }
    };
}
