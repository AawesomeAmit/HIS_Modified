package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.his.android.Model.DistrictMaster;
import com.his.android.Model.DoctorsList;
import com.his.android.Model.EducationList;
import com.his.android.Model.PatientRegistration;
import com.his.android.Model.StateMaster;
import com.his.android.Model.WardNameList;
import com.his.android.R;
import com.his.android.Response.DistrictMasterResp;
import com.his.android.Response.DoctorWardResp;
import com.his.android.Response.PatientRegistrationListResp;
import com.his.android.Response.PatientRegistrationResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistCovidPatient extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    List<StateMaster> stateList;
    List<WardNameList> wardNameList;
    List<EducationList> educationList;
    List<DoctorsList> doctorNameList;
    List<AgeUnit> ageUnitList;
    List<AgeUnit> genderList;
    PatientRegistration patientRegistration;
    private static int subDeptID;
    List<DistrictMaster> cityList;
    private SimpleDateFormat format2;
    private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    private Date today = new Date();
    private TextView txtFrmDate;
    ArrayAdapter<StateMaster> adpState;
    ArrayAdapter<WardNameList> adpWard;
    ArrayAdapter<EducationList> adpEdu;
    ArrayAdapter<DoctorsList> adpDoctor;
    ArrayAdapter<AgeUnit> adpAge;
    ArrayAdapter<AgeUnit> adpGender;
    ArrayAdapter<DistrictMaster> adpCity;
    private TextView txtFrmTime, tvSave;
    CheckBox chkCoq, chkMultiVit, chkGreenTea, chkBroccoli, chkCinnamon;
    int aadharVal, bplVal, xRayVal, sampleVal;
    RadioButton rbAadharY, rbAadharN, rbBplY, rbBplN, rbXRayY, rbXRayN, rbSampleY, rbSampleN;
    Spinner spnWard, spnAgeUnit, spnGender, spnEdu, spnDoctor, spnState, spnCity;
    EditText edtFloor, edtCaseId, edtPid, edtName, edtAge, edtAddress, edtPulse, edtBpSys, edtBpDias, edtTemp, edtSpo2, edtRbs, edtHistory, edtSymptoms;
    private static String fromDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_covid_patient);
        txtFrmTime = findViewById(R.id.txtFrmTime);
        txtFrmDate = findViewById(R.id.txtFrmDate);
        edtCaseId = findViewById(R.id.edtCaseId);
//        edtPid = findViewById(R.id.edtPid);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtAddress = findViewById(R.id.edtAddress);
        edtPulse = findViewById(R.id.edtPulse);
        rbAadharY = findViewById(R.id.rbAadharY);
        rbAadharN = findViewById(R.id.rbAadharN);
        rbBplY = findViewById(R.id.rbBplY);
        edtCaseId = findViewById(R.id.edtCaseId);
        rbBplN = findViewById(R.id.rbBplN);
        rbXRayY = findViewById(R.id.rbXRayY);
        rbXRayN = findViewById(R.id.rbXRayN);
        rbSampleY = findViewById(R.id.rbSampleY);
        rbSampleN = findViewById(R.id.rbSampleN);
        edtBpSys = findViewById(R.id.edtBpSys);
        edtBpSys = findViewById(R.id.edtBpSys);
        edtBpDias = findViewById(R.id.edtBpDias);
        edtTemp = findViewById(R.id.edtTemp);
        edtSpo2 = findViewById(R.id.edtSpo2);
        edtRbs = findViewById(R.id.edtRbs);
        edtHistory = findViewById(R.id.edtHistory);
        edtSymptoms = findViewById(R.id.edtSymptoms);
        edtFloor = findViewById(R.id.edtFloor);
        spnWard = findViewById(R.id.spnWard);
        spnAgeUnit = findViewById(R.id.spnAgeUnit);
        spnGender = findViewById(R.id.spnGender);
        spnEdu = findViewById(R.id.spnEdu);
        spnState = findViewById(R.id.spnState);
        spnCity = findViewById(R.id.spnCity);
        spnDoctor = findViewById(R.id.spnDoctor);
        chkCoq = findViewById(R.id.chkCoq);
        chkMultiVit = findViewById(R.id.chkMultiVit);
        chkGreenTea = findViewById(R.id.chkGreenTea);
        chkBroccoli = findViewById(R.id.chkBroccoli);
        chkCinnamon = findViewById(R.id.chkCinnamon);
        tvSave = findViewById(R.id.tvSave);
        tvSave.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format2 = new SimpleDateFormat("hh:mm a");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
//        today.setHours(8);
//        today.setMinutes(0);
        txtFrmTime.setText(format2.format(today));
        txtFrmDate.setOnClickListener(this);
        txtFrmTime.setOnClickListener(this);
        stateList = new ArrayList<>();
        stateList.add(0, new StateMaster(0, "Select State"));
        wardNameList = new ArrayList<>();
        wardNameList.add(0, new WardNameList(0, "Select Ward"));
        educationList = new ArrayList<>();
        educationList.add(0, new EducationList(0, "Select Level"));
        doctorNameList = new ArrayList<>();
        doctorNameList.add(0, new DoctorsList(0, "Select Doctor"));
        cityList = new ArrayList<>();
        cityList.add(0, new DistrictMaster(0, "Select City"));
        ageUnitList = new ArrayList<>();
        ageUnitList.add(0, new AgeUnit(1, "Year"));
        ageUnitList.add(1, new AgeUnit(2, "Month"));
        ageUnitList.add(2, new AgeUnit(3, "Day"));
        adpAge = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, ageUnitList);
        spnAgeUnit.setAdapter(adpAge);
        genderList = new ArrayList<>();
        genderList.add(0, new AgeUnit(1, "M"));
        genderList.add(1, new AgeUnit(2, "F"));
        adpGender = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, genderList);
        spnGender.setAdapter(adpGender);
        loadData();
        rbAadharY.setOnCheckedChangeListener(this);
        rbAadharN.setOnCheckedChangeListener(this);
        rbBplY.setOnCheckedChangeListener(this);
        rbBplN.setOnCheckedChangeListener(this);
        rbXRayY.setOnCheckedChangeListener(this);
        rbXRayN.setOnCheckedChangeListener(this);
        rbSampleY.setOnCheckedChangeListener(this);
        rbSampleN.setOnCheckedChangeListener(this);
        spnAgeUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!edtAge.getText().toString().trim().equalsIgnoreCase("")) {
                    if (Integer.parseInt(edtAge.getText().toString().trim()) >= 18 && spnAgeUnit.getSelectedItemPosition() == 0) {
                        subDeptID = 2;
                        getDoctor(2);
                    } else {
                        subDeptID = 7;
                        getDoctor(7);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getDistrict(stateList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equalsIgnoreCase("")) {
                    if (Integer.parseInt(charSequence.toString()) >= 18) {
                        if (spnAgeUnit.getSelectedItemPosition() == 0) {
                            subDeptID = 2;
                            getDoctor(2);
                        } else {
                            subDeptID = 7;
                            getDoctor(7);
                        }
                    } else {
                        subDeptID = 7;
                        getDoctor(7);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getDoctor(int id) {
        Utils.showRequestDialog(mActivity);
        Call<DoctorWardResp> call = RetrofitClient.getInstance().getApi().getWardDoctors(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), id);
        call.enqueue(new Callback<DoctorWardResp>() {
            @Override
            public void onResponse(Call<DoctorWardResp> call, Response<DoctorWardResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        doctorNameList = response.body().getDoctorsList();
                        wardNameList = null;
                        wardNameList = new ArrayList<>();
                        wardNameList.add(0, new WardNameList(0, "Select Ward"));
                        wardNameList.addAll(1, response.body().getWardList());
                        adpDoctor = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, doctorNameList);
                        spnDoctor.setAdapter(adpDoctor);
                        adpWard = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, wardNameList);
                        spnWard.setAdapter(adpWard);
                    }
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<DoctorWardResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void getDistrict(int id) {
        Utils.showRequestDialog(mActivity);
        Call<DistrictMasterResp> call = RetrofitClient.getInstance().getApi().getDistrictList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), id);
        call.enqueue(new Callback<DistrictMasterResp>() {
            @Override
            public void onResponse(Call<DistrictMasterResp> call, Response<DistrictMasterResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        cityList = response.body().getDistrictMaster();
                    }
                    adpCity = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, cityList);
                    spnCity.setAdapter(adpCity);
                    if (id == 34)
                        spnCity.setSelection(51);
                    Utils.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<DistrictMasterResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void saveData() {
        if (!edtName.getText().toString().trim().equals("")) {
            if (!edtAge.getText().toString().trim().equals("")) {
                Utils.showRequestDialog(mActivity);
                Date today1 = new Date();
                String dob;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                if (Integer.parseInt(edtAge.getText().toString().trim()) > 0) {
                    if (spnAgeUnit.getSelectedItemPosition() == 0) {
                        cal.add(Calendar.YEAR, -Integer.parseInt(edtAge.getText().toString().trim()));
                    } else if (spnAgeUnit.getSelectedItemPosition() == 1)
                        cal.add(Calendar.MONTH, -Integer.parseInt(edtAge.getText().toString().trim()));
                    else
                        cal.add(Calendar.DATE, -Integer.parseInt(edtAge.getText().toString().trim()));
                    dob = format.format(cal.getTime());
                } else dob = format.format(today1);
                Call<PatientRegistrationResp> call = RetrofitClient.getInstance().getApi().savePatientRegistration(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                        SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtName.getText().toString().trim(), "0", "",
                        spnGender.getSelectedItem().toString(), dob, "", stateList.get(spnState.getSelectedItemPosition()).getId().toString(),
                        cityList.get(spnCity.getSelectedItemPosition()).getId().toString(), edtAddress.getText().toString().trim(), "0", "0",
                        "", "0", "0", "", String.valueOf(subDeptID),
                        doctorNameList.get(spnDoctor.getSelectedItemPosition()).getDoctorID().toString(), "0",
                        SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), "3059", "2028", "", "",//66, 33
                        "", "2028", "", "0", edtBpSys.getText().toString().trim(), edtBpDias.getText().toString().trim(),
                        edtPulse.getText().toString().trim(), edtTemp.getText().toString().trim(), "",
                        "", educationList.get(spnEdu.getSelectedItemPosition()).getId().toString(), edtSpo2.getText().toString().trim(), edtRbs.getText().toString().trim());
                call.enqueue(new Callback<PatientRegistrationResp>() {
                    @Override
                    public void onResponse(Call<PatientRegistrationResp> call, Response<PatientRegistrationResp> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                patientRegistration = response.body().getPatientRegistration().get(0);
                                saveIPD();
                                Utils.hideDialog();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientRegistrationResp> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            } else Toast.makeText(mActivity, "Please Enter Valid Age!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(mActivity, "Please Enter Patient Name!", Toast.LENGTH_SHORT).show();
    }

    private void saveIPD() {
        Utils.showRequestDialog(mActivity);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().insertIPDRegistration(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(patientRegistration.getPid()), String.valueOf(subDeptID),
                doctorNameList.get(spnDoctor.getSelectedItemPosition()).getDoctorID().toString(), wardNameList.get(spnWard.getSelectedItemPosition()).getId().toString(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(chkCoq.isChecked()), String.valueOf(chkMultiVit.isChecked()),
                String.valueOf(chkGreenTea.isChecked()), String.valueOf(chkBroccoli.isChecked()), String.valueOf(chkCinnamon.isChecked()), "3059",
                "2028", String.valueOf(sampleVal), String.valueOf(aadharVal), edtBpSys.getText().toString().trim(), edtBpDias.getText().toString().trim(),
                edtSpo2.getText().toString().trim(), edtPulse.getText().toString().trim(), edtTemp.getText().toString().trim(),
                "1", edtCaseId.getText().toString().trim(), String.valueOf(bplVal), String.valueOf(xRayVal),
                String.valueOf(sampleVal), edtHistory.getText().toString().trim(), edtSymptoms.getText().toString().trim(),
                format.format(today), edtRbs.getText().toString().trim());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChecklistCovidPatient.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, ChecklistCovidPatient.class));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void loadData() {
        Utils.showRequestDialog(mActivity);
        Call<PatientRegistrationListResp> call = RetrofitClient.getInstance().getApi().getPatientRegistrationList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<PatientRegistrationListResp>() {
            @Override
            public void onResponse(Call<PatientRegistrationListResp> call, Response<PatientRegistrationListResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        stateList.addAll(1, response.body().getStateMaster());
//                        wardNameList.addAll(1, response.body().getWardNameList());
                        educationList.addAll(1, response.body().getEducationList());
                    }
                    adpState = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, stateList);
                    spnState.setAdapter(adpState);
                    spnState.setSelection(35);
                    adpWard = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, wardNameList);
                    spnWard.setAdapter(adpWard);
                    adpDoctor = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, doctorNameList);
                    spnDoctor.setAdapter(adpDoctor);
                    adpEdu = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, educationList);
                    spnEdu.setAdapter(adpEdu);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientRegistrationListResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.rbAadharY) {
            if (rbAadharY.isChecked())
                aadharVal = 1;
            else aadharVal = 0;
        } else if (compoundButton.getId() == R.id.rbAadharN) {
            if (rbAadharN.isChecked())
                aadharVal = 0;
            else aadharVal = 1;
        } else if (compoundButton.getId() == R.id.rbBplY) {
            if (rbBplY.isChecked())
                bplVal = 1;
            else bplVal = 0;
        } else if (compoundButton.getId() == R.id.rbBplN) {
            if (rbBplN.isChecked())
                bplVal = 0;
            else bplVal = 1;
        } else if (compoundButton.getId() == R.id.rbXRayY) {
            if (rbXRayY.isChecked())
                xRayVal = 1;
            else xRayVal = 0;
        } else if (compoundButton.getId() == R.id.rbXRayN) {
            if (rbXRayN.isChecked())
                xRayVal = 0;
            else xRayVal = 1;
        } else if (compoundButton.getId() == R.id.rbSampleY) {
            if (rbSampleY.isChecked())
                sampleVal = 1;
            else sampleVal = 0;
        } else if (compoundButton.getId() == R.id.rbSampleN) {
            if (rbSampleN.isChecked())
                sampleVal = 0;
            else sampleVal = 1;
        }
    }

    private class AgeUnit {
        String value;
        int id;

        AgeUnit(int id, String value) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }

        @NonNull
        @Override
        public String toString() {
            return value;
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
        } else if (view.getId() == R.id.txtFrmTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour = i;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtFrmTime.setText(format2.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        } else if (view.getId() == R.id.tvSave) {
            saveData();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, PreDashboard.class));
    }
}