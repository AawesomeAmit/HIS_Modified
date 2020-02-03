package com.trueform.era.his.Activity.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.trueform.era.his.Fragment.IORange;
import com.trueform.era.his.Fragment.VitalRange;
import com.trueform.era.his.R;

public class SectionsPagerAdapterRange extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_input_range, R.string.tab_vital_range};
    private final Context mContext;

    public SectionsPagerAdapterRange(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new IORange();
        else if(position==1)
            return new VitalRange();
        return PlaceholderFragmentRange.newInstance(position + 1);
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