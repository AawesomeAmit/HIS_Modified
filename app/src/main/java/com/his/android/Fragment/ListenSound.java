package com.his.android.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.his.android.R;
import com.his.android.Response.VoiceDataResp;
import com.his.android.Utils.RetrofitClient;

import retrofit2.Call;

public class ListenSound extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_listen_sound, container, false);
//        Call<VoiceDataResp> call= RetrofitClient.getInstance().getApi().getPatientVoiceData()
        return view;
    }
}