package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodIntakeStep1 extends BaseActivity implements OnClickListener {
    TextView txtQue, txtTime, txtBack, txtNext;
    Date today;
    Calendar c;
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;
    SimpleDateFormat format, format1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake_step1);
        txtQue=findViewById(R.id.txtQue);
        txtTime=findViewById(R.id.txtTime);
        txtBack=findViewById(R.id.txtBack);
        txtNext=findViewById(R.id.txtNext);
        format = new SimpleDateFormat("hh:mm a");
        txtTime.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        c = Calendar.getInstance();
        today = new Date();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        format = new SimpleDateFormat("hh:mm a");
        txtTime.setText(format.format(today));
        format1 = new SimpleDateFormat("HH:mm");
        SharedPrefManager.getInstance(mActivity).setFoodTime(format1.format(today));
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtBack){
            startActivity(new Intent(mActivity, DietIntakeSequence.class));
        } else if(view.getId()==R.id.txtNext){
            startActivity(new Intent(mActivity, FoodIntakeStep2.class));
        } else if(view.getId()==R.id.txtTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, j, i1) -> {
                mHour = j;
                mMinute = i1;
                today.setHours(mHour);
                today.setMinutes(mMinute);
                txtTime.setText(format.format(today));
                SharedPrefManager.getInstance(mActivity).setFoodTime(format1.format(today));
            }, mHour, mMinute, false);
            timePickerDialog.updateTime(today.getHours(), today.getMinutes());
            timePickerDialog.show();
        }
    }
}