package com.his.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.PatientList;
import com.his.android.Activity.PriscriptionOverviewPopup;
import com.his.android.Activity.SendMessage;
import com.his.android.Fragment.MedicineSidePathway;
import com.his.android.Fragment.NutriAnalyserFragment;
import com.his.android.Model.DieteticsPatientList;
import com.his.android.R;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;

import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DieteticsPatientListAdp extends RecyclerView.Adapter<DieteticsPatientListAdp.RecyclerViewHolder> {
    private Context mCtx;
    private List<DieteticsPatientList> physioPatientLists;
    private PatientList patientList=new PatientList();
    public DieteticsPatientListAdp(Context mCtx, List<DieteticsPatientList> physioPatientLists) {
        this.mCtx = mCtx;
        this.physioPatientLists =physioPatientLists;
    }

    @NonNull
    @Override
    public DieteticsPatientListAdp.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inner_patient_list, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DieteticsPatientListAdp.RecyclerViewHolder holder, final int i) {
        holder.txtPName.setText(physioPatientLists.get(i).getName());
        holder.txtPId.setText(String.valueOf(physioPatientLists.get(i).getpID()));
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
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
//            Intent intent = new Intent(mCtx, Dashboard.class);
            Intent intent = new Intent(mCtx, NutriAnalyserFragment.class);
            mCtx.startActivity(intent);
        });
        holder.txtDiagnosis.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
//            Intent intent = new Intent(mCtx, Dashboard.class);
            Intent intent = new Intent(mCtx, NutriAnalyserFragment.class);
            mCtx.startActivity(intent);
        });
        holder.txtPId.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
//            Intent intent = new Intent(mCtx, Dashboard.class);
            Intent intent = new Intent(mCtx, NutriAnalyserFragment.class);
            mCtx.startActivity(intent);
        });
        holder.imgInfo.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
            Intent intent = new Intent(mCtx, PriscriptionOverviewPopup.class);
            mCtx.startActivity(intent);
        });
        holder.txtMed.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
            Intent intent = new Intent(mCtx, MedicineSidePathway.class);
            mCtx.startActivity(intent);
        });
        holder.imgMsg.setOnClickListener(view -> {
            SharedPrefManager.getInstance(mCtx).setDieteticsPatient(physioPatientLists.get(i));
            SharedPrefManager.getInstance(mCtx).setPid(physioPatientLists.get(i).getpID());
            SharedPrefManager.getInstance(mCtx).setIpNo("0");
            mCtx.startActivity(new Intent(mCtx, SendMessage.class));
        });
        holder.txtRemove.setOnClickListener(view -> new AlertDialog.Builder(mCtx).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to remove patient?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Call<ResponseBody> call = RetrofitClient1.getInstance().getApi().removePatientFromNutritionalPanel("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", physioPatientLists.get(i).getMemberId(), physioPatientLists.get(i).getUserLoginId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.v("status", String.valueOf(response.code()));
                            if (response.isSuccessful()) {
                                Toast.makeText(mCtx, "Removed Successfully!", Toast.LENGTH_SHORT).show();
                                patientList.bindDieteticsPatient();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.v("status", Objects.requireNonNull(t.getMessage()));
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
        TextView txtPName,txtPId,txtPAge,txtGender,txtDiagnosis, txtRemove, txtMed;
        ImageView imgInfo, imgMsg;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPName=itemView.findViewById(R.id.txtPName);
            txtPId=itemView.findViewById(R.id.txtPId);
            txtPAge=itemView.findViewById(R.id.txtPAge);
            txtGender=itemView.findViewById(R.id.txtGender);
            txtRemove=itemView.findViewById(R.id.txtStop);
            txtDiagnosis=itemView.findViewById(R.id.txtDiagnosis);
            imgInfo=itemView.findViewById(R.id.imgInfo);
            txtMed=itemView.findViewById(R.id.txtMed);
            imgMsg=itemView.findViewById(R.id.imgMsg);
            txtRemove.setVisibility(View.VISIBLE);
        }
    }
}
