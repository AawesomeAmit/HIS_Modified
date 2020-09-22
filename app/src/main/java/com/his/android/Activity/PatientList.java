package com.his.android.Activity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.CovidPatientListAdp;
import com.his.android.Adapter.DieteticsPatientListAdp;
import com.his.android.Adapter.IcuPatientListAdp;
import com.his.android.Adapter.PatientListAdp;
import com.his.android.Adapter.PhysioPatientListAdp;
import com.his.android.Model.AdmittedPatient;
import com.his.android.Model.AdmittedPatientICU;
import com.his.android.Model.DieteticsPatientList;
import com.his.android.Model.PhysioPatientList;
import com.his.android.R;
import com.his.android.Response.CovidPatientResp;
import com.his.android.Response.DieteticsPatientResp;
import com.his.android.Response.IcuPatientListResp;
import com.his.android.Response.IpdPatientListResp;
import com.his.android.Response.PhysioPatientListResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.database.DatabaseController;
import com.his.android.database.TableICUAdmittedPatientList;

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

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class PatientList extends AppCompatActivity implements View.OnClickListener {

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
    ArrayAdapter<com.his.android.Model.PatientList> covidArrayAdp;
    ArrayAdapter<AdmittedPatientICU> icuArrayAdapter;
    List<AdmittedPatient> admittedPatientList;
    List<com.his.android.Model.PatientList> covidPAtientList;
    List<AdmittedPatientICU> icuPatientList;
    List<AdmittedPatient> admittedPatientList1;
    static List<PhysioPatientList> physioPatientLists;
    static List<DieteticsPatientList> dieteticsPatientLists;
    List<AdmittedPatientICU> icuPatientList1;
    IpdPatientListResp ipdPatientListResp;
    CovidPatientResp covidPatientResp;
    IcuPatientListResp icuPatientListResp;

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
                Call<IpdPatientListResp> call = RetrofitClient.getInstance().getApi().getIPDPatientList(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid(), SharedPrefManager.getInstance(PatientList.this).getHeadID());
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
        } else if (SharedPrefManager.getInstance(this).getHeadID() == 3 || SharedPrefManager.getInstance(this).getHeadID() == 4 || SharedPrefManager.getInstance(this).getHeadID() == 2029 || SharedPrefManager.getInstance(this).getHeadID() == 2030 || (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 34) || (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 35) || (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 36)) {
            /*int wardId = 0;
            if (SharedPrefManager.getInstance(this).getHeadID() == 3)
                wardId = 39;
            else if (SharedPrefManager.getInstance(this).getHeadID() == 4) wardId = 36;*/
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
                } else
                    Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
        } else
            Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
                            if (covidPatientResp.getPatientList().size() > 0) {
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
        } else
            Toast.makeText(PatientList.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PatientList.this, PreDashboard.class));
    }

    @Override
    public void onClick(View view) {
        Utils.showRequestDialog(context);
        if (view.getId() == R.id.btnGo) {
            if (!edtPid.getText().toString().isEmpty()) {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().savePatientPhysiotherapyPanel(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getHeadID(), edtPid.getText().toString().trim(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(PatientList.this, "Patient Added Successfully!", Toast.LENGTH_LONG).show();
                            bindPhysioList();
                        } else {
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
        } else if (view.getId() == R.id.txtFrmDate) {
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
        } else if (view.getId() == R.id.txtToDate) {
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
        } else if (view.getId() == R.id.btnCovid) {
            bindCovidPatient();
        }
    }

    private void hitGetICUPatientList() {

        if (ConnectivityChecker.checker(context)) {

            Utils.showRequestDialog(context);
            Call<IcuPatientListResp> call = RetrofitClient.getInstance().getApi().getICUPatientList(
                    SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(),
                    SharedPrefManager.getInstance(PatientList.this).getUser().getUserid(),
                    SharedPrefManager.getInstance(PatientList.this).getHeadID());
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
                            icuPatientList = icuPatientListResp.getAdmittedPatientICU();
                            icuPatientList1 = icuPatientListResp.getAdmittedPatientICU();
                            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList));

                            try {
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
        } else {

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
}