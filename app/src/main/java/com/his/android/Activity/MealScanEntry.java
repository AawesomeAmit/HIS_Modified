package com.his.android.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.R;
import com.his.android.Response.InsertResponse;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class MealScanEntry extends BaseActivity {
    TextView txtMeal, txtQty, btnNo, btnYes, textView33, txtDate, txtTime;
    Date today = new Date();
    int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Calendar c;
    SimpleDateFormat format2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_scan_entry);
        txtMeal = findViewById(R.id.txtMed);
        txtQty = findViewById(R.id.txtStr);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);
        txtDate=findViewById(R.id.txtDate);
        txtTime=findViewById(R.id.txtTime);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(Utils.formatDate(mYear + "/" + (mMonth + 1) + "/" + mDay));
        format2 = new SimpleDateFormat("hh:mm a");
        txtTime.setText(format2.format(today));
        txtDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year-1900);
                        txtDate.setText(Utils.formatDate(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        txtTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, i, i1) -> {
                mHour=i;
                mMinute=i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format2.format(today));
            },mHour,mMinute,false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        });
        textView33 = findViewById(R.id.textView33);
        if (getIntent().getStringExtra("intake") != null) {
            if(getIntent().getStringExtra("isSupplement").equals("3"))
                textView33.setText(R.string.dose);
            txtMeal.setText(getIntent().getStringExtra("intake"));
            txtQty.setText(getIntent().getStringExtra("qty")+" "+getIntent().getStringExtra("unit")+" "+getIntent().getStringExtra("doseForm"));
        }
        btnNo.setOnClickListener(view -> {
            moveTaskToBack(true);
            finish();
            startActivity(new Intent(mActivity, MealScanner.class));
        });
        btnYes.setOnClickListener(view -> addInput());
    }

    private void addInput() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String dateToStr = format.format(today);
        String timeToStr = format1.format(today);
        Utils.showRequestDialog(mActivity);
        Call<InsertResponse> call1;
        if (Objects.requireNonNull(getIntent().getStringExtra("isSupplement")).equalsIgnoreCase("1")) {
            call1 = RetrofitClient1.getInstance().getApi().addIntakeDetails(NUTRI_TOKEN, Integer.valueOf(Objects.requireNonNull(getIntent().getStringExtra("intakeID"))), getIntent().getStringExtra("qty"), timeToStr, getIntent().getStringExtra("unitID"), dateToStr, String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId()), SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId(), SharedPrefManager.getInstance(mActivity).getUser().getUserid(), 1, "S");
            call1.enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call1, Response<InsertResponse> response) {
                    if (Objects.requireNonNull(response.body()).getResponseCode() == 1) {
                        startActivity(new Intent(mActivity, MealScanner.class));
                    }
                    Toast.makeText(mActivity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<InsertResponse> call1, Throwable t) {
                    Toast.makeText(mActivity, "Network issue!", Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
        }
        else if (Objects.requireNonNull(getIntent().getStringExtra("isSupplement")).equalsIgnoreCase("2")){
            call1 = RetrofitClient1.getInstance().getApi().addSupplementDetails(NUTRI_TOKEN, Integer.valueOf(Objects.requireNonNull(getIntent().getStringExtra("intakeID"))), getIntent().getStringExtra("qty"), timeToStr, getIntent().getStringExtra("unitID"), dateToStr, String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId()), SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId(), SharedPrefManager.getInstance(mActivity).getUser().getUserid(), 1, "S");
            call1.enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call1, Response<InsertResponse> response) {
                    if (Objects.requireNonNull(response.body()).getResponseCode() == 1) {
                        startActivity(new Intent(mActivity, MealScanner.class));
                    }
                    Toast.makeText(mActivity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<InsertResponse> call1, Throwable t) {
                    Toast.makeText(mActivity, "Network issue!", Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
        }
        else {
            Call<ResponseBody> call2 = RetrofitClient.getInstance().getApi().saveIntakePrescription1(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), "", Integer.valueOf(getIntent().getStringExtra("pmID")), Integer.valueOf(getIntent().getStringExtra("prescriptionID")), 1, String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()), dateToStr+" "+timeToStr);
            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mActivity, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mActivity, MealScanner.class));
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call2, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, PreDashboard.class));
    }
}