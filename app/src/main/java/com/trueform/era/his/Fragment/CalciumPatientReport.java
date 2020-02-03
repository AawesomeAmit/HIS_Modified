package com.trueform.era.his.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.CalciumPatientHourly;
import com.trueform.era.his.Model.DischargeTypeList;
import com.trueform.era.his.Model.SubdeptList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.CalciumPatientDataResp;
import com.trueform.era.his.Response.DischargeTypeResp;
import com.trueform.era.his.Response.SubDeptCalReportResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

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

public class CalciumPatientReport extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvReport;
    Context context;
    List<DischargeTypeList> dischargeTypeList;
    List<SubdeptList> subdeptList;
    ArrayAdapter<SubdeptList> subdeptListadp;
    ArrayAdapter<DischargeTypeList> dischargeTypeListAdp;
    SimpleDateFormat format2;
    Calendar c;
    private int mYear = 0, mMonth = 0, mDay = 0;
    private int tYear = 0, tMonth = 0, tDay = 0;
    Date today = new Date();
    Date toToday = new Date();
    private static String fromDate = "";
    private static String toDate = "";
    TextView btnShow, txtToDate, txtFrmDate;
    Spinner spnType, spnDept;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalciumPatientReport() {
        // Required empty public constructor
    }

    public static CalciumPatientReport newInstance(String param1, String param2) {
        CalciumPatientReport fragment = new CalciumPatientReport();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_calcium_patient_report, container, false);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=view.getContext();
        rvReport=view.findViewById(R.id.rvReport);
        btnShow=view.findViewById(R.id.btnShow);
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
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL, false);
        rvReport.setLayoutManager(layoutManager);
        Call<SubDeptCalReportResp> call = RetrofitClient.getInstance().getApi().initControlsSubDept(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<SubDeptCalReportResp>() {
            @Override
            public void onResponse(Call<SubDeptCalReportResp> call, Response<SubDeptCalReportResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        subdeptList.addAll(1, response.body().getSubdeptList());
                        subdeptListadp = new ArrayAdapter<>(context, R.layout.spinner_layout, subdeptList);
                        spnDept.setAdapter(subdeptListadp);
                    }
                }else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubDeptCalReportResp> call, Throwable t) {

            }
        });
        Call<DischargeTypeResp> call1 = RetrofitClient.getInstance().getApi().initDischargeDetailsList(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call1.enqueue(new Callback<DischargeTypeResp>() {
            @Override
            public void onResponse(Call<DischargeTypeResp> call, Response<DischargeTypeResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        dischargeTypeList.addAll(1, response.body().getDischargeTypeList());
                        dischargeTypeListAdp = new ArrayAdapter<>(context, R.layout.spinner_layout, dischargeTypeList);
                        spnType.setAdapter(dischargeTypeListAdp);
                    }
                } else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
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
    private void showData(){
        Utils.showRequestDialog(context);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if(toToday.getTime()>=today.getTime()) {
            Call<CalciumPatientDataResp> call = RetrofitClient.getInstance().getApi().getCalciumPatientHourlyReport(SharedPrefManager.getInstance(context).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), format.format(today), format.format(toToday), dischargeTypeList.get(spnType.getSelectedItemPosition()).getId(), String.valueOf(subdeptList.get(spnDept.getSelectedItemPosition()).getSubids()));
            call.enqueue(new Callback<CalciumPatientDataResp>() {
                @Override
                public void onResponse(Call<CalciumPatientDataResp> call, Response<CalciumPatientDataResp> response) {
                    if (response.isSuccessful()) {
                        CalciumPatientDataResp calciumPatientDataResp = response.body();
                        if (calciumPatientDataResp != null) {
                            CalciumReportAdp calciumReportAdp = new CalciumReportAdp(calciumPatientDataResp.getCalciumPatientHourly());
                            rvReport.setAdapter(calciumReportAdp);
                        } else rvReport.setAdapter(null);
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
                public void onFailure(Call<CalciumPatientDataResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }else{
            Toast.makeText(context, "To date should not be less than the from date!", Toast.LENGTH_LONG).show();
            Utils.hideDialog();
        }
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
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public class CalciumReportAdp extends  RecyclerView.Adapter<CalciumReportAdp.RecyclerViewHolder>{
        List<CalciumPatientHourly> calciumPatientHourly;

        CalciumReportAdp(List<CalciumPatientHourly> calciumPatientHourly) {
            this.calciumPatientHourly = calciumPatientHourly;
        }

        @NonNull
        @Override
        public CalciumReportAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.inner_calcium_report, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CalciumReportAdp.RecyclerViewHolder holder, int i) {
            holder.txtPName.setText(calciumPatientHourly.get(i).getPatientName());
            holder.txtAge.setText(calciumPatientHourly.get(i).getAgeGender());
            holder.txtWard.setText(calciumPatientHourly.get(i).getWardname());
            holder.txtDoA.setText(calciumPatientHourly.get(i).getAdmitDateTime());
            holder.txtDiagnosis.setText(calciumPatientHourly.get(i).getDiagnosis().toString());
            holder.txt8am.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital8AM()));
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
            holder.txt7pm.setText(Html.fromHtml(calciumPatientHourly.get(i).getVital7PM()));
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
            TextView txtPName, txtAge, txtWard, txtDiagnosis, txtDoA, txt8am, txt9am, txt10am, txt11am, txt12am, txt1pm,
                    txt2pm, txt3pm, txt4pm, txt5pm, txt6pm, txt7pm, txt8pm, txt9pm, txt10pm, txt11pm, txt12pm, txt1am,
                    txt2am, txt3am, txt4am, txt5am, txt6am, txt7am;
            ProgressBar prCal_8am, prCal_9am, prCal_10am, prCal_11am, prCal_12am, prCal_7am, prCal_6am, prCal_5am, prCal_4am, prCal_3am, prCal_2am, prCal_1am, prCal_8pm, prCal_9pm, prCal_10pm, prCal_11pm, prCal_12pm, prCal_7pm, prCal_6pm, prCal_5pm, prCal_4pm, prCal_3pm, prCal_2pm, prCal_1pm;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtPName=itemView.findViewById(R.id.txtPName);
                txtAge=itemView.findViewById(R.id.txtAge);
                txtPName=itemView.findViewById(R.id.txtPName);
                txtWard=itemView.findViewById(R.id.txtWard);
                txtDoA=itemView.findViewById(R.id.txtDoA);
                txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
                txt8am=itemView.findViewById(R.id.txt8am);
                txt9am=itemView.findViewById(R.id.txt9am);
                txt10am=itemView.findViewById(R.id.txt10am);
                txt11am=itemView.findViewById(R.id.txt11am);
                txt12am=itemView.findViewById(R.id.txt12am);
                txt1pm=itemView.findViewById(R.id.txt1pm);
                txt2pm=itemView.findViewById(R.id.txt2pm);
                txt3pm=itemView.findViewById(R.id.txt3pm);
                txt4pm=itemView.findViewById(R.id.txt4pm);
                txt5pm=itemView.findViewById(R.id.txt5pm);
                txt6pm=itemView.findViewById(R.id.txt6pm);
                txt7pm=itemView.findViewById(R.id.txt7pm);
                txt8pm=itemView.findViewById(R.id.txt8pm);
                txt9pm=itemView.findViewById(R.id.txt9pm);
                txt10pm=itemView.findViewById(R.id.txt10pm);
                txt11pm=itemView.findViewById(R.id.txt11pm);
                txt12pm=itemView.findViewById(R.id.txt12pm);
                txt1am=itemView.findViewById(R.id.txt1am);
                txt2am=itemView.findViewById(R.id.txt2am);
                txt3am=itemView.findViewById(R.id.txt3am);
                txt4am=itemView.findViewById(R.id.txt4am);
                txt5am=itemView.findViewById(R.id.txt5am);
                txt6am=itemView.findViewById(R.id.txt6am);
                txt7am=itemView.findViewById(R.id.txt7am);
                prCal_8am =itemView.findViewById(R.id.prCal_8am);
                prCal_9am =itemView.findViewById(R.id.prCal_9am);
                prCal_10am =itemView.findViewById(R.id.prCal_10am);
                prCal_11am =itemView.findViewById(R.id.prCal_11am);
                prCal_12am =itemView.findViewById(R.id.prCal_12am);
                prCal_1am =itemView.findViewById(R.id.prCal_1am );
                prCal_2am =itemView.findViewById(R.id.prCal_2am );
                prCal_3am =itemView.findViewById(R.id.prCal_3am );
                prCal_4am =itemView.findViewById(R.id.prCal_4am );
                prCal_5am =itemView.findViewById(R.id.prCal_5am );
                prCal_6am =itemView.findViewById(R.id.prCal_6am );
                prCal_7am =itemView.findViewById(R.id.prCal_7am );
                prCal_8pm =itemView.findViewById(R.id.prCal_8pm);
                prCal_9pm =itemView.findViewById(R.id.prCal_9pm);
                prCal_10pm =itemView.findViewById(R.id.prCal_10pm);
                prCal_11pm =itemView.findViewById(R.id.prCal_11pm);
                prCal_12pm =itemView.findViewById(R.id.prCal_12pm);
                prCal_1pm =itemView.findViewById(R.id.prCal_1pm );
                prCal_2pm =itemView.findViewById(R.id.prCal_2pm );
                prCal_3pm =itemView.findViewById(R.id.prCal_3pm );
                prCal_4pm =itemView.findViewById(R.id.prCal_4pm );
                prCal_5pm =itemView.findViewById(R.id.prCal_5pm );
                prCal_6pm =itemView.findViewById(R.id.prCal_6pm );
                prCal_7pm =itemView.findViewById(R.id.prCal_7pm );
            }
        }
    }
}
