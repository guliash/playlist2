package ru.yandex.yamblz.playlist2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import ru.yandex.yamblz.playlist2.MyApplication;
import ru.yandex.yamblz.playlist2.di.components.AppComponent;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("TAG", "ON CREATE " + this + " " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.e("TAG", "ON RESUME " + this);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("TAG", "ON PAUSE " + this);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("TAG", "ON SAVE INSTANCE STATE " + this + " " + outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.e("TAG", "ON VIEW STATE RESTORED " + savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e("TAG", "ON DESTROY " + this);
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return ((MyApplication)getActivity().getApplication()).getAppComponent();
    }

}
