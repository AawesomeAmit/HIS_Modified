package com.his.android.Fragment.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.his.android.Fragment.AddInvestigation;
import com.his.android.Fragment.AddMedication;
import com.his.android.R;

public class SectionsPagerAdapterMedInvestigation extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    public SectionsPagerAdapterMedInvestigation(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new AddMedication();
        else if(position==1)
            return new AddInvestigation();
        return PlaceholderFragmentMedInvestigation.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}