package com.his.android.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.IntakeMedicationAdp;
import com.his.android.Model.IntakeList;
import com.his.android.R;
import com.his.android.Response.IntakePrescriptionResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntakeMedication extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<IntakeList> intakeList;
    RecyclerView rView;
    ProgressDialog dialog;
    Context context;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public IntakeMedication() {
        // Required empty public constructor
    }
    public static IntakeMedication newInstance(String param1, String param2) {
        IntakeMedication fragment = new IntakeMedication();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_prescribed_mediine, container, false);
        rView=view.findViewById(R.id.rView);
        context = view.getContext();
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.show();
        rView.setLayoutManager(new LinearLayoutManager(context));
        Call<IntakePrescriptionResp> call= RetrofitClient.getInstance().getApi().getgetintakeList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<IntakePrescriptionResp>() {
            @Override
            public void onResponse(Call<IntakePrescriptionResp> call, Response<IntakePrescriptionResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        intakeList = response.body().getIntakeList();
                        if (intakeList.size() > 0) {
                            rView.setAdapter(new IntakeMedicationAdp(context, intakeList));
                        }
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<IntakePrescriptionResp> call, Throwable t) {
                dialog.dismiss();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
