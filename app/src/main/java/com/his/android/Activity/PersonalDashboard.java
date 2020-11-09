package com.his.android.Activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Activity.UploadMultipleImg.Universalres;
import com.his.android.Model.IntakeDetail;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.Model.PatientDetailDashboard;
import com.his.android.Model.VitalDetail;
import com.his.android.Model.MedicineDetail;
import com.his.android.Model.Ward;
import com.his.android.Response.PatientDashboardResp;
import com.his.android.R;
import com.his.android.Response.WardResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.InputFilterMinMax;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.his.android.Model.IntakeDashboard;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.pm.ActivityInfo;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonalDashboard extends BaseActivity {
    TextView txtName, txtTransfer, txtDiagnosis, diagnosis, txtRefresh;
    EditText edtPid;
    RecyclerView rvActivity, rvMedication, rvIntake, rvVitals;
    String date = "", time;
    SimpleDateFormat format1;
    SimpleDateFormat format2;
    SimpleDateFormat format3;
    SimpleDateFormat format4;
    static List<Ward> wardLists1=new ArrayList<>();
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    Spinner popUpspnWard;
    ArrayAdapter arrayAdapter;
    Dialog dialog;
    Date today;
    Calendar c;
    List<PatientDetailDashboard> patientDetailsDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_dashboard);
        txtName = findViewById(R.id.txtName);
        txtDiagnosis = findViewById(R.id.txtDiagnosis);
        edtPid = findViewById(R.id.edtPid);
        rvActivity = findViewById(R.id.rvActivity);
        rvMedication = findViewById(R.id.rvMedication);
        rvIntake = findViewById(R.id.rvIntake);
        rvVitals = findViewById(R.id.rvVitals);
        txtRefresh = findViewById(R.id.txtRefresh);
        txtTransfer = findViewById(R.id.txtTransfer);
        txtDiagnosis = findViewById(R.id.txtDiagnosis);
        diagnosis = findViewById(R.id.diagnosis);
        rvActivity.setLayoutManager(new LinearLayoutManager(mActivity));
        rvMedication.setLayoutManager(new LinearLayoutManager(mActivity));
        rvVitals.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL));
        rvIntake.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearLayoutManager manager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        rvVitals.setLayoutManager(manager);
        format1 = new SimpleDateFormat("yyyy-MM-dd");
        format4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format2 = new SimpleDateFormat("HH:mm");
        format3 = new SimpleDateFormat("hh:mm a");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        edtPid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtRefresh.setVisibility(View.GONE);
                txtTransfer.setVisibility(View.GONE);
                txtDiagnosis.setVisibility(View.GONE);
                diagnosis.setVisibility(View.GONE);
                txtName.setText("");
                rvVitals.setAdapter(null);
                rvActivity.setAdapter(null);
                rvMedication.setAdapter(null);
                rvIntake.setAdapter(null);
                if (charSequence.length() > 6) {
                    bind(1, 1, 1, 1, 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hitGetWard();
        txtTransfer.setOnClickListener(view -> alertPatientTransfer(patientDetailsDashboard.get(0).getPmID(), patientDetailsDashboard.get(0).getCorrectWardName(), patientDetailsDashboard.get(0).getCorrectWardID())
                /*hitPatientTransfer(patientDetailsDashboard.get(0).getPmID(), patientDetailsDashboard.get(0).getCorrectWardName(), patientDetailsDashboard.get(0).getCorrectWardID())*/);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (edtPid.getText().length() > 6) {
                    bind(1, 1, 1, 1, 1);
                }
            }
        }, 0, 3600000);
        txtRefresh.setOnClickListener(view -> bind(1, 1, 1, 1,1));
    }

    private void startStop(int physicalActivityID, int activityStatus) {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().startStopPatientActivity(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtPid.getText().toString().trim(), physicalActivityID, activityStatus, SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    bind(0, 0, 1, 0, 0);
                } else {
                    try {
                        Toast.makeText(PersonalDashboard.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bind(int patientDetails, int vitalDetails, int patientActivityDetails, int medicineDetails, int intakeDetails){
        Utils.showRequestDialog(mActivity);
        Call<PatientDashboardResp> call= RetrofitClient.getInstance().getApi().getPersonalDashBoardDetails(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtPid.getText().toString().trim(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), patientDetails, vitalDetails, patientActivityDetails, medicineDetails, intakeDetails);
        call.enqueue(new Callback<PatientDashboardResp>() {
            @Override
            public void onResponse(Call<PatientDashboardResp> call, Response<PatientDashboardResp> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getPatientDetails().isEmpty()) {
                        patientDetailsDashboard = response.body().getPatientDetails();
                        if (!response.body().getPatientDetails().get(0).getCurrentWardID().equals(response.body().getPatientDetails().get(0).getCorrectWardID())) {
                            new AlertDialog.Builder(mActivity).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                                    .setMessage("Are you sure that you want to transfer patient to " + patientDetailsDashboard.get(0).getCorrectWardName() + "?")
                                    .setCancelable(true)
                                    .setPositiveButton(
                                            "Yes",
                                            (dialog, id) -> {
                                                hitPatientTransfer(patientDetailsDashboard.get(0).getPmID(), patientDetailsDashboard.get(0).getCorrectWardName(), patientDetailsDashboard.get(0).getCorrectWardID());
                                                if (patientDetails == 1) {
                                                    txtName.setText(response.body().getPatientDetails().get(0).getPatientName() + "/" + response.body().getPatientDetails().get(0).getAge() + " " + response.body().getPatientDetails().get(0).getAgeUnit() + ", "
                                                            + response.body().getPatientDetails().get(0).getCorrectWardName());
                                                    txtDiagnosis.setText(response.body().getPatientDetails().get(0).getDiagnosis());
                                                }
                                                if (patientActivityDetails == 1)
                                                    rvActivity.setAdapter(new ActivityDashboardAdp(response.body().getPatientActivityDetails()));
                                                if (intakeDetails == 1)
                                                    rvIntake.setAdapter(new IntakeAdp(response.body().getIntakeDetails()));
                                                if (vitalDetails == 1)
                                                    rvVitals.setAdapter(new VitalAdp(response.body().getVitalDetails()));
                                                if (medicineDetails == 1)
                                                    rvMedication.setAdapter(new MedicineDetailAdp(response.body().getMedicineDetails()));
                                            })

                                    .setNegativeButton(
                                            "No",
                                            (dialog, id) -> {
                                                dialog.cancel();
                                                txtName.setText("");
                                                rvVitals.setAdapter(null);
                                                rvActivity.setAdapter(null);
                                                rvMedication.setAdapter(null);
                                                rvIntake.setAdapter(null);
                                            })
                                    .show();
                        } else {
                            if (patientDetails == 1) {
                                txtName.setText(response.body().getPatientDetails().get(0).getPatientName() + "/" + response.body().getPatientDetails().get(0).getAge() + " " + response.body().getPatientDetails().get(0).getAgeUnit() + ", "
                                        + response.body().getPatientDetails().get(0).getCorrectWardName());
                                txtDiagnosis.setText(response.body().getPatientDetails().get(0).getDiagnosis());
                            }
                            if (patientActivityDetails == 1)
                                rvActivity.setAdapter(new ActivityDashboardAdp(response.body().getPatientActivityDetails()));
                            if (intakeDetails == 1)
                                rvIntake.setAdapter(new IntakeAdp(response.body().getIntakeDetails()));
                            if (vitalDetails == 1)
                                rvVitals.setAdapter(new VitalAdp(response.body().getVitalDetails()));
                            if (medicineDetails == 1)
                                rvMedication.setAdapter(new MedicineDetailAdp(response.body().getMedicineDetails()));
                        }
                    } else {
                        if (patientDetails == 1) {
                            txtName.setText(response.body().getPatientDetails().get(0).getPatientName() + "/" + response.body().getPatientDetails().get(0).getAge() + " " + response.body().getPatientDetails().get(0).getAgeUnit() + ", "
                                    + response.body().getPatientDetails().get(0).getCorrectWardName());
                            txtDiagnosis.setText(response.body().getPatientDetails().get(0).getDiagnosis());
                        }
                        if (patientActivityDetails == 1)
                            rvActivity.setAdapter(new ActivityDashboardAdp(response.body().getPatientActivityDetails()));
                        if (intakeDetails == 1)
                            rvIntake.setAdapter(new IntakeAdp(response.body().getIntakeDetails()));
                        if (vitalDetails == 1)
                            rvVitals.setAdapter(new VitalAdp(response.body().getVitalDetails()));
                        if (medicineDetails == 1)
                            rvMedication.setAdapter(new MedicineDetailAdp(response.body().getMedicineDetails()));
                    }
                    txtRefresh.setVisibility(View.VISIBLE);
                    txtTransfer.setVisibility(View.VISIBLE);
                    txtDiagnosis.setVisibility(View.VISIBLE);
                    diagnosis.setVisibility(View.VISIBLE);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientDashboardResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void showPopup(int dietID, int isSupplement, String percent) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_dashboard_medication_submit, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        TextView txtDate = popupView.findViewById(R.id.txtDate);
        TextView txtTime = popupView.findViewById(R.id.txtTime);
        TextView btnSave = popupView.findViewById(R.id.txtSubmit);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnSave.setOnClickListener(view -> {
            giveDiet(dietID, format1.format(today), format2.format(today), isSupplement, percent);
            popupWindow.dismiss();
        });
        c = Calendar.getInstance();
        today = new Date();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format3.format(today));
        txtDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        txtTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, j, i1) -> {
                mHour = j;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format3.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        });
    }
    private void showPopupMed(int prescriptionID, int pmID) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_dashboard_medication_submit, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        TextView txtDate = popupView.findViewById(R.id.txtDate);
        TextView txtTime = popupView.findViewById(R.id.txtTime);
        TextView btnSave = popupView.findViewById(R.id.txtSubmit);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnSave.setOnClickListener(view -> {
            action(prescriptionID, pmID, format4.format(today));
            popupWindow.dismiss();
        });
        c = Calendar.getInstance();
        today = new Date();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtDate.setText(Utils.formatDate(date));
        txtTime.setText(format3.format(today));
        txtDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(date));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        txtTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, j, i1) -> {
                mHour = j;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format3.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        });
    }
    private void action(int prescriptionID, int pmID, String dateTime) {
        Utils.showRequestDialog(mActivity);

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveIntakePrescription1(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), "", pmID, prescriptionID, 0, String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()), dateTime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    bind(0, 0, 0, 1, 0);
                } else {
                    try {
                        Toast.makeText(PersonalDashboard.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(PersonalDashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void giveDiet(int dietID, String dietDate, String dietTime, int isSupplement, String consumptionPercentage) {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateIntakeConsumption(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtPid.getText().toString().trim(), dietID, dietDate, dietTime, isSupplement, consumptionPercentage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    bind(0, 0, 0, 0, 1);
                } else {
                    try {
                        Toast.makeText(PersonalDashboard.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
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
    }
    private void blink(LinearLayout txt){
        ObjectAnimator anim=ObjectAnimator.ofInt(txt, "BackgroundColor", Color.WHITE, Color.parseColor("#579AD3"), Color.WHITE);
        anim.setDuration(800).setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
    }
    public class ActivityDashboardAdp extends RecyclerView.Adapter<ActivityDashboardAdp.RecyclerViewHolder> {
        private List<PatientActivityDetail> patientActivityDetails;
        public ActivityDashboardAdp(List<PatientActivityDetail> patientActivityDetails) {
            this.patientActivityDetails = patientActivityDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_patient_dashboard_activity, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtActivity.setText(String.valueOf(patientActivityDetails.get(i).getPhysicalActivityName()));
            if (patientActivityDetails.get(i).getActivityStatus().equalsIgnoreCase("r"))
                blink(holder.llMain);
            holder.txtActivity.setOnClickListener(view -> startStop(patientActivityDetails.get(i).getPhysicalActivityID(), patientActivityDetails.get(i).getActivityStatus().equalsIgnoreCase("r")?0:1));
        }

        @Override
        public int getItemCount() {
            return patientActivityDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtActivity;
            ImageView imgActivity;
            LinearLayout llMain;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtActivity =itemView.findViewById(R.id.txtActivity);
                imgActivity =itemView.findViewById(R.id.imgActivity);
                llMain =itemView.findViewById(R.id.llMain);
            }
        }
    }

    public class IntakeAdp extends RecyclerView.Adapter<IntakeAdp.RecyclerViewHolder> {
        private List<IntakeDetail> foodDetails;
        Gson gson = new Gson();
        public IntakeAdp(List<IntakeDetail> foodDetails) {
            this.foodDetails = foodDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_dashboard_meal, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtDate.setText("Adviced Time: "+foodDetails.get(i).getIntakeDateTime());
            List<IntakeDashboard> intakeDashboardList= gson.fromJson(foodDetails.get(i).getIntake(), new TypeToken<List<IntakeDashboard>>(){}.getType());
            holder.rvInnerMeal.setAdapter(new IntakeInnerAdp(intakeDashboardList));
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtDate;
            RecyclerView rvInnerMeal;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDate =itemView.findViewById(R.id.txtDate);
                rvInnerMeal=itemView.findViewById(R.id.rvInnerMeal);
                rvInnerMeal.setLayoutManager(new LinearLayoutManager(mActivity));
            }
        }
    }

    public class IntakeInnerAdp extends RecyclerView.Adapter<IntakeInnerAdp.RecyclerViewHolder> {
        private List<IntakeDashboard> foodDetails;
        Gson gson = new Gson();
        public IntakeInnerAdp(List<IntakeDashboard> foodDetails) {
            this.foodDetails = foodDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_inner_dashboard_meal, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtFluid.setText(String.valueOf(foodDetails.get(i).getIntakeName()));
            holder.txtQty.setText(String.valueOf(foodDetails.get(i).getGivenIntakeQuantity()));
            holder.txtUnit.setText(String.valueOf(foodDetails.get(i).getUnit()));
            ((RadioButton)holder.rgConsumption.getChildAt(4)).setChecked(true);
            holder.txtGive.setOnClickListener(view -> {
                int selectedId = holder.rgConsumption.getCheckedRadioButtonId();
                RadioButton rbSelected = findViewById(selectedId);
                showPopup(foodDetails.get(i).getDietID(), foodDetails.get(i).getIsSupplement(), rbSelected.getText().toString().substring(0, rbSelected.getText().length()-1));
            });
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtFluid,txtQty,txtUnit, txtDateTime, txtEdit, txtSave, txtPer, txtEditDate, txtClose, txtGive;
            RadioGroup rgConsumption;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtFluid =itemView.findViewById(R.id.txtFluid);
                txtQty=itemView.findViewById(R.id.txtStr);
                txtUnit=itemView.findViewById(R.id.txtUnit);
                txtDateTime=itemView.findViewById(R.id.txtDateTime);
                txtEdit=itemView.findViewById(R.id.txtEdit);
                rgConsumption=itemView.findViewById(R.id.rgConsumption);
                txtSave=itemView.findViewById(R.id.txtSave);
                txtPer=itemView.findViewById(R.id.txtPer);
                txtGive=itemView.findViewById(R.id.txtGive);
                txtClose=itemView.findViewById(R.id.txtClose);
            }
        }
    }

    public class VitalAdp extends RecyclerView.Adapter<VitalAdp.RecyclerViewHolder> {
        private List<VitalDetail> vitalDetailList;
        public VitalAdp(List<VitalDetail> vitalDetailList) {
            this.vitalDetailList = vitalDetailList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_dashboard_vital, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            Picasso.with(mActivity).load(vitalDetailList.get(i).getVitalIcon()).into(holder.imgVital);
            holder.txtVital.setText(/*vitalDetailList.get(i).getVitalName() + " - " + */vitalDetailList.get(i).getVitalValue());
        }

        @Override
        public int getItemCount() {
            return vitalDetailList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtVital;
            ImageView imgVital;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtVital =itemView.findViewById(R.id.txtVital);
                imgVital =itemView.findViewById(R.id.imgVital);
            }
        }
    }


    public class MedicineDetailAdp extends RecyclerView.Adapter<MedicineDetailAdp.RecyclerViewHolder> {
        private List<MedicineDetail> prescriptionList;

        public MedicineDetailAdp(List<MedicineDetail> prescriptionList) {
            this.prescriptionList = prescriptionList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_dashboaard_prescription, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
                holder.txtMed.setText(prescriptionList.get(i).getMedicineName().trim().toUpperCase());
                holder.txtStr.setText(prescriptionList.get(i).getDoseStrength() + " " + prescriptionList.get(i).getDoseUnit());
                holder.txtFreq.setText(prescriptionList.get(i).getDoseFrequency());
                holder.txtDosage.setText(prescriptionList.get(i).getDosageForm());
                holder.txtRemark.setText(prescriptionList.get(i).getRemark());
                if(prescriptionList.get(i).getRemark().equalsIgnoreCase("")){
                    holder.txtRemark.setVisibility(View.GONE);
                    holder.remark.setVisibility(View.GONE);
                } else {
                    holder.txtRemark.setVisibility(View.VISIBLE);
                    holder.remark.setVisibility(View.VISIBLE);
                }
                holder.tvGivenTime.setText(prescriptionList.get(i).getDuration());
                holder.txtComment.setOnClickListener(view -> showPopupMed(prescriptionList.get(i).getPrescriptionID(), prescriptionList.get(i).getPmID()));
                holder.txtGive.setOnClickListener(view -> showPopupMed(prescriptionList.get(i).getPrescriptionID(), prescriptionList.get(i).getPmID()));
        }

        @Override
        public int getItemCount() {
            return prescriptionList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtMed, txtStr, txtFreq, txtDate, txtDosage, txtRemark, tvGivenTime, tvGivenBy, txtGive, txtComment, remark;
            ConstraintLayout llMain;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMed = itemView.findViewById(R.id.txtMed);
                txtStr = itemView.findViewById(R.id.txtStr);
                txtFreq = itemView.findViewById(R.id.txtFreq);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtDosage = itemView.findViewById(R.id.txtDosage);
                txtRemark = itemView.findViewById(R.id.txtRemark);
                tvGivenTime = itemView.findViewById(R.id.tvGivenTime);
                tvGivenBy = itemView.findViewById(R.id.tvGivenBy);
                llMain = itemView.findViewById(R.id.llMain);
                txtGive = itemView.findViewById(R.id.txtGive);
                txtComment = itemView.findViewById(R.id.txtComment);
                remark = itemView.findViewById(R.id.remark);
            }
        }
    }

    private void alertPatientTransfer(int pmID, String wardName, int wardID) {
        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_personal_dashboard_transfer);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        popUpspnWard = dialog.findViewById(R.id.spnWard);
        arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, wardLists1);
        popUpspnWard.setAdapter(arrayAdapter);
        ivClose.setOnClickListener(view -> dialog.dismiss());
        tvSubmit.setOnClickListener(view -> {
            try {
                if (popUpspnWard.getSelectedItemPosition()==0) {
                    Toast.makeText(mActivity, "Please select ward", Toast.LENGTH_SHORT).show();
                } else {
                    if (ConnectivityChecker.checker(mActivity)) {
                        hitPatientTransfer(pmID, wardLists1.get(popUpspnWard.getSelectedItemPosition()).getShortName(), wardLists1.get(popUpspnWard.getSelectedItemPosition()).getId());
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void hitPatientTransfer(int pmID, String wardName, int wardID) {
        Utils.showRequestDialog(mActivity);
        Call<Universalres> call = RetrofitClient.getInstance().getApi().patientIPDTransferToWard(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(pmID),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(wardID)
        );

        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Transferred Successfully!", Toast.LENGTH_SHORT).show();
                    if(dialog!=null)
                    dialog.dismiss();
                    //if(!out)
//                    hitAccept();
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
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void hitGetWard() {
        Utils.showRequestDialog(mActivity);
        Call<WardResp> call = RetrofitClient.getInstance().getApi().getWardTransferList(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()
        );

        call.enqueue(new Callback<WardResp>() {
            @Override
            public void onResponse(Call<WardResp> call, Response<WardResp> response) {
                if (response.isSuccessful()) {
                    wardLists1.clear();
                    wardLists1.add(new Ward(0, "Select Ward", 0));
                    wardLists1.addAll(response.body().getWardTransferList());
                } else Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<WardResp> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}