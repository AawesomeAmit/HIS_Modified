package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.DiseasePatientList;
import com.his.android.R;
import com.his.android.Response.DiseaseListResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseaseList extends AppCompatActivity {
    RecyclerView recyclerDiseaseList;
    List<com.his.android.Model.DiseaseList> diseaseLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);
        recyclerDiseaseList=findViewById(R.id.recyclerDiseaseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDiseaseList.setHasFixedSize(true);
        recyclerDiseaseList.setLayoutManager(layoutManager);
        diseaseLists=new ArrayList<>();
        Call<DiseaseListResp> call = RetrofitClient.getInstance().getApi().getUserWiseResearchDiseaseList(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), String.valueOf(SharedPrefManager.getInstance(this).getUser().getUserid()));
        call.enqueue(new Callback<DiseaseListResp>() {
            @Override
            public void onResponse(Call<DiseaseListResp> call, Response<DiseaseListResp> response) {
                if(response.isSuccessful()){
                    Log.v("status", String.valueOf(response.body()));
                    if (response.body() != null && response.body().getDiseaseList().size() > 0) {
                        diseaseLists = response.body().getDiseaseList();
                        recyclerDiseaseList.setAdapter(new DiseaseListAdp(DiseaseList.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<DiseaseListResp> call, Throwable t) {
                Log.v("status", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    public class DiseaseListAdp extends RecyclerView.Adapter<DiseaseListAdp.RecyclerViewHolder> {
        private Context mCtx;
        DiseaseListAdp(Context mCtx) {
            this.mCtx = mCtx;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_disease_list, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final DiseaseListAdp.RecyclerViewHolder holder, final int i) {
            holder.txtSno.setText(String.valueOf(i+1));
            holder.txtDiseaseName.setText(diseaseLists.get(i).getDiseaseName());
            holder.txtDiseaseName.setOnClickListener(view -> {
                Intent intent=new Intent(DiseaseList.this, DiseasePatientList.class);
                intent.putExtra("assignedId", diseaseLists.get(i).getUserNotificationAssignID());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return diseaseLists.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtSno, txtDiseaseName;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSno =itemView.findViewById(R.id.txtSno);
                txtDiseaseName =itemView.findViewById(R.id.txtDiseaseName);
            }
        }
    }
}
