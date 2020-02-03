package com.trueform.era.his.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.PrescribedMedicationAdp;
import com.trueform.era.his.Model.PrescriptionList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetPrescriptionResponse;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescribedMedicine extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<PrescriptionList> prescriptionList;
    RecyclerView rView;
    ProgressDialog dialog;
    Context context;
    private String mParam1;
    private String mParam2;

    TextView tvGivenByHead;

    LinearLayout llMain;

    private OnFragmentInteractionListener mListener;

    public PrescribedMedicine() {
        // Required empty public constructor
    }

    public static PrescribedMedicine newInstance(String param1, String param2) {
        PrescribedMedicine fragment = new PrescribedMedicine();
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
        View view = inflater.inflate(R.layout.fragment_prescribed_mediine, container, false);
        rView = view.findViewById(R.id.rView);
        llMain = view.findViewById(R.id.llMain);
        llMain.setWeightSum(12);
        tvGivenByHead = view.findViewById(R.id.tvGivenByHead);
        tvGivenByHead.setVisibility(View.GONE);
        context = view.getContext();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        //dialog.show();
        rView.setLayoutManager(new LinearLayoutManager(context));
        Call<GetPrescriptionResponse> call = RetrofitClient.getInstance().getApi().getintakePrescription(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<GetPrescriptionResponse>() {
            @Override
            public void onResponse(Call<GetPrescriptionResponse> call, Response<GetPrescriptionResponse> response) {
                dialog.show();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        prescriptionList = response.body().getPrescriptionList();
                        if (prescriptionList.size() > 0) {
                            rView.setAdapter(new PrescribedMedicationAdp(context, prescriptionList));
                        }
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetPrescriptionResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
