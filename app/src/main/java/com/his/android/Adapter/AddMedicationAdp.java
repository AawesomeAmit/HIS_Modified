package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Fragment.AddMedication;
import com.his.android.Model.Prescription;
import com.his.android.R;
import com.his.android.Response.PrescribedMedResp;

public class AddMedicationAdp extends RecyclerView.Adapter<AddMedicationAdp.RecyclerViewHolder> {
    private Context mCtx;
    private PrescribedMedResp medicineSearchResp;
    private AddMedication medication=new AddMedication();
    public AddMedicationAdp(Context mCtx, PrescribedMedResp medicineSearchResp) {
        this.mCtx = mCtx;
        this.medicineSearchResp=medicineSearchResp;
    }

    @NonNull
    @Override
    public AddMedicationAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_med_list, viewGroup, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddMedicationAdp.RecyclerViewHolder holder, final int i) {
        final Prescription medicineSearch=medicineSearchResp.getPrescription().get(i);
        holder.txtMedName.setText(medicineSearch.getDrugName());
        holder.txtStr.setText(medicineSearch.getDoseStrength()+" "+medicineSearch.getDoseUnit());
        holder.txtNumDays.setText(medicineSearch.getDuration());
        holder.txtRemark.setText(medicineSearch.getRemark());
        if(medicineSearch.getDosageForm().length()>2)
        holder.txtDoseForm.setText(medicineSearch.getDosageForm().substring(0,3));
        else holder.txtDoseForm.setText(medicineSearch.getDosageForm());
        if(medicineSearch.getDoseFrequency().length()>2)
        holder.txtDuration.setText(medicineSearch.getDoseFrequency().substring(0,3));
        else holder.txtDuration.setText(medicineSearch.getDoseFrequency());
        if(medicineSearch.getIsShow()!=null) {
            if (medicineSearch.getIsShow() == 1) {
                holder.txtEdit.setVisibility(View.VISIBLE);
                holder.txtDelete.setVisibility(View.VISIBLE);
            } else if (medicineSearch.getIsShow() == 0) {

            } else {
                holder.txtAction.setVisibility(View.VISIBLE);
                holder.txtStop.setVisibility(View.GONE);
            }
        }
        holder.txtAction.setOnClickListener(view -> medication.removeRow(i));
        holder.txtStop.setOnClickListener(view -> new AlertDialog.Builder(mCtx).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to stop this medicine?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> medication.stopDelMed(i, 2, 1))

                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show());
        holder.txtDelete.setOnClickListener(view -> new AlertDialog.Builder(mCtx).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to remove this medicine?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> medication.stopDelMed(i, 0, 0))

                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show());
        holder.txtEdit.setOnClickListener(view -> {
            medication.edit(medicineSearch.getDrugID(), medicineSearch.getDrugName(), medicineSearch.getDosageForm(), medicineSearch.getDoseStrength(), medicineSearch.getDoseUnit(), medicineSearch.getDoseFrequency(), medicineSearch.getDuration(), medicineSearch.getRemark());
            medication.removeRow(i);
        });
    }

    @Override
    public int getItemCount() {
        return medicineSearchResp == null ? 0 : medicineSearchResp.getPrescription().size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedName,txtNumDays, txtRemark, txtAction, txtEdit, txtDoseForm, txtDuration, txtStr, txtStop, txtDelete;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedName=itemView.findViewById(R.id.tvSub);
            txtNumDays=itemView.findViewById(R.id.txtNumDays);
            txtRemark=itemView.findViewById(R.id.txtRemark);
            txtAction=itemView.findViewById(R.id.txtAction);
            txtEdit=itemView.findViewById(R.id.txtEdit);
            txtDoseForm=itemView.findViewById(R.id.txtDoseForm);
            txtDuration=itemView.findViewById(R.id.txtDuration);
            txtStr=itemView.findViewById(R.id.txtStr);
            txtStop=itemView.findViewById(R.id.txtStop);
            txtDelete=itemView.findViewById(R.id.txtDelete);
        }
    }
}
