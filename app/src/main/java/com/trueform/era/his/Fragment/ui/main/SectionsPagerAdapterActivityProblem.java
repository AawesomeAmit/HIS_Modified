package com.trueform.era.his.Fragment.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.trueform.era.his.Fragment.ActivityInput;
import com.trueform.era.his.Fragment.ProblemInput;
import com.trueform.era.his.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapterActivityProblem extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_activity, R.string.tab_problem};
    private final Context mContext;

    public SectionsPagerAdapterActivityProblem(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new ActivityInput();
        if(position==1)
            return new ProblemInput();
        return PlaceholderFragmentActivityProblem.newInstance(position + 1);
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