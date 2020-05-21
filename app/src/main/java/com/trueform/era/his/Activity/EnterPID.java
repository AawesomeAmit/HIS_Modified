package com.trueform.era.his.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trueform.era.his.R;
import com.trueform.era.his.Response.CheckPidResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterPID extends AppCompatActivity {
    EditText edtPid;
    TextView btnGo, txtDrName, txtDept, btnNotAvailable, tvPatientList, txtCovid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pid);
        edtPid = findViewById(R.id.edtPid);
        btnGo = findViewById(R.id.btnGo);
        txtCovid = findViewById(R.id.txtCovid);
        btnNotAvailable = findViewById(R.id.btnNotAvailable);
        tvPatientList = findViewById(R.id.tvPatientList);
        txtDrName = findViewById(R.id.txtDrName);
        txtDept = findViewById(R.id.txtDept);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());

        if (SharedPrefManager.getInstance(EnterPID.this).getHeadID() == 26){
            btnNotAvailable.setVisibility(View.VISIBLE);
            tvPatientList.setVisibility(View.VISIBLE);
            btnGo.setText("Go with PID");
        }else {
            btnNotAvailable.setVisibility(View.GONE);
            tvPatientList.setVisibility(View.GONE);
        }

        btnGo.setOnClickListener(view -> {
            try {
                if (!edtPid.getText().toString().isEmpty()) {
                    if (ConnectivityChecker.checker(EnterPID.this)) {
                    progressDialog.show();
                    Call<CheckPidResp> call = RetrofitClient.getInstance().getApi().checkCRNo(SharedPrefManager.getInstance(EnterPID.this).getUser().getAccessToken(), SharedPrefManager.getInstance(EnterPID.this).getUser().getUserid().toString(), Integer.valueOf(edtPid.getText().toString().trim()), SharedPrefManager.getInstance(EnterPID.this).getSubDept().getId(), SharedPrefManager.getInstance(EnterPID.this).getUser().getUserid());
                    call.enqueue(new Callback<CheckPidResp>() {
                        @Override
                        public void onResponse(Call<CheckPidResp> call, Response<CheckPidResp> response) {
                            if (response.isSuccessful()) {
                                CheckPidResp checkPidResp = response.body();
                                if ((checkPidResp != null ? checkPidResp.getPatientDetails().size() : 0) > 0) {
                                    SharedPrefManager.getInstance(EnterPID.this).setOpdPatient(checkPidResp.getPatientDetails().get(0));
                                    SharedPrefManager.getInstance(EnterPID.this).setPid(checkPidResp.getPatientDetails().get(0).getPid());
                                    SharedPrefManager.getInstance(EnterPID.this).setIpNo("0");
                                    if (SharedPrefManager.getInstance(EnterPID.this).getHeadID() == 7 || SharedPrefManager.getInstance(EnterPID.this).getHeadID() == 11)
                                        startActivity(new Intent(EnterPID.this, Dashboard.class).putExtra("status", "3"));
                                    else if (SharedPrefManager.getInstance(EnterPID.this).getHeadID() == 1)
                                        startActivity(new Intent(EnterPID.this, Dashboard.class).putExtra("status", "6"));
                                    else if (SharedPrefManager.getInstance(EnterPID.this).getHeadID() == 26)
                                        startActivity(new Intent(EnterPID.this, GuardPostActivity.class));
                                    else
                                        startActivity(new Intent(EnterPID.this, Dashboard.class).putExtra("status", "1"));
                                    progressDialog.dismiss();
                                }
                            } else {
                                Toast.makeText(EnterPID.this, response.message(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckPidResp> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                    } else Toast.makeText(EnterPID.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Enter PID", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                progressDialog.dismiss();
                e.printStackTrace();
            }


        });
        txtCovid.setOnClickListener(view -> {
            startActivity(new Intent(EnterPID.this, PatientList.class));
        });
        btnNotAvailable.setOnClickListener(view -> {
            SharedPrefManager.getInstance(EnterPID.this).setPid(0);
            startActivity(new Intent(EnterPID.this, GuardPostActivity.class));
        });

        tvPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnterPID.this, GuardPostPatientListActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EnterPID.this, PreDashboard.class));
    }
}
