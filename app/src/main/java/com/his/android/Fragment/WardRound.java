package com.his.android.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VoiceData;
import com.his.android.R;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseFragment;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WardRound extends BaseFragment {
    EditText edtBpSys, edtBpDias, edtPulse, edtFlowRate, edtFio2, edtPeep, edtVentilatorRr, edtTidal, edtInfusionRate, edtSpo2, edtTemp, edtRespRate;
    TextView btnAdd, btnSave;
    Spinner spnInfusion, spnO2SupportType, spnPositionType;
    RecyclerView rvInfusion;
    private List<Item> positionList;
    private List<Item> o2SupportList;
    private List<Item> infusionList;
    private List<InfusionNorad> infusionNoradList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ward_round, container, false);
        edtBpSys=view.findViewById(R.id.edtBpSys);
        edtBpDias=view.findViewById(R.id.edtBpDias);
        edtPulse=view.findViewById(R.id.edtPulse);
        edtFlowRate=view.findViewById(R.id.edtFlowRate);
        edtFio2=view.findViewById(R.id.edtFio2);
        edtPeep=view.findViewById(R.id.edtPeep);
        edtVentilatorRr=view.findViewById(R.id.edtVentilatorRr);
        edtTidal=view.findViewById(R.id.edtTidal);
        edtInfusionRate=view.findViewById(R.id.edtInfusionRate);
        spnInfusion=view.findViewById(R.id.spnInfusion);
        spnO2SupportType =view.findViewById(R.id.spnO2SupportType);
        spnPositionType=view.findViewById(R.id.spnPositionType);
        edtSpo2=view.findViewById(R.id.edtSpo2);
        edtTemp=view.findViewById(R.id.edtTemp);
        edtRespRate=view.findViewById(R.id.edtRespRate);
        btnAdd=view.findViewById(R.id.btnAdd);
        btnSave=view.findViewById(R.id.btnSave);
        rvInfusion=view.findViewById(R.id.rvInfusion);
        rvInfusion.setLayoutManager(new LinearLayoutManager(mActivity));
        positionList=new ArrayList<>();
        o2SupportList=new ArrayList<>();
        infusionList=new ArrayList<>();
        infusionNoradList=new ArrayList<>();
        positionList.add(0, new Item("-Select Position Type-", 0));
        positionList.add(1, new Item("Sitting", 1));
        positionList.add(2, new Item("Supine", 2));
        positionList.add(3, new Item("Prone", 3));
        positionList.add(4, new Item("Left Lateral", 4));
        positionList.add(5, new Item("Right Lateral", 5));
        positionList.add(6, new Item("Fowler Position", 6));
        o2SupportList.add(0, new Item("-Select Life Support-", 0));
        o2SupportList.add(1, new Item("BIPAP", 3));
        o2SupportList.add(2, new Item("HFNC", 4));
        o2SupportList.add(3, new Item("Nasal Pronge", 1));
        o2SupportList.add(4, new Item("NRM", 5));
        o2SupportList.add(5, new Item("O2 Mask", 2));
        o2SupportList.add(6, new Item("Room Air", 26));
        o2SupportList.add(7, new Item("NIV", 24));
        o2SupportList.add(8, new Item("MV", 25));
        infusionList.add(0, new Item("-Select Infusion-", 0));
        infusionList.add(1, new Item("Fenta", 79));
        infusionList.add(2, new Item("Midaz", 419));
        infusionList.add(3, new Item("Norad", 534));
        ArrayAdapter<Item> positionAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, positionList);
        ArrayAdapter<Item> o2SupportAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, o2SupportList);
        ArrayAdapter<Item> infusionAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, infusionList);
        spnPositionType.setAdapter(positionAdp);
        spnO2SupportType.setAdapter(o2SupportAdp);
        spnInfusion.setAdapter(infusionAdp);
        spnO2SupportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(false);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==1){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(true);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==2){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(true);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==3){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(false);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==4){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(false);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==5){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(false);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==6){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(false);
                    edtFio2.setText("");
                    edtFio2.setEnabled(false);
                    edtPeep.setText("");
                    edtPeep.setEnabled(false);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(false);
                    edtTidal.setText("");
                    edtTidal.setEnabled(false);
                } else if (i==7){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(true);
                    edtPeep.setText("");
                    edtPeep.setEnabled(true);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(true);
                    edtTidal.setText("");
                    edtTidal.setEnabled(true);
                } else if (i==8){
                    edtFlowRate.setText("");
                    edtFlowRate.setEnabled(true);
                    edtFio2.setText("");
                    edtFio2.setEnabled(true);
                    edtPeep.setText("");
                    edtPeep.setEnabled(true);
                    edtVentilatorRr.setText("");
                    edtVentilatorRr.setEnabled(true);
                    edtTidal.setText("");
                    edtTidal.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSave.setOnClickListener(view1 -> {
            Utils.showRequestDialog(mActivity);
            Gson gson = new Gson();
            Call<ResponseBody> call= RetrofitClient.getInstance().getApi().saveWardRoundForm(
                    SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                    String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                    edtBpSys.getText().toString().trim(),
                    edtBpDias.getText().toString().trim(),
                    edtPulse.getText().toString().trim(),
                    edtRespRate.getText().toString().trim(),
                    edtTemp.getText().toString().trim(),
                    edtSpo2.getText().toString().trim(),
                    positionList.get(spnPositionType.getSelectedItemPosition()).getValue(),
                    o2SupportList.get(spnO2SupportType.getSelectedItemPosition()).getId(),
                    edtFlowRate.getText().toString().trim(),
                    edtFio2.getText().toString().trim(),
                    edtPeep.getText().toString().trim(),
                    edtTidal.getText().toString().trim(),
                    SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(mActivity).getHeadID(),
                    gson.toJson(infusionNoradList));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(mActivity, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        });
        btnAdd.setOnClickListener(view1 -> {
            if (spnInfusion.getSelectedItemPosition()==0)return;
            if (edtInfusionRate.getText().toString().isEmpty())return;
            infusionNoradList.add(infusionNoradList.size(), new InfusionNorad(infusionList.get(spnInfusion.getSelectedItemPosition()).getId(), infusionList.get(spnInfusion.getSelectedItemPosition()).getValue(), edtInfusionRate.getText().toString().trim()));
            edtInfusionRate.setText("");
            spnInfusion.setSelection(0);
            rvInfusion.setAdapter(new InfusionDetailAdp(infusionNoradList));
        });
        return view;
    }
    private class InfusionDetailAdp extends RecyclerView.Adapter<InfusionDetailAdp.RecyclerViewHolder>{
        List<InfusionNorad> infusionNoradList;

        public InfusionDetailAdp(List<InfusionNorad> infusionNoradList) {
            this.infusionNoradList = infusionNoradList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.inner_infusion_ward_round, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            holder.txtType.setText(infusionNoradList.get(position).getDrugName());
            holder.txtRate.setText(infusionNoradList.get(position).getDoseStrength());
            holder.btnDelete.setOnClickListener(view -> {
                infusionNoradList.remove(position);
                rvInfusion.setAdapter(new InfusionDetailAdp(infusionNoradList));
            });
        }

        @Override
        public int getItemCount() {
            return infusionNoradList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView txtType, txtRate, btnDelete;
            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                txtType=itemView.findViewById(R.id.txtType);
                txtRate=itemView.findViewById(R.id.txtRate);
                btnDelete=itemView.findViewById(R.id.btnDelete);
            }
        }
    }
    private class InfusionNorad {
        @SerializedName("drugID")
        @Expose
        public Integer drugID;
        @SerializedName("drugName")
        @Expose
        public String drugName;
        @SerializedName("doseStrength")
        @Expose
        public String doseStrength;

        public InfusionNorad(Integer drugID, String drugName, String doseStrength) {
            this.drugID = drugID;
            this.drugName = drugName;
            this.doseStrength = doseStrength;
        }

        public Integer getDrugID() {
            return drugID;
        }

        public void setDrugID(Integer drugID) {
            this.drugID = drugID;
        }

        public String getDrugName() {
            return drugName;
        }

        public void setDrugName(String drugName) {
            this.drugName = drugName;
        }

        public String getDoseStrength() {
            return doseStrength;
        }

        public void setDoseStrength(String doseStrength) {
            this.doseStrength = doseStrength;
        }
    }
    private class Item {
        String value;
        int id;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return value;
        }

        public Item(String value, int id) {
            this.value = value;
            this.id = id;
        }
    }
}