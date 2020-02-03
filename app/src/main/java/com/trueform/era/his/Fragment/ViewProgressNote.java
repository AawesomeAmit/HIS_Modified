package com.trueform.era.his.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.R;
import com.trueform.era.his.Response.ViewProgressResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProgressNote extends Fragment {
    private ProgressDialog progressDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewProgressNote() {
        // Required empty public constructor
    }

    public static ViewProgressNote newInstance(String param1, String param2) {
        ViewProgressNote fragment = new ViewProgressNote();
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
        View view=inflater.inflate(R.layout.fragment_view_progress_note, container, false);
        final Context context=view.getContext();
        final RecyclerView rView=view.findViewById(R.id.rView);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        rView.setLayoutManager(new LinearLayoutManager(context));
       /* Call<ViewProgressResp> call= RetrofitClient.getInstance().getApi().getProgressHistory(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ViewProgressResp>() {
            @Override
            public void onResponse(Call<ViewProgressResp> call, Response<ViewProgressResp> response) {
                progressDialog.show();
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                       // rView.setAdapter(new ViewNotificationAdp(context, response.body().getProgressList()));
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ViewProgressResp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"Network error!", Toast.LENGTH_LONG).show();
            }
        });*/
        return view;
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
