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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.ChatActivity;
import com.his.android.Activity.ChatTitle;
import com.his.android.Activity.Dashboard;
import com.his.android.Activity.PatientList;
import com.his.android.Activity.PriscriptionOverviewPopup;
import com.his.android.Fragment.MedicineSidePathway;
import com.his.android.Model.PhysioPatientList;
import com.his.android.R;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysioPatientListAdp extends RecyclerView.Adapter<PhysioPatientListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<PhysioPatientList> physioPatientLists;
    private PatientList patientList = new PatientList();

    public PhysioPatientListAdp(Context mCtx, List<PhysioPatientList> physioPatientLists) {
        this.mCtx = mCtx;
        this.physioPatientLists = physioPatientLists;
    }

    @NonNull
    @Override
    public PhysioPatientListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhysioPatientListAdp.RecyclerViewHolder holder, final int i) {
        holder.txtPName.setText(physioPatientLists.get(i).getPatientName());
        holder.txtPId.setText(String.valueOf(physioPatientLists.get(i).getPid()));
        holder.txtPAge.setText(String.valueOf(physioPatientLists.get(i).getAge()));
        Drawable img;
        if (physioPatientLists.get(i).getGender().equalsIgnoreCase("male")) {
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
        holder.txtPName.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
        });
        holder.txtDiagnosis.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
        });
        holder.txtPId.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
            Intent intent = new Intent(mCtx, Dashboard.class);
            mCtx.startActivity(intent);
        });
        holder.imgInfo.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
            Intent intent = new Intent(mCtx, PriscriptionOverviewPopup.class);
            intent.putExtra("PatientName", physioPatientLists.get(i).getPatientName());
            intent.putExtra("Pid", physioPatientLists.get(i).getPid());
            intent.putExtra("ward", physioPatientLists.get(i).getAddress() + " - " + physioPatientLists.get(i).getAge());

            mCtx.startActivity(intent);
        });
        holder.txtMed.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
            Intent intent = new Intent(mCtx, MedicineSidePathway.class);
            mCtx.startActivity(intent);
        });
        holder.imgMsg.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setPhysioPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getPid());
            SharedPrefManager.getInstance(mCtx).setIpNo(physioPatientLists.get(i).getIpNo());
//            mCtx.startActivity(new Intent(mCtx, ChatActivity.class));
            mCtx.startActivity(new Intent(mCtx, ChatTitle.class));
        });
        holder.txtRemove.setOnClickListener(view -> new AlertDialog.Builder(mCtx).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to remove patient?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().removePatientFromPhysiotherapyPanel(SharedPrefManager.getInstance(mCtx).getUser().getAccessToken(), SharedPrefManager.getInstance(mCtx).getUser().getUserid().toString(), SharedPrefManager.getInstance(mCtx).getSubDept().getId(), physioPatientLists.get(i).getId(), SharedPrefManager.getInstance(mCtx).getUser().getUserid());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mCtx, "Removed Successfully!", Toast.LENGTH_SHORT).show();
                                patientList.bindPhysioList();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                })

                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show());
        holder.txtGender.setText(physioPatientLists.get(i).getGender());
    }

    @Override
    public int getItemCount() {
        return physioPatientLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtPName, txtPId, txtPAge, txtGender, txtDiagnosis, txtRemove, txtMed;
        ImageView imgInfo, imgMsg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName = itemView.findViewById(R.id.txtPName);
            txtPId = itemView.findViewById(R.id.txtPId);
            txtPAge = itemView.findViewById(R.id.txtPAge);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtRemove = itemView.findViewById(R.id.txtStop);
            txtDiagnosis = itemView.findViewById(R.id.txtDiagnosis);
            imgInfo = itemView.findViewById(R.id.imgInfo);
            txtRemove.setVisibility(View.VISIBLE);
            txtMed = itemView.findViewById(R.id.txtMed);
            imgMsg = itemView.findViewById(R.id.imgMsg);
        }
    }
}
