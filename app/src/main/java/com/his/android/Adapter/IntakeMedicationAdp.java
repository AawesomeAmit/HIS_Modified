package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.his.android.Model.IntakeList;
import com.his.android.R;

import java.util.List;

public class IntakeMedicationAdp extends RecyclerView.Adapter<IntakeMedicationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<IntakeList> intakeList;
    public IntakeMedicationAdp(Context mCtx, List<IntakeList> intakeList) {
        this.mCtx = mCtx;
        this.intakeList=intakeList;
    }

    @NonNull
    @Override
    public IntakeMedicationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_intake_medication, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntakeMedicationAdp.RecyclerViewHolder holder, int i) {
        IntakeList intakeLists =intakeList.get(i);
        if (i != 0) {
            if(intakeLists.getIntakeDate().equalsIgnoreCase(intakeList.get(i-1).getIntakeDate())){
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText(intakeLists.getIntakeDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txtDate.setText(intakeLists.getIntakeDate());
            holder.txtDate.setVisibility(View.VISIBLE);
        }


        /*if (prescriptionLists.getIntakeDateTime() != null){
            holder.llMain.setVisibility(View.VISIBLE);
            holder.txtMed.setText(prescriptionLists.getDrugName());
            holder.txtStr.setText(prescriptionLists.getDoseStrength()+" "+prescriptionLists.getDoseUnit());
            holder.txtFreq.setText(prescriptionLists.getDoseFrequency());
            holder.txtDosage.setText(prescriptionLists.getDosageForm());
            holder.txtRemark.setText(prescriptionLists.getRemark());

            String[] time = prescriptionLists.getIntakeDateTime().split("T");

            holder.tvGivenTime.setText( Utils.formatTimeNew(time[1]));

        }else {
            holder.llMain.setVisibility(View.GONE);
        }*/

        holder.txtMed.setText(intakeLists.getDrugName());
        holder.txtStr.setText(intakeLists.getDoseStrength()+" "+intakeLists.getDoseUnit());
        holder.txtTime.setText(intakeLists.getDoseFrequency());
        holder.txtDosage.setText(intakeLists.getDosageForm());
        holder.txtRemark.setText(intakeLists.getRemark());

       // holder.tvGivenTime.setText(Utils.formatTimeNew(intakeLists.getIntakeDateTime()));
        holder.tvGivenTime.setText((intakeLists.getIntakeDateTime().trim()));
        holder.tvGivenBy.setText(intakeLists.getDisplayName());
    }

    @Override
    public int getItemCount() {
        return intakeList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtMed, txtStr, txtTime, txtDate, txtDosage,txtRemark, tvGivenTime, tvGivenBy;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMed=itemView.findViewById(R.id.txtMed);
            txtStr =itemView.findViewById(R.id.txtStr);
            txtTime =itemView.findViewById(R.id.txtTime);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtDosage =itemView.findViewById(R.id.txtDosage);
            txtRemark =itemView.findViewById(R.id.txtRemark);
            tvGivenTime =itemView.findViewById(R.id.tvGivenTime);
            tvGivenBy =itemView.findViewById(R.id.tvGivenBy);
        }
    }
}
