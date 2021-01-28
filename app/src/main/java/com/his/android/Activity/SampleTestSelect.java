package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.BillList;
import com.his.android.Model.SampleTestList;
import com.his.android.R;
import com.his.android.Response.SampleTestResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleTestSelect extends BaseActivity {
    RecyclerView rvTests;
    TextView txtNext, txtBack;
    List<SampleTestList> sampleTestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_test_select);
        rvTests=findViewById(R.id.rvTests);
        txtNext=findViewById(R.id.txtNext);
        txtBack=findViewById(R.id.txtBack);
        sampleTestList=new ArrayList<>();
        load();
        txtNext.setOnClickListener(v -> {
            Gson gson = new Gson();
            List<SampleTestList> sampleTestListChecked=new ArrayList<>();
            for (int i=0;i<sampleTestList.size();i++){
                if (sampleTestList.get(i).getSelected()){
                    sampleTestListChecked.add(sampleTestList.get(i));
                }
            }
            if (sampleTestListChecked.size()>0)
            startActivity(new Intent(mActivity, SampleCollectionSave.class).putExtra("testList", gson.toJson(sampleTestListChecked)).putExtra("billId", getIntent().getStringExtra("billId")).putExtra("sampleId", getIntent().getStringExtra("sampleId")));
            else Toast.makeText(mActivity, "Please select atleast one test!", Toast.LENGTH_SHORT).show();
        });
        txtBack.setOnClickListener(v -> startActivity(new Intent(mActivity, SampleCollection.class)));
    }
    private void load(){
        Utils.showRequestDialog(mActivity);
        Call<SampleTestResp> call= RetrofitClient.getInstance().getApi().getTestListForSampleCollectionByBillID(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()), getIntent().getStringExtra("sampleId"), getIntent().getStringExtra("billId"));
        call.enqueue(new Callback<SampleTestResp>() {
            @Override
            public void onResponse(Call<SampleTestResp> call, Response<SampleTestResp> response) {
                if(response.isSuccessful()){
                    sampleTestList=response.body().getTable();
                    rvTests.setAdapter(new SampleTestAdp(sampleTestList));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<SampleTestResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    class SampleTestAdp extends RecyclerView.Adapter<SampleTestAdp.ViewHolder> {
        List<SampleTestList> sampleTestList;

        public SampleTestAdp(List<SampleTestList> sampleTestList) {
            this.sampleTestList = sampleTestList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_sample_test, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtTest.setText(sampleTestList.get(position).getItemName());
            holder.llMain.setOnClickListener(view -> {
                if (sampleTestList.get(position).getSelected()) {
                    sampleTestList.get(position).setSelected(false);
                    holder.imgCheck.setImageResource(R.drawable.ic_baseline_check_circle_outline);
                }
                else {
                    sampleTestList.get(position).setSelected(true);
                    holder.imgCheck.setImageResource(R.drawable.ic_baseline_check_circle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return sampleTestList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTest;
            ImageView imgCheck;
            ConstraintLayout llMain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTest = itemView.findViewById(R.id.txtTest);
                imgCheck = itemView.findViewById(R.id.imgCheck);
                llMain = itemView.findViewById(R.id.llMain);
            }
        }
    }
}