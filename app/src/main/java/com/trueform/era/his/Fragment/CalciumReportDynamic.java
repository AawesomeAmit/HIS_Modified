package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.DischargeTypeList;
import com.trueform.era.his.Model.SubdeptList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.DischargeTypeResp;
import com.trueform.era.his.Response.SubDeptCalReportResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalciumReportDynamic extends BaseFragment implements View.OnClickListener {
    private List<DischargeTypeList> dischargeTypeList;
    private List<SubdeptList> subdeptList;
    private ArrayAdapter<SubdeptList> subdeptListadp;
    private ArrayAdapter<DischargeTypeList> dischargeTypeListAdp;
    SimpleDateFormat format2;
    Calendar c;
    private int mYear = 0, mMonth = 0, mDay = 0;
    private int tYear = 0, tMonth = 0, tDay = 0;
    Date today = new Date();
    private Date toToday = new Date();
    private static String fromDate = "";
    private static String toDate = "";
    private TextView txtToDate;
    private TextView txtFrmDate;
    private Spinner spnType, spnDept;

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_calcium_report_dynamic, container, false);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView btnShow = view.findViewById(R.id.btnShow);
        txtToDate =view.findViewById(R.id.txtToDate);
        txtFrmDate=view.findViewById(R.id.txtFrmDate);
        spnType=view.findViewById(R.id.spnType);
        spnDept=view.findViewById(R.id.spnDept);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        format2 = new SimpleDateFormat("hh:mm a");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(toDate));
        txtFrmDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        subdeptList=new ArrayList<>();
        dischargeTypeList=new ArrayList<>();
        subdeptList.add(0, new SubdeptList(0, "All"));
        dischargeTypeList.add(0, new DischargeTypeList(0, "All"));
        /*LinearLayoutManager layoutManager=new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        rvReport.setLayoutManager(layoutManager);*/
        Call<SubDeptCalReportResp> call = RetrofitClient.getInstance().getApi().initControlsSubDept(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<SubDeptCalReportResp>() {
            @Override
            public void onResponse(Call<SubDeptCalReportResp> call, Response<SubDeptCalReportResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        subdeptList.addAll(1, response.body().getSubdeptList());
                        subdeptListadp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, subdeptList);
                        spnDept.setAdapter(subdeptListadp);
                    }
                }else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubDeptCalReportResp> call, Throwable t) {

            }
        });
        Call<DischargeTypeResp> call1 = RetrofitClient.getInstance().getApi().initDischargeDetailsList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call1.enqueue(new Callback<DischargeTypeResp>() {
            @Override
            public void onResponse(Call<DischargeTypeResp> call, Response<DischargeTypeResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        dischargeTypeList.addAll(1, response.body().getDischargeTypeList());
                        dischargeTypeListAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, dischargeTypeList);
                        spnType.setAdapter(dischargeTypeListAdp);
                    }
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DischargeTypeResp> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtFrmDate.setText(Utils.formatDate(fromDate));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if (view.getId() == R.id.txtToDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        tYear = year;
                        tMonth = monthOfYear;
                        tDay = dayOfMonth;
                        toDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        toToday.setDate(dayOfMonth);
                        toToday.setMonth(monthOfYear);
                        toToday.setYear(year - 1900);
                        txtToDate.setText(Utils.formatDate(toDate));
                    }, tYear, tMonth, tDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.btnShow){
            //showData();
        }
    }
}
