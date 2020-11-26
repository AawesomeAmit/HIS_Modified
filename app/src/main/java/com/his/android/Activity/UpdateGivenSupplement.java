package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.UpdateSupplementList;
import com.his.android.R;
import com.his.android.Response.UpdateSupplementResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGivenSupplement extends BaseActivity {
    RecyclerView rvPendingSupp, rvUpcomingSupp, rvPreviousSupp;
    List<UpdateSupplementList> previousSupplementList, pendingSupplementList, upcomingSupplementList;
    Gson gson;
    Date today;
    Calendar c;
    boolean isShow=false;
    TextView txtShowPrev;
    ImageView imgPrev, imgPending;
    LinearLayout llPrevious, llPending;
    SimpleDateFormat format1, format2, format3;
    int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_given_supplement);
        rvPendingSupp = findViewById(R.id.rvPendingSupp);
        txtShowPrev = findViewById(R.id.txtShowPrev);
        llPrevious = findViewById(R.id.llPrevious);
        llPending = findViewById(R.id.llPending);
        imgPrev = findViewById(R.id.imgPrev);
        imgPending = findViewById(R.id.imgPending);
        rvUpcomingSupp = findViewById(R.id.rvUpcomingSupp);
        rvPreviousSupp = findViewById(R.id.rvPreviousSupp);
        format1 = new SimpleDateFormat("HH:mm");
        format2 = new SimpleDateFormat("hh:mm a");
        format3 = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();
        today = new Date();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        rvUpcomingSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        rvPendingSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        rvPreviousSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        gson = new Gson();
        bind();
        txtShowPrev.setOnClickListener(view -> {
            if (!isShow) {
                llPrevious.setVisibility(View.VISIBLE);
                llPending.setVisibility(View.GONE);
                isShow = true;
                imgPrev.setVisibility(View.GONE);
                imgPending.setVisibility(View.VISIBLE);
            } else {
                llPrevious.setVisibility(View.GONE);
                llPending.setVisibility(View.VISIBLE);
                isShow = false;
                imgPrev.setVisibility(View.VISIBLE);
                imgPending.setVisibility(View.GONE);
            }
        });
    }

    private void bind() {
        Utils.showRequestDialog(mActivity);
        Call<List<UpdateSupplementResp>> call = RetrofitClient.getInstance().getApi().getSupplementList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid());
        call.enqueue(new Callback<List<UpdateSupplementResp>>() {
            @Override
            public void onResponse(Call<List<UpdateSupplementResp>> call, Response<List<UpdateSupplementResp>> response) {
                if (response.isSuccessful()) {
                    previousSupplementList = gson.fromJson(response.body().get(0).getPreviousList(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                    pendingSupplementList = gson.fromJson(response.body().get(0).getPendingList(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                    upcomingSupplementList = gson.fromJson(response.body().get(0).getUpcomingList(), new TypeToken<List<UpdateSupplementList>>() {}.getType());
                    rvPreviousSupp.setAdapter(new PreviousSupplementAdp(previousSupplementList));
                    rvPendingSupp.setAdapter(new PendingSupplementAdp(pendingSupplementList));
                    rvUpcomingSupp.setAdapter(new UpcomingSupplementAdp(upcomingSupplementList));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<UpdateSupplementResp>> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void giveDiet(int dietID, String dietDate, String dietTime, String consumptionPercentage) {
        Utils.showRequestDialog1(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().UpdateIntakeConsumption(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()), dietID, dietDate, dietTime, 1, consumptionPercentage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    bind();
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
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
    public class PreviousSupplementAdp extends RecyclerView.Adapter<PreviousSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> previousSupplementList;
        public PreviousSupplementAdp(List<UpdateSupplementList> UpdateSupplementList) {
            this.previousSupplementList = UpdateSupplementList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_upcoming_supplement, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            try {
                holder.txtSupplement.setText(previousSupplementList.get(i).getIntakeName());
                holder.txtQty.setText(previousSupplementList.get(i).getIntakeQuantity() + " " + previousSupplementList.get(i).getUnit());
                holder.txtPrescribedTime.setText(previousSupplementList.get(i).getPrescribedTime());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return previousSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement = itemView.findViewById(R.id.txtSupplement);
                txtQty = itemView.findViewById(R.id.txtQty);
                txtPrescribedTime = itemView.findViewById(R.id.txtPrescribedTime);
            }
        }
    }

    public class PendingSupplementAdp extends RecyclerView.Adapter<PendingSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> pendingSupplementList;

        public PendingSupplementAdp(List<UpdateSupplementList> updateSupplementList) {
            this.pendingSupplementList = updateSupplementList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_pending_supplement, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtGivenTime.setText(format2.format(today));
            holder.txtGivenDate.setText(Utils.formatDate(mYear + "/" + (mMonth + 1) + "/" + mDay));
            holder.txtSupplement.setText(pendingSupplementList.get(i).getIntakeName());
            holder.txtQty.setText(pendingSupplementList.get(i).getIntakeQuantity() + " " + pendingSupplementList.get(i).getUnit());
            holder.txtPrescribedTime.setText(pendingSupplementList.get(i).getPrescribedTime());
            holder.txtGivenTime.setOnClickListener(view -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, R.style.DialogTheme, (timePicker, j, i1) -> {
                    mHour = j;
                    mMinute = i1;
                    today.setHours(mHour);
                    today.setMinutes(mMinute);
                    holder.txtGivenTime.setText(format2.format(today));
                }, mHour, mMinute, false);
                timePickerDialog.updateTime(today.getHours(), today.getMinutes());
                timePickerDialog.show();
            });
            holder.txtGivenDate.setOnClickListener(view -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, R.style.DialogTheme,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            today.setDate(dayOfMonth);
                            today.setMonth(monthOfYear);
                            today.setYear(year - 1900);
                            holder.txtGivenDate.setText(Utils.formatDate(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth));
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            });
            holder.imgNo.setOnClickListener(view -> giveDiet(pendingSupplementList.get(i).getDietID(), format3.format(today), format1.format(today), "0"));
            holder.imgYes.setOnClickListener(view -> giveDiet(pendingSupplementList.get(i).getDietID(), format3.format(today), format1.format(today), "100"));
        }

        @Override
        public int getItemCount() {
            return pendingSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime, txtGivenDate;
            ImageView imgYes, imgNo, txt75, txt100;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement = itemView.findViewById(R.id.txtSupplement);
                txtQty = itemView.findViewById(R.id.txtQty);
                txtPrescribedTime = itemView.findViewById(R.id.txtPrescribedTime);
                txtGivenTime = itemView.findViewById(R.id.txtGivenTime);
                txtGivenDate = itemView.findViewById(R.id.txtGivenDate);
                imgYes = itemView.findViewById(R.id.imgYes);
                imgNo = itemView.findViewById(R.id.imgNo);
            }
        }
    }

    public class UpcomingSupplementAdp extends RecyclerView.Adapter<UpcomingSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> upcomingSupplementList;

        public UpcomingSupplementAdp(List<UpdateSupplementList> updateSupplementList) {
            this.upcomingSupplementList = updateSupplementList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_upcoming_supplement, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtSupplement.setText(upcomingSupplementList.get(i).getIntakeName());
            holder.txtQty.setText(upcomingSupplementList.get(i).getIntakeQuantity() + " " + upcomingSupplementList.get(i).getUnit());
            holder.txtPrescribedTime.setText(upcomingSupplementList.get(i).getPrescribedTime());
        }

        @Override
        public int getItemCount() {
            return upcomingSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement = itemView.findViewById(R.id.txtSupplement);
                txtQty = itemView.findViewById(R.id.txtQty);
                txtPrescribedTime = itemView.findViewById(R.id.txtPrescribedTime);
            }
        }
    }
}