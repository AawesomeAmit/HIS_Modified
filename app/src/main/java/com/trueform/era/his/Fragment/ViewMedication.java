package com.trueform.era.his.Fragment;

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
import com.trueform.era.his.Fragment.ui.main.SectionsPagerAdapter;
import com.trueform.era.his.R;

public class ViewMedication extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_view_medication2, container, false);
        FragmentManager fragmentManager=getFragmentManager();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), fragmentManager);
        ViewPager viewPager = view.findViewById(R.id.view_pager1);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs1);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
}