package com.trueform.era.his.Fragment;

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

import com.trueform.era.his.Adapter.PatientRangeHistoryAdp;
import com.trueform.era.his.Model.DiagnosisMaster;
import com.trueform.era.his.Model.TypeMaster;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.RangeHistoryResp;
import com.trueform.era.his.Response.RangeMasterResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IORange.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IORange#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IORange extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    Spinner spnType, spnDiagnosis, spnUnit;
    EditText edtMin, edtMax;
    TextView btnSave;
    static Context context;
    static RecyclerView rvPRange;
    Spinner spnConsultant;
    List<DiagnosisMaster> diagnosisList;
    List<TypeMaster> typeList;
    List<UnitMaster> unitList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IORange() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IORange.
     */
    // TODO: Rename and change types and number of parameters
    public static IORange newInstance(String param1, String param2) {
        IORange fragment = new IORange();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_iorange, container, false);
        spnDiagnosis=view.findViewById(R.id.spnDiagnosis);
        context=view.getContext();
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        spnConsultant=toolbar.findViewById(R.id.spnConsultant);
        Utils.showRequestDialog(context);
        spnUnit=view.findViewById(R.id.spnUnit);
        spnType =view.findViewById(R.id.spnType);
        rvPRange =view.findViewById(R.id.rvPRange);
        edtMax=view.findViewById(R.id.edtMax);
        edtMin=view.findViewById(R.id.edtMin);
        btnSave=view.findViewById(R.id.btnSave);
        rvPRange.setLayoutManager(new LinearLayoutManager(context));
        btnSave.setOnClickListener(this);
        diagnosisList=new ArrayList<>();
        typeList =new ArrayList<>();
        unitList=new ArrayList<>();
        diagnosisList.add(0, new DiagnosisMaster(0, "Select Diagnosis"));
        typeList.add(0, new TypeMaster(0, "Select Type"));
        unitList.add(0, new UnitMaster(0, "Select Unit"));
        Call<RangeMasterResp> call= RetrofitClient.getInstance().getApi().getPageLoadMaster(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<RangeMasterResp>() {
            @Override
            public void onResponse(Call<RangeMasterResp> call, Response<RangeMasterResp> response) {
                if(response.isSuccessful()){
                    if((response.body() != null ? response.body().getDiagnosisMaster().size() : 0) >0)
                        diagnosisList.addAll(1, response.body().getDiagnosisMaster());
                    if((response.body() != null ? response.body().getVitalMaster().size() : 0) >0)
                        typeList.addAll(1, response.body().getTypeMaster());
                    if((response.body() != null ? response.body().getUnitMaster().size() : 0) >0)
                        unitList.addAll(1, response.body().getUnitMaster());
                }
                ArrayAdapter<DiagnosisMaster> diagnosisAdp=new ArrayAdapter<>(context, R.layout.spinner_layout, diagnosisList);
                ArrayAdapter<TypeMaster> typeAdp=new ArrayAdapter<>(context, R.layout.spinner_layout, typeList);
                ArrayAdapter<UnitMaster> unitAdp=new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                spnDiagnosis.setAdapter(diagnosisAdp);
                spnUnit.setAdapter(unitAdp);
                spnType.setAdapter(typeAdp);
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
    private void bind(){
        Call<RangeHistoryResp> call1=RetrofitClient.getInstance().getApi().getPatientRangeHistory(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getSubDept().getId());
        call1.enqueue(new Callback<RangeHistoryResp>() {
            @Override
            public void onResponse(Call<RangeHistoryResp> call, Response<RangeHistoryResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        rvPRange.setAdapter(new PatientRangeHistoryAdp(context, response.body().getRangeHistory()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<RangeHistoryResp> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
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
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) {
            if (spnType.getSelectedItemPosition() != 0 && !edtMin.getText().toString().isEmpty() && !edtMax.getText().toString().isEmpty() && spnUnit.getSelectedItemPosition() != 0) {
                if (SharedPrefManager.getInstance(context).getUser().getDesigid()==1) {
                    saveRange(SharedPrefManager.getInstance(context).getUser().getUserid());
                }
                else if (SharedPrefManager.getInstance(context).getUser().getDesigid()!=1 && spnConsultant.getSelectedItemPosition() != 0) {
                    saveRange(SharedPrefManager.getInstance(context).getConsultantList().get(spnConsultant.getSelectedItemPosition()).getUserid());
                } else Toast.makeText(context, "Consultant name required!", Toast.LENGTH_LONG).show();
            } else Toast.makeText(context, "Please Enter Type, Min, Max & Unit", Toast.LENGTH_LONG).show();
        }
    }
    private void saveRange(int drId){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().savePatientRange(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getIpNo(), String.valueOf(diagnosisList.get(spnDiagnosis.getSelectedItemPosition()).getDiagID()), drId, edtMax.getText().toString().trim(), edtMin.getText().toString().trim(), SharedPrefManager.getInstance(context).getSubDept().getId(), String.valueOf(typeList.get(spnType.getSelectedItemPosition()).getId()), String.valueOf(unitList.get(spnUnit.getSelectedItemPosition()).getUnitid()), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Patient Range Saved Successfully!", Toast.LENGTH_LONG).show();
                    bind();
                } else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }
    public void delete(int id){
        Utils.showRequestDialog(context);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().updatePatientRange(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), id);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
