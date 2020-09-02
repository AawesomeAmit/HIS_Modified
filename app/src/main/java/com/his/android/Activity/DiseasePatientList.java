package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.R;
import com.his.android.Response.DiseasePatientListResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseasePatientList extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerDiseasePatient;
    TextView txtDate;
    static String date = "";
    SimpleDateFormat format;
    int mYear = 0, mMonth = 0, mDay = 0;
    Date today = new Date();
    Calendar c;
    List<com.his.android.Model.DiseasePatientList> patientList;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_patient_list);
        recyclerDiseasePatient=findViewById(R.id.recyclerDiseasePatient);
        txtDate=findViewById(R.id.txtDate);
        txtDate.setOnClickListener(this);
        recyclerDiseasePatient.setHasFixedSize(true);
        recyclerDiseasePatient.setLayoutManager(new LinearLayoutManager(this));
        patientList=new ArrayList<>();
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        bind();
    }
    @SuppressLint("SimpleDateFormat")
    private void bind(){
        format = new SimpleDateFormat("yyyy-MM-dd");
        Call<DiseasePatientListResp> call = RetrofitClient.getInstance().getApi().getResearchDiseasePatientList(SharedPrefManager.getInstance(DiseasePatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(DiseasePatientList.this).getUser().getUserid().toString(), getIntent().getIntExtra("assignedId", 0), format.format(today), String.valueOf(SharedPrefManager.getInstance(DiseasePatientList.this).getUser().getUserid()));
        call.enqueue(new Callback<DiseasePatientListResp>() {
            @Override
            public void onResponse(Call<DiseasePatientListResp> call, Response<DiseasePatientListResp> response) {
                if(response.isSuccessful()){
                    Utils.showRequestDialog(DiseasePatientList.this);
                    if (response.body() != null) {
                        patientList=response.body().getPatientList();
                        recyclerDiseasePatient.setAdapter(new DiseasePatientListAdp(DiseasePatientList.this));
                    }
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<DiseasePatientListResp> call, Throwable t) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(DiseasePatientList.this, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                        bind();
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        }
    }

    public class DiseasePatientListAdp extends RecyclerView.Adapter<DiseasePatientListAdp.RecyclerViewHolder> {
        private Context mCtx;
        DiseasePatientListAdp(Context mCtx) {
            this.mCtx = mCtx;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_disease_patient_list, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final DiseasePatientListAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSno.setText(String.valueOf(i+1));
            holder.txtName.setText(patientList.get(i).getPatientName());
            holder.txtPid.setText(String.valueOf(patientList.get(i).getPid()));
            holder.txtDisease.setText(patientList.get(i).getDiseaseName());
            holder.txtSubDept.setText(patientList.get(i).getSubDepartmentName());
            holder.txtDoctorName.setText(patientList.get(i).getDoctorName());
            holder.txtMob.setText(patientList.get(i).getMobileNo());
        }

        @Override
        public int getItemCount() {
            return patientList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSno, txtName, txtPid, txtDisease, txtSubDept, txtDoctorName, txtMob;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSno =itemView.findViewById(R.id.txtSno);
                txtName =itemView.findViewById(R.id.txtName);
                txtPid =itemView.findViewById(R.id.txtPid);
                txtDisease =itemView.findViewById(R.id.txtDisease);
                txtSubDept =itemView.findViewById(R.id.txtSubDept);
                txtDoctorName =itemView.findViewById(R.id.txtDoctorName);
                txtMob =itemView.findViewById(R.id.txtMob);
            }
        }
    }
}
