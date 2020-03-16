package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.trueform.era.his.Model.CalciumPatientHourly;
import com.trueform.era.his.Model.CalciumReportWardList;
import com.trueform.era.his.Model.CalciumVitalReport;
import com.trueform.era.his.Model.DischargeTypeList;
import com.trueform.era.his.Model.DynamicDate;
import com.trueform.era.his.Model.DynamicDateValue;
import com.trueform.era.his.Model.ResultListForAndroid;
import com.trueform.era.his.Model.SubdeptList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.CalciumDynamicReportResp;
import com.trueform.era.his.Response.CalciumPatientDataResp;
import com.trueform.era.his.Response.CalciumWardListResp;
import com.trueform.era.his.Response.DischargeTypeResp;
import com.trueform.era.his.Response.SubDeptCalReportResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
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
    private List<CalciumReportWardList> calciumReportWardList;
    private ArrayAdapter<CalciumReportWardList> calciumReportWardListAdp;
    SimpleDateFormat format2;
    Calendar c;
    JSONArray jsonArray;
    RecyclerView rvReport;
    private int mYear = 0, mMonth = 0, mDay = 0;
    private int tYear = 0, tMonth = 0, tDay = 0;
    Date today = new Date();
    private Date toToday = new Date();
    private static String fromDate = "";
    private static String toDate = "";
    private TextView txtToDate;
    private TextView txtFrmDate;
    private Spinner spnType, spnDept, spnWard;

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_calcium_report_dynamic, container, false);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView btnShow = view.findViewById(R.id.btnShow);
        txtToDate =view.findViewById(R.id.txtToDate);
        rvReport =view.findViewById(R.id.rvReport);
        txtFrmDate=view.findViewById(R.id.txtFrmDate);
        spnType=view.findViewById(R.id.spnType);
        spnDept=view.findViewById(R.id.spnDept);
        spnWard=view.findViewById(R.id.spnWard);
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
        calciumReportWardList=new ArrayList<>();
        subdeptList.add(0, new SubdeptList(0, "All"));
        dischargeTypeList.add(0, new DischargeTypeList(0, "All"));
        calciumReportWardList.add(0, new CalciumReportWardList(0, "All"));
        LinearLayoutManager layoutManager=new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        rvReport.setLayoutManager(layoutManager);
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
        Call<CalciumWardListResp> call2=RetrofitClient.getInstance().getApi().getWardBySubDepartment(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), "");
        call2.enqueue(new Callback<CalciumWardListResp>() {
            @Override
            public void onResponse(Call<CalciumWardListResp> call, Response<CalciumWardListResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        calciumReportWardList.addAll(1, response.body().getWardList());
                        calciumReportWardListAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, calciumReportWardList);
                        spnWard.setAdapter(calciumReportWardListAdp);
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
            public void onFailure(Call<CalciumWardListResp> call, Throwable t) {

            }
        });
        return view;
    }

    private void showData(){
        Utils.showRequestDialog(mActivity);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(toToday.getTime()>=today.getTime()) {
            /*Call<CalciumDynamicReportResp> call = RetrofitClient.getInstance().getApi().getPatientCalciumHourlyReport(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), format.format(today), format.format(toToday), dischargeTypeList.get(spnType.getSelectedItemPosition()).getId(), String.valueOf(subdeptList.get(spnDept.getSelectedItemPosition()).getSubids()), "0");
            call.enqueue(new Callback<CalciumDynamicReportResp>() {
                @Override
                public void onResponse(Call<CalciumDynamicReportResp> call, Response<CalciumDynamicReportResp> response) {
                    if (response.isSuccessful()) {
                        CalciumDynamicReportResp calciumPatientDataResp = response.body();
                        if (calciumPatientDataResp != null) {
                            //CalciumReportAdp calciumReportAdp = new CalciumReportAdp(calciumPatientDataResp.getResultListForAndroid());
                            //rvReport.setAdapter(calciumReportAdp);
                        } else rvReport.setAdapter(null);
                    } else {
                        try {
                            Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<CalciumDynamicReportResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });*/
            Log.v("hitApi:", RetrofitClient.BASE_URL + "CalciumReport/GetPatientCalciumHourlyReport");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("date", format.format(today));
                jsonObject.put("toDate", format.format(toToday));
                jsonObject.put("dischargeID", dischargeTypeList.get(spnType.getSelectedItemPosition()).getId());
                jsonObject.put("subDeptId", String.valueOf(subdeptList.get(spnDept.getSelectedItemPosition()).getSubids()));
                jsonObject.put("wardID", calciumReportWardList.get(spnWard.getSelectedItemPosition()).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AndroidNetworking.post(RetrofitClient.BASE_URL + "CalciumReport/GetPatientCalciumHourlyReport")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(mActivity).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                jsonArray= response.getJSONArray("resultListForAndroid");
                                List<ResultListForAndroid> resultListForAndroidList=new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ResultListForAndroid resultListForAndroid=new ResultListForAndroid();
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String jsonArray1= jsonObject1.getString("dynamicDate");
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<DynamicDate>>(){}.getType();
                                    List<DynamicDate> dynamicDateList = gson.fromJson(jsonArray1, type);
                                    //List<DynamicDate> dynamicDateList =new ArrayList<>();

                                    /*for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject2=jsonArray.getJSONObject(j);
                                        JSONArray jsonArray2=jsonObject2.getJSONArray("value");
                                        DynamicDate dynamicDate=new DynamicDate();
                                        List<DynamicDateValue> dynamicDateValueList=new ArrayList<>();
                                        for (int k = 0; k < jsonArray2.length(); k++) {
                                            JSONObject jsonObject3=jsonArray.getJSONObject(k);
                                            JSONArray jsonArray3=jsonObject3.getJSONArray("vitalDetail");
                                            DynamicDateValue dynamicDateValue=new DynamicDateValue();
                                            List<CalciumVitalReport> calciumVitalReportList=new ArrayList<>();
                                            for (int l = 0; l < jsonArray3.length(); l++) {
                                                JSONObject jsonObject4=jsonArray.getJSONObject(j);
                                                CalciumVitalReport calciumVitalReport=new CalciumVitalReport();
                                                calciumVitalReport.setPmid(jsonObject4.getInt("pmid"));
                                                calciumVitalReport.setVmid(jsonObject4.getInt("vmid"));
                                                calciumVitalReport.setVitalName(jsonObject4.getString("vitalName"));
                                                calciumVitalReport.setVmValue(jsonObject4.getDouble("vmValue"));
                                                calciumVitalReportList.add(calciumVitalReport);
                                            }
                                            dynamicDateValue.setVitalDetail(calciumVitalReportList);
                                            dynamicDateValueList.add(dynamicDateValue);
                                        }
                                        dynamicDate.setDate(jsonObject1.getString("date"));
                                        dynamicDate.setValue(dynamicDateValueList);
                                        dynamicDateList.add(dynamicDate);
                                    }*/

                                    resultListForAndroid.setPid(jsonObject1.getInt("pid"));
                                    resultListForAndroid.setPmID(jsonObject1.getInt("pmID"));
                                    resultListForAndroid.setIpNo(jsonObject1.getString("ipNo"));
                                    resultListForAndroid.setPatientName(jsonObject1.getString("patientName"));
                                    resultListForAndroid.setAdmitDate(jsonObject1.getString("admitDate"));
                                    //resultListForAndroid.setWardName(jsonObject1.getString("wardName"));
                                    resultListForAndroid.setDiagnosis(jsonObject1.getString("diagnosis"));
                                    resultListForAndroid.setDynamicDate(dynamicDateList);
                                    resultListForAndroidList.add(resultListForAndroid);
                                }
                                rvReport.setAdapter(new CalciumReportAdp(resultListForAndroidList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Utils.hideDialog();
                        }

                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() != 0) {
                                //loader.cancel();
                                Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                                Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            } else {
                                Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            }
                            Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }
                    });
        }else{
            Toast.makeText(mActivity, "To date should not be less than the from date!", Toast.LENGTH_LONG).show();
            Utils.hideDialog();
        }
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
            showData();
        }
    }

    public class CalciumReportAdp extends  RecyclerView.Adapter<CalciumReportAdp.RecyclerViewHolder>{
        List<ResultListForAndroid> calciumPatientHourly;

        CalciumReportAdp(List<ResultListForAndroid> calciumPatientHourly) {
            this.calciumPatientHourly = calciumPatientHourly;
        }

        @NonNull
        @Override
        public CalciumReportAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.inner_dynamic_calcium_report, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CalciumReportAdp.RecyclerViewHolder holder, int i) {
            holder.txtPName.setText(calciumPatientHourly.get(i).getPatientName());
            //holder.txtWard.setText(calciumPatientHourly.get(i).getWardName());
            holder.txtDoA.setText(calciumPatientHourly.get(i).getAdmitDate());
            holder.txtDiagnosis.setText(calciumPatientHourly.get(i).getDiagnosis());
            holder.rvInnerReport.setAdapter(new InnerCalciumReportAdp(calciumPatientHourly.get(i).getDynamicDate()));
            /*holder.txt8am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital8AM()));
            holder.txt9am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital9AM()));
            holder.txt10am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital10AM()));
            holder.txt11am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital11AM()));
            holder.txt12am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital12AM()));
            holder.txt1am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital1AM()));
            holder.txt2am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital2AM()));
            holder.txt3am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital3AM()));
            holder.txt4am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital4AM()));
            holder.txt5am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital5AM()));
            holder.txt6am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital6AM()));
            holder.txt7am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital7AM()));
            holder.txt8pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital8PM()));
            holder.txt9pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital9PM()));
            holder.txt10pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital10PM()));
            holder.txt11pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital11PM()));
            holder.txt12pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital12PM()));
            holder.txt1pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital1PM()));
            holder.txt2pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital2PM()));
            holder.txt3pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital3PM()));
            holder.txt4pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital4PM()));
            holder.txt5pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital5PM()));
            holder.txt6pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital6PM()));
            holder.txt7pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital7PM()));*/
            /*holder.prCal_8am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_8am.setProgress(calciumPatientHourly.get(i).getCalVal8AM());
            holder.prCal_9am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_9am.setProgress(calciumPatientHourly.get(i).getCalVal9AM());
            holder.prCal_10am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_10am.setProgress(calciumPatientHourly.get(i).getCalVal10AM());
            holder.prCal_11am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_11am.setProgress(calciumPatientHourly.get(i).getCalVal11AM());
            holder.prCal_12am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_12am.setProgress(calciumPatientHourly.get(i).getCalVal12AM());
            holder.prCal_1am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_1am.setProgress(calciumPatientHourly.get(i).getCalVal1AM());
            holder.prCal_2am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_2am.setProgress(calciumPatientHourly.get(i).getCalVal2AM());
            holder.prCal_3am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_3am.setProgress(calciumPatientHourly.get(i).getCalVal3AM());
            holder.prCal_4am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_4am.setProgress(calciumPatientHourly.get(i).getCalVal4AM());
            holder.prCal_5am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_5am.setProgress(calciumPatientHourly.get(i).getCalVal5AM());
            holder.prCal_6am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_6am.setProgress(calciumPatientHourly.get(i).getCalVal6AM());
            holder.prCal_7am.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_7am.setProgress(calciumPatientHourly.get(i).getCalVal7AM());
            holder.prCal_8pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_8pm.setProgress(calciumPatientHourly.get(i).getCalVal8PM());
            holder.prCal_9pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_9pm.setProgress(calciumPatientHourly.get(i).getCalVal9PM());
            holder.prCal_10pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_10pm.setProgress(calciumPatientHourly.get(i).getCalVal10PM());
            holder.prCal_11pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_11pm.setProgress(calciumPatientHourly.get(i).getCalVal11PM());
            holder.prCal_12pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_12pm.setProgress(calciumPatientHourly.get(i).getCalVal12PM());
            holder.prCal_1pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_1pm.setProgress(calciumPatientHourly.get(i).getCalVal1PM());
            holder.prCal_2pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_2pm.setProgress(calciumPatientHourly.get(i).getCalVal2PM());
            holder.prCal_3pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_3pm.setProgress(calciumPatientHourly.get(i).getCalVal3PM());
            holder.prCal_4pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_4pm.setProgress(calciumPatientHourly.get(i).getCalVal4PM());
            holder.prCal_5pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_5pm.setProgress(calciumPatientHourly.get(i).getCalVal5PM());
            holder.prCal_6pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_6pm.setProgress(calciumPatientHourly.get(i).getCalVal6PM());
            holder.prCal_7pm.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_OUT);
            holder.prCal_7pm.setProgress(calciumPatientHourly.get(i).getCalVal7PM());*/
        }

        @Override
        public int getItemCount() {
            return calciumPatientHourly.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtPName, txtAge, txtWard, txtDiagnosis, txtDoA;
            RecyclerView rvInnerReport;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtPName=itemView.findViewById(R.id.txtPName);
                txtAge=itemView.findViewById(R.id.txtAge);
                txtPName=itemView.findViewById(R.id.txtPName);
                txtWard=itemView.findViewById(R.id.txtWard);
                txtDoA=itemView.findViewById(R.id.txtDoA);
                txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
                rvInnerReport=itemView.findViewById(R.id.rvInnerReport);
                rvInnerReport.setLayoutManager(new LinearLayoutManager(mActivity));
            }
        }
    }

    public class InnerCalciumReportAdp extends  RecyclerView.Adapter<InnerCalciumReportAdp.RecyclerViewHolder>{
        List<DynamicDate> dynamicDateList;

        InnerCalciumReportAdp(List<DynamicDate> dynamicDateList) {
            this.dynamicDateList = dynamicDateList;
        }

        @NonNull
        @Override
        public InnerCalciumReportAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.inner_inner_dynamic_calcium_report, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull InnerCalciumReportAdp.RecyclerViewHolder holder, int i) {
            holder.txtDateTime.setText(dynamicDateList.get(i).getDate());
            for (int j = 0; j < dynamicDateList.get(i).getValue().size(); j++) {
                holder.rvInnerInnerVitalValue.setAdapter(new InnerValuetAdp(dynamicDateList.get(i).getValue().get(j).getVitalDetail()));
            }
        }

        @Override
        public int getItemCount() {
            return dynamicDateList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtDateTime;
            RecyclerView rvInnerInnerVitalValue, rvInnerInnerInvestigationValue, rvInnerInnerFoodValue, rvInnerInnerMedicineValue, rvInnerInnerDeathValue, rvInnerInnerNutritionValue;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDateTime=itemView.findViewById(R.id.txtDateTime);
                rvInnerInnerVitalValue=itemView.findViewById(R.id.rvInnerInnerVitalValue);
                /*rvInnerInnerInvestigationValue=itemView.findViewById(R.id.rvInnerInnerInvestigationValue);
                rvInnerInnerFoodValue=itemView.findViewById(R.id.rvInnerInnerFoodValue);
                rvInnerInnerMedicineValue=itemView.findViewById(R.id.rvInnerInnerMedicineValue);
                rvInnerInnerDeathValue=itemView.findViewById(R.id.rvInnerInnerDeathValue);
                rvInnerInnerNutritionValue=itemView.findViewById(R.id.rvInnerInnerNutritionValue);*/
                rvInnerInnerVitalValue.setLayoutManager(new LinearLayoutManager(mActivity));
            }
        }
    }

    public class InnerValuetAdp extends  RecyclerView.Adapter<InnerValuetAdp.RecyclerViewHolder>{
        List<CalciumVitalReport> dynamicDateValueList;

        InnerValuetAdp(List<CalciumVitalReport> dynamicDateValueList) {
            this.dynamicDateValueList = dynamicDateValueList;
        }

        @NonNull
        @Override
        public InnerValuetAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.inner_inner_inner_calcium, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull InnerValuetAdp.RecyclerViewHolder holder, int i) {
            holder.txtName.setText(dynamicDateValueList.get(i).getVitalName());
            holder.txtValue.setText(String.valueOf(dynamicDateValueList.get(i).getVmValue()));
        }

        @Override
        public int getItemCount() {
            return dynamicDateValueList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtName, txtValue;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtName=itemView.findViewById(R.id.txtName);
                txtValue=itemView.findViewById(R.id.txtValue);
            }
        }
    }

}
