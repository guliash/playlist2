package ru.yandex.yamblz.playlist2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

public class SingersPagerAdapter extends FragmentStatePagerAdapter {

    private List<Singer> mSingers;

    public SingersPagerAdapter(FragmentManager fragmentManager, List<Singer> singers) {
        super(fragmentManager);
        mSingers = singers;
    }

    @Override
    public Fragment getItem(int position) {
        return DescFragment.newInstance(mSingers.get(position));
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
