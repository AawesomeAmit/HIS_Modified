package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.OutputAdp;
import com.trueform.era.his.Model.OutputList;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.FluidListResp;
import com.trueform.era.his.Response.GetIntakeOuttakeResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Output extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private Spinner spnOutput;
    private EditText edtVal;
    private Spinner spnUnit;
    Context context;
    private TextView txtDate;
    private TextView txtTime;
    private List<OutputList> outputLists;
    private List<UnitMaster> unitList;
    private RecyclerView rvOutput;
    static String date = "", time;
    SimpleDateFormat format2;
    int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Date today = new Date();
    Calendar c;
    private ArrayAdapter<OutputList> outputAdp;
    private ArrayAdapter<UnitMaster> unitAdp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Output() {
        // Required empty public constructor
    }

    public static Output newInstance(String param1, String param2) {
        Output fragment = new Output();
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

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_output, container, false);
        spnOutput =view.findViewById(R.id.spnOutput);
        edtVal=view.findViewById(R.id.edtQty);
        spnUnit=view.findViewById(R.id.txtUnit);
        TextView btnSave = view.findViewById(R.id.btnSave);
        rvOutput=view.findViewById(R.id.rvOutput);
        context=view.getContext();
        outputLists =new ArrayList<>();
        unitList=new ArrayList<>();
        txtDate=view.findViewById(R.id.txtDate);
        txtTime=view.findViewById(R.id.txtTime);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(date);
        //time=c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " + c.get(Calendar.AM_PM);
        //txtTime.setText(time);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        format2 = new SimpleDateFormat("hh:mm a");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        //txtDate.setText(format1.format(today));
        txtTime.setText(format2.format(today));
        rvOutput.setLayoutManager(new LinearLayoutManager(context));
        outputLists.clear();
        unitList.clear();
        outputLists.add(0, new OutputList(0,"-Select-", 0));
        unitList.add(0, new UnitMaster(0,"-Select-"));
        Call<FluidListResp> call= RetrofitClient.getInstance().getApi().intakeOutType(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<FluidListResp>() {
            @Override
            public void onResponse(Call<FluidListResp> call, Response<FluidListResp> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        FluidListResp fluidListResp=response.body();
                        if(fluidListResp.getOutputList().size()>0)
                            outputLists.addAll(1,fluidListResp.getOutputList());
                        if(fluidListResp.getUnitMaster()!=null)
                            if(fluidListResp.getUnitMaster().size()>0)
                                unitList.addAll(1,fluidListResp.getUnitMaster());
                    }
                    outputAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, outputLists);
                    unitAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, unitList);
                    spnOutput.setAdapter(outputAdp);
                    spnUnit.setAdapter(unitAdp);
                }
            }

            @Override
            public void onFailure(Call<FluidListResp> call, Throwable t) {

            }
        });
        bindData();
        btnSave.setOnClickListener(view1 -> {
            if(spnOutput.getSelectedItemPosition()!=0 && spnUnit.getSelectedItemPosition()!=0 && !(edtVal.getText().toString().isEmpty())){
                saveFluid();
            }
        });
        return view;
    }
    private void bindData(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTo = new SimpleDateFormat("dd/MM/yyyy");
        String dateToStr = format.format(today);
        Call<GetIntakeOuttakeResp> call1= RetrofitClient.getInstance().getApi().getIntakeOutputData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), dateToStr+" 12:00 AM", SharedPrefManager.getInstance(context).getIpNo(), formatTo.format(today)+" 11:59 PM", SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call1.enqueue(new Callback<GetIntakeOuttakeResp>() {
            @Override
            public void onResponse(Call<GetIntakeOuttakeResp> call, Response<GetIntakeOuttakeResp> response) {
                if(response.isSuccessful()){
                    GetIntakeOuttakeResp getIntakeOuttakeResp=response.body();
                    if(getIntakeOuttakeResp!=null){
                        rvOutput.setAdapter(new OutputAdp(context, getIntakeOuttakeResp.getOutputHistory()));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetIntakeOuttakeResp> call, Throwable t) {

            }
        });
    }
    private void saveFluid(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateToStr = format.format(today);
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().saveOutputData(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(context).getPid(), SharedPrefManager.getInstance(context).getHeadID(), SharedPrefManager.getInstance(context).getIpNo(), dateToStr, edtVal.getText().toString().trim(), outputLists.get(spnOutput.getSelectedItemPosition()).getId(), unitList.get(spnUnit.getSelectedItemPosition()).getUnitid(), SharedPrefManager.getInstance(context).getSubDept().getId(), SharedPrefManager.getInstance(context).getUser().getUserid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    edtVal.setText("");
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    bindData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(context, "Network issue!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year-1900);
                        txtDate.setText(Utils.formatDate(date));
                        bindData();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            },mHour,mMinute,false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        }
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}