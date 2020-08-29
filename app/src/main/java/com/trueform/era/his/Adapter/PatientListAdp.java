package com.trueform.era.his.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Activity.Dashboard;
import com.trueform.era.his.Activity.EnterPID;
import com.trueform.era.his.Activity.GuardPostActivity;
import com.trueform.era.his.Activity.PriscriptionOverviewPopup;
import com.trueform.era.his.Fragment.MedicineSidePathway;
import com.trueform.era.his.Model.AdmittedPatient;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.CheckPidResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientListAdp extends RecyclerView.Adapter<PatientListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<AdmittedPatient> admittedPatient;
    public PatientListAdp(Context mCtx, List<AdmittedPatient> admittedPatient) {
        this.mCtx = mCtx;
        this.admittedPatient=admittedPatient;
    }

    @NonNull
    @Override
    public PatientListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PatientListAdp.RecyclerViewHolder holder, int i) {
        holder.txtPName.setText(admittedPatient.get(i).getPname());
        holder.txtPId.setText(String.valueOf(admittedPatient.get(i).getPid()));
        holder.txtPAge.setText(admittedPatient.get(i).getAge());//+" "+admittedPatient.get(i).getAgeUnit()
        Drawable img;
        if (admittedPatient.get(i).getSex().equalsIgnoreCase("m")) {
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
        if (admittedPatient.get(i).getRead()) holder.txtNew.setVisibility(View.GONE);
        else holder.txtNew.setVisibility(View.VISIBLE);
        holder.txtGender.setText(admittedPatient.get(i).getGender());
        holder.txtDiagnosis.setText(admittedPatient.get(i).getWardName() + " - " + admittedPatient.get(i).getConsultantName());
        holder.txtPName.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 2) {
                if (!admittedPatient.get(i).getRead())
                    checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
                SharedPrefManager.getInstance(mCtx).setAdmitPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.txtDiagnosis.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 2) {
                if (!admittedPatient.get(i).getRead())
                    checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
                SharedPrefManager.getInstance(mCtx).setAdmitPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.txtPId.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 2) {
                if (!admittedPatient.get(i).getRead())
                    checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
                SharedPrefManager.getInstance(mCtx).setAdmitPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                Intent intent = new Intent(mCtx, Dashboard.class);
                mCtx.startActivity(intent);
            }
        });
        holder.imgInfo.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 2) {
                if (!admittedPatient.get(i).getRead())
                    checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
                SharedPrefManager.getInstance(mCtx).setAdmitPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
                Intent intent = new Intent(mCtx, PriscriptionOverviewPopup.class);
                mCtx.startActivity(intent);
            }
        });
        holder.txtMed.setOnClickListener(View -> {
            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 2) {
                if (!admittedPatient.get(i).getRead())
                    checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
                SharedPrefManager.getInstance(mCtx).setAdmitPatient(admittedPatient.get(i));
                SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
                SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
                Intent intent = new Intent(mCtx, MedicineSidePathway.class);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return admittedPatient.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtPName,txtPId,txtPAge,txtGender,txtDiagnosis, txtMed, txtNew;
        ImageView imgInfo;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName=itemView.findViewById(R.id.txtPName);
            txtPId=itemView.findViewById(R.id.txtPId);
            txtPAge=itemView.findViewById(R.id.txtPAge);
            txtGender=itemView.findViewById(R.id.txtGender);
            txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
            imgInfo=itemView.findViewById(R.id.imgInfo);
            txtMed=itemView.findViewById(R.id.txtMed);
            txtNew=itemView.findViewById(R.id.txtNew);
        }
    }
    private void checkCrNo(String pid){
        if (ConnectivityChecker.checker(mCtx)) {
            Utils.showRequestDialog(mCtx);
            Call<CheckPidResp> call = RetrofitClient.getInstance().getApi().checkCRNo(SharedPrefManager.getInstance(mCtx).getUser().getAccessToken(), SharedPrefManager.getInstance(mCtx).getUser().getUserid().toString(), pid, SharedPrefManager.getInstance(mCtx).getSubDept().getId(), SharedPrefManager.getInstance(mCtx).getUser().getUserid());
            call.enqueue(new Callback<CheckPidResp>() {
                @Override
                public void onResponse(Call<CheckPidResp> call, Response<CheckPidResp> response) {
                    if (response.isSuccessful()) {
                        CheckPidResp checkPidResp = response.body();
                        if ((checkPidResp != null ? checkPidResp.getPatientDetails().size() : 0) > 0) {
                            /*SharedPrefManager.getInstance(mCtx).setOpdPatient(checkPidResp.getPatientDetails().get(0));
                            SharedPrefManager.getInstance(mCtx).setPid(checkPidResp.getPatientDetails().get(0).getPid());*/
                            Toast.makeText(mCtx, response.message(), Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }
                    } else {
                        Toast.makeText(mCtx, response.message(), Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<CheckPidResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        } else Toast.makeText(mCtx, mCtx.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
}
