package ru.yandex.yamblz.playlist2.ui.fragments;

import android.support.v4.app.DialogFragment;

import ru.yandex.yamblz.playlist2.MyApplication;
import ru.yandex.yamblz.playlist2.di.components.AppComponent;

public class BaseDialogFragment extends DialogFragment {

    protected AppComponent getAppComponent() {
        return ((MyApplication)getActivity().getApplication()).getAppComponent();
    }

}
