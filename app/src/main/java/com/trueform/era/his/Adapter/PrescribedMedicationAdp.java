package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trueform.era.his.Model.PrescriptionList;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.Utils;

import java.util.List;

import bsh.util.Util;

public class PrescribedMedicationAdp extends RecyclerView.Adapter<PrescribedMedicationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<PrescriptionList> prescriptionList;

    public PrescribedMedicationAdp(Context mCtx, List<PrescriptionList> prescriptionList) {
        this.mCtx = mCtx;
        this.prescriptionList = prescriptionList;
    }

    @NonNull
    @Override
    public PrescribedMedicationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_view_prescription, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescribedMedicationAdp.RecyclerViewHolder holder, int i) {
        PrescriptionList prescriptionLists = prescriptionList.get(i);

        holder.tvGivenBy.setVisibility(View.GONE);

        holder.llMain.setWeightSum(12);

        if (i != 0) {
            if (prescriptionLists.getCreatedDate().equalsIgnoreCase(prescriptionList.get(i - 1).getCreatedDate())) {
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(prescriptionLists.getCreatedDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(prescriptionLists.getCreatedDate());
            holder.txtDate.setVisibility(View.VISIBLE);
        }

        if (!prescriptionLists.getCreatedDate().equalsIgnoreCase(Utils.getCurrentDate())){

            holder.txtDate.setVisibility(View.GONE);
        }

        if (prescriptionLists.getIsStop() == 0 && prescriptionLists.getStatus() == 1) {
            holder.llMain.setVisibility(View.VISIBLE);
            holder.txtMed.setText(prescriptionLists.getDrugName().trim().toUpperCase());
            holder.txtStr.setText(prescriptionLists.getDoseStrength() + " " + prescriptionLists.getDoseUnit());
            holder.txtFreq.setText(prescriptionLists.getDoseFrequency());
            holder.txtDosage.setText(prescriptionLists.getDosageForm());
            holder.txtRemark.setText(prescriptionLists.getRemark());

           // String[] time = prescriptionLists.getIntakeDateTime().split("T");
           // holder.tvGivenTime.setText(Utils.formatTimeNew(time[1]));
            holder.tvGivenTime.setText(prescriptionLists.getDuration());

        } else {
            holder.llMain.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtMed, txtStr, txtFreq, txtDate, txtDosage, txtRemark, tvGivenTime, tvGivenBy;

        LinearLayout llMain;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMed = itemView.findViewById(R.id.txtMed);
            txtStr = itemView.findViewById(R.id.txtStr);
            txtFreq = itemView.findViewById(R.id.txtFreq);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDosage = itemView.findViewById(R.id.txtDosage);
            txtRemark = itemView.findViewById(R.id.txtRemark);
            tvGivenTime = itemView.findViewById(R.id.tvGivenTime);
            tvGivenBy = itemView.findViewById(R.id.tvGivenBy);
            llMain = itemView.findViewById(R.id.llMain);
        }
    }
}
