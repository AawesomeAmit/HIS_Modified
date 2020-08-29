package com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Activity.MainActivity;
import com.trueform.era.his.Activity.PreDashboard;
import com.trueform.era.his.Activity.UploadMultipleImg.Api;
import com.trueform.era.his.Activity.UploadMultipleImg.ApiUtilsLocalUrl;
import com.trueform.era.his.Activity.UploadMultipleImg.DischargePatient.DischargeTypeList;
import com.trueform.era.his.Activity.UploadMultipleImg.DischargePatient.GetDischargeTypeRes;
import com.trueform.era.his.Activity.UploadMultipleImg.Universalres;
import com.trueform.era.his.Adapter.CovidPatientListAdp;
import com.trueform.era.his.Adapter.DieteticsPatientListAdp;
import com.trueform.era.his.Adapter.IcuPatientListAdp;
import com.trueform.era.his.Adapter.PatientListAdp;
import com.trueform.era.his.Adapter.PhysioPatientListAdp;
import com.trueform.era.his.Model.AdmittedPatient;
import com.trueform.era.his.Model.AdmittedPatientICU;
import com.trueform.era.his.Model.DieteticsPatientList;
import com.trueform.era.his.Model.PhysioPatientList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.CovidPatientResp;
import com.trueform.era.his.Response.DieteticsPatientResp;
import com.trueform.era.his.Response.IcuPatientListResp;
import com.trueform.era.his.Response.IpdPatientListResp;
import com.trueform.era.his.Response.PhysioPatientListResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.database.DatabaseController;
import com.trueform.era.his.database.TableICUAdmittedPatientList;
import com.trueform.era.his.view.BaseActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class PatientList extends BaseActivity implements View.OnClickListener {

    TextView txtDept, txtDeptT, txtDrName, txtSubDept, img, btnGo, txtFrmDate, txtToDate, btnCovid;
    static RecyclerView rView;
    static Spinner spnSearch;
    EditText edtPid;
    DieteticsPatientResp dieteticsPatientResp;
    static Context context;
    RelativeLayout spnLayout;
    static RelativeLayout pidLayout;
    LinearLayout llCovid;
    private Date fToday = new Date();
    private Date tToday = new Date();
    private int fYear = 0, fMonth = 0, fDay = 0, tYear = 0, tMonth = 0, tDay = 0;
    ArrayAdapter<AdmittedPatient> ipdArrayAdapter;
    ArrayAdapter<com.trueform.era.his.Model.PatientList> covidArrayAdp;
    ArrayAdapter<AdmittedPatientICU> icuArrayAdapter;
    List<AdmittedPatient> admittedPatientList;
    List<com.trueform.era.his.Model.PatientList> covidPAtientList;
    List<AdmittedPatientICU> icuPatientList;
    List<AdmittedPatient> admittedPatientList1;
    static List<PhysioPatientList> physioPatientLists;
    static List<DieteticsPatientList> dieteticsPatientLists;
    List<AdmittedPatientICU> icuPatientList1;
    IpdPatientListResp ipdPatientListResp;
    CovidPatientResp covidPatientResp;
    IcuPatientListResp icuPatientListResp;

    Dialog dialog;
    Spinner popupspnDepartment,popUpspnConsultant,popUpspnWard;
    EditText popUpEtReason;
    List<GetDepartmentList> departmentList = new ArrayList<>();
    List<ConsultantList> consultantLists = new ArrayList<>();
    List<WardList> wardLists = new ArrayList<>();
    private String departmentID="";
    private String consultantID="";
    private String wardID="";


    TextView tvTransferPt;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        txtDept = findViewById(R.id.txtDept);
        txtSubDept = findViewById(R.id.txtSubDept);
        txtDeptT = findViewById(R.id.txtDeptT);
        txtToDate = findViewById(R.id.txtToDate);
        txtFrmDate = findViewById(R.id.txtFrmDate);
        btnCovid = findViewById(R.id.btnCovid);
        txtDrName = findViewById(R.id.txtDrName);
        edtPid = findViewById(R.id.edtPid);
        btnGo = findViewById(R.id.btnGo);
        llCovid = findViewById(R.id.llCovid);
        spnSearch = findViewById(R.id.spnSearch);
        img = findViewById(R.id.img);

        tvTransferPt = findViewById(R.id.tvTransferPt);

        tvTransferPt.setOnClickListener(view -> alertTransferPatient());

        spnLayout = findViewById(R.id.spnLayout);
        pidLayout = findViewById(R.id.pidLayout);
        admittedPatientList = new ArrayList<>();
        admittedPatientList1 = new ArrayList<>();
        physioPatientLists = new ArrayList<>();
        dieteticsPatientLists = new ArrayList<>();
        covidPAtientList = new ArrayList<>();
        context = PatientList.this;
        icuPatientList = new ArrayList<>();
        icuPatientList1 = new ArrayList<>();
        rView = findViewById(R.id.rView);
        btnGo.setOnClickListener(this);
        btnCovid.setOnClickListener(this);
        txtFrmDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        fYear = c.get(Calendar.YEAR);
        fMonth = c.get(Calendar.MONTH);
        fDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        txtFrmDate.setText(Utils.formatDate(fYear + "/" + (fMonth + 1) + "/" + fDay));
        txtToDate.setText(Utils.formatDate(tYear + "/" + (tMonth + 1) + "/" + tDay));
        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        String dept = SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName() + " Department";
        String subDept = SharedPrefManager.getInstance(this).getHead().getHeadName() + " Patient List";
        txtSubDept.setText(subDept);
        txtDept.setText(dept);
        txtDeptT.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());
        rView.setLayoutManager(new LinearLayoutManager(this));

        if (SharedPrefManager.getInstance(this).getHeadID() == 2) {
            if (ConnectivityChecker.checker(context)) {
                Utils.showRequestDialog(context);
                Call<IpdPatientListResp> call = RetrofitClient.getInstance().getApi().getIPDPatientList(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid(), SharedPrefManager.getInstance(mActivity).getHeadID());
                call.enqueue(new Callback<IpdPatientListResp>() {
                    @Override
                    public void onResponse(Call<IpdPatientListResp> call, Response<IpdPatientListResp> response) {
                        Utils.showRequestDialog(context);
                        if (response.isSuccessful()) {
                            ipdPatientListResp = response.body();
                            if (ipdPatientListResp != null) {
                                ipdArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, ipdPatientListResp.getAdmittedPatient());
                                ipdArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                                spnSearch.setAdapter(ipdArrayAdapter);
                                spnSearch.setSelection(-1);
                                admittedPatientList = ipdPatientListResp.getAdmittedPatient();
                                admittedPatientList1 = ipdPatientListResp.getAdmittedPatient();
                                rView.setAdapter(new PatientListAdp(PatientList.this, admittedPatientList));
                            }
                        } else {
                            Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<IpdPatientListResp> call, Throwable t) {
                        Toast.makeText(PatientList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
            } else {
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

        } else if (SharedPrefManager.getInstance(this).getHeadID() == 7) {
            bindDieteticsPatient();
        } else if (SharedPrefManager.getInstance(this).getHeadID() == 1) {
            llCovid.setVisibility(View.VISIBLE);
            bindCovidPatient();
        } else if (SharedPrefManager.getInstance(this).getHeadID() == 9) {
            bindPhysioList();
        } else if (SharedPrefManager.getInstance(this).getHeadID() == 3 || SharedPrefManager.getInstance(this).getHeadID() == 4 || SharedPrefManager.getInstance(this).getHeadID() == 2029 || SharedPrefManager.getInstance(this).getHeadID() == 2030) {
            /*int wardId;
            if (SharedPrefManager.getInstance(this).getHeadID() == 3)
                wardId = 39;
            else wardId = 36;*/
            hitGetICUPatientList();
        }
        spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    try {
                        if (admittedPatientList.size() > 0) {
                            admittedPatientList1 = Collections.singletonList(admittedPatientList.get(i));
                            rView.setAdapter(new PatientListAdp(PatientList.this, admittedPatientList1));
                        } else if (icuPatientList.size() > 0) {
                            icuPatientList1 = Collections.singletonList(icuPatientList.get(i));
                            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList1));
                        } else if (physioPatientLists.size() > 0) {
                            physioPatientLists = Collections.singletonList(physioPatientLists.get(i));
                            rView.setAdapter(new PhysioPatientListAdp(PatientList.this, physioPatientLists));
                        } else if (dieteticsPatientLists.size() > 0) {
                            dieteticsPatientLists = Collections.singletonList(dieteticsPatientLists.get(i));
                            rView.setAdapter(new DieteticsPatientListAdp(PatientList.this, dieteticsPatientLists));
                        } else if (covidPAtientList.size() > 0) {
                            covidPAtientList = Collections.singletonList(covidPAtientList.get(i));
                            rView.setAdapter(new CovidPatientListAdp(PatientList.this, covidPAtientList));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(PatientList.this, img);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                if (ConnectivityChecker.checker(getApplicationContext())) {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().logOut(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getFCMToken(), String.valueOf(SharedPrefManager.getInstance(PatientList.this).getUser().getUserid()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utils.showRequestDialog(PatientList.this);
                            if (response.isSuccessful()) {
                                Toast.makeText(PatientList.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                                SharedPrefManager.getInstance(PatientList.this).clear();
                                Intent intent = new Intent(PatientList.this, MainActivity.class);
                                startActivity(intent);
                            }
                            Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(PatientList.this, "Network problem!", Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }
                    });
                } else Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                return true;
            });
            menu.show();
        });
        /*rView.addOnItemTouchListener(new RecyclerTouchListener(this, rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 2) {
                    SharedPrefManager.getInstance(PatientList.this).setAdmitPatient(admittedPatientList1.get(position));
                    SharedPrefManager.getInstance(PatientList.this).setPid(admittedPatientList1.get(position).getPid());
                    Intent intent = new Intent(PatientList.this, Dashboard.class);
                    startActivity(intent);
                }
                else if (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 3 || SharedPrefManager.getInstance(PatientList.this).getHeadID() == 4) {
                    SharedPrefManager.getInstance(PatientList.this).setIcuAdmitPatient(icuPatientList1.get(position));
                    SharedPrefManager.getInstance(PatientList.this).setPid(icuPatientList1.get(position).getPid());
                    Intent intent = new Intent(PatientList.this, Dashboard.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }


    public void bindDieteticsPatient() {
        if (ConnectivityChecker.checker(getApplicationContext())) {
            Utils.showRequestDialog(context);
            Call<DieteticsPatientResp> call = RetrofitClient1.getInstance().getApi().getNutritionalPanelPatientList(
                    NUTRI_TOKEN,
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString());
            call.enqueue(new Callback<DieteticsPatientResp>() {
                @Override
                public void onResponse(Call<DieteticsPatientResp> call, Response<DieteticsPatientResp> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getResponseCode() == 1) {
                            dieteticsPatientResp = response.body();
                            if (dieteticsPatientResp != null && dieteticsPatientResp.getResponseValue().size() > 0) {
                                ArrayAdapter<DieteticsPatientList> dieteticsAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_text_size, dieteticsPatientResp.getResponseValue());
                                dieteticsAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                                spnSearch.setAdapter(dieteticsAdapter);
                                spnSearch.setSelection(-1);
                                dieteticsPatientLists = dieteticsPatientResp.getResponseValue();
                                rView.setAdapter(new DieteticsPatientListAdp(context, dieteticsPatientLists));
                            }
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<DieteticsPatientResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
    private void bindCovidPatient() {
        if (ConnectivityChecker.checker(getApplicationContext())) {
            Utils.showRequestDialog(context);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Call<CovidPatientResp> call = RetrofitClient.getInstance().getApi().getPatientDetailCovid19(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), format.format(fToday), format.format(tToday));
            call.enqueue(new Callback<CovidPatientResp>() {
                @Override
                public void onResponse(Call<CovidPatientResp> call, Response<CovidPatientResp> response) {
                    if (response.isSuccessful()) {
                        covidPatientResp = response.body();
                        if (covidPatientResp != null) {
                            if(covidPatientResp.getPatientList().size()>0) {
                                covidArrayAdp = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, covidPatientResp.getPatientList());
                                covidArrayAdp.setDropDownViewResource(R.layout.spinner_item_text_size);
                                spnSearch.setAdapter(covidArrayAdp);
                                spnSearch.setSelection(-1);
                                covidPAtientList = covidPatientResp.getPatientList();
                                rView.setAdapter(new CovidPatientListAdp(PatientList.this, covidPAtientList));
                            }
                        }
                    } else {
                        Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<CovidPatientResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else
            Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
    public void bindPhysioList() {
        if (ConnectivityChecker.checker(getApplicationContext())) {
            Utils.showRequestDialog(context);
            pidLayout.setVisibility(View.VISIBLE);
            Call<PhysioPatientListResp> call = RetrofitClient.getInstance().getApi().getPhysiotherapyPatientList(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
            call.enqueue(new Callback<PhysioPatientListResp>() {
                @Override
                public void onResponse(Call<PhysioPatientListResp> call, Response<PhysioPatientListResp> response) {
                    if (response.isSuccessful()) {
                        PhysioPatientListResp physioPatientListResp = response.body();
                        if (physioPatientListResp != null && physioPatientListResp.getPhysioPatientList().size() > 0) {
                            ArrayAdapter<PhysioPatientList> physioAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_text_size, physioPatientListResp.getPhysioPatientList());
                            physioAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                            spnSearch.setAdapter(physioAdapter);
                            spnSearch.setSelection(-1);
                            physioPatientLists = physioPatientListResp.getPhysioPatientList();
                            rView.setAdapter(new PhysioPatientListAdp(context, physioPatientListResp.getPhysioPatientList()));
                        }
                    } else
                        Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<PhysioPatientListResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PatientList.this, PreDashboard.class));
    }

    @Override
    public void onClick(View view) {
        Utils.showRequestDialog(context);
        if(view.getId()==R.id.btnGo){
            if(!edtPid.getText().toString().isEmpty()) {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().savePatientPhysiotherapyPanel(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getHeadID(), edtPid.getText().toString().trim(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(PatientList.this, "Patient Added Successfully!", Toast.LENGTH_LONG).show();
                            bindPhysioList();
                        }else {
                            try {
                                Toast.makeText(PatientList.this, response.errorBody() != null ? response.errorBody().string() : null, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            } else Toast.makeText(this, "Please enter PID!", Toast.LENGTH_SHORT).show();
        } else if(view.getId()==R.id.txtFrmDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        fYear = year;
                        fMonth = monthOfYear;
                        fDay = dayOfMonth;
                        //fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        fToday.setDate(dayOfMonth);
                        fToday.setMonth(monthOfYear);
                        fToday.setYear(year - 1900);
                        txtFrmDate.setText(Utils.formatDate(fYear + "/" + (fMonth + 1) + "/" + fDay));
                    }, fYear, fMonth, fDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.txtToDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        tYear = year;
                        tMonth = monthOfYear;
                        tDay = dayOfMonth;
                        //fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        tToday.setDate(dayOfMonth);
                        tToday.setMonth(monthOfYear);
                        tToday.setYear(year - 1900);
                        txtToDate.setText(Utils.formatDate(tYear + "/" + (tMonth + 1) + "/" + tDay));
                    }, tYear, tMonth, tDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        } else if(view.getId()==R.id.btnCovid){
            bindCovidPatient();
        }
    }

    private void hitGetICUPatientList(){

        if (ConnectivityChecker.checker(context)){

            Utils.showRequestDialog(context);
            Call<IcuPatientListResp> call= RetrofitClient.getInstance().getApi().getICUPatientList(
                    SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(),
                    SharedPrefManager.getInstance(PatientList.this).getUser().getUserid(),
                    SharedPrefManager.getInstance(mActivity).getHeadID());
            call.enqueue(new Callback<IcuPatientListResp>() {
                @Override
                public void onResponse(Call<IcuPatientListResp> call, Response<IcuPatientListResp> response) {
                    if (response.isSuccessful()) {
                        icuPatientListResp = response.body();
                        if (icuPatientListResp != null) {
                            icuArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, icuPatientListResp.getAdmittedPatientICU());
                            icuArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                            spnSearch.setAdapter(icuArrayAdapter);
                            spnSearch.setSelection(-1);
                            icuPatientList=icuPatientListResp.getAdmittedPatientICU();
                            icuPatientList1=icuPatientListResp.getAdmittedPatientICU();
                            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList));

                            try{
                                DatabaseController.myDataBase.beginTransaction();

                                DatabaseController.deleteRow(TableICUAdmittedPatientList.icu_admitted_patient_list,
                                        TableICUAdmittedPatientList.icuPatientColumn.headId_subDeptId.toString(),
                                        SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString());

                                for (int i = 0; i < response.body().getAdmittedPatientICU().size(); i++) {

                                    AdmittedPatientICU admittedPatientICU = response.body().getAdmittedPatientICU().get(i);

                                    ContentValues contentValues = new ContentValues();

                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.headId_subDeptId.toString(), SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pmid.toString(), admittedPatientICU.getPmid());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.wardID.toString(), admittedPatientICU.getWardID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.previousWardID.toString(), admittedPatientICU.getPreviousWardID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.bedName.toString(), admittedPatientICU.getBedName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pid.toString(), admittedPatientICU.getPid());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.crNo.toString(), admittedPatientICU.getCrNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ipNo.toString(), admittedPatientICU.getIpNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.userID.toString(), admittedPatientICU.getUserID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.subDeptID.toString(), admittedPatientICU.getSubDeptID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.subDepartmentName.toString(), admittedPatientICU.getSubDepartmentName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.status.toString(), admittedPatientICU.getStatus());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.consultantName.toString(), admittedPatientICU.getConsultantName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pname.toString(), admittedPatientICU.getPname());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.sex.toString(), admittedPatientICU.getSex());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.gender.toString(), admittedPatientICU.getGender());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.age.toString(), admittedPatientICU.getAge());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ageUnit.toString(), admittedPatientICU.getAgeUnit());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.address.toString(), admittedPatientICU.getAddress());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.fatherName.toString(), admittedPatientICU.getFatherName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.phoneNo.toString(), admittedPatientICU.getPhoneNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.relation.toString(), admittedPatientICU.getRelation());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.admitDate.toString(), admittedPatientICU.getAdmitDate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.dischargeDate.toString(), admittedPatientICU.getDischargeDate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ddate.toString(), admittedPatientICU.getDdate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.dischargeStatus.toString(), admittedPatientICU.getDischargeStatus());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.previousWardName.toString(), admittedPatientICU.getPreviousWardName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.notificationCount.toString(), admittedPatientICU.getNotificationCount());

                                   // DatabaseController.insertUpdateData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list, "subDeptID", admittedPatientICU.getPmid().toString());
//                                    DatabaseController.insertUpdateData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list, "subDeptID", admittedPatientICU.getPid().toString());

                                    DatabaseController.insertData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list);

                                }

                                DatabaseController.myDataBase.setTransactionSuccessful();

                            } finally {
                                DatabaseController.myDataBase.endTransaction();

                                Utils.hideDialog();


                            }

                        }
                    } else
                        Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<IcuPatientListResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }else {

            icuPatientListResp = new IcuPatientListResp();

            icuPatientList.addAll(DatabaseController.getICUAdmittedPatientList(
                    SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString()));

            icuPatientList1.addAll(DatabaseController.getICUAdmittedPatientList(SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString()));

            icuPatientListResp.setAdmittedPatientICU(icuPatientList);

            icuArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, icuPatientListResp.getAdmittedPatientICU());
            icuArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
            spnSearch.setAdapter(icuArrayAdapter);
            spnSearch.setSelection(-1);

//            icuPatientList = icuPatientListResp.getAdmittedPatientICU();
//            icuPatientList1 = icuPatientListResp.getAdmittedPatientICU();
            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList));

        }


    }

    //Dialog transfer patient
    private void alertTransferPatient() {

        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_patient_transfer);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);

        popupspnDepartment = dialog.findViewById(R.id.spnDepartment);
        popUpspnConsultant = dialog.findViewById(R.id.spnConsultant);
        popUpspnWard = dialog.findViewById(R.id.spnWard);
        popUpEtReason = dialog.findViewById(R.id.etReason);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

            if (ConnectivityChecker.checker(context)) {
               // hitTransferPatient();
                hitGetDepartment();

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        if (departmentID.equals("0") || departmentID.equals("")) {
                            Toast.makeText(mActivity, "Please select department", Toast.LENGTH_SHORT).show();
                        }
                        else if (consultantID.equals("0") || consultantID.equals("")) {
                            Toast.makeText(mActivity, "Please select consultant", Toast.LENGTH_SHORT).show();
                        }else if (wardID.equals("0") || wardID.equals("")) {
                            Toast.makeText(mActivity, "Please select ward", Toast.LENGTH_SHORT).show();
                        } else if (popUpEtReason.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter reason", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            if (ConnectivityChecker.checker(context)) {
                                 hitTransferPatient();

                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }
            });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    //Hit Get Department
    private void hitGetDepartment() {
        departmentList.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<GetDepartmentRes> call = iRestInterfaces.getDepartmentList(
                "NkU1MzdFRTM5OENDNEEwQUI2MkIwQjQ3NjI5N0U1NUUtMTIzNDU2Nw==",
                "1234567"
        );

        call.enqueue(new Callback<GetDepartmentRes>() {
            @Override
            public void onResponse(Call<GetDepartmentRes> call, Response<GetDepartmentRes> response) {

                if (response.isSuccessful()) {

                    departmentList.add(new GetDepartmentList());
                    departmentList.get(0).setId(0);
                    departmentList.get(0).setSubDepartmentName("Select Department");

                    departmentList.addAll(response.body().getSubDept());

                    if(departmentList.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, departmentList);
                        popupspnDepartment.setAdapter(arrayAdapter);

                        popupspnDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                departmentID = String.valueOf(departmentList.get(popupspnDepartment.getSelectedItemPosition()).getId());
                                if (ConnectivityChecker.checker(context)) {
                                    hitGetConsultant();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetDepartmentRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //hit Get Consultant
    private void hitGetConsultant() {

        consultantLists.clear();

       Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<ConsultantRes> call = iRestInterfaces.getConsultantAndDepartmentList(
                "NkU1MzdFRTM5OENDNEEwQUI2MkIwQjQ3NjI5N0U1NUUtMTIzNDU2Nw==",
                "1234567",
                departmentID
        );

        call.enqueue(new Callback<ConsultantRes>() {
            @Override
            public void onResponse(Call<ConsultantRes> call, Response<ConsultantRes> response) {

                if (response.isSuccessful()) {

                   consultantLists.add(new ConsultantList());
                   consultantLists.get(0).setUserID(0);
                   consultantLists.get(0).setDisplayName("Select Consultant");

                  consultantLists.addAll(response.body().getDoctors());


                    if (consultantLists.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.inflate_spinner_item, consultantLists);
                        popUpspnConsultant.setAdapter(arrayAdapter);

                        popUpspnConsultant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                consultantID = String.valueOf(consultantLists.get(popUpspnConsultant.getSelectedItemPosition()).getUserID());

                                Log.v("asfasgtrhasb", String.valueOf(consultantLists.get(popUpspnConsultant.getSelectedItemPosition()).getUserID()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Utils.hideDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }

                    //For ward List
                    wardLists.add(new WardList());
                    wardLists.get(0).setId(0);
                    wardLists.get(0).setName("Select Ward");

                    wardLists.addAll(response.body().getWards());


                    if (wardLists.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.inflate_spinner_item, wardLists);
                        popUpspnWard.setAdapter(arrayAdapter);

                        popUpspnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                wardID = String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId());

                                Log.v("asfasgtrhasb", String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Utils.hideDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }


                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                //Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ConsultantRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    //Hit Transfer Patient
    private void hitTransferPatient() {
        Utils.showRequestDialog(mActivity);
        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<Universalres> call = iRestInterfaces.hitTransferPatient(
                "NkU1MzdFRTM5OENDNEEwQUI2MkIwQjQ3NjI5N0U1NUUtMTIzNDU2Nw==",
                "1234567",
                "1000010",
                consultantID,
                "",
                "",
                popUpEtReason.getText().toString(),
                departmentID,
                "transfer",
                wardID,
                "1234567"


        );

        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Transfer Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
                else {
                    // error case
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "Data not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<Universalres> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


}