package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.ChatActivity;
import com.his.android.Activity.ChatTitle;
import com.his.android.Activity.Dashboard;
import com.his.android.Fragment.MedicineSidePathway;
import com.his.android.Model.PatientList;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;

import java.util.List;

public class CovidPatientListAdp extends RecyclerView.Adapter<CovidPatientListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<PatientList> admittedPatient;
    public CovidPatientListAdp(Context mCtx, List<PatientList> admittedPatient) {
        this.mCtx = mCtx;
        this.admittedPatient=admittedPatient;
    }

    @NonNull
    @Override
    public CovidPatientListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CovidPatientListAdp.RecyclerViewHolder holder, int i) {
        holder.txtPName.setText(admittedPatient.get(i).getName());
        holder.txtPId.setText(String.valueOf(admittedPatient.get(i).getPid()));
        holder.txtPAge.setText(admittedPatient.get(i).getAge());
        Drawable img;
        if (admittedPatient.get(i).getGender().contains("Male")) {
            img = mCtx.getResources().getDrawable(R.drawable.male);
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            holder.txtPId.setBackgroundResource(R.drawable.pid_bg_blue);
            holder.txtGender.setTextColor(mCtx.getResources().getColor(R.color.blue_gender));
        } else {
            img = mCtx.getResources().getDrawable(R.drawable.female);
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            holder.txtPId.setBackgroundResource(R.drawable.pid_bg);
            holder.txtGender.setTextColor(mCtx.getResources().getColor(R.color.pink));
        }
        holder.txtGender.setText(admittedPatient.get(i).getGender());
        holder.txtPName.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 1) {
                SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
                //SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setIsCovid(true);
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.txtDiagnosis.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 1) {
                SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
                //SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setIsCovid(true);
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.txtPId.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 1) {
                SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
                //SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setIsCovid(true);
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.imgInfo.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 1) {
                SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
                //SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setIsCovid(true);
                Intent intent = new Intent(mCtx, Dashboard.class);
                intent.putExtra("PatientName", admittedPatient.get(i).getName());
                intent.putExtra("Pid", admittedPatient.get(i).getPid());
                intent.putExtra("ward", admittedPatient.get(i).getProblem() + " - " + admittedPatient.get(i).getAge());

                mCtx.startActivity(intent);
            }
        });
        holder.txtMed.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 1) {
                SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
                //SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setIsCovid(true);
                Intent intent = new Intent(mCtx, MedicineSidePathway.class);
                mCtx.startActivity(intent);
            }
        });

        holder.imgMsg.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setCovidPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getName());
            SharedPrefManager.getInstance(mCtx).setIsCovid(true);
//            mCtx.startActivity(new Intent(mCtx, ChatActivity.class));
            mCtx.startActivity(new Intent(mCtx, ChatTitle.class));
        });
    }

    @Override
    public int getItemCount() {
        return admittedPatient.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtPName,txtPId,txtPAge,txtGender,txtDiagnosis, txtMed;
        ImageView imgInfo, imgMsg;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName=itemView.findViewById(R.id.txtPName);
            txtPId=itemView.findViewById(R.id.txtPId);
            txtPAge=itemView.findViewById(R.id.txtPAge);
            txtGender=itemView.findViewById(R.id.txtGender);
            txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
            imgInfo=itemView.findViewById(R.id.imgInfo);
            txtMed=itemView.findViewById(R.id.txtMed);
            imgMsg=itemView.findViewById(R.id.imgMsg);
        }
    }
}
