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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.ChatActivity;
import com.his.android.Activity.ChatTitle;
import com.his.android.Activity.Dashboard;
import com.his.android.Activity.PreDashboard;
import com.his.android.Activity.PriscriptionOverviewPopup;
import com.his.android.Fragment.MedicineSidePathway;
import com.his.android.Model.AdmittedPatientICU;
import com.his.android.R;
import com.his.android.Response.CheckPidResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IcuPatientListAdp extends RecyclerView.Adapter<IcuPatientListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<AdmittedPatientICU> admittedPatient;

    public IcuPatientListAdp(Context mCtx, List<AdmittedPatientICU> admittedPatient) {
        this.mCtx = mCtx;
        this.admittedPatient = admittedPatient;
    }

    @NonNull
    @Override
    public IcuPatientListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IcuPatientListAdp.RecyclerViewHolder holder, int i) {
        holder.txtPName.setText(admittedPatient.get(i).getPname());
        holder.txtPId.setText(String.valueOf(admittedPatient.get(i).getPid()));
        holder.txtPAge.setText(admittedPatient.get(i).getAge() + " " + admittedPatient.get(i).getAgeUnit());
        Drawable img;
        if (admittedPatient.get(i).getGender().equalsIgnoreCase("male")) {
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
        holder.txtGender.setText(admittedPatient.get(i).getSex());
        holder.txtDiagnosis.setText(admittedPatient.get(i).getWardName() + " - " + admittedPatient.get(i).getConsultantName());
        holder.txtPName.setOnClickListener(View -> {
//            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 3 || SharedPrefManager.getInstance(mCtx).getHeadID() == 4) {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
//            }
        });
        holder.txtPId.setOnClickListener(View -> {
//            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 3 || SharedPrefManager.getInstance(mCtx).getHeadID() == 4) {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
//            }
        });
        holder.txtDiagnosis.setOnClickListener(View -> {
//            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 3 || SharedPrefManager.getInstance(mCtx).getHeadID() == 4) {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
//            }
        });
        holder.imgInfo.setOnClickListener(View -> {
//            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 3 || SharedPrefManager.getInstance(mCtx).getHeadID() == 4) {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
            Intent intent = new Intent(mCtx, PriscriptionOverviewPopup.class);
            intent.putExtra("PatientName", admittedPatient.get(i).getPname() + "( " + admittedPatient.get(i).getAge() + " )");
            intent.putExtra("Pid", String.valueOf(admittedPatient.get(i).getPid()));
            intent.putExtra("ward", admittedPatient.get(i).getWardName() + " - " + admittedPatient.get(i).getConsultantName());

            mCtx.startActivity(intent);
//            }
        });
        holder.txtMed.setOnClickListener(View -> {
//            if (SharedPrefManager.getInstance(mCtx).getHeadID() == 3 || SharedPrefManager.getInstance(mCtx).getHeadID() == 4) {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
            Intent intent = new Intent(mCtx, MedicineSidePathway.class);
            mCtx.startActivity(intent);
//            }
        });
        holder.imgMsg.setOnClickListener(view -> {
            if (!admittedPatient.get(i).getRead())
                checkCrNo(String.valueOf(admittedPatient.get(i).getPid()));
            SharedPrefManager.getInstance(mCtx).setIcuAdmitPatient(admittedPatient.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(admittedPatient.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(admittedPatient.get(i).getIpNo());
            SharedPrefManager.getInstance(mCtx).setPmId(admittedPatient.get(i).getPmid());
            SharedPrefManager.getInstance(mCtx).setPtName(admittedPatient.get(i).getPname());
//            mCtx.startActivity(new Intent(mCtx, ChatActivity.class));
            mCtx.startActivity(new Intent(mCtx, ChatTitle.class));
        });
    }

    @Override
    public int getItemCount() {
        return +admittedPatient.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtPName, txtPId, txtPAge, txtGender, txtDiagnosis, txtMed, txtNew;
        ImageView imgInfo, imgMsg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName = itemView.findViewById(R.id.txtPName);
            txtPId = itemView.findViewById(R.id.txtPId);
            txtPAge = itemView.findViewById(R.id.txtPAge);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtDiagnosis = itemView.findViewById(R.id.txtDiagnosis);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            txtMed = itemView.findViewById(R.id.txtMed);
            txtNew = itemView.findViewById(R.id.txtNew);
            imgMsg = itemView.findViewById(R.id.imgMsg);
        }
    }

    private void checkCrNo(String pid) {
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
        } else
            Toast.makeText(mCtx, mCtx.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }
}
