package com.his.android.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.his.android.Fragment.ui.main.SectionsPagerAdapterNutrition;
import com.his.android.Model.GetMemberId;
import com.his.android.R;
import com.his.android.Response.MemberIdResp;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutritiAnalyzer extends Fragment {
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_nutriti_analyzer, container, false);
        FragmentManager fragmentManager=getFragmentManager();
        SectionsPagerAdapterNutrition sectionsPagerAdapter = new SectionsPagerAdapterNutrition(view.getContext(), fragmentManager);
        ViewPager viewPager = view.findViewById(R.id.view_pagerNutrition);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabsNutrition);
        tabs.setupWithViewPager(viewPager);
        context=view.getContext();
        Call<MemberIdResp> call= RetrofitClient1.getInstance().getApi().getUserProfileByPID("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", String.valueOf(SharedPrefManager.getInstance(context).getPid()));
        call.enqueue(new Callback<MemberIdResp>() {
            @Override
            public void onResponse(Call<MemberIdResp> call, Response<MemberIdResp> response) {
                if (response.body() != null) {
                    MemberIdResp memberIdResp = response.body();
                    if (memberIdResp.getResponseCode() == 1) {
                        SharedPrefManager.getInstance(context).setMemberId(memberIdResp.getResponseValue().get(0));
                    } else SharedPrefManager.getInstance(context).setMemberId(new GetMemberId(0,0));
                }
            }

            @Override
            public void onFailure(Call<MemberIdResp> call, Throwable t) {
                Log.v("showError", t.getMessage());
            }
        });
        return view;
    }
}