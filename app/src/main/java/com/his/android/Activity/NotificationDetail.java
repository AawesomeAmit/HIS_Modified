package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.his.android.Model.ActivityList;
import com.his.android.Model.InvestigationList;
import com.his.android.Model.PatientDetailRDA;
import com.his.android.Model.ProblemList;
import com.his.android.Model.SideEffectList;
import com.his.android.Model.SymptomList;
import com.his.android.R;
import com.his.android.Response.InvestigationResultNotificationResp;
import com.his.android.Response.RdaNotificationResp;
import com.his.android.Response.ResearchDiseaseNotificationResp;
import com.his.android.Response.VitalList;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetail extends AppCompatActivity implements View.OnClickListener {
    TextView txtPName, txtRange, txtDepartment, txtGender, txtAge, txtDName, consultant, dept, txtTitle, txtResult
            , txtTest, txtDiagnosis, btnChange, btnDecline, txtDate, txtRda, txtActualRda, txtAdvice, txtSignSymptom, txtSignNutrient;
    LinearLayout llDiagnosis, llRda, llInvestigation, llRdaText, llMain, llAdvice, llRdaChange, llSign;
    EditText edtRda;
    Drawable img;
    static String date = "";
    SimpleDateFormat format;
    int mYear = 0, mMonth = 0, mDay = 0;
    Date today = new Date();
    Calendar c;
    List<ResearchDiseaseNotificationResp> researchDiseaseNotificationRespList;
    List<InvestigationResultNotificationResp> investigationResultNotificationRespList;
    RdaNotificationResp rdaNotificationResp;
    RecyclerView rViewSide, rViewSymptoms, rViewProblems, rViewInvestigations, rViewVitals, rViewActivity;
    @SuppressLint({"ClickableViewAccessibility", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        txtPName = findViewById(R.id.txtPName);
        txtRange = findViewById(R.id.txtRange);
        btnDecline = findViewById(R.id.btnDecline);
        llSign = findViewById(R.id.llSign);
        btnChange = findViewById(R.id.btnChange);
        txtSignNutrient = findViewById(R.id.txtSignNutrient);
        txtSignSymptom = findViewById(R.id.txtSignSymptom);
        txtAdvice = findViewById(R.id.txtAdvice);
        rViewActivity = findViewById(R.id.rViewActivity);
        llRdaChange = findViewById(R.id.llRdaChange);
        llAdvice = findViewById(R.id.llAdvice);
        rViewVitals = findViewById(R.id.rViewVitals);
        llMain = findViewById(R.id.llMain);
        edtRda = findViewById(R.id.edtRda);
        txtActualRda = findViewById(R.id.txtActualRda);
        llRdaText = findViewById(R.id.llRdaText);
        txtRda = findViewById(R.id.txtRda);
        txtAge = findViewById(R.id.txtAge);
        txtDate=findViewById(R.id.txtDate);
        txtDate.setOnClickListener(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        llDiagnosis = findViewById(R.id.llDiagnosis);
        txtDiagnosis = findViewById(R.id.txtDiagnosis);
        txtTest = findViewById(R.id.txtTest);
        llRda = findViewById(R.id.llRda);
        txtTitle = findViewById(R.id.txtTitle);
        llInvestigation = findViewById(R.id.llInvestigation);
        txtGender = findViewById(R.id.txtGender);
        txtDepartment = findViewById(R.id.txtDepartment);
        txtResult = findViewById(R.id.txtResult);
        dept = findViewById(R.id.dept);
        consultant = findViewById(R.id.consultant);
        txtDName = findViewById(R.id.txtDName);
        rViewSide = findViewById(R.id.rViewSide);
        rViewSymptoms = findViewById(R.id.rViewSymptoms);
        rViewProblems = findViewById(R.id.rViewProblems);
        rViewInvestigations = findViewById(R.id.rViewInvestigations);
        btnChange.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
        FlexboxLayoutManager linearLayoutManager = new FlexboxLayoutManager(this);
        linearLayoutManager.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager.setAlignItems(AlignItems.STRETCH);
        FlexboxLayoutManager linearLayoutManager1 = new FlexboxLayoutManager(this);
        linearLayoutManager1.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager1.setAlignItems(AlignItems.STRETCH);
        FlexboxLayoutManager linearLayoutManager2 = new FlexboxLayoutManager(this);
        linearLayoutManager2.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager2.setAlignItems(AlignItems.STRETCH);
        FlexboxLayoutManager linearLayoutManager3 = new FlexboxLayoutManager(this);
        linearLayoutManager3.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager3.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager3.setAlignItems(AlignItems.STRETCH);
        FlexboxLayoutManager linearLayoutManager4 = new FlexboxLayoutManager(this);
        linearLayoutManager4.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager4.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager4.setAlignItems(AlignItems.STRETCH);
        FlexboxLayoutManager linearLayoutManager5 = new FlexboxLayoutManager(this);
        linearLayoutManager5.setFlexDirection(FlexDirection.ROW);
        linearLayoutManager5.setJustifyContent(JustifyContent.FLEX_START);
        linearLayoutManager5.setAlignItems(AlignItems.STRETCH);
        rViewSide.setLayoutManager(linearLayoutManager);
        rViewSide.setNestedScrollingEnabled(false);
        rViewSide.setHasFixedSize(true);
        rViewSymptoms.setLayoutManager(linearLayoutManager1);
        rViewSymptoms.setNestedScrollingEnabled(false);
        rViewSymptoms.setHasFixedSize(true);
        rViewProblems.setLayoutManager(linearLayoutManager2);
        rViewProblems.setNestedScrollingEnabled(false);
        rViewProblems.setHasFixedSize(true);
        rViewInvestigations.setLayoutManager(linearLayoutManager3);
        rViewInvestigations.setNestedScrollingEnabled(false);
        rViewInvestigations.setHasFixedSize(true);
        rViewActivity.setLayoutManager(linearLayoutManager4);
        rViewActivity.setNestedScrollingEnabled(false);
        rViewActivity.setHasFixedSize(true);
        rViewVitals.setLayoutManager(linearLayoutManager5);
        rViewVitals.setNestedScrollingEnabled(false);
        rViewVitals.setHasFixedSize(true);
        txtTitle.setText(getIntent().getStringExtra("title"));
        rViewSide.setOnTouchListener((v, event) -> true);
        rViewSymptoms.setOnTouchListener((v, event) -> true);
        rViewProblems.setOnTouchListener((v, event) -> true);
        rViewInvestigations.setOnTouchListener((v, event) -> true);
        rViewVitals.setOnTouchListener((v, event) -> true);
        rViewActivity.setOnTouchListener((v, event) -> true);
        llSign.setVisibility(View.GONE);
        if (Objects.requireNonNull(getIntent().getStringExtra("nType")).equalsIgnoreCase("2")) {
            llDiagnosis.setVisibility(View.VISIBLE);
            llInvestigation.setVisibility(View.GONE);
            llRdaText.setVisibility(View.GONE);
            consultant.setVisibility(View.VISIBLE);//2057187
            dept.setVisibility(View.VISIBLE);
            txtDName.setVisibility(View.VISIBLE);
            txtDepartment.setVisibility(View.VISIBLE);
            llRda.setVisibility(View.GONE);
            llAdvice.setVisibility(View.GONE);
            llRdaChange.setVisibility(View.GONE);
            Call<List<ResearchDiseaseNotificationResp>> call1 = RetrofitClient.getInstance().getApi().getResearchDiseaseNotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<List<ResearchDiseaseNotificationResp>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<ResearchDiseaseNotificationResp>> call, Response<List<ResearchDiseaseNotificationResp>> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            researchDiseaseNotificationRespList = response.body();
                            if (researchDiseaseNotificationRespList.size() > 0) {
                                txtPName.setText(researchDiseaseNotificationRespList.get(0).getPatientName() + "(" + researchDiseaseNotificationRespList.get(0).getPid() + ")");
                                txtDName.setText(researchDiseaseNotificationRespList.get(0).getConsultantName());
                                txtDepartment.setText(researchDiseaseNotificationRespList.get(0).getDepartment());
                                txtDiagnosis.setText(researchDiseaseNotificationRespList.get(0).getDiagnosisName() );
                                txtAge.setText(researchDiseaseNotificationRespList.get(0).getAge() + " " + researchDiseaseNotificationRespList.get(0).getAgeUnit());
                                txtGender.setText(researchDiseaseNotificationRespList.get(0).getGender());
                                if(researchDiseaseNotificationRespList.get(0).getGender().equalsIgnoreCase("male")) {
                                    img = getApplicationContext().getResources().getDrawable(R.drawable.male);
                                    txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.blue_gender));
                                }
                                else{
                                    img = getApplicationContext().getResources().getDrawable(R.drawable.female);
                                    txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.pink));
                                }
                                txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<ResearchDiseaseNotificationResp>> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (Objects.requireNonNull(getIntent().getStringExtra("nType")).equalsIgnoreCase("3")) {
            llInvestigation.setVisibility(View.VISIBLE);
            llDiagnosis.setVisibility(View.GONE);
            consultant.setVisibility(View.VISIBLE);
            llRdaText.setVisibility(View.GONE);
            dept.setVisibility(View.VISIBLE);
            txtDName.setVisibility(View.VISIBLE);
            txtDepartment.setVisibility(View.VISIBLE);
            llRda.setVisibility(View.GONE);
            Call<List<InvestigationResultNotificationResp>> call1 = RetrofitClient.getInstance().getApi().getInvestigationResultNotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<List<InvestigationResultNotificationResp>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<InvestigationResultNotificationResp>> call, Response<List<InvestigationResultNotificationResp>> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            investigationResultNotificationRespList = response.body();
                            if (investigationResultNotificationRespList.size() > 0) {
                                txtPName.setText(investigationResultNotificationRespList.get(0).getPatientName() + "("+ investigationResultNotificationRespList.get(0).getPid().toString()+")");
                                txtDName.setText(investigationResultNotificationRespList.get(0).getConsultantName());
                                txtResult.setText(investigationResultNotificationRespList.get(0).getResult() + " " + investigationResultNotificationRespList.get(0).getResultUnit());
                                txtRange.setText(Html.fromHtml(investigationResultNotificationRespList.get(0).getTestNormalRange()));
                                txtDepartment.setText(investigationResultNotificationRespList.get(0).getDepartment());
                                txtAge.setText(investigationResultNotificationRespList.get(0).getAge() + " " + investigationResultNotificationRespList.get(0).getAgeUnit());
                                txtGender.setText(investigationResultNotificationRespList.get(0).getGender());
                                txtTest.setText(Html.fromHtml(investigationResultNotificationRespList.get(0).getTestName()));
                                if(investigationResultNotificationRespList.get(0).getGender().equalsIgnoreCase("male")) {
                                    img = NotificationDetail.this.getResources().getDrawable(R.drawable.male);
                                    txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.blue_gender));
                                }
                                else {
                                    img = NotificationDetail.this.getResources().getDrawable(R.drawable.female);
                                    txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.pink));
                                }
                                txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<InvestigationResultNotificationResp>> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (getIntent().getExtras().getString("nType").equalsIgnoreCase("6")) {
            llDiagnosis.setVisibility(View.GONE);
            llInvestigation.setVisibility(View.GONE);
            consultant.setVisibility(View.GONE);
            dept.setVisibility(View.GONE);
            llRdaText.setVisibility(View.GONE);
            txtDName.setVisibility(View.GONE);
            txtDepartment.setVisibility(View.GONE);
            llRda.setVisibility(View.VISIBLE);
            llAdvice.setVisibility(View.GONE);
            llRdaChange.setVisibility(View.VISIBLE);
            Call<RdaNotificationResp> call1 = RetrofitClient.getInstance().getApi().getRDAChangeNotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<RdaNotificationResp>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<RdaNotificationResp> call, Response<RdaNotificationResp> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                         if (response.isSuccessful()) {
                            rdaNotificationResp = response.body();
                            txtPName.setText(rdaNotificationResp.getPatientDetails().get(0).getPatientName() + "("+ rdaNotificationResp.getPatientDetails().get(0).getPid().toString()+")");
                            edtRda.setText(rdaNotificationResp.getPatientDetails().get(0).getRdaPercentage().toString());
                            txtAge.setText(rdaNotificationResp.getPatientDetails().get(0).getAge() + " " + rdaNotificationResp.getPatientDetails().get(0).getAgeUnit());
                            txtGender.setText(rdaNotificationResp.getPatientDetails().get(0).getGender());
                             if(rdaNotificationResp.getPatientDetails().get(0).getGender().equalsIgnoreCase("male")) {
                                 img = NotificationDetail.this.getResources().getDrawable(R.drawable.male);
                                 txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.blue_gender));
                             }
                             else{
                                 img = NotificationDetail.this.getResources().getDrawable(R.drawable.female);
                                 txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.pink));
                             }
                             txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                             rViewSide.setAdapter(new SideEffectAdp(NotificationDetail.this, rdaNotificationResp.getSideEffectList()));
                             rViewSymptoms.setAdapter(new SymptomAdp(NotificationDetail.this, rdaNotificationResp.getSymptomList()));
                             rViewProblems.setAdapter(new ProblemAdp(NotificationDetail.this, rdaNotificationResp.getProblemList()));
                             rViewActivity.setAdapter(new ActivityAdp(NotificationDetail.this, rdaNotificationResp.getActivityLists()));
                             rViewVitals.setAdapter(new VitalAdp(NotificationDetail.this, rdaNotificationResp.getVitalLists()));
                             rViewInvestigations.setAdapter(new InvestigationAdp(NotificationDetail.this, rdaNotificationResp.getInvestigationList()));
                        } else{
                            try {
                                Toast.makeText(NotificationDetail.this, Objects.requireNonNull(response.errorBody()).string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(NotificationDetail.this, NotificationList.class));
                        }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<RdaNotificationResp> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (getIntent().getExtras().getString("nType").equalsIgnoreCase("8")) {
            llDiagnosis.setVisibility(View.GONE);
            llInvestigation.setVisibility(View.GONE);
            consultant.setVisibility(View.GONE);
            dept.setVisibility(View.GONE);
            llRdaText.setVisibility(View.GONE);
            txtDName.setVisibility(View.GONE);
            txtDepartment.setVisibility(View.GONE);
            llRda.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            llRdaChange.setVisibility(View.GONE);
            llAdvice.setVisibility(View.VISIBLE);
            Call<RdaNotificationResp> call1 = RetrofitClient.getInstance().getApi().getAdviceNotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<RdaNotificationResp>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<RdaNotificationResp> call, Response<RdaNotificationResp> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                    if (response.isSuccessful()) {
                        rdaNotificationResp = response.body();
                        txtPName.setText(rdaNotificationResp.getPatientDetails().get(0).getPatientName() + "("+ rdaNotificationResp.getPatientDetails().get(0).getPid().toString()+")");
                        txtAdvice.setText(rdaNotificationResp.getPatientDetails().get(0).getAdvice());
                        txtAge.setText(rdaNotificationResp.getPatientDetails().get(0).getAge() + " " + rdaNotificationResp.getPatientDetails().get(0).getAgeUnit());
                        txtGender.setText(rdaNotificationResp.getPatientDetails().get(0).getGender());
                        if(rdaNotificationResp.getPatientDetails().get(0).getGender().equalsIgnoreCase("male")) {
                            img = getApplicationContext().getResources().getDrawable(R.drawable.male);
                            txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.blue_gender));
                        }
                        else{
                            img = getApplicationContext().getResources().getDrawable(R.drawable.female);
                            txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.pink));
                        }
                        txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                        //rViewSide.setAdapter(new SideEffectAdp(NotificationDetail.this, rdaNotificationResp.getSideEffectList()));
                        rViewSymptoms.setAdapter(new SymptomAdp(NotificationDetail.this, rdaNotificationResp.getSymptomList()));
                        rViewProblems.setAdapter(new ProblemAdp(NotificationDetail.this, rdaNotificationResp.getProblemList()));
                        rViewActivity.setAdapter(new ActivityAdp(NotificationDetail.this, rdaNotificationResp.getActivityLists()));
                        rViewVitals.setAdapter(new VitalAdp(NotificationDetail.this, rdaNotificationResp.getVitalLists()));
                        rViewInvestigations.setAdapter(new InvestigationAdp(NotificationDetail.this, rdaNotificationResp.getInvestigationList()));
                    } else{
                        try {
                            Toast.makeText(NotificationDetail.this, Objects.requireNonNull(response.errorBody()).string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(NotificationDetail.this, NotificationList.class));
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<RdaNotificationResp> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (getIntent().getExtras().getString("nType").equalsIgnoreCase("7")) {
            llDiagnosis.setVisibility(View.GONE);
            llInvestigation.setVisibility(View.GONE);
            consultant.setVisibility(View.GONE);
            dept.setVisibility(View.GONE);
            llRdaText.setVisibility(View.VISIBLE);
            txtDName.setVisibility(View.GONE);
            txtDepartment.setVisibility(View.GONE);
            llRda.setVisibility(View.GONE);
            Call<List<PatientDetailRDA>> call1 = RetrofitClient.getInstance().getApi().getChangedRDANotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<List<PatientDetailRDA>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<PatientDetailRDA>> call, Response<List<PatientDetailRDA>> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                    if (response.isSuccessful()) {
                        List<PatientDetailRDA> patientDetailRDA = response.body();
                        if (patientDetailRDA != null) {
                            txtPName.setText(patientDetailRDA.get(0).getPatientName() + "("  + patientDetailRDA.get(0).getPid().toString() + ")");
                            txtRda.setText(patientDetailRDA.get(0).getRdaPercentage().toString());
                            txtAge.setText(patientDetailRDA.get(0).getAge() + " " + patientDetailRDA.get(0).getAgeUnit());
                            txtGender.setText(patientDetailRDA.get(0).getGender());
                            if(patientDetailRDA.get(0).getGender().equalsIgnoreCase("male")) {
                                img = NotificationDetail.this.getResources().getDrawable(R.drawable.male);
                                txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.blue_gender));
                            }
                            else {
                                img = NotificationDetail.this.getResources().getDrawable(R.drawable.female);
                                txtGender.setTextColor(NotificationDetail.this.getResources().getColor(R.color.pink));
                            }
                            txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                            txtActualRda.setText(patientDetailRDA.get(0).getOriginalRDA()+" "+patientDetailRDA.get(0).getUnitName());
                        }
                    } else{
                        try {
                            Toast.makeText(NotificationDetail.this, Objects.requireNonNull(response.errorBody()).string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(NotificationDetail.this, NotificationList.class));
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<PatientDetailRDA>> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (getIntent().getExtras().getString("nType").equalsIgnoreCase("9")) {
            llDiagnosis.setVisibility(View.GONE);
            llInvestigation.setVisibility(View.GONE);
            consultant.setVisibility(View.GONE);
            dept.setVisibility(View.GONE);
            llRdaText.setVisibility(View.GONE);
            txtDName.setVisibility(View.GONE);
            txtDepartment.setVisibility(View.GONE);
            llRda.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            llRdaChange.setVisibility(View.GONE);
            llAdvice.setVisibility(View.GONE);
            llSign.setVisibility(View.VISIBLE);
            Call<List<PatientDetailRDA>> call1 = RetrofitClient.getInstance().getApi().getSymptomDueToNutrientPercentageNotificationDetail(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString());
            call1.enqueue(new Callback<List<PatientDetailRDA>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<PatientDetailRDA>> call, Response<List<PatientDetailRDA>> response) {
                    Utils.showRequestDialog(NotificationDetail.this);
                    if (response.isSuccessful()) {
                        //rdaNotificationResp.getPatientDetails() = response.body();
                        List<PatientDetailRDA> patientDetails=response.body();
                        txtPName.setText(patientDetails.get(0).getPatientName() + "("+ patientDetails.get(0).getPid().toString()+")");
                        txtAdvice.setText(patientDetails.get(0).getAdvice());
                        txtAge.setText(patientDetails.get(0).getAge() + " " + patientDetails.get(0).getAgeUnit());
                        txtGender.setText(patientDetails.get(0).getGender());
                        if(patientDetails.get(0).getGender().equalsIgnoreCase("male")) {
                            img = getApplicationContext().getResources().getDrawable(R.drawable.male);
                            txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.blue_gender));
                        }
                        else{
                            img = getApplicationContext().getResources().getDrawable(R.drawable.female);
                            txtGender.setTextColor(getApplicationContext().getResources().getColor(R.color.pink));
                        }
                        txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                        txtSignSymptom.setText(patientDetails.get(0).getSymptomName());
                        txtSignNutrient.setText(patientDetails.get(0).getNutrientName());
                        //rViewSide.setAdapter(new SideEffectAdp(NotificationDetail.this, rdaNotificationResp.getSideEffectList()));
                        /*rViewSymptoms.setAdapter(new SymptomAdp(NotificationDetail.this, rdaNotificationResp.getSymptomList()));
                        rViewProblems.setAdapter(new ProblemAdp(NotificationDetail.this, rdaNotificationResp.getProblemList()));
                        rViewActivity.setAdapter(new ActivityAdp(NotificationDetail.this, rdaNotificationResp.getActivityLists()));
                        rViewVitals.setAdapter(new VitalAdp(NotificationDetail.this, rdaNotificationResp.getVitalLists()));
                        rViewInvestigations.setAdapter(new InvestigationAdp(NotificationDetail.this, rdaNotificationResp.getInvestigationList()));*/
                    } else{
                        try {
                            Toast.makeText(NotificationDetail.this, Objects.requireNonNull(response.errorBody()).string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(NotificationDetail.this, NotificationList.class));
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<PatientDetailRDA>> call, Throwable t) {
                    Utils.hideDialog();
                    Toast.makeText(NotificationDetail.this, "Network issue", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            llMain.setVisibility(View.GONE);
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NotificationDetail.this, NotificationList.class));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnChange){
            change();
        } else if(view.getId()==R.id.btnDecline){
            startActivity(new Intent(NotificationDetail.this, NotificationList.class));
        } else if(view.getId()==R.id.txtDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(NotificationDetail.this, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                        Log.v("changeTime", String.valueOf(new Date().getTime()));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        }
    }
    @SuppressLint("SimpleDateFormat")
    public void change(){
        Utils.showRequestDialog(NotificationDetail.this);
        format = new SimpleDateFormat("yyyy-MM-dd");
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changeRDAThroughNotification(SharedPrefManager.getInstance(NotificationDetail.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), Integer.valueOf(Objects.requireNonNull(getIntent().getExtras().getString("nId"))), SharedPrefManager.getInstance(NotificationDetail.this).getUser().getUserid().toString(), edtRda.getText().toString().trim(), format.format(today));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NotificationDetail.this, "RDA changed successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NotificationDetail.this, NotificationList.class));
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NotificationDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }
        });
    }
    public class SideEffectAdp extends RecyclerView.Adapter<SideEffectAdp.RecyclerViewHolder> {
        private Context mCtx;
        List<SideEffectList> sideEffectList;
        SideEffectAdp(Context mCtx, List<SideEffectList> sideEffectList) {
            this.mCtx = mCtx;
            this.sideEffectList = sideEffectList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_symptoms_notification, null);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final SideEffectAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSymptom.setText(sideEffectList.get(i).getSideEffectName());
        }

        @Override
        public int getItemCount() {
            return sideEffectList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSymptom;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSymptom =itemView.findViewById(R.id.txtSymptom);
            }
        }
    }
    public class SymptomAdp extends RecyclerView.Adapter<SymptomAdp.RecyclerViewHolder> {
        private Context mCtx;
        List<SymptomList> symptomList;

        SymptomAdp(Context mCtx, List<SymptomList> symptomList) {
            this.mCtx = mCtx;
            this.symptomList = symptomList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_symptoms_notification, null);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final SymptomAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSymptom.setText(symptomList.get(i).getSymptomName());
        }

        @Override
        public int getItemCount() {
            return symptomList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSymptom;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSymptom =itemView.findViewById(R.id.txtSymptom);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotificationDetail.this, NotificationList.class));
    }

    public class ProblemAdp extends RecyclerView.Adapter<ProblemAdp.RecyclerViewHolder> {
        private Context mCtx;List<ProblemList> problemList;

        public ProblemAdp(Context mCtx, List<ProblemList> problemList) {
            this.mCtx = mCtx;
            this.problemList = problemList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_symptoms_notification, null);
            //RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ProblemAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSymptom.setText(problemList.get(i).getProblemName());
        }

        @Override
        public int getItemCount() {
            return problemList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSymptom;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSymptom =itemView.findViewById(R.id.txtSymptom);
            }
        }
    }
    public class VitalAdp extends RecyclerView.Adapter<VitalAdp.RecyclerViewHolder> {
        private Context mCtx;
        List<VitalList> vitalLists;

        public VitalAdp(Context mCtx, List<VitalList> vitalLists) {
            this.mCtx = mCtx;
            this.vitalLists = vitalLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_investigation_notification, null);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final VitalAdp.RecyclerViewHolder holder, final int i) {
            holder.txtInvestigation.setText(vitalLists.get(i).getVitalName());
            holder.txtInvestigationValue.setText(vitalLists.get(i).getValue());
        }

        @Override
        public int getItemCount() {
            return vitalLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtInvestigation, txtInvestigationValue;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtInvestigation =itemView.findViewById(R.id.txtInvestigation);
                txtInvestigationValue =itemView.findViewById(R.id.txtInvestigationValue);
            }
        }
    }
    public class ActivityAdp extends RecyclerView.Adapter<ActivityAdp.RecyclerViewHolder> {
        private Context mCtx;
        List<ActivityList> activityLists;

        public ActivityAdp(Context mCtx, List<ActivityList> activityLists) {
            this.mCtx = mCtx;
            this.activityLists = activityLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_symptoms_notification, null);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ActivityAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSymptom.setText(activityLists.get(i).getActivityName());
        }

        @Override
        public int getItemCount() {
            return activityLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSymptom;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSymptom =itemView.findViewById(R.id.txtSymptom);
            }
        }
    }
    public class InvestigationAdp extends RecyclerView.Adapter<InvestigationAdp.RecyclerViewHolder> {
        private Context mCtx;List<InvestigationList> investigationLists;

        InvestigationAdp(Context mCtx, List<InvestigationList> investigationLists) {
            this.mCtx = mCtx;
            this.investigationLists = investigationLists;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_investigation_notification, null);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final InvestigationAdp.RecyclerViewHolder holder, final int i) {
            holder.txtInvestigation.setText(investigationLists.get(i).getInvestigationName());
            holder.txtInvestigationValue.setText(investigationLists.get(i).getInvestigationResult());
        }

        @Override
        public int getItemCount() {
            return investigationLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtInvestigation, txtInvestigationValue;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtInvestigation =itemView.findViewById(R.id.txtInvestigation);
                txtInvestigationValue =itemView.findViewById(R.id.txtInvestigationValue);
            }
        }
    }
}
