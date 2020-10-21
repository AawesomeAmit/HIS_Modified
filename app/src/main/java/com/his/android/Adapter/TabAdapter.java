package com.his.android.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.his.android.Fragment.DynamicFragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String tag;

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
            return DynamicFragment.addfrag(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}