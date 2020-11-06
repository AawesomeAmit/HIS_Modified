package com.his.android.Activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.view.WindowManager;
import android.view.animation.Animation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.IntakeDetail;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.Model.VitalDetail;
import com.his.android.Model.MedicineDetail;
import com.his.android.Response.PatientDashboardResp;
import com.his.android.R;
import com.his.android.Utils.InputFilterMinMax;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.his.android.Model.IntakeDashboard;
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
    TextView txtName;
    EditText edtPid;
    RecyclerView rvActivity, rvMedication, rvIntake, rvVitals;
    String date = "", time;
    SimpleDateFormat format1;
    SimpleDateFormat format2;
    SimpleDateFormat format3;
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    Date today;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_dashboard);
        txtName=findViewById(R.id.txtName);
        edtPid=findViewById(R.id.edtPid);
        rvActivity=findViewById(R.id.rvActivity);
        rvMedication=findViewById(R.id.rvMedication);
        rvIntake=findViewById(R.id.rvIntake);
        rvVitals=findViewById(R.id.rvVitals);
        rvActivity.setLayoutManager(new LinearLayoutManager(mActivity));
        rvMedication.setLayoutManager(new LinearLayoutManager(mActivity));
        rvVitals.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL));
        rvIntake.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearLayoutManager manager=new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        rvVitals.setLayoutManager(manager);
        format1 = new SimpleDateFormat("yyyy-MM-dd");
        format2 = new SimpleDateFormat("HH:mm");
        format3 = new SimpleDateFormat("hh:mm a");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        edtPid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtName.setText("");
                rvVitals.setAdapter(null);
                rvActivity.setAdapter(null);
                rvMedication.setAdapter(null);
                rvIntake.setAdapter(null);
                if(charSequence.length()>6){
                    bind(1, 1, 1, 1, 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                        Toast.makeText(PersonalDashboard.this, response.body().string(), Toast.LENGTH_SHORT).show();
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
                if(response.isSuccessful()){
                    if (patientDetails==1)
                    txtName.setText(response.body().getPatientDetails().get(0).getPatientName() + " " + response.body().getPatientDetails().get(0).getAge() + " " + response.body().getPatientDetails().get(0).getAgeUnit());
                    if (patientActivityDetails==1)
                    rvActivity.setAdapter(new ActivityDashboardAdp(response.body().getPatientActivityDetails()));
                    if (intakeDetails==1)
                    rvIntake.setAdapter(new IntakeAdp(response.body().getIntakeDetails()));
                    if (vitalDetails==1)
                    rvVitals.setAdapter(new VitalAdp(response.body().getVitalDetails()));
                    if (medicineDetails==1)
                    rvMedication.setAdapter(new MedicineDetailAdp(response.body().getMedicineDetails()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientDashboardResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void showPopup(int prescriptionID, int pmID) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_medication_staff_comment, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
        EditText edtComment = popupView.findViewById(R.id.edtComment);
        CheckBox chkMedicine = popupView.findViewById(R.id.chkMedicine);
        TextView btnSave = popupView.findViewById(R.id.btnSave);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        lLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
        btnSave.setOnClickListener(view -> {
            if (chkMedicine.isChecked())
                action(prescriptionID, pmID, edtComment.getText().toString().trim(), 2);
            else action(prescriptionID, pmID, edtComment.getText().toString().trim(), 0);
        });
    }
    private void action(int prescriptionID, int pmID, String comment, int status) {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveIntakePrescription(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), comment, pmID, prescriptionID, status, String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    bind(0, 0, 0, 1, 0);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
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
                    Toast.makeText(mActivity, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    bind(0, 0, 0, 0, 1);
                } else {
                    try {
                        Toast.makeText(PersonalDashboard.this, response.body().string(), Toast.LENGTH_SHORT).show();
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
    private void blink(TextView txt){
        ObjectAnimator anim=ObjectAnimator.ofInt(txt, "BackgroundColor", Color.WHITE, Color.RED, Color.WHITE);
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
                blink(holder.txtActivity);
//            Picasso.with(mActivity).load(patientActivityDetails.get(i).getIconImage()).into(holder.imgActivity);
//            Picasso.with(mActivity).load(patientActivityDetails.get(i).getIconImage()).resize((int) getResources().getDimension(R.dimen._15sdp), (int) getResources().getDimension(R.dimen._15sdp)).into(holder.imgActivity);
            holder.txtActivity.setOnClickListener(view -> startStop(patientActivityDetails.get(i).getPhysicalActivityID(), patientActivityDetails.get(i).getActivityStatus().equalsIgnoreCase("r")?0:1));
        }

        @Override
        public int getItemCount() {
            return patientActivityDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtActivity;
            ImageView imgActivity;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtActivity =itemView.findViewById(R.id.txtActivity);
                imgActivity =itemView.findViewById(R.id.imgActivity);
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
            holder.txtDate.setText(foodDetails.get(i).getIntakeDateTime());
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
            c = Calendar.getInstance();
            today = new Date();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            date = mYear + "/" + (mMonth + 1) + "/" + mDay;
            holder.txtFluid.setText(String.valueOf(foodDetails.get(i).getIntakeName()));
            holder.txtQty.setText(String.valueOf(foodDetails.get(i).getGivenIntakeQuantity()));
            holder.txtUnit.setText(String.valueOf(foodDetails.get(i).getUnit()));
            holder.txtDate.setOnClickListener(view -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            today.setDate(dayOfMonth);
                            today.setMonth(monthOfYear);
                            today.setYear(year - 1900);
                            holder.txtDate.setText(Utils.formatDate(date));
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            });
            holder.txtTime.setOnClickListener(view -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, j, i1) -> {
                    mHour = j;
                    mMinute = i1;
                    today.setHours(mHour);
                    today.setMinutes(mMinute);
                    holder.txtTime.setText(format3.format(today));
                }, mHour, mMinute, false);
                timePickerDialog.updateTime(today.getHours(), today.getMinutes());
                timePickerDialog.show();
            });

             /*int selectedId = holder.rgConsumption.getCheckedRadioButtonId();
            RadioButton rbSelected=findViewById(selectedId);
            giveDiet(foodDetails.get(i).getDietID(), holder.txtDate.getText().toString(), holder.txtTime.getText().toString(), foodDetails.get(i).getIsSupplement(), rbSelected.getText().toString());*/

            holder.txtDate.setText(Utils.formatDate(date));
            holder.txtTime.setText(format3.format(today));
            holder.txtGive.setOnClickListener(view -> new AlertDialog.Builder(mActivity).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setCancelable(true)
                    .setPositiveButton(
                            "Yes",
                            (dialog, id) -> {
                                int selectedId = holder.rgConsumption.getCheckedRadioButtonId();
                                RadioButton rbSelected = findViewById(selectedId);
                                giveDiet(foodDetails.get(i).getDietID(), format1.format(today), format2.format(today), foodDetails.get(i).getIsSupplement(), rbSelected.getText().toString().substring(0, rbSelected.getText().length()-1));
                            })

                    .setNegativeButton(
                            "No",
                            (dialog, id) -> dialog.cancel())
                    .show());
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtFluid,txtQty,txtUnit, txtDateTime, txtEdit, txtSave, txtPer, txtEditDate, txtClose, txtDate, txtTime, txtGive;
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
                txtDate=itemView.findViewById(R.id.txtDate);
                txtTime=itemView.findViewById(R.id.txtTime);
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
            holder.txtVital.setText(vitalDetailList.get(i).getVitalName() + " - " + vitalDetailList.get(i).getVitalValue());
        }

        @Override
        public int getItemCount() {
            return vitalDetailList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtVital;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtVital =itemView.findViewById(R.id.txtVital);
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
//                holder.tvGivenBy.setText(prescriptionList.get(i).getPrescribeBy());
                holder.tvGivenTime.setText(prescriptionList.get(i).getDuration());
                /*if (prescriptionList.get(i).getColorStatus() == null)
                    holder.txtGive.setBackgroundResource(R.drawable.ic_check_blue);
                else {
                    if (prescriptionList.get(i).getColorStatus().equalsIgnoreCase("blue"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_blue);
                    else if (prescriptionList.get(i).getColorStatus().equalsIgnoreCase("green"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_green);
                    else if (prescriptionList.get(i).getColorStatus().equalsIgnoreCase("red"))
                        holder.txtGive.setBackgroundResource(R.drawable.ic_check_red);
                }*/
                holder.txtComment.setOnClickListener(view -> showPopup(prescriptionList.get(i).getPrescriptionID(), prescriptionList.get(i).getPmID()));
                holder.txtGive.setOnClickListener(view -> new AlertDialog.Builder(mActivity).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setCancelable(true)
                        .setPositiveButton(
                                "Yes",
                                (dialog, id) -> action(prescriptionList.get(i).getPrescriptionID(), prescriptionList.get(i).getPmID(), "", 0))

                        .setNegativeButton(
                                "No",
                                (dialog, id) -> dialog.cancel())
                        .show());
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
}