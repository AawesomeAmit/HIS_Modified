package com.trueform.era.his.Fragment.ui.main;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.trueform.era.his.Fragment.IntakeMedication;
import com.trueform.era.his.Fragment.PrescribedMedicine;
import com.trueform.era.his.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_medication1, R.string.tab_medication2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new PrescribedMedicine();
        else if(position==1)
            return new IntakeMedication();
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