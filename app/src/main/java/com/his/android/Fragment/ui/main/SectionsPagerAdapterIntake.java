package com.his.android.Fragment.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.his.android.Fragment.InputFluid;
import com.his.android.Fragment.InputMeal;
import com.his.android.Fragment.InputSupplement;
import com.his.android.R;

public class SectionsPagerAdapterIntake extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_intake1, R.string.tab_intake2, R.string.tab_intake3};
    private final Context mContext;

    public SectionsPagerAdapterIntake(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new InputFluid();
        else if(position==1)
            return new InputMeal();
        else if(position==2)
            return new InputSupplement();
        return PlaceholderFragmentIntake.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}