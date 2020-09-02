package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.VitalRangeHistoryAdp;
import com.his.android.Model.VitalMaster;
import com.his.android.R;
import com.his.android.Response.RangeMasterResp;
import com.his.android.Response.VitalRangeHistoryResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalRange extends Fragment implements View.OnClickListener {
    private Spinner spnVital;
    private EditText edtMin, edtMax;
    TextView btnSave;
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private Spinner spnConsultant;
    private static RecyclerView rvVitalRange;
    private List<VitalMaster> vitalList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VitalRange() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VitalRange.
     */
    // TODO: Rename and change types and number of parameters
    public static VitalRange newInstance(String param1, String param2) {
        VitalRange fragment = new VitalRange();
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
        View view=inflater.inflate(R.layout.fragment_vital_range, container, false);
        spnVital =view.findViewById(R.id.spnVital);
        context=view.getContext();
        Utils.showRequestDialog(context);
        edtMax=view.findViewById(R.id.edtMax);
        rvVitalRange=view.findViewById(R.id.rvVitalRange);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        spnConsultant=toolbar.findViewById(R.id.spnConsultant);
        edtMin=view.findViewById(R.id.edtMin);
        btnSave=view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        vitalList =new ArrayList<>();
        rvVitalRange.setLayoutManager(new LinearLayoutManager(context));
        vitalList.add(0, new VitalMaster(0, "Select Vital"));
        Call<RangeMasterResp> call= RetrofitClient.getInstance().getApi().getPageLoadMaster(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<RangeMasterResp>() {
            @Override
            public void onResponse(Call<RangeMasterResp> call, Response<RangeMasterResp> response) {
                if(response.isSuccessful()){
                    if((response.body() != null ? response.body().getVitalMaster().size() : 0) >0)
                        vitalList.addAll(1, response.body().getVitalMaster());
                }
                ArrayAdapter<VitalMaster> vitalAdp=new ArrayAdapter<>(context, R.layout.spinner_layout, vitalList);
                spnVital.setAdapter(vitalAdp);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<RangeMasterResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
        bind();
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
    private void bind(){
        Call<VitalRangeHistoryResp> call1=RetrofitClient.getInstance().getApi().getVitalRangeHistory(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId());
        call1.enqueue(new Callback<VitalRangeHistoryResp>() {
            @Override
            public void onResponse(Call<VitalRangeHistoryResp> call, Response<VitalRangeHistoryResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        rvVitalRange.setAdapter(new VitalRangeHistoryAdp(context, response.body().getVitalRangeHistory()));
                    }
                }
            }

            @Override
            public void onFailure(Call<VitalRangeHistoryResp> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) {
            if (spnVital.getSelectedItemPosition() != 0 && !edtMin.getText().toString().isEmpty() && !edtMax.getText().toString().isEmpty()) {
                if (SharedPrefManager.getInstance(context).getUser().getDesigid() == 1)
                save(SharedPrefManager.getInstance(context).getUser().getUserid());
                else if (SharedPrefManager.getInstance(context).getUser().getDesigid()!=1 && spnConsultant.getSelectedItemPosition() != 0) {
                    save(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else
                    Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context, "Please Enter Vital, Min & Max Value", Toast.LENGTH_LONG).show();
        }
    }
    private void save(int drId){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveVitalRange(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), drId, edtMax.getText().toString().trim(), edtMin.getText().toString().trim(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid(), String.valueOf(vitalList.get(spnVital.getSelectedItemPosition()).getId()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Vital Range Saved Successfully!", Toast.LENGTH_LONG).show();
                    bind();
                } else {
                    try {
                        Toast.makeText(context, response.errorBody() != null ? response.errorBody().string() : null, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    public void delete(int id){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().updateVitalRange(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    bind();
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
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
