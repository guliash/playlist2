package ru.yandex.yamblz.playlist2.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.playlist2.structures.Singer;
import ru.yandex.yamblz.playlist2.ui.fragments.PhotoFragment;

public class SingersPagerAdapter extends FragmentStatePagerAdapter {

    private List<Singer> mSingers;

    public SingersPagerAdapter(FragmentManager fragmentManager, List<Singer> singers) {
        super(fragmentManager);
        mSingers = singers;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(mSingers.get(position));
    }

    @Override
    public int getCount() {
        return mSingers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSingers.get(position).getName();
    }
}
