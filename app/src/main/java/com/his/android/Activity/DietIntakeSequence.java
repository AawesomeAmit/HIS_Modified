package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.his.android.Model.FoodTiming;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import com.his.android.R;
import com.his.android.view.BaseActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietIntakeSequence extends BaseActivity {
    RecyclerView rvTiming;
    TextView txtDate;
    Date today;
    Calendar c;
    List<FoodTiming> foodTimingList;
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_intake_sequence);
        txtDate=findViewById(R.id.txtDate);
        rvTiming=findViewById(R.id.rvTiming);
        rvTiming.setLayoutManager(new LinearLayoutManager(mActivity));
        c = Calendar.getInstance();
        today = new Date();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        foodTimingList=new ArrayList<>();
//        SharedPrefManager.getInstance(mActivity).setPid(2154772);
        txtDate.setText(Utils.formatDate(mYear + "/" + (mMonth + 1) + "/" + mDay));
        SharedPrefManager.getInstance(mActivity).setFoodDate(mYear + "-" + (mMonth + 1) + "-" + mDay);
        txtDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        today.setDate(dayOfMonth);
                        today.setMonth(monthOfYear);
                        today.setYear(year - 1900);
                        txtDate.setText(Utils.formatDate(mYear + "/" + (mMonth + 1) + "/" + mDay));
                        SharedPrefManager.getInstance(mActivity).setFoodDate(mYear + "-" + (mMonth + 1) + "-" + mDay);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        });
        bindTiming();
    }
    private void bindTiming(){
        if(ConnectivityChecker.checker(mActivity)) {
            Utils.showRequestDialog(mActivity);
            Call<List<FoodTiming>> call = RetrofitClient.getInstance().getApi().getFoodTimingList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
            call.enqueue(new Callback<List<FoodTiming>>() {
                @Override
                public void onResponse(Call<List<FoodTiming>> call, Response<List<FoodTiming>> response) {
                    if (response.isSuccessful()) {
                        foodTimingList=response.body();
                        rvTiming.setAdapter(new DietTimingAdp(foodTimingList));
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<List<FoodTiming>> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }
    }

    public class DietTimingAdp extends RecyclerView.Adapter<DietTimingAdp.RecyclerViewHolder> {
        private List<FoodTiming> foodTimings;
        public DietTimingAdp(List<FoodTiming> foodTimings) {
            this.foodTimings = foodTimings;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sequence_diet, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtTime.setText(String.valueOf(foodTimings.get(i).getFoodTime()));
//            Picasso.with(mActivity).load(patientActivityDetails.get(i).getIconImage()).into(holder.imgTime);
            holder.llMain.setOnClickListener(view -> {
                if (foodTimings.get(i).isClicked()) {
                    holder.txtTime.setTextColor(Color.BLACK);
                    foodTimings.get(i).setClicked(false);
                    holder.imgTime.setColorFilter(Color.BLACK);
                    holder.llMain.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_activity4));
                } else {
                    holder.txtTime.setTextColor(Color.WHITE);
                    foodTimings.get(i).setClicked(true);
                    holder.imgTime.setColorFilter(Color.WHITE);
                    holder.llMain.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.btn_activity5));
                    SharedPrefManager.getInstance(mActivity).setFoodTimeId(foodTimings.get(i).getFoodTimeID());
                    startActivity(new Intent(mActivity, FoodIntakeStep1.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodTimings.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtTime;
            ImageView imgTime;
            LinearLayout llMain;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTime =itemView.findViewById(R.id.txtTime);
                imgTime =itemView.findViewById(R.id.imgTime);
                llMain =itemView.findViewById(R.id.llMain);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, ScanSelector.class));
    }
}