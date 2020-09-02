package com.his.android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.his.android.Fragment.ui.main.SectionsPagerAdapterActivityProblem;
import com.his.android.R;

public class ActivityProblemInput extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_problem_input, container, false);
        FragmentManager fragmentManager=getFragmentManager();
        SectionsPagerAdapterActivityProblem sectionsPagerAdapterActivityProblem = new SectionsPagerAdapterActivityProblem(view.getContext(), fragmentManager);
        ViewPager viewPager = view.findViewById(R.id.view_pager_activity_prob);
        viewPager.setAdapter(sectionsPagerAdapterActivityProblem);
        TabLayout tabs = view.findViewById(R.id.tabs_activity_prob);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
}