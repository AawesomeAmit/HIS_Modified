package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.his.android.Adapter.ShowVitalAdp;
import com.his.android.Model.VoiceData;
import com.his.android.R;
import com.his.android.Response.VoiceDataResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListenSound extends BaseFragment implements View.OnClickListener {

    private TextView txtFrmDate, txtToDate, btnGo;
    private static String fromDate = "";
    private static String toDate = "";
    private int mYear = 0, mMonth = 0, mDay = 0, tYear = 0, tMonth = 0, tDay = 0;
    private Date today = new Date();
    private Date toToday = new Date();
    SimpleDateFormat format;
    RecyclerView rvSound;
    VoiceDataResp voiceDataResp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_listen_sound, container, false);
        txtFrmDate=view.findViewById(R.id.txtFrmDate);
        txtToDate=view.findViewById(R.id.txtToDate);
        btnGo=view.findViewById(R.id.btnGo);
        rvSound=view.findViewById(R.id.rvSound);
        rvSound.setLayoutManager(new LinearLayoutManager(mActivity));
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        format = new SimpleDateFormat("yyyy-MM-dd");
        fromDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        toDate = mYear + "/" + (mMonth + 1) + "/" + mDay;
        txtFrmDate.setText(Utils.formatDate(fromDate));
        txtToDate.setText(Utils.formatDate(toDate));
        txtFrmDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        return view;
    }
    private void bind(){
        Utils.showRequestDialog(mActivity);
        Call<VoiceDataResp> call= RetrofitClient.getInstance().getApi().getPatientVoiceData(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(mActivity).getSubDept().getId(),
                format.format(today),
                format.format(toToday),
                "stethoscope"
                );
        call.enqueue(new Callback<VoiceDataResp>() {
            @Override
            public void onResponse(Call<VoiceDataResp> call, Response<VoiceDataResp> response) {
                if (response.isSuccessful()){
                    voiceDataResp=response.body();
                    rvSound.setAdapter(new SoundListAdp(voiceDataResp.getVoiceDataList()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VoiceDataResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtFrmDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
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
        } else if (view.getId() == R.id.txtToDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
                    (view12, year, monthOfYear, dayOfMonth) -> {
                        tYear = year;
                        tMonth = monthOfYear;
                        tDay = dayOfMonth;
                        toDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        toToday.setDate(dayOfMonth);
                        toToday.setMonth(monthOfYear);
                        toToday.setYear(year - 1900);
                        txtToDate.setText(Utils.formatDate(toDate));
                    }, tYear, tMonth, tDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(toToday.getTime());
        } else if(view.getId()==R.id.btnGo) bind();
    }
    private class SoundListAdp extends RecyclerView.Adapter<SoundListAdp.RecyclerViewHolder>{
        List<VoiceData> voiceDataList;

        public SoundListAdp(List<VoiceData> voiceDataList) {
            this.voiceDataList = voiceDataList;
        }

        @NonNull
        @Override
        public SoundListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sound, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundListAdp.RecyclerViewHolder holder, int position) {
            holder.txtTitle.setText(voiceDataList.get(position).getVoiceData());
        }

        @Override
        public int getItemCount() {
            return voiceDataList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTitle=itemView.findViewById(R.id.txtTitle);
            }
        }
    }
}