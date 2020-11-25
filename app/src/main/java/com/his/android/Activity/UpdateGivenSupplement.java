package com.his.android.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.UpdateSupplementList;
import com.his.android.R;
import com.his.android.Response.UpdateSupplementResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGivenSupplement extends BaseActivity {
    RecyclerView rvPendingSupp, rvUpcomingSupp;
    List<UpdateSupplementList> previousSupplementList,pendingSupplementList, upcomingSupplementList;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_given_supplement);
        rvPendingSupp = findViewById(R.id.rvPendingSupp);
        rvUpcomingSupp = findViewById(R.id.rvUpcomingSupp);
        rvUpcomingSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        rvPendingSupp.setLayoutManager(new LinearLayoutManager(mActivity));
        gson = new Gson();
        bind();
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

    public class PreviousSupplementAdp extends RecyclerView.Adapter<PreviousSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> UpdateSupplementList;
        public PreviousSupplementAdp(List<UpdateSupplementList> UpdateSupplementList) {
            this.UpdateSupplementList = UpdateSupplementList;
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
            holder.txtSupplement.setText(upcomingSupplementList.get(i).getIntakeName());
            holder.txtQty.setText(upcomingSupplementList.get(i).getIntakeQuantity()+" "+upcomingSupplementList.get(i).getUnit());
            holder.txtPrescribedTime.setText(upcomingSupplementList.get(i).getPrescribedTime());
        }

        @Override
        public int getItemCount() {
            return UpdateSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime;
            ImageView imgYes, imgNo, txt75, txt100;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement =itemView.findViewById(R.id.txtSupplement);
                txtQty =itemView.findViewById(R.id.txtQty);
                txtPrescribedTime =itemView.findViewById(R.id.txtPrescribedTime);
                txtGivenTime =itemView.findViewById(R.id.txtGivenTime);
            }
        }
    }
    public class PendingSupplementAdp extends RecyclerView.Adapter<PendingSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> updateSupplementList;
        public PendingSupplementAdp(List<UpdateSupplementList> updateSupplementList) {
            this.updateSupplementList = updateSupplementList;
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
            holder.txtSupplement.setText(upcomingSupplementList.get(i).getIntakeName());
            holder.txtQty.setText(upcomingSupplementList.get(i).getIntakeQuantity()+" "+upcomingSupplementList.get(i).getUnit());
            holder.txtPrescribedTime.setText(upcomingSupplementList.get(i).getPrescribedTime());
        }

        @Override
        public int getItemCount() {
            return updateSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime;
            ImageView imgYes, imgNo, txt75, txt100;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement =itemView.findViewById(R.id.txtSupplement);
                txtQty =itemView.findViewById(R.id.txtQty);
                txtPrescribedTime =itemView.findViewById(R.id.txtPrescribedTime);
                txtGivenTime =itemView.findViewById(R.id.txtGivenTime);
                imgYes =itemView.findViewById(R.id.imgYes);
                imgNo =itemView.findViewById(R.id.imgNo);
            }
        }
    }
    public class UpcomingSupplementAdp extends RecyclerView.Adapter<UpcomingSupplementAdp.RecyclerViewHolder> {
        private List<UpdateSupplementList> updateSupplementList;
        public UpcomingSupplementAdp(List<UpdateSupplementList> updateSupplementList) {
            this.updateSupplementList = updateSupplementList;
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
            holder.txtSupplement.setText(updateSupplementList.get(i).getIntakeName());
            holder.txtQty.setText(upcomingSupplementList.get(i).getIntakeQuantity()+" "+upcomingSupplementList.get(i).getUnit());
            holder.txtPrescribedTime.setText(upcomingSupplementList.get(i).getPrescribedTime());
        }

        @Override
        public int getItemCount() {
            return updateSupplementList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSupplement, txtQty, txtPrescribedTime, txtGivenTime;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSupplement =itemView.findViewById(R.id.txtSupplement);
                txtQty =itemView.findViewById(R.id.txtQty);
                txtPrescribedTime =itemView.findViewById(R.id.txtPrescribedTime);
            }
        }
    }
}