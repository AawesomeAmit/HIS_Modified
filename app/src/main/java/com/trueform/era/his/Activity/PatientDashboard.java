package com.trueform.era.his.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trueform.era.his.Model.PatientDiagnosisVitalListModel;
import com.trueform.era.his.Model.PatientPerformanceListModel;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.GetNutrientInBodyResp;
import com.trueform.era.his.Response.PatientDiagnosisResultResp;
import com.trueform.era.his.Response.PatientPerformanceListResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDashboard extends BaseFragment {
    RecyclerView recyclerViewPatient;
    List<PatientPerformanceListModel> patientList;
    List<PatientDiagnosisVitalListModel> vitalList;
    private int mExpandedPosition = -1;
    private DiseasePatientListAdp diseasePatientListAdp;

    TextView tvVitalName, tvVitalValue, tvVitalDate, tvVitalRange, tvNutrientName, tvNutrientValue, tvNutrientRda, tvNutrientDate,
            tvIpOpQuantity, tvIpOpDate, tvNoDataVital, tvNoDataNutrient, tvNoDataIpOp, tvNoDataInvestigation;

    SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_patient_dashboard, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);

        recyclerViewPatient = view.findViewById(R.id.recyclerViewPatient);
        recyclerViewPatient.setHasFixedSize(true);
        recyclerViewPatient.setLayoutManager(new LinearLayoutManager(getActivity()));
        patientList = new ArrayList<>();

        hitGetPatientPerformanceList();

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this::hitGetPatientPerformanceList);

        return view;
    }

    private void hitGetPatientPerformanceList() {
        mSwipeRefreshLayout.setRefreshing(true);
        Call<PatientPerformanceListResp> call = RetrofitClient.getInstance().getApi().getPatientPerformanceList(SharedPrefManager.getInstance(getActivity()).getUser().getAccessToken(), SharedPrefManager.getInstance(getActivity()).getUser().getUserid().toString());
        call.enqueue(new Callback<PatientPerformanceListResp>() {
            @Override
            public void onResponse(Call<PatientPerformanceListResp> call, Response<PatientPerformanceListResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        patientList = response.body().getPerformanceList();
                        diseasePatientListAdp = new DiseasePatientListAdp(getActivity());
                        recyclerViewPatient.setAdapter(diseasePatientListAdp);
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientPerformanceListResp> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Utils.hideDialog();
            }
        });
    }

    private void hitGetPatientDiagnosisResult(String pmID, String pId, int pos) {
        Utils.showRequestDialog(getActivity());
        Call<PatientDiagnosisResultResp> call = RetrofitClient.getInstance().getApi().getPatientDiagnosisResult(
                SharedPrefManager.getInstance(getActivity()).getUser().getAccessToken(),
                SharedPrefManager.getInstance(getActivity()).getUser().getUserid().toString(),
                pmID);
        call.enqueue(new Callback<PatientDiagnosisResultResp>() {
            @Override
            public void onResponse(Call<PatientDiagnosisResultResp> call, Response<PatientDiagnosisResultResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        vitalList = response.body().getVitalList();

                        if (vitalList.size() == 0) {
                            tvNoDataVital.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getIntakeList().size() == 0 && response.body().getOutputList().size() == 0) {
                            tvNoDataIpOp.setVisibility(View.VISIBLE);
                        } else {
                            tvNoDataIpOp.setVisibility(View.GONE);
                        }

                        String name = "", value = "", date = "", range = "";

                        for (int i = 0; i < vitalList.size(); i++) {

                            if (vitalList.get(i).getVitalName() != null)
                                name = name + (vitalList.get(i).getVitalName()) + "\n";

                            if (vitalList.get(i).getVmValue() != null)
                                value = value + (vitalList.get(i).getVmValue()) + "\n";

                            if (vitalList.get(i).getLastVitalDate() != null)
                                date = date + (vitalList.get(i).getLastVitalDate()) + "\n";

                            if (vitalList.get(i).getNormalRange() != null)
                                range = range + (vitalList.get(i).getNormalRange()) + "\n";

                        }

                        tvVitalName.setText(name);
                        tvVitalValue.setText(value);
                        tvVitalDate.setText(date);
                        tvVitalRange.setText(range);
                        String quantity = "", dateIpOp = "", intQuantity = "", maxIntakeDate = "", outQuantity = "", maxOutputDate = "";
                        // for (int i = 0; i < response.body().getIntakeList().size(); i++)
                        if (response.body().getIntakeList().size() > 0) {
                            if (response.body().getIntakeList().get(0).getMaxIntakeDate() != null)
                                intQuantity = response.body().getIntakeList().get(0).getQuantity().toString();
                            if (response.body().getIntakeList().get(0).getQuantity() != null)
                                maxIntakeDate = response.body().getIntakeList().get(0).getMaxIntakeDate();
                        }
                        // for (int i = 0; i < response.body().getOutputList().size(); i++)
                        if (response.body().getOutputList().size() > 0) {
                            if (response.body().getOutputList().get(0).getMaxOutputDate() != null)
                                outQuantity = response.body().getOutputList().get(0).getQuantity().toString();
                            if (response.body().getOutputList().get(0).getQuantity() != null)
                                maxOutputDate = response.body().getOutputList().get(0).getMaxOutputDate();
                        }
                        quantity = intQuantity + "/" + outQuantity;
                        dateIpOp = maxIntakeDate + "/" + maxOutputDate;
                        tvIpOpQuantity.setText(quantity);
                        tvIpOpDate.setText(dateIpOp);
                        //diseasePatientListAdp.notifyItemChanged(pos);
                        hitGetNutrientInBody(pId, pos);
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PatientDiagnosisResultResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void hitGetNutrientInBody(String pId, int pos) {
        Utils.showRequestDialog(getActivity());

        Call<GetNutrientInBodyResp> call = RetrofitClient.getInstance().getApi().getNutrientInBody(
                SharedPrefManager.getInstance(getActivity()).getUser().getAccessToken(),
                SharedPrefManager.getInstance(getActivity()).getUser().getUserid().toString(),
                pId);
        call.enqueue(new Callback<GetNutrientInBodyResp>() {
            @Override
            public void onResponse(Call<GetNutrientInBodyResp> call, Response<GetNutrientInBodyResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResultList().size() == 0) {
                            tvNoDataNutrient.setVisibility(View.VISIBLE);
                        } else {
                            tvNoDataNutrient.setVisibility(View.GONE);
                        }
                        //recyclerViewPatient.setAdapter(new DiseasePatientListAdp(PatientDashboard.this));
                        String name = "", value = "", date = "", rda = "";
                        for (int i = 0; i < response.body().getResultList().size(); i++) {
                            if (response.body().getResultList().get(i).getNutrientName() != null)
                                name = name + (response.body().getResultList().get(i).getNutrientName()) + "\n";
                            if (response.body().getResultList().get(i).getNutrientValue() != null)
                                value = value + (response.body().getResultList().get(i).getNutrientValue()) + "\n";
                            if (response.body().getResultList().get(i).getValueDateTime() != null)
                                date = date + (response.body().getResultList().get(i).getValueDateTime()) + "\n";
                            if (response.body().getResultList().get(i).getNutrientRDA() != null)
                                rda = rda + (response.body().getResultList().get(i).getNutrientRDA()) + "\n";
                        }

                        tvNutrientName.setText(name);
                        tvNutrientValue.setText(value);
                        tvNutrientDate.setText(date);
                        tvNutrientRda.setText(rda);
                        //diseasePatientListAdp.notifyItemChanged(pos);
                    }
                }
                Utils.hideDialog();
            }
            @Override
            public void onFailure(Call<GetNutrientInBodyResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    public class DiseasePatientListAdp extends RecyclerView.Adapter<DiseasePatientListAdp.RecyclerViewHolder> {
        private Context mCtx;
        DiseasePatientListAdp(Context mCtx) {
            this.mCtx = mCtx;
        }
        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mCtx).inflate(R.layout.inflate_patient_dashboard, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint({"SetTextI18n", "NewApi"})
        @Override
        public void onBindViewHolder(@NonNull final DiseasePatientListAdp.RecyclerViewHolder holder, final int i) {
            if (patientList.get(i).isSelected()) {
                holder.clSub.setVisibility(View.VISIBLE);
                holder.ivArrow.animate().setDuration(200).rotation(90);
            } else {
                holder.clSub.setVisibility(View.GONE);
                holder.ivArrow.animate().setDuration(200).rotation(0);
            }
            holder.clMain.setOnClickListener(view -> {
                if (mExpandedPosition >= 0) {
                    int prev = mExpandedPosition;
                    notifyItemChanged(prev);
                }
                mExpandedPosition = i;
                if (holder.clSub.isShown()) {
                    patientList.get(holder.getLayoutPosition()).setSelected(false);
                } else {
                    patientList.get(holder.getLayoutPosition()).setSelected(true);
                    changeStateOfItemsInLayout(holder.getLayoutPosition());
                    hitGetPatientDiagnosisResult(patientList.get(mExpandedPosition).getPmID().toString(),
                            patientList.get(mExpandedPosition).getPid().toString(), mExpandedPosition);
                }
                notifyItemChanged(mExpandedPosition);
            });

            holder.tvPid.setText(patientList.get(i).getPid().toString());
            holder.tvName.setText(patientList.get(i).getPatientName());
            holder.tvWard.setText((patientList.get(i).getWardName()));

            holder.tvIpNo.setText(patientList.get(i).getIpNo());
            holder.tvAgeGender.setText(patientList.get(i).getAge());
            if (patientList.get(i).getGender().equalsIgnoreCase("M")) {
                holder.tvAgeGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_male_symbol, 0);
            } else if (patientList.get(i).getGender().equalsIgnoreCase("F")) {
                holder.tvAgeGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_female_symbol, 0);
            }
            holder.tvAdmitDate.setText(patientList.get(i).getAdmitDateTime());
            holder.tvCity.setText(patientList.get(i).getDistrictName());
            holder.tvPhone.setText(patientList.get(i).getMobileNo());
            holder.tvDiagnosis.setText(patientList.get(i).getDiagnosis());
            holder.tvConsultant.setText(patientList.get(i).getConsultantName());

            try {

                if (patientList.get(i).getVitalDetail() != null) {
                    JSONArray jsonArrayVitalDetail = new JSONArray(patientList.get(i).getVitalDetail());

                    for (int j = 0; j < jsonArrayVitalDetail.length(); j++) {
                        JSONObject jsonObject = jsonArrayVitalDetail.getJSONObject(j);

                        if (jsonObject.getString("vitalName").equalsIgnoreCase("bp_sys")) {

                            holder.tvBpBox.setText(jsonObject.get("vmValue").toString());
                            double value = Double.parseDouble(jsonObject.get("vmValue").toString());
                            double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                            double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                            //double perc = 0;

                            int range = checkRange(value,minValue,maxValue,Double.parseDouble(jsonObject.get("variationPercent").toString()));

                            if (range == 1){
                                holder.tvBpBox.setTextColor(getResources().getColor(R.color.greenDark));
                            }else if (range == 2){
                                holder.tvBpBox.setTextColor(getResources().getColor(R.color.orangeDark));
                            }else if (range == 3){
                                holder.tvBpBox.setTextColor(getResources().getColor(R.color.darkRed));
                            }

                        } else if (jsonObject.getString("vitalName").equalsIgnoreCase("temperature")) {

                            holder.tvTempBox.setText(jsonObject.get("vmValue").toString());

                            if (jsonObject.has("minValue") && jsonObject.has("maxValue")){
                                double value = Double.parseDouble(jsonObject.get("vmValue").toString());
                                double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                                double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                                //double perc = 0;

                                int range = checkRange(value,minValue,maxValue,Double.parseDouble(jsonObject.get("variationPercent").toString()));

                                if (range == 1){
                                    holder.tvTempBox.setTextColor(getResources().getColor(R.color.greenDark));
                                }else if (range == 2){
                                    holder.tvTempBox.setTextColor(getResources().getColor(R.color.orangeDark));
                                }else if (range == 3){
                                    holder.tvTempBox.setTextColor(getResources().getColor(R.color.darkRed));
                                }

                            }


                        } else if (jsonObject.getString("vitalName").equalsIgnoreCase("respRate")) {

                            holder.tvRespBox.setText(jsonObject.get("vmValue").toString());

                            if (jsonObject.has("minValue") && jsonObject.has("maxValue")){
                                double value = Double.parseDouble(jsonObject.get("vmValue").toString());
                                double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                                double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                                //double perc = 0;

                                int range = checkRange(value,minValue,maxValue,Double.parseDouble(jsonObject.get("variationPercent").toString()));

                                if (range == 1){
                                    holder.tvRespBox.setTextColor(getResources().getColor(R.color.greenDark));
                                }else if (range == 2){
                                    holder.tvRespBox.setTextColor(getResources().getColor(R.color.orangeDark));
                                }else if (range == 3){
                                    holder.tvRespBox.setTextColor(getResources().getColor(R.color.darkRed));
                                }
                            }

                        } else if (jsonObject.getString("vitalName").equalsIgnoreCase("heartRate")) {

                            holder.tvHbBox.setText(jsonObject.get("vmValue").toString());

                            if (jsonObject.has("minValue") && jsonObject.has("maxValue")){

                                double value = Double.parseDouble(jsonObject.get("vmValue").toString());
                                double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                                double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                                //double perc = 0;

                                int range = checkRange(value,minValue,maxValue,Double.parseDouble(jsonObject.get("variationPercent").toString()));

                                if (range == 1){
                                    holder.tvHbBox.setTextColor(getResources().getColor(R.color.greenDark));
                                }else if (range == 2){
                                    holder.tvHbBox.setTextColor(getResources().getColor(R.color.orangeDark));
                                }else if (range == 3){
                                    holder.tvHbBox.setTextColor(getResources().getColor(R.color.darkRed));
                                }
                            }
                        } else if (jsonObject.getString("vitalName").equalsIgnoreCase("Pulse")) {

                            holder.tvPulseBox.setText(jsonObject.get("vmValue").toString());

                            if (jsonObject.has("minValue") && jsonObject.has("maxValue")){

                                double value = Double.parseDouble(jsonObject.get("vmValue").toString());
                                double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                                double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                                //double perc = 0;

                                int range = checkRange(value,minValue,maxValue,Double.parseDouble(jsonObject.get("variationPercent").toString()));

                                if (range == 1){
                                    holder.tvPulseBox.setTextColor(getResources().getColor(R.color.greenDark));
                                }else if (range == 2){
                                    holder.tvPulseBox.setTextColor(getResources().getColor(R.color.orangeDark));
                                }else if (range == 3){
                                    holder.tvPulseBox.setTextColor(getResources().getColor(R.color.darkRed));
                                }
                            }
                        }


                    }

                }

                if (patientList.get(i).getIntakeDetail() != null) {
                    JSONArray jsonArrayVitalIntake = new JSONArray(patientList.get(i).getIntakeDetail());

                    for (int j = 0; j < jsonArrayVitalIntake.length(); j++) {
                        JSONObject jsonObject = jsonArrayVitalIntake.getJSONObject(j);

                        holder.ivIntakeBox.setTooltipText(jsonObject.get("quantity").toString());

                        double value = Double.parseDouble(jsonObject.get("quantity").toString());
                        double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                        double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                        //double perc = 0;

                        if (minValue != 0 && maxValue !=0){
                            int range = checkRange(value,minValue,maxValue,Double.parseDouble("5"));

                            if (range == 1){
                                holder.ivIntakeBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.greenDark));
                            }else if (range == 2){
                                holder.ivIntakeBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.orangeDark));
                            }else if (range == 3){
                                holder.ivIntakeBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.darkRed));
                            }
                        }


                    }
                }

                if (patientList.get(i).getOutputDetail() != null) {
                    JSONArray jsonArrayVitalOutput = new JSONArray(patientList.get(i).getOutputDetail());

                    for (int j = 0; j < jsonArrayVitalOutput.length(); j++) {
                        JSONObject jsonObject = jsonArrayVitalOutput.getJSONObject(j);

                        holder.ivOutputBox.setTooltipText(jsonObject.get("quantity").toString());

                        double value = Double.parseDouble(jsonObject.get("quantity").toString());
                        double minValue = Double.parseDouble(jsonObject.get("minValue").toString());
                        double maxValue = Double.parseDouble(jsonObject.get("maxValue").toString());
                        //double perc = 0;

                        if (minValue != 0 && maxValue !=0){
                            int range = checkRange(value,minValue,maxValue,Double.parseDouble("5"));

                            if (range == 1){
                                holder.ivOutputBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.greenDark));
                            }else if (range == 2){
                                holder.ivOutputBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.orangeDark));
                            }else if (range == 3){
                                holder.ivOutputBox.setImageTintList(ContextCompat.getColorStateList(mCtx, R.color.darkRed));
                            }
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.scrollViewVital.setOnTouchListener((view, motionEvent) -> {
                // Disallow the touch request for parent scroll on touch of child view
                //view.getParent().requestDisallowInterceptTouchEvent(false);

                recyclerViewPatient.requestDisallowInterceptTouchEvent(false);
                return true;
            });

            holder.tvVital.setOnClickListener(view -> {
                defaultViews(holder);
                defaultViewsVisibility(holder);

                holder.tvVital.setTextColor(getResources().getColor(R.color.white));
                holder.tvVital.setBackgroundResource(R.drawable.shape_rectangle_left_top_corner);

                holder.clVital.setVisibility(View.VISIBLE);
            });

            holder.tvNutrientMinerals.setOnClickListener(view -> {
                defaultViews(holder);
                defaultViewsVisibility(holder);

                holder.tvNutrientMinerals.setTextColor(getResources().getColor(R.color.white));
                holder.tvNutrientMinerals.setBackgroundColor(getResources().getColor(R.color.textBrown));

                holder.clNutrientMinerals.setVisibility(View.VISIBLE);
            });

            holder.tvInputOutput.setOnClickListener(view -> {

                defaultViews(holder);
                defaultViewsVisibility(holder);

                holder.tvInputOutput.setTextColor(getResources().getColor(R.color.white));
                holder.tvInputOutput.setBackgroundResource(R.drawable.shape_rectangle_left_bottom_corner);

                holder.clInputOutput.setVisibility(View.VISIBLE);
            });

            holder.tvInvestigation.setOnClickListener(view -> {

                defaultViews(holder);
                defaultViewsVisibility(holder);

                holder.tvInvestigation.setTextColor(getResources().getColor(R.color.white));
                holder.tvInvestigation.setBackgroundColor(getResources().getColor(R.color.textBrown));

                holder.clInvestigation.setVisibility(View.VISIBLE);
            });

            holder.tvComplication.setOnClickListener(view -> {

                defaultViews(holder);
                defaultViewsVisibility(holder);

                holder.tvComplication.setTextColor(getResources().getColor(R.color.white));
                holder.tvComplication.setBackgroundColor(getResources().getColor(R.color.textBrown));

                holder.clComplication.setVisibility(View.VISIBLE);
            });

        }

        private void changeStateOfItemsInLayout(int p) {
            for (int x = 0; x < patientList.size(); x++) {
                if (x == p) {
                    patientList.get(x).setSelected(true);
                    //Since this is the tapped item, we will skip
                    //the rest of loop for this item and set it expanded
                    continue;
                }
                patientList.get(x).setSelected(false);
            }
        }


        private int checkRange(double value, double minValue, double maxValue, double variationPercent) {

            double perc = 0;

            int colorType = 0;

            if (value < minValue) {

                perc = ((minValue - value) / minValue * 100);

                if (Math.abs(perc) <= variationPercent) {
                    colorType = 2;

                } else if (Math.abs(perc) > variationPercent) {

                    colorType = 3;
                }

            } else if (value > maxValue) {
                perc = ((maxValue - value) / maxValue * 100);

                if (Math.abs(perc) < variationPercent) {

                    colorType = 2;

                } else if (Math.abs(perc) > variationPercent) {

                    colorType = 3;
                }

            } else if (value > minValue && value < maxValue) {

                colorType = 1;

            } else {
                colorType = 0;
            }

            return colorType;
        }


        @Override
        public int getItemCount() {
            return patientList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        private void defaultViews(RecyclerViewHolder holder) {
            holder.tvVital.setTextColor(getResources().getColor(R.color.textBrown));
            holder.tvNutrientMinerals.setTextColor(getResources().getColor(R.color.textBrown));
            holder.tvInputOutput.setTextColor(getResources().getColor(R.color.textBrown));
            holder.tvInvestigation.setTextColor(getResources().getColor(R.color.textBrown));
            holder.tvComplication.setTextColor(getResources().getColor(R.color.textBrown));

            holder.tvVital.setBackground(null);
            holder.tvNutrientMinerals.setBackground(null);
            holder.tvInputOutput.setBackground(null);
            holder.tvInvestigation.setBackground(null);
            holder.tvComplication.setBackground(null);
        }

        private void defaultViewsVisibility(RecyclerViewHolder holder) {
            holder.clComplication.setVisibility(View.GONE);
            holder.clInvestigation.setVisibility(View.GONE);
            holder.clInputOutput.setVisibility(View.GONE);
            holder.clNutrientMinerals.setVisibility(View.GONE);
            holder.clVital.setVisibility(View.GONE);
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvPid, tvName, tvWard, tvIpNo, tvAgeGender, tvAdmitDate, tvCity, tvPhone, tvDiagnosis, tvConsultant,
                    tvVital, tvInvestigation, tvNutrientMinerals, tvComplication, tvInputOutput;
            TextView tvBpBox, tvTempBox, tvRespBox, tvHbBox, tvPulseBox, tvUserStatusBox;
            ImageView ivArrow, ivIntakeBox, ivOutputBox;
            ConstraintLayout clSub, clMain, clVital, clNutrientMinerals, clInputOutput, clInvestigation, clComplication;

            NestedScrollView scrollViewVital;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);

                scrollViewVital = itemView.findViewById(R.id.scrollViewVital);

                clSub = itemView.findViewById(R.id.clSub);
                clMain = itemView.findViewById(R.id.clMain);
                clVital = itemView.findViewById(R.id.clVital);
                clNutrientMinerals = itemView.findViewById(R.id.clNutrientMinerals);
                clInputOutput = itemView.findViewById(R.id.clInputOutput);
                clComplication = itemView.findViewById(R.id.clComplication);
                clInvestigation = itemView.findViewById(R.id.clInvestigation);

                tvPid = itemView.findViewById(R.id.tvPid);
                tvName = itemView.findViewById(R.id.tvName);
                tvWard = itemView.findViewById(R.id.tvWard);
                tvNoDataVital = itemView.findViewById(R.id.tvNoDataVital);
                tvNoDataNutrient = itemView.findViewById(R.id.tvNoDataNutrient);
                tvNoDataIpOp = itemView.findViewById(R.id.tvNoDataIpOp);
                tvNoDataInvestigation = itemView.findViewById(R.id.tvNoDataInvestigation);

                tvBpBox = itemView.findViewById(R.id.tvBpBox);
                tvTempBox = itemView.findViewById(R.id.tvTempBox);
                tvRespBox = itemView.findViewById(R.id.tvRespBox);
                tvHbBox = itemView.findViewById(R.id.tvHbBox);
                tvPulseBox = itemView.findViewById(R.id.tvPulseBox);
                ivIntakeBox = itemView.findViewById(R.id.ivIntakeBox);
                ivOutputBox = itemView.findViewById(R.id.ivOutputBox);
                tvUserStatusBox = itemView.findViewById(R.id.tvUserStatusBox);
                ivArrow = itemView.findViewById(R.id.ivArrow);

                tvIpNo = itemView.findViewById(R.id.tvIpNo);
                tvAgeGender = itemView.findViewById(R.id.tvAgeGender);
                tvAdmitDate = itemView.findViewById(R.id.tvAdmitDate);
                tvCity = itemView.findViewById(R.id.tvCity);
                tvPhone = itemView.findViewById(R.id.tvPhone);
                tvDiagnosis = itemView.findViewById(R.id.tvDiagnosis);
                tvConsultant = itemView.findViewById(R.id.tvConsultant);
                tvVital = itemView.findViewById(R.id.tvVital);
                tvNutrientMinerals = itemView.findViewById(R.id.tvNutrientMinerals);
                tvComplication = itemView.findViewById(R.id.tvComplication);
                tvInputOutput = itemView.findViewById(R.id.tvInputOutput);
                tvInvestigation = itemView.findViewById(R.id.tvInvestigation);

                tvVitalName = itemView.findViewById(R.id.tvVitalName);
                tvVitalValue = itemView.findViewById(R.id.tvVitalValue);
                tvVitalDate = itemView.findViewById(R.id.tvVitalDate);
                tvVitalRange = itemView.findViewById(R.id.tvVitalRange);
                tvNutrientName = itemView.findViewById(R.id.tvNutrientName);
                tvNutrientValue = itemView.findViewById(R.id.tvNutrientValue);
                tvNutrientRda = itemView.findViewById(R.id.tvNutrientRda);
                tvNutrientDate = itemView.findViewById(R.id.tvNutrientDate);
                tvIpOpQuantity = itemView.findViewById(R.id.tvIpOpQuantity);
                tvIpOpDate = itemView.findViewById(R.id.tvIpOpDate);

            }
        }
    }
}
