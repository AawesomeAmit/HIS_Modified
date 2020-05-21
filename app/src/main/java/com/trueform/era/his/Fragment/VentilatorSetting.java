package com.trueform.era.his.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Model.VentilatorData;
import com.trueform.era.his.Model.VentilatorList;
import com.trueform.era.his.Model.VentilatorSettingList;
import com.trueform.era.his.Model.VentilatorSettingResp;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.VentilatorDataResp;
import com.trueform.era.his.Response.VentilatorListResp;
import com.trueform.era.his.Response.VentilatorSettingDetailResp;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VentilatorSetting extends BaseFragment {
    Spinner spnVentilator;
    EditText edtPressure, edtVolume, edtBreathRate;
    List<VentilatorList> ventilatorList;
    ArrayAdapter<VentilatorList> ventilatorListAdp;
    TextView btnSave;
    RecyclerView rvVentilator;
    String machineID;
    public static VentilatorSetting newInstance(String param1, String param2) {
        VentilatorSetting fragment = new VentilatorSetting();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ventilator_setting, container, false);
        spnVentilator = view.findViewById(R.id.spnVentilator);
        edtPressure = view.findViewById(R.id.edtPressure);
        edtVolume = view.findViewById(R.id.edtVolume);
        btnSave = view.findViewById(R.id.btnSave);
        rvVentilator = view.findViewById(R.id.rvVentilator);
        rvVentilator.setLayoutManager(new LinearLayoutManager(mActivity));
        edtBreathRate = view.findViewById(R.id.edtBreathRate);
        ventilatorList=new ArrayList<>();
        ventilatorList.add(new VentilatorList(0, "Select Ventilator"));
        bindList();
        bind();
        spnVentilator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    machineID = ventilatorList.get(i).getId().toString();
                    spinnerSelected(machineID);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSave.setOnClickListener(view1 -> {
            if(spnVentilator.getSelectedItemPosition()!=0) {
                Utils.showRequestDialog(mActivity);
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveVentilatorSetting(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), machineID, edtPressure.getText().toString().trim(), edtVolume.getText().toString().trim(), edtBreathRate.getText().toString().trim(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            bind();
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            }else Toast.makeText(mActivity, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    private void spinnerSelected(String machineID) {
        Utils.showRequestDialog(mActivity);
        Call<VentilatorSettingDetailResp> call = RetrofitClient.getInstance().getApi().getVentilatorSettingByMachineID(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), machineID);
        call.enqueue(new Callback<VentilatorSettingDetailResp>() {
            @Override
            public void onResponse(Call<VentilatorSettingDetailResp> call, Response<VentilatorSettingDetailResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getVentilatorSettingDetails().size() > 0) {
                            edtPressure.setText(String.valueOf(response.body().getVentilatorSettingDetails().get(0).getPressure()));
                            edtVolume.setText(String.valueOf(response.body().getVentilatorSettingDetails().get(0).getVolume()));
                            edtBreathRate.setText(String.valueOf(response.body().getVentilatorSettingDetails().get(0).getBreathRate()));
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VentilatorSettingDetailResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bindList() {
        Utils.showRequestDialog(mActivity);
        Call<VentilatorListResp> call = RetrofitClient.getInstance().getApi().getVentilatorList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<VentilatorListResp>() {
            @Override
            public void onResponse(Call<VentilatorListResp> call, Response<VentilatorListResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        ventilatorList.addAll(1, response.body().getVentilatorList());
                        ventilatorListAdp = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, ventilatorList);
                    }
                }
                spnVentilator.setAdapter(ventilatorListAdp);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VentilatorListResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bind(){
        Utils.showRequestDialog(mActivity);
        Call<VentilatorSettingResp> call = RetrofitClient.getInstance().getApi().getVentilatorSetting(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<VentilatorSettingResp>() {
            @Override
            public void onResponse(Call<VentilatorSettingResp> call, Response<VentilatorSettingResp> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        rvVentilator.setAdapter(new VentilatorAdp(response.body().getVentilatorSettingList()));
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VentilatorSettingResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private void bindPopup(String machineID){
        Utils.showRequestDialog(mActivity);
        Call<VentilatorDataResp> call = RetrofitClient.getInstance().getApi().getVentilatorData(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), machineID);
        call.enqueue(new Callback<VentilatorDataResp>() {
            @Override
            public void onResponse(Call<VentilatorDataResp> call, Response<VentilatorDataResp> response) {
                if(response.isSuccessful()){
                    View popupView = getLayoutInflater().inflate(R.layout.popup_ventilator, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                    LinearLayout lLayout = popupView.findViewById(R.id.lLayout);
                    RecyclerView rvVentilatorData = popupView.findViewById(R.id.rvVentilatorData);
                    rvVentilatorData.setLayoutManager(new LinearLayoutManager(mActivity));
                    rvVentilatorData.setAdapter(new VentilatorPopupAdp(response.body().getVentilatorData()));
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    int[] location = new int[2];
                    lLayout.getLocationOnScreen(location);
                    popupWindow.showAtLocation(lLayout, Gravity.CENTER, 0, 0);
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<VentilatorDataResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    private class VentilatorAdp extends RecyclerView.Adapter<VentilatorAdp.ViewHolder> {
        private List<VentilatorSettingList> ventilatorSettingLists;
        VentilatorAdp(List<VentilatorSettingList> ventilatorSettingLists) {
            this.ventilatorSettingLists = ventilatorSettingLists;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inflate_ventilator, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.txtVentilator.setText(ventilatorSettingLists.get(i).getMachineName());
            viewHolder.txtPressure.setText(String.valueOf(ventilatorSettingLists.get(i).getPressure()));
            viewHolder.txtVolume.setText(String.valueOf(ventilatorSettingLists.get(i).getVolume()));
            viewHolder.txtBreath.setText(String.valueOf(ventilatorSettingLists.get(i).getBreathRate()));
            viewHolder.rowLayout.setOnClickListener(view -> {
                bindPopup(String.valueOf(ventilatorSettingLists.get(i).getMachineID()));
            });
        }

        @Override
        public int getItemCount() {
            return ventilatorSettingLists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtVentilator, txtPressure, txtVolume, txtBreath;
            LinearLayout rowLayout;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtVentilator = itemView.findViewById(R.id.txtVentilator);
                txtPressure = itemView.findViewById(R.id.txtPressure);
                txtVolume = itemView.findViewById(R.id.txtVolume);
                txtBreath = itemView.findViewById(R.id.txtBreath);
                rowLayout = itemView.findViewById(R.id.rowLayout);
            }
        }
    }
    private class VentilatorPopupAdp extends RecyclerView.Adapter<VentilatorPopupAdp.ViewHolder> {
        private List<VentilatorData> ventilatorData;
        VentilatorPopupAdp(List<VentilatorData> ventilatorData) {
            this.ventilatorData = ventilatorData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.inflate_popup_ventilator, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.txtDate.setText(ventilatorData.get(i).getDateTime());
            viewHolder.txtPressure.setText(String.valueOf(ventilatorData.get(i).getPressure()));
            viewHolder.txtVolume.setText(String.valueOf(ventilatorData.get(i).getVolume()));
            viewHolder.txtBreath.setText(String.valueOf(ventilatorData.get(i).getBreathRate()));
            viewHolder.txtTemp.setText(String.valueOf(ventilatorData.get(i).getTemperature()));
        }

        @Override
        public int getItemCount() {
            return ventilatorData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtDate, txtPressure, txtVolume, txtBreath, txtTemp;
            LinearLayout rowLayout;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtPressure = itemView.findViewById(R.id.txtPressure);
                txtVolume = itemView.findViewById(R.id.txtVolume);
                txtBreath = itemView.findViewById(R.id.txtBreath);
                rowLayout = itemView.findViewById(R.id.rowLayout);
                txtTemp = itemView.findViewById(R.id.txtTemp);
            }
        }
    }
}
