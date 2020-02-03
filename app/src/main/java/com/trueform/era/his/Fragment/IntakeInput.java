package com.trueform.era.his.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trueform.era.his.Fragment.ui.main.SectionsPagerAdapterIntake;
import com.trueform.era.his.Model.GetMemberId;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.MemberIdResp;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntakeInput extends Fragment {
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_intake_input, container, false);
        FragmentManager fragmentManager=getFragmentManager();
        context=view.getContext();
        SectionsPagerAdapterIntake sectionsPagerAdapter = new SectionsPagerAdapterIntake(view.getContext(), fragmentManager);
        ViewPager viewPager = view.findViewById(R.id.view_pagers);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs_activity_prob);
        tabs.setupWithViewPager(viewPager);
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}