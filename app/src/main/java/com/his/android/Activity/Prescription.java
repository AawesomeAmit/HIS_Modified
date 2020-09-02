package com.his.android.Activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.his.android.Fragment.ui.main.SectionsPagerAdapterMedInvestigation;
import com.his.android.R;

public class Prescription extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_view_medication, container, false);
        SectionsPagerAdapterMedInvestigation sectionsPagerAdapter = new SectionsPagerAdapterMedInvestigation(view.getContext(), getFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_activity);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tab);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}