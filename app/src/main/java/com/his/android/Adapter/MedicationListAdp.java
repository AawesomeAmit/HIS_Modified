package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.his.android.Model.AdmittedPatient;
import com.his.android.R;

import java.util.List;

public class MedicationListAdp extends RecyclerView.Adapter<MedicationListAdp.RecyclerViewHolder> {
    private Context mCtx;
    Drawable img =null;
    private List<AdmittedPatient> admittedPatient;
    public MedicationListAdp(Context mCtx, List<AdmittedPatient> admittedPatient) {
        this.mCtx = mCtx;
        this.admittedPatient=admittedPatient;
    }

    @NonNull
    @Override
    public MedicationListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationListAdp.RecyclerViewHolder holder, int i) {
        holder.txtPName.setText(admittedPatient.get(i).getPname());
        holder.txtPId.setText(String.valueOf(admittedPatient.get(i).getPid()));
        holder.txtPAge.setText(String.valueOf(admittedPatient.get(i).getAge()));
        if(admittedPatient.get(i).getSex().equalsIgnoreCase("m")) {
            img = mCtx.getResources().getDrawable( R.drawable.male );
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
            holder.txtPId.setBackgroundResource(R.drawable.pid_bg_blue);
            holder.txtGender.setTextColor(mCtx.getResources().getColor(R.color.blue_gender));
        }
        else {
            img = mCtx.getResources().getDrawable( R.drawable.female );
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
            holder.txtPId.setBackgroundResource(R.drawable.pid_bg);
            holder.txtGender.setTextColor(mCtx.getResources().getColor(R.color.pink));
        }
        holder.txtGender.setText(admittedPatient.get(i).getGender());
    }

    @Override
    public int getItemCount() {
        return admittedPatient.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtPName,txtPId,txtPAge,txtGender,txtDiagnosis;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName=itemView.findViewById(R.id.txtPName);
            txtPId=itemView.findViewById(R.id.txtPId);
            txtPAge=itemView.findViewById(R.id.txtPAge);
            txtGender=itemView.findViewById(R.id.txtGender);
            txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
        }
    }
}
