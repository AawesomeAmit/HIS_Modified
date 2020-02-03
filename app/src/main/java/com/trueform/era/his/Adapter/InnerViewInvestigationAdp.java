package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Fragment.ViewInvestigation;
import com.trueform.era.his.Model.GetSubTest;
import com.trueform.era.his.R;

import java.util.List;

public class InnerViewInvestigationAdp extends RecyclerView.Adapter<InnerViewInvestigationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<GetSubTest> getSubTestList;
    private Integer catId;
    private ViewInvestigation viewInvestigation=new ViewInvestigation();
    InnerViewInvestigationAdp(Context mCtx, List<GetSubTest> getSubTestList, Integer catId) {
        this.mCtx = mCtx;
        this.getSubTestList=getSubTestList;
        this.catId=catId;
    }

    @NonNull
    @Override
    public InnerViewInvestigationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_inner_investigation, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InnerViewInvestigationAdp.RecyclerViewHolder holder, int i) {
        holder.txtTestName.setText(getSubTestList.get(i).getSubTestName());
        holder.txtValue.setText(getSubTestList.get(i).getResult() + " " + (getSubTestList.get(i).getUnitname() != null ? getSubTestList.get(i).getUnitname() : ""));
        holder.txtRange.setText(getSubTestList.get(i).getResultRemark());
        holder.llChart.setOnClickListener(view1 -> viewInvestigation.showChart(view1, getSubTestList.get(i).getId(), catId));
        if (getSubTestList.get(i).getNormalResult() != null) {
            if (getSubTestList.get(i).getNormalResult()) {
                holder.txtValue.setTextColor(mCtx.getResources().getColor(R.color.black));
            } else holder.txtValue.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        try {
            return getSubTestList.size();
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTestName,txtValue,txtRange;
        ConstraintLayout llChart;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTestName=itemView.findViewById(R.id.txtTestName);
            txtValue=itemView.findViewById(R.id.txtUnit);
            txtRange=itemView.findViewById(R.id.txtRange);
            llChart=itemView.findViewById(R.id.llChart);
        }
    }
}
