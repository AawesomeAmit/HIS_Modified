package com.his.android.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.AddMedicationAdp;
import com.his.android.Fragment.AddMedication;
import com.his.android.Model.Prescription;
import com.his.android.R;
import com.his.android.Response.InsertResponse;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class MealScanEntry extends BaseActivity {
    TextView txtMeal, txtQty, btnNo, btnYes;
    Date today = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_scan_entry);
        txtMeal = findViewById(R.id.txtMeal);
        txtQty = findViewById(R.id.txtQty);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);
        if (getIntent().getStringExtra("intake") != null) {
            txtMeal.setText(getIntent().getStringExtra("intake"));
            txtQty.setText(getIntent().getStringExtra("qty")+" "+getIntent().getStringExtra("unit"));
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
        if (Objects.requireNonNull(getIntent().getStringExtra("isSupplement")).equalsIgnoreCase("1"))
            call1 = RetrofitClient1.getInstance().getApi().addSupplementDetails(NUTRI_TOKEN, Integer.valueOf(Objects.requireNonNull(getIntent().getStringExtra("intakeID"))), getIntent().getStringExtra("qty"), timeToStr, getIntent().getStringExtra("unitID"), dateToStr, String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId()), SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId(), SharedPrefManager.getInstance(mActivity).getUser().getUserid(), 1);
        else call1 = RetrofitClient1.getInstance().getApi().addIntakeDetails(NUTRI_TOKEN, Integer.valueOf(Objects.requireNonNull(getIntent().getStringExtra("intakeID"))), getIntent().getStringExtra("qty"), timeToStr, getIntent().getStringExtra("unitID"), dateToStr, String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId()), SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId(), 1);
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
}