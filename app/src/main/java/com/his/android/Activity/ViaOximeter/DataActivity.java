package com.his.android.Activity.ViaOximeter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.his.android.Activity.BP.BLE.DeviceControlActivity;
import com.his.android.Activity.ScanSelector;
import com.his.android.Model.PatientInfoBarcode;
import com.his.android.Response.PatientBarcodeResp;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.his.android.Activity.ScannerActivity;
import com.his.android.R;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.vphealthy.oximetersdk.OxiOprateManager;
import com.vphealthy.oximetersdk.listener.base.IABleConnectStatusListener;
import com.vphealthy.oximetersdk.listener.base.IABluetoothStateListener;
import com.vphealthy.oximetersdk.listener.base.IBleWriteResponse;
import com.vphealthy.oximetersdk.listener.data.OnACKDataListener;
import com.vphealthy.oximetersdk.listener.data.OnDetectDataListener;
import com.vphealthy.oximetersdk.model.data.AckData;
import com.vphealthy.oximetersdk.model.data.DetectData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends BaseActivity {
    private final static String TAG = DataActivity.class.getSimpleName();
    TextView device_address, btnScan, btnGetData, btnSaveData, connectionState, txtSpo2, txtPulse, txtPi, txtHrv;
    EditText txtId;
    static String spo2, pulse, pi, hrv;
    LinearLayout ll;

    private IBleWriteResponse getBleWriteResponse() {
        return state -> {
            if (state == Code.REQUEST_SUCCESS) Log.i(TAG, "write success");
            else Log.i(TAG, "write fail");
        };
    }

    String mac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);

        init();
    }

    private void init() {
        mac = getIntent().getStringExtra("mac");
        listenState(mac);
        device_address = findViewById(R.id.device_address);
        btnScan = findViewById(R.id.btnScan);
        txtId = findViewById(R.id.txtId);
        btnGetData = findViewById(R.id.btnGetData);
        btnSaveData = findViewById(R.id.btnSaveData);
        connectionState = findViewById(R.id.connection_state);
        txtPulse = findViewById(R.id.txtPulse);
        txtSpo2 = findViewById(R.id.txtSpo2);
        txtPi = findViewById(R.id.txtPi);
        txtHrv = findViewById(R.id.txtHrv);
        ll = findViewById(R.id.ll);
        device_address.setText(mac);

        btnScan.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, ScannerActivity.class);
            intent1.putExtra("redi", "7");
            intent1.putExtra("mac", mac);
            startActivity(intent1);
            finish();
        });

        if (getIntent().getStringExtra("status") != null) {
            txtId.setText(String.valueOf(SharedPrefManager.getInstance(DataActivity.this).getPid()));
        }
        txtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>6){
                    Utils.showRequestDialog(DataActivity.this);
                    Call<PatientBarcodeResp> call = RetrofitClient.getInstance().getApi().getPatientDetailByBarcode(SharedPrefManager.getInstance(DataActivity.this).getUser().getAccessToken(), SharedPrefManager.getInstance(DataActivity.this).getUser().getUserid().toString(), txtId.getText().toString().trim());
                    call.enqueue(new Callback<PatientBarcodeResp>() {
                        @Override
                        public void onResponse(Call<PatientBarcodeResp> call, Response<PatientBarcodeResp> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getPatientInfo().size() > 0) {
                                    PatientInfoBarcode patientInfo = response.body().getPatientInfo().get(0);
                                    ScannerActivity.patientInfo = patientInfo;
                                    SharedPrefManager.getInstance(DataActivity.this).setScanned(true);
                                    SharedPrefManager.getInstance(DataActivity.this).setPid(patientInfo.getPid());
                                    SharedPrefManager.getInstance(DataActivity.this).setIpNo(patientInfo.getIpNo());
                                    SharedPrefManager.getInstance(DataActivity.this).setPmId(patientInfo.getPmID());
                                    SharedPrefManager.getInstance(DataActivity.this).setPtName(patientInfo.getPatientName());
                                    SharedPrefManager.getInstance(DataActivity.this).setCr(patientInfo.getCrNo());
                                    SharedPrefManager.getInstance(DataActivity.this).setSubdeptID(patientInfo.getAdmitSubDepartmentID());
                                    SharedPrefManager.getInstance(DataActivity.this).setHeadID(patientInfo.getHeadId(), "", "");
                                }
                            } else {
                                try {
                                    Toast.makeText(DataActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Utils.hideDialog();
                        }

                        @Override
                        public void onFailure(Call<PatientBarcodeResp> call, Throwable t) {
                            Utils.hideDialog();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnGetData.setOnClickListener(view -> start_recevice_data());

        btnSaveData.setOnClickListener(view -> {
//            displayData(spo2, pulse);
            JSONArray dtTableArray = new JSONArray();
            try {
                if (pulse != null && !pulse.equals("0")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("vmID", "206");
                    jsonObject1.put("vmValue", pulse);
                    dtTableArray.put(jsonObject1);
                }

                if (spo2 != null && !spo2.equals("0")) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("vmID", "56");
                    jsonObject2.put("vmValue", spo2);
                    dtTableArray.put(jsonObject2);
                }

                if (pi != null && !pi.equals("0")) {
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("vmID", "203");
                    jsonObject3.put("vmValue", pi);
                    dtTableArray.put(jsonObject3);
                }

                if (hrv != null && !hrv.equals("0")) {
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("vmID", "204");
                    jsonObject4.put("vmValue", hrv);
                    dtTableArray.put(jsonObject4);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (ConnectivityChecker.checker(mActivity)) {
                    saveBluetoothVital(dtTableArray);
                } else {
                    Toast.makeText(mActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getIntent().getStringExtra("show") != null) {
            if (getIntent().getStringExtra("show").equalsIgnoreCase("0")) {
                ll.setVisibility(View.GONE);
            } else ll.setVisibility(View.VISIBLE);
        }
    }

    private void listenState(String mac) {

        OxiOprateManager.getMangerInstance(getApplicationContext()).registerBluetoothStateListener(new IABluetoothStateListener() {
            @Override
            public void onBluetoothStateChanged(boolean isOpen) {
                String msg;
                if (isOpen) {
//                    msg = getString(R.string.sys_blue_state_open);
                } else {
//                    msg = getString(R.string.sys_blue_state_close);
                }
//                Log.i(TAG, msg);
//                mSystemBlueInfoTv.setText(msg);
            }
        });


        OxiOprateManager.getMangerInstance(getApplicationContext()).registerConnectStatusListener(mac, new IABleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int code) {
                if (code == Constants.STATUS_CONNECTED) {
                    String connectStr = getString(R.string.connected);
                    Log.i(TAG, connectStr);
                    connectionState.setText(connectStr);
//                    start_recevice_data();
                } else {
                    String unConnectStr = getString(R.string.disconnected);
                    Log.i(TAG, unConnectStr);
                    connectionState.setText(unConnectStr);
                    txtPulse.setText(getString(R.string.no_data));
                    txtSpo2.setText(getString(R.string.no_data));
                    txtHrv.setText(getString(R.string.no_data));
                    txtPi.setText(getString(R.string.no_data));
                }
            }
        });
    }

    /*@OnClick(R.id.oprate_readbase_info)
    public void readBasenfo() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).readDeviceBaseInfo(getBleWriteResponse(), new OnDeviceDataListener() {
            @Override
            public void onDataChange(DeviceData deviceData) {
                Log.i(TAG, deviceData.toString());
                mResultBaseinfoTv.setText(getString(R.string.base_info) + ":" + deviceData.toString());
            }
        });
    }


    @OnClick(R.id.oprate_readbattery_info)
    public void readBatterynfo() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).readBatterInfo(getBleWriteResponse(), new OnBatteryDataListener() {
            @Override
            public void onDataChange(BatteryData batteryData) {
                Log.i(TAG, batteryData.toString());
                mResultBatteryinfoTv.setText(getString(R.string.battery_info) + ":" + batteryData.toString());
            }
        });
    }*/

    //    @OnClick(R.id.start_recevice_data)
    public void start_recevice_data() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).startListenTestData(getBleWriteResponse(), new OnACKDataListener() {
            @Override
            public void onDataChange(AckData ackData) {
                listenDetectResult();
//                mReceviceDataTv.setText(getString(R.string.start_recevice_data) + ":" + ackData.toString());
            }
        });
    }

    /*@OnClick(R.id.stop_recevice_data)
    public void stop_recevice_data() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).stopListenTestData(getBleWriteResponse(), new OnACKDataListener() {
            @Override
            public void onDataChange(AckData ackData) {
                mReceviceDataTv.setText(getString(R.string.stop_recevice_data) + ":" + ackData.toString());
            }
        });
    }

    @OnClick(R.id.get_curve_data)
    public void getCurveData() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).setLightDataCallBack(true, new OnAdcDataListener() {
            @Override
            public void onDataChange(AdcCurveData adcCurveData) {
                mDetectCurveTv.setText(getString(R.string.curve_result) + ":" + adcCurveData.toString());
            }
        });
    }*/

    public void listenDetectResult() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).setOnDetectDataListener(detectData -> {
            txtSpo2.setText(String.valueOf(detectData.getSpo2h()));
            txtPulse.setText(String.valueOf(detectData.getHeart()));

            pulse = String.valueOf(detectData.getHeart());
            spo2 = String.valueOf(detectData.getSpo2h());
            pi = String.valueOf(detectData.getPerfusionIndex());
            hrv = String.valueOf(detectData.getHrv());
            txtPi.setText(String.valueOf(detectData.getPerfusionIndex()));
            txtHrv.setText(String.valueOf(detectData.getHrv()));
        });
    }

    public void disconnectDevice() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).disconnectWatch(mac);
        finish();
    }

    @Override
    public void onBackPressed() {
        disconnectDevice();
    }

    public void saveBluetoothVital(JSONArray dt) {
        Utils.showRequestDialog(DataActivity.this);
        Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");
        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PID", txtId.getText().toString().trim());
            jsonObject.put("headID", SharedPrefManager.getInstance(DataActivity.this).getHeadID().toString());
            jsonObject.put("entryDate", format.format(today));
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(DataActivity.this).getSubdeptID().toString());
            jsonObject.put("isFinalDiagnosis", false);
            jsonObject.put("isMachine", "1");
            jsonObject.put("ipNo", SharedPrefManager.getInstance(DataActivity.this).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(DataActivity.this).getUser().getUserid());
            jsonObject.put("consultantName", SharedPrefManager.getInstance(DataActivity.this).getUser().getUserid());
            jsonObject.put("vitals", dt);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/saveVitals")
                .addHeaders("accessToken", SharedPrefManager.getInstance(DataActivity.this).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(DataActivity.this).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DataActivity.this, "Vitals saved successfully", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Toast.makeText(DataActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }
}