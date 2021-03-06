package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.his.android.R;
import com.his.android.Response.GetIntakeOuttakeResp;
import com.his.android.Response.PatientDiagnosisDetailsByPID;
import com.his.android.Response.PrescribedMedResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriscriptionOverviewPopup extends BaseActivity {
    TextView btnMedication, btnObservation, txtSymptom, txtPHistory, txtPExamination, txtPDiagnosis, tvIntakeOutput, btnViewMedication, btnViewInvestigation;
    ProgressDialog progressDialog;
    PatientDiagnosisDetailsByPID diagnosisDetailsByPID;

    LinearLayout llProgressNote, llComplaint, llPatientHistory, llPhysicalExamination, llProcedureNote, llProvisionalDiagnosis, llAngioReport, llOTNote, llConsultantNote, llPatientNote, llNursingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priscription_overview_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        btnMedication = findViewById(R.id.btnMedication);
        btnViewMedication = findViewById(R.id.btnViewMedication);
        btnViewInvestigation = findViewById(R.id.btnViewInvestigation);
        btnObservation = findViewById(R.id.btnObservation);
        txtSymptom = findViewById(R.id.txtSymptom);
        txtPHistory = findViewById(R.id.txtPHistory);
        txtPExamination = findViewById(R.id.txtPExamination);
        txtPDiagnosis = findViewById(R.id.txtPDiagnosis);
        tvIntakeOutput = findViewById(R.id.tvIntakeOutput);

        llComplaint = findViewById(R.id.llComplaint);
        llProgressNote = findViewById(R.id.llProgressNote);
        llPatientHistory = findViewById(R.id.llPatientHistory);
        llPhysicalExamination = findViewById(R.id.llPhysicalExamination);
        llProvisionalDiagnosis = findViewById(R.id.llProvisionalDiagnosis);
        llProcedureNote = findViewById(R.id.llProcedureNote);
        llOTNote = findViewById(R.id.llOTNote);
        llConsultantNote = findViewById(R.id.llConsultantNote);
        llPatientNote = findViewById(R.id.llPatientNote);
        llNursingNote = findViewById(R.id.llNursingNote);
        llAngioReport = findViewById(R.id.llAngioReport);


        Intent intent1 = getIntent();

        String name = intent1.getStringExtra("PatientName");
        String pid = intent1.getStringExtra("Pid");
        String ward = intent1.getStringExtra("ward");


        llComplaint.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, ComplaintActivity.class));

        });

        llPatientHistory.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, PatientHistoryActivity.class));

        });

        llPhysicalExamination.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, PhysicalExaminationActivity.class));
        });

        llProgressNote.setOnClickListener(view -> {

            Intent intentq = new Intent(mActivity, ProgressNoteActivity.class);

            intentq.putExtra("pname", name);
            intentq.putExtra("pid", pid);
            intentq.putExtra("ward", ward);
//            intentq.putExtra("Pid", intent.getStringExtra("Pid"));
//            intentq.putExtra("cons", intent.getStringExtra("ward"));
            startActivity(intentq);

        });

        llProcedureNote.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, ProcedureNoteActivity.class));

        });

        llOTNote.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, OTNote.class));

        });

        llConsultantNote.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, ConsultantNote.class));

        });

        llPatientNote.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, PatientNote.class));

        });

        llNursingNote.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, NursingNote.class));

        });

        llAngioReport.setOnClickListener(view -> {
            startActivity(new Intent(PriscriptionOverviewPopup.this, AngioReportActivity.class));

        });


        if (SharedPrefManager.getInstance(this).getHeadID() == 1) {
            Utils.showRequestDialog(mActivity);
            Call<PatientDiagnosisDetailsByPID> call = RetrofitClient.getInstance().getApi().getDiagnosisByPID(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getPid(), SharedPrefManager.getInstance(this).getHeadID(), SharedPrefManager.getInstance(this).getSubDept().getId(), SharedPrefManager.getInstance(this).getUser().getUserid());
            call.enqueue(new Callback<PatientDiagnosisDetailsByPID>() {
                @Override
                public void onResponse(Call<PatientDiagnosisDetailsByPID> call, Response<PatientDiagnosisDetailsByPID> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            diagnosisDetailsByPID = response.body();
                            SharedPrefManager.getInstance(PriscriptionOverviewPopup.this).savePatientHistoryList(diagnosisDetailsByPID.getPatientHistory(), "patientHistoryList");
                            for (int i = 0; i < diagnosisDetailsByPID.getPatientHistory().size(); i++) {
                                if (diagnosisDetailsByPID.getPatientHistory().get(i).getPdmId() == 1) {
                                    txtSymptom.setText(Html.fromHtml(diagnosisDetailsByPID.getPatientHistory().get(i).getDetails()));
                                    txtSymptom.setVisibility(View.VISIBLE);
                                }
                                if (diagnosisDetailsByPID.getPatientHistory().get(i).getPdmId() == 2) {
                                    txtPHistory.setText(Html.fromHtml(diagnosisDetailsByPID.getPatientHistory().get(i).getDetails()));
                                    txtPHistory.setVisibility(View.VISIBLE);
                                }
                                if (diagnosisDetailsByPID.getPatientHistory().get(i).getPdmId() == 3) {
                                    txtPExamination.setText(Html.fromHtml(diagnosisDetailsByPID.getPatientHistory().get(i).getDetails()));
                                    txtPExamination.setVisibility(View.VISIBLE);
                                }
                                if (diagnosisDetailsByPID.getPatientHistory().get(i).getPdmId() == 4) {
                                    txtPDiagnosis.setText(Html.fromHtml(diagnosisDetailsByPID.getPatientHistory().get(i).getDetails()));
                                    txtPDiagnosis.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<PatientDiagnosisDetailsByPID> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else {
            bindData();
            hitGetIntakeOutputData();
        }
        btnMedication.setOnClickListener(view -> {
            Intent intent = new Intent(PriscriptionOverviewPopup.this, Dashboard.class);
            intent.putExtra("statuss", "1");
            if (getIntent().getStringExtra("status") != null)
                intent.putExtra("status", "1");
            startActivity(intent);
        });
        btnObservation.setOnClickListener(view -> {
            Intent intent = new Intent(PriscriptionOverviewPopup.this, Dashboard.class);
            intent.putExtra("statuss", "2");
            if (getIntent().getStringExtra("status") != null)
                intent.putExtra("status", "1");
            startActivity(intent);
        });
        btnViewMedication.setOnClickListener(view -> {
            Intent intent = new Intent(PriscriptionOverviewPopup.this, Dashboard.class);
            intent.putExtra("statuss", "4");
            if (getIntent().getStringExtra("status") != null)
                intent.putExtra("status", "1");
            startActivity(intent);
        });
        btnViewInvestigation.setOnClickListener(view -> {
            Intent intent = new Intent(PriscriptionOverviewPopup.this, Dashboard.class);
            intent.putExtra("statuss", "5");
            if (getIntent().getStringExtra("status") != null)
                intent.putExtra("status", "1");
            startActivity(intent);
        });
    }

    private void bindData() {
        Utils.showRequestDialog(mActivity);
        Call<PrescribedMedResp> call1 = RetrofitClient.getInstance().getApi().getCurrentPrescripttionHistory(SharedPrefManager.getInstance(getApplicationContext()).getUser().getAccessToken(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserid().toString(), SharedPrefManager.getInstance(getApplicationContext()).getPid(), SharedPrefManager.getInstance(getApplicationContext()).getHeadID(), SharedPrefManager.getInstance(getApplicationContext()).getSubDept().getId(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserid(), null);
        call1.enqueue(new Callback<PrescribedMedResp>() {
            @Override
            public void onResponse(Call<PrescribedMedResp> call1, Response<PrescribedMedResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String text = "";
                        for (int i = 0; i < response.body().getPatientHistory().size(); i++) {
                            if (response.body().getPatientHistory().get(i).getPdmId() == 4) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    text = text + "\u2022 " + Html.fromHtml(response.body().getPatientHistory().get(i).getDetails().trim(), Html.FROM_HTML_MODE_COMPACT) + "\n";
                                } else {
                                    text = text + "\u2022 " + Html.fromHtml(response.body().getPatientHistory().get(i).getDetails().trim()) + "\n";
                                }
                            }

                        }
                        if (response.body().getPatientHistory().size() > 0) {
                            txtPDiagnosis.setVisibility(View.VISIBLE);
                            txtPDiagnosis.setText(text);
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PrescribedMedResp> call1, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void hitGetIntakeOutputData() {
        Utils.showRequestDialog(mActivity);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String dateToStr = format.format(new Date());

        Call<GetIntakeOuttakeResp> call1 = RetrofitClient.getInstance().getApi().getIntakeOutputData(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(mActivity).getPid(),
                SharedPrefManager.getInstance(mActivity).getHeadID(),
                format.format(Utils.getYesterdayDate()) + " 08:00 AM",
                SharedPrefManager.getInstance(mActivity).getIpNo(),
                dateToStr + " 08:00 AM",
                SharedPrefManager.getInstance(mActivity).getSubDept().getId(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid());

        call1.enqueue(new Callback<GetIntakeOuttakeResp>() {
            @Override
            public void onResponse(Call<GetIntakeOuttakeResp> call, Response<GetIntakeOuttakeResp> response) {
                if (response.isSuccessful()) {
                    GetIntakeOuttakeResp getIntakeOuttakeResp = response.body();
                    if (getIntakeOuttakeResp != null) {
                        tvIntakeOutput.setVisibility(View.VISIBLE);

                        float intakeSum = 0;
                        float outputSum = 0;

                        for (int i = 0; i < getIntakeOuttakeResp.getIntakeHistory().size(); i++) {
                            intakeSum = intakeSum + getIntakeOuttakeResp.getIntakeHistory().get(i).getQuantity();
                        }

                        for (int i = 0; i < getIntakeOuttakeResp.getOutputHistory().size(); i++) {
                            outputSum = outputSum + getIntakeOuttakeResp.getOutputHistory().get(i).getQuantity();
                        }

                        tvIntakeOutput.setText("Total Intake Quantity: " + (int) intakeSum + "\n" +
                                "Total Output Quantity: " + (int) outputSum);

                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetIntakeOuttakeResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }


}
