package com.his.android.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.his.android.R;
import com.his.android.view.BaseFragment;

public class NeuroIcuDailyChart extends BaseFragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view=inflater.inflate(R.layout.fragment_neuro_icu_daily_chart, container, false);
        view=inflater.inflate(R.layout.inner_neuro_icu_daily_chart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}