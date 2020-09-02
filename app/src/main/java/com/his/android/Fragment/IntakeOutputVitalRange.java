package com.his.android.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.his.android.Activity.ui.main.SectionsPagerAdapterRange;
import com.his.android.R;

public class IntakeOutputVitalRange extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_intake_output_vital_range, container, false);
        FragmentManager fragmentManagerRange=getFragmentManager();
        SectionsPagerAdapterRange sectionsPagerAdapter = new SectionsPagerAdapterRange(view.getContext(), fragmentManagerRange);
        ViewPager viewPager = view.findViewById(R.id.viewPagerIVRange);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabsIVRange);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
}