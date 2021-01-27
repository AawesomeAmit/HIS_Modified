package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.SampleTestList;
import com.his.android.R;
import com.his.android.Response.SampleSaveResp;
import com.his.android.Response.SampleTypeResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleCollectionSave extends BaseActivity {
    TextView txtDate, txtTime, txtBack, txtSave;
    Date today = new Date();
    int mYear = 0, mMonth = 0, mDay = 0, mHour=0, mMinute=0;
    Calendar c;
    ConstraintLayout clMain;
    SimpleDateFormat format2;
    Gson gson = new Gson();
    RecyclerView rvSample;
    SimpleDateFormat format;
    List<SampleTestList> sampleTestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_collection_save);
        txtBack=findViewById(R.id.txtBack);
        txtSave=findViewById(R.id.txtSave);
        txtDate=findViewById(R.id.txtDate);
        txtTime=findViewById(R.id.txtTime);
        clMain=findViewById(R.id.clMain);
        rvSample=findViewById(R.id.rvSample);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(Utils.formatDate(mYear + "/" + (mMonth + 1) + "/" + mDay));
        format2 = new SimpleDateFormat("hh:mm a");
        txtTime.setText(format2.format(today));
        sampleTestList=new ArrayList<>();
        Log.v("testList", getIntent().getStringExtra("billId"));
        sampleTestList=gson.fromJson(getIntent().getStringExtra("testList"), new TypeToken<ArrayList<SampleTestList>>() {}.getType());
        rvSample.setAdapter(new BillAdp(sampleTestList));
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
        txtSave.setOnClickListener(v -> {
            Utils.showRequestDialog(mActivity);
            JSONArray array = null;
            try {
                array=new JSONArray(getIntent().getStringExtra("testList"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Call<SampleSaveResp> call= RetrofitClient.getInstance().getApi().saveSampleCollectionOldBill(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()), array, format.format(today), getIntent().getStringExtra("billId"));
            call.enqueue(new Callback<SampleSaveResp>() {
                @Override
                public void onResponse(Call<SampleSaveResp> call, Response<SampleSaveResp> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(mActivity, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                        showPopup(response.body().getTable().get(0).getColumn1());
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<SampleSaveResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        });
        txtBack.setOnClickListener(v -> startActivity(new Intent(SampleCollectionSave.this, SampleTestSelect.class).putExtra("billId", getIntent().getStringExtra("billId")).putExtra("sampleId", getIntent().getStringExtra("sampleId"))));
    }
    private void showPopup(String billID) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_sample_save, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        TextView txt=popupView.findViewById(R.id.txt);
        TextView txtSave=popupView.findViewById(R.id.txtSave);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        clMain.getLocationOnScreen(location);
        popupWindow.showAtLocation(clMain, Gravity.CENTER, 0, 0);
        txt.setText("The generated bill No. is : "+billID);
        txtSave.setOnClickListener(v -> startActivity(new Intent(mActivity, ScanSelector.class)));
    }
    class BillAdp extends RecyclerView.Adapter<BillAdp.ViewHolder> {
        List<SampleTestList> billList;
        public BillAdp(List<SampleTestList> billList) {
            this.billList = billList;
        }
        @NonNull
        @Override
        public BillAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sample, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new BillAdp.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BillAdp.ViewHolder holder, int position) {
            holder.txtBill.setText(billList.get(position).getItemName());
        }

        @Override
        public int getItemCount() {
            return billList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtBill;
            LinearLayout llMain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtBill = itemView.findViewById(R.id.txtBill);
                llMain = itemView.findViewById(R.id.llMain);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SampleCollectionSave.this, SampleTestSelect.class).putExtra("billId", getIntent().getStringExtra("billId")));
    }
}