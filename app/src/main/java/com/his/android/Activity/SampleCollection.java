package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Adapter.ActivityDisplayAdp;
import com.his.android.Model.BillList;
import com.his.android.R;
import com.his.android.Response.BillResp;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleCollection extends BaseActivity {
    RecyclerView rvBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_collection);
        rvBill = findViewById(R.id.rvBill);
        load();
    }

    private void load() {
        Utils.showRequestDialog(mActivity);
        Call<BillResp> call = RetrofitClient.getInstance().getApi().getBillListForSampleCollection(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        call.enqueue(new Callback<BillResp>() {
            @Override
            public void onResponse(Call<BillResp> call, Response<BillResp> response) {
                if (response.isSuccessful()) {
                    rvBill.setAdapter(new BillAdp(response.body().getTable()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<BillResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    class BillAdp extends RecyclerView.Adapter<BillAdp.ViewHolder> {
        List<BillList> billList;

        public BillAdp(List<BillList> billList) {
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtBill.setText(billList.get(position).getBillNo());
            holder.llMain.setOnClickListener(view -> startActivity(new Intent(mActivity, SampleTestSelect.class).putExtra("billId", billList.get(position).getBillID().toString())));
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
}