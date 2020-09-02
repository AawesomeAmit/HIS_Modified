package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Model.GetSubTest;
import com.his.android.Model.PatientTest;
import com.his.android.R;

import java.util.List;

public class ViewInvestigationAdp extends RecyclerView.Adapter<ViewInvestigationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<PatientTest> patientTest;
    public ViewInvestigationAdp(Context mCtx, List<PatientTest> patientTest) {
        this.mCtx = mCtx;
        this.patientTest=patientTest;
    }

    @NonNull
    @Override
    public ViewInvestigationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_view_investigation, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewInvestigationAdp.RecyclerViewHolder holder, int i) {
        Gson gson = new Gson();
        try {
            holder.txtTestType.setText(patientTest.get(i).getItemName());
            List<GetSubTest> list = gson.fromJson(patientTest.get(i).getSubTest(), new TypeToken<List<GetSubTest>>() {}.getType());
            Log.v("subTestList", String.valueOf(list));
            holder.rViewInvestigation.setAdapter(new InnerViewInvestigationAdp(mCtx, list, patientTest.get(i).getBillID()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return patientTest.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTestType,txtTestName,txtValue,txtRange, txtChart;
        RelativeLayout llType;
        RecyclerView rViewInvestigation;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            llType=itemView.findViewById(R.id.llType);
            txtTestType=itemView.findViewById(R.id.txtTestType);
            txtTestName=itemView.findViewById(R.id.txtTestName);
            txtValue=itemView.findViewById(R.id.txtUnit);
            txtRange=itemView.findViewById(R.id.txtRange);
            txtChart=itemView.findViewById(R.id.txtChart);
            rViewInvestigation=itemView.findViewById(R.id.rViewInvestigation);
            rViewInvestigation.setLayoutManager(new LinearLayoutManager(mCtx));
        }
    }
}
