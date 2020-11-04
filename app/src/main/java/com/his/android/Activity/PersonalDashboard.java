package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.his.android.Fragment.Intake;
import com.his.android.Model.IntakeDetail;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.Response.IntakeData;
import com.his.android.Response.PatientDashboardResp;
import com.his.android.R;
import com.his.android.Response.UnitResp;
import com.his.android.Utils.InputFilterMinMax;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.squareup.picasso.Picasso;
import com.his.android.Model.IntakeDashboard;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.pm.ActivityInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PersonalDashboard extends BaseActivity {
    TextView txtName;
    EditText edtPid;
    RecyclerView rvActivity, rvMedication, rvIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_dashboard);
        txtName=findViewById(R.id.txtName);
        edtPid=findViewById(R.id.edtPid);
        rvActivity=findViewById(R.id.rvActivity);
        rvMedication=findViewById(R.id.rvMedication);
        rvIntake=findViewById(R.id.rvIntake);
        rvActivity.setLayoutManager(new LinearLayoutManager(mActivity));
        rvMedication.setLayoutManager(new LinearLayoutManager(mActivity));
        rvIntake.setLayoutManager(new LinearLayoutManager(mActivity));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        edtPid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtName.setText("");
                if(charSequence.length()>6){
                    bind(1, 1, 1, 1, 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void start() {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().startPatientPhysicalActivity(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtPid.getText().toString().trim(), "", "", SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void bind(int patientDetails, int vitalDetails, int patientActivityDetails, int medicineDetails, int intakeDetails){
        Utils.showRequestDialog(mActivity);
        Call<PatientDashboardResp> call= RetrofitClient.getInstance().getApi().getPersonalDashBoardDetails(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), edtPid.getText().toString().trim(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), patientDetails, vitalDetails, patientActivityDetails, medicineDetails, intakeDetails);
        call.enqueue(new Callback<PatientDashboardResp>() {
            @Override
            public void onResponse(Call<PatientDashboardResp> call, Response<PatientDashboardResp> response) {
                if(response.isSuccessful()){
                    txtName.setText(response.body().getPatientDetails().get(0).getPatientName() + " " + response.body().getPatientDetails().get(0).getAge() + " " + response.body().getPatientDetails().get(0).getAgeUnit());
                    rvActivity.setAdapter(new ActivityDashboardAdp(response.body().getPatientActivityDetails()));
                    rvIntake.setAdapter(new IntakeAdp(response.body().getIntakeDetails()));
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientDashboardResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    public class ActivityDashboardAdp extends RecyclerView.Adapter<ActivityDashboardAdp.RecyclerViewHolder> {
        private List<PatientActivityDetail> patientActivityDetails;
        public ActivityDashboardAdp(List<PatientActivityDetail> patientActivityDetails) {
            this.patientActivityDetails = patientActivityDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_patient_dashboard_activity, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtActivity.setText(String.valueOf(patientActivityDetails.get(i).getPhysicalActivityName()));
//            Picasso.with(mActivity).load(patientActivityDetails.get(i).getIconImage()).into(holder.imgActivity);
//            Picasso.with(mActivity).load(patientActivityDetails.get(i).getIconImage()).resize((int) getResources().getDimension(R.dimen._15sdp), (int) getResources().getDimension(R.dimen._15sdp)).into(holder.imgActivity);
//            holder.txtEditDate.setOnClickListener(view -> dateTimePopup(foodDetails.get(i).getId(), foodDetails.get(i).getIsSupplement(), foodDetails.get(i).getIntakeName()));
            /*holder.txtActivity.setOnClickListener(view -> {
                    Utils.showRequestDialog(mActivity);
                    if (patientActivityDetails.get(i).getActivityStatus().equalsIgnoreCase("S"))
                        call = RetrofitClient1.getInstance().getApi().UpdateIntakeConsumption("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", "",SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId()));
                    else call = RetrofitClient1.getInstance().getApi().UpdateConsumptionPercentage("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", holder.edtQty.getText().toString().trim(), String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId()), array, SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()), patientActivityDetails.get(i).getPhysicalActivityID().toString());
                    call.enqueue(new Callback<UnitResp>() {
                        @Override
                        public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null && response.body().getResponseCode() == 1) {
                                    bind(0, 0, 1, 0, 0);
                                    notifyItemChanged(i);
                                    Utils.hideDialog();
                                } else if (response.body() != null) {
                                    Toast.makeText(mActivity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UnitResp> call, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
            });*/
        }

        @Override
        public int getItemCount() {
            return patientActivityDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtActivity;
            ImageView imgActivity;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtActivity =itemView.findViewById(R.id.txtActivity);
                imgActivity =itemView.findViewById(R.id.imgActivity);
            }
        }
    }

    public class IntakeAdp extends RecyclerView.Adapter<IntakeAdp.RecyclerViewHolder> {
        private List<IntakeDetail> foodDetails;
        Gson gson = new Gson();
        public IntakeAdp(List<IntakeDetail> foodDetails) {
            this.foodDetails = foodDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_dashboard_meal, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtDate.setText(foodDetails.get(i).getIntakeDateTime());
            List<IntakeDashboard> intakeDashboardList= gson.fromJson(foodDetails.get(i).getIntake(), new TypeToken<List<IntakeDashboard>>(){}.getType());
            holder.rvInnerMeal.setAdapter(new IntakeInnerAdp(intakeDashboardList));
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtDate;
            RecyclerView rvInnerMeal;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDate =itemView.findViewById(R.id.txtDate);
                rvInnerMeal=itemView.findViewById(R.id.rvInnerMeal);
                rvInnerMeal.setLayoutManager(new LinearLayoutManager(mActivity));
            }
        }
    }

    public class IntakeInnerAdp extends RecyclerView.Adapter<IntakeInnerAdp.RecyclerViewHolder> {
        private List<IntakeDashboard> foodDetails;
        Gson gson = new Gson();
        public IntakeInnerAdp(List<IntakeDashboard> foodDetails) {
            this.foodDetails = foodDetails;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_input_meal, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
            holder.txtFluid.setText(String.valueOf(foodDetails.get(i).getIntakeName()));
            holder.txtQty.setText(String.valueOf(foodDetails.get(i).getGivenIntakeQuantity()));
            holder.txtUnit.setText(String.valueOf(foodDetails.get(i).getUnit()));
            /*holder.txtSave.setOnClickListener(view -> {
                JSONArray array=new JSONArray();
                JSONObject object = new JSONObject();
                try {
                    object.put("dietID", foodDetails.get(i).getId());
                    object.put("consumptionPercentage", holder.edtQty.getText().toString().trim());
                    array.put(object);
                    Log.v("array", String.valueOf(array));
                    Utils.showRequestDialog(mActivity);
                    Call<UnitResp> call = null;
                    if (foodDetails.get(i).getIsSupplement()==0)
                        call = RetrofitClient1.getInstance().getApi().UpdateIntakeConsumption("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", array,SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId()));
                    else call = RetrofitClient1.getInstance().getApi().UpdateConsumptionPercentage("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", holder.edtQty.getText().toString().trim(), String.valueOf(SharedPrefManager.getInstance(mActivity).getMemberId().getUserLoginId()), array, SharedPrefManager.getInstance(mActivity).getMemberId().getMemberId().toString(), String.valueOf(SharedPrefManager.getInstance(mActivity).getUser().getUserid()), foodDetails.get(i).getId().toString());
                    call.enqueue(new Callback<UnitResp>() {
                        @Override
                        public void onResponse(Call<UnitResp> call, Response<UnitResp> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null && response.body().getResponseCode() == 1) {
                                    holder.txtEdit.setVisibility(View.VISIBLE);
                                    holder.txtClose.setVisibility(View.GONE);
                                    holder.txtSave.setVisibility(View.GONE);
                                    holder.edtQty.setVisibility(View.GONE);
                                    holder.txtPer.setVisibility(View.GONE);
                                    holder.edtQty.setEnabled(false);
                                    bind();
                                    notifyItemChanged(i);
                                    Utils.hideDialog();
                                } else if (response.body() != null) {
                                    Toast.makeText(mActivity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UnitResp> call, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return foodDetails.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtFluid,txtQty,txtUnit, txtDateTime, txtEdit, txtSave, txtPer, txtEditDate, txtClose;
            EditText edtQty;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtFluid =itemView.findViewById(R.id.txtFluid);
                txtQty=itemView.findViewById(R.id.txtStr);
                txtUnit=itemView.findViewById(R.id.txtUnit);
                txtDateTime=itemView.findViewById(R.id.txtDateTime);
                txtEdit=itemView.findViewById(R.id.txtEdit);
                edtQty=itemView.findViewById(R.id.edtQty);
                txtSave=itemView.findViewById(R.id.txtSave);
                txtPer=itemView.findViewById(R.id.txtPer);
                txtEditDate=itemView.findViewById(R.id.txtEditDate);
                txtClose=itemView.findViewById(R.id.txtClose);
                edtQty.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});
            }
        }
    }
}