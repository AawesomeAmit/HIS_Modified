package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Model.GetPatientListModel;
import com.his.android.R;
import com.his.android.Response.GetPatientListResp;
import com.his.android.Response.UpdatePatientPidResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardPostPatientListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    TextView txtDrName, txtDept, tvNoData, tvDate;

    ImageView ivCalender;

    int mDay = 0, mMonth = 0, mYear = 0, hour = 0, minutes = 0;

    String fromDate;

    Dialog dialog;

    ConstraintLayout clHeader;

    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_post_patient_list);

        init();
    }

    private void init() {
        txtDrName = findViewById(R.id.txtDrName);
        txtDept = findViewById(R.id.txtDept);
        tvNoData = findViewById(R.id.tvNoData);
        tvDate = findViewById(R.id.tvDate);

        clHeader = findViewById(R.id.clHeader);

        ivCalender = findViewById(R.id.ivCalender);

        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        fromDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);

        tvDate.setText(Utils.formatDateNew(fromDate));

        ivCalender.setOnClickListener(view -> selectDate(tvDate));

        tvDate.setOnClickListener(view -> selectDate(tvDate));

        if (ConnectivityChecker.checker(this)){
            hitGetPatientList(fromDate);
        }else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


    }

    private void hitGetPatientList(String fromDate) {

        Utils.showRequestDialog(GuardPostPatientListActivity.this);

        Call<GetPatientListResp> call = RetrofitClient.getInstance().getApi().getPatientList(
                SharedPrefManager.getInstance(GuardPostPatientListActivity.this).getUser().getAccessToken(),
                SharedPrefManager.getInstance(GuardPostPatientListActivity.this).getUser().getUserid().toString(),
                fromDate);

        call.enqueue(new Callback<GetPatientListResp>() {
            @Override
            public void onResponse(Call<GetPatientListResp> call, Response<GetPatientListResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getPatientList().size()>0){

                            tvNoData.setVisibility(View.GONE);
                            clHeader.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                            AdapterPatient adapterPatient = new AdapterPatient(response.body().getPatientList());
                            recyclerView.setAdapter(adapterPatient);
                        }else {
                            tvNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            clHeader.setVisibility(View.GONE);
                        }

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetPatientListResp> call, Throwable t) {
                Log.e("onFailure:",t.getMessage());

                Toast.makeText(GuardPostPatientListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                tvNoData.setVisibility(View.VISIBLE);

                recyclerView.setVisibility(View.GONE);
                clHeader.setVisibility(View.GONE);

                Utils.hideDialog();
            }
        });
    }

    private void hitUpdatePatientPID(String id, String pid) {

        Utils.showRequestDialog(GuardPostPatientListActivity.this);

        Call<UpdatePatientPidResp> call = RetrofitClient.getInstance().getApi().updatePatientPID(
                SharedPrefManager.getInstance(GuardPostPatientListActivity.this).getUser().getAccessToken(),
                SharedPrefManager.getInstance(GuardPostPatientListActivity.this).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(GuardPostPatientListActivity.this).getUser().getUserid().toString(),
                pid,
                id);

        call.enqueue(new Callback<UpdatePatientPidResp>() {
            @Override
            public void onResponse(Call<UpdatePatientPidResp> call, Response<UpdatePatientPidResp> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        if (response.body().getPatientExistance().get(0).getStatusCode() == 0){
                            Toast.makeText(GuardPostPatientListActivity.this,
                                    response.body().getPatientExistance().get(0).getStatusMsg(), Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(GuardPostPatientListActivity.this,
                                    response.body().getPatientExistance().get(0).getStatusMsg(), Toast.LENGTH_SHORT).show();
                            hitGetPatientList(fromDate);
                        }

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<UpdatePatientPidResp> call, Throwable t) {
                Log.e("onFailure:",t.getMessage());

                Toast.makeText(GuardPostPatientListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }


    private class AdapterPatient extends RecyclerView.Adapter<HolderPatient> {

        List<GetPatientListModel> data;

        public AdapterPatient(List<GetPatientListModel> favList) {
            data = favList;
        }

        public HolderPatient onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderPatient(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_patient_list, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderPatient holder, final int position) {

            holder.tvPatientName.setText(data.get(position).getPatientName());
            holder.tvAgeGender.setText(data.get(position).getGender());
            holder.tvProblem.setText(data.get(position).getProblem());

            holder.ivUpdate.setOnClickListener(view -> {
                showUpdateDialog(data.get(position).getId().toString());
            });

        }


        public int getItemCount() {
            return data.size();
        }
    }

    private class HolderPatient extends RecyclerView.ViewHolder {

        TextView tvPatientName, tvAgeGender, tvProblem;

        ImageView ivUpdate;

        public HolderPatient(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvAgeGender = itemView.findViewById(R.id.tvAgeGender);
            tvProblem = itemView.findViewById(R.id.tvProblem);

            ivUpdate = itemView.findViewById(R.id.ivUpdate);

        }
    }

    private void selectDate(final TextView textView){
        DatePickerDialog datePickerDialog = new DatePickerDialog(GuardPostPatientListActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    fromDate = year + "-"
                            + (monthOfYear + 1) + "-" + dayOfMonth;

                    tvDate.setText(Utils.formatDateNew(fromDate));

                    if (ConnectivityChecker.checker(GuardPostPatientListActivity.this)){
                        hitGetPatientList(fromDate);
                    }else {
                        Toast.makeText(GuardPostPatientListActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }


                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                }, mYear, mMonth, mDay);
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }

    public void showUpdateDialog(String id) {

        dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.alert_update_pid);

        dialog.show();

        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);

        EditText etPid = dialog.findViewById(R.id.etPid);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitUpdatePatientPID(id, etPid.getText().toString());
                //dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}
