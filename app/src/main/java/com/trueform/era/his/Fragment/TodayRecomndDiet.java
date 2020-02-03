package com.trueform.era.his.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.trueform.era.his.R;


public class TodayRecomndDiet extends Fragment {

    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_today_recomnd_diet, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        NutriAnalyserFragment.check = 2;

        bundle = new Bundle();

        displayView(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //bundle.putString("cal", getArguments().getString("cal"));
                switch (tab.getPosition()) {
                    case 0:

                        displayView(0);

                        NutriAnalyserFragment.check = 2;

//                        ((DashBoard)getActivity()).displayView(11);

                        break;
                    case 1:
                        displayView(1);

                        NutriAnalyserFragment.check = 3;
//                        ((DashBoard)getActivity()).displayView(10);
                        break;

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void displayView(int position){
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new NutriAnalyserIntakeDiet();
                fragment.setArguments(bundle);

                break;

            case 1:
                fragment = new HealthPrediction();
                fragment.setArguments(bundle);

                break;

        }

        if (fragment != null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayoutToday, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }
    }
}
