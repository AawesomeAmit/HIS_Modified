package com.his.android.Activity.BP.Medcheck;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.MedCheckActivity;
import com.getmedcheck.lib.constant.Constants;
import com.getmedcheck.lib.events.EventClearCommand;
import com.getmedcheck.lib.events.EventReadingProgress;
import com.getmedcheck.lib.model.BleDevice;
import com.getmedcheck.lib.model.BloodGlucoseData;
import com.getmedcheck.lib.model.BloodPressureData;
import com.getmedcheck.lib.model.IDeviceData;
import com.getmedcheck.lib.utils.StringUtils;
import com.his.android.Activity.ScannerActivity;
import com.his.android.R;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DeviceConnectionActivity extends MedCheckActivity implements View.OnClickListener {
    private Date today = new Date();
    public static void start(Context context, BleDevice bleDevice) {
        Intent starter = new Intent(context, DeviceConnectionActivity.class);
        starter.putExtra("DATA", bleDevice);
        context.startActivity(starter);
    }

    private BleDevice mBleDevice;
    private TextView mTvDeviceName, txtPid, txtSys, txtDias, txtPulse, btnSaveData, btnScan;
    private TextView mTvConnectionState;
    private Button mBtnConnect, mBtnReadData, mBtnClearData, mBtnTimeSync, mBtnDisconnect;
    private LinearLayout mLlCoreOperations, mLlBLEDeviceOperation;
    private TextView mTvResult;
    private boolean mAllPermissionsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_connection);

        if (getIntent() != null && getIntent().hasExtra("DATA")) {
            mBleDevice = getIntent().getParcelableExtra("DATA");
            connectDevice();
        }
        initView();
        requestLocationPermission();
        if (mAllPermissionsReady) {
            checkDeviceOnline();
        } else {
            Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (mBleDevice != null && !TextUtils.isEmpty(mBleDevice.getDeviceName())) {
                getSupportActionBar().setTitle(mBleDevice.getDeviceName());
            } else {
                getSupportActionBar().setTitle("Device Connection");
            }
        }

        mTvDeviceName = findViewById(R.id.tvDeviceName);
        mTvConnectionState = findViewById(R.id.tvStatus);
        mLlCoreOperations = findViewById(R.id.llCoreOperations);
        mLlBLEDeviceOperation = findViewById(R.id.llBLEDeviceOperation);
        txtPid = findViewById(R.id.txtPid);
        if(getIntent().hasExtra("status")) txtPid.setText(String.valueOf(SharedPrefManager.getInstance(DeviceConnectionActivity.this).getPid()));
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
        mBtnConnect = findViewById(R.id.btnConnect);
        mBtnConnect.setOnClickListener(this);
        mBtnReadData = findViewById(R.id.btnReadData);
        mBtnReadData.setOnClickListener(this);
        mBtnClearData = findViewById(R.id.btnClearData);
        mBtnClearData.setOnClickListener(this);
        mBtnTimeSync = findViewById(R.id.btnTimeSync);
        mBtnTimeSync.setOnClickListener(this);
        mBtnDisconnect = findViewById(R.id.btnDisconnect);
        mBtnDisconnect.setOnClickListener(this);
        btnSaveData = findViewById(R.id.btnSaveData);
        btnSaveData.setOnClickListener(this);

        mTvResult = findViewById(R.id.tvResult);
        txtSys = findViewById(R.id.txtSys);
        txtDias = findViewById(R.id.txtDias);
        txtPulse = findViewById(R.id.txtPulse);

        if (mBleDevice != null) {
            mTvDeviceName.setText(mBleDevice.getDeviceName());
        }

        registerCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPermissionGrantedAndBluetoothOn() {
        mAllPermissionsReady = true;
        mLlCoreOperations.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDeviceClearCommand(int state) {
        super.onDeviceClearCommand(state);
        switch (state) {
            case EventClearCommand.CLEAR_START:
                mTvResult.setText("Clear Start");
                break;
            case EventClearCommand.CLEAR_COMPLETE:
                mTvResult.setText("Clear Successfully Completed");
                break;
            case EventClearCommand.CLEAR_FAIL:
                mTvResult.setText("Clear Fail");
                break;
        }
    }

    @Override
    protected void onDeviceConnectionStateChange(BleDevice bleDevice, int status) {
        super.onDeviceConnectionStateChange(bleDevice, status);
        if (bleDevice.getMacAddress().equals(mBleDevice.getMacAddress()) && status == 1) {
//            mLlBLEDeviceOperation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDeviceDataReadingStateChange(int state, String message) {
        super.onDeviceDataReadingStateChange(state, message);
        mTvConnectionState.setText(message);
        mBtnConnect.setEnabled(!(state == EventReadingProgress.COMPLETED));
    }

    @Override
    protected void onDeviceDataReceive(BluetoothDevice device, ArrayList<IDeviceData> deviceData, String json, String deviceType) {
        super.onDeviceDataReceive(device, deviceData, json, deviceType);
        if (deviceData == null) {
            return;
        }

        Log.e("MedcheckJson", "onDeviceDataReceive: "+ json );

        if (deviceData.size() == 0) {
            mTvResult.setText("No Data Found!");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ").append(deviceType).append("\n\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

        for (IDeviceData deviceDatum : deviceData) {

            if (deviceDatum.getType().equals(Constants.TYPE_BPM)) {
                BloodPressureData bloodPressureData = (BloodPressureData) deviceDatum;

                stringBuilder.append("SYS: ").append(bloodPressureData.getSystolic()).append(" mmHg, ");
                stringBuilder.append("DIA: ").append(bloodPressureData.getDiastolic()).append(" mmHg, ");
                stringBuilder.append("PUL: ").append(bloodPressureData.getHeartRate()).append(" min\n");
                stringBuilder.append("IHB: ").append(bloodPressureData.getIHB()).append(", ");
                stringBuilder.append("DATE: ").append(sdf.format(bloodPressureData.getDateTime()));
                stringBuilder.append("\n------------------------\n");

            } else if (deviceDatum.getType().equals(Constants.TYPE_BGM)) {
                BloodGlucoseData bloodGlucoseData = (BloodGlucoseData) deviceDatum;

                DecimalFormat df = new DecimalFormat("0.0");
                float val = 0;
                if (StringUtils.isNumber(bloodGlucoseData.getHigh())) {
                    val = Float.parseFloat(bloodGlucoseData.getHigh()) / 18f;
                }

                stringBuilder.append(df.format(val)).append(" mmol/L (").append(bloodGlucoseData.getHigh()).append(" mg/dL)\n");
                stringBuilder.append(bloodGlucoseData.getAcPcStringValue()).append("\n");
                stringBuilder.append("DATE: ").append(sdf.format(bloodGlucoseData.getDateTime()));
                stringBuilder.append("\n------------------------\n");
            }
        }

        mTvResult.setText(stringBuilder.toString());
        BloodPressureData bloodPressureData = (BloodPressureData) deviceData.get(deviceData.size()-1);
        txtSys.setText(bloodPressureData.getSystolic());
        txtDias.setText(bloodPressureData.getDiastolic());
        txtPulse.setText(bloodPressureData.getHeartRate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                if (mAllPermissionsReady) {
                    checkDeviceOnline();
                } else {
                    Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnReadData:
                readData();
                break;
            case R.id.btnClearData:
                clearData();
                break;
            case R.id.btnTimeSync:
                timeSync();
                break;
            case R.id.btnDisconnect:
                disconnectDevice();
                break;
            case R.id.btnSaveData:
                saveBluetoothVital();
                break;
            case R.id.btnScan:
                Intent intent1=new Intent(DeviceConnectionActivity.this, ScannerActivity.class);
                intent1.putExtra("redi", "6");
                intent1.putExtra("DATA", (BleDevice) getIntent().getParcelableExtra("DATA"));
                startActivity(intent1);
                finish();
                break;
        }
    }

    public void saveBluetoothVital() {
        if (ConnectivityChecker.checker(DeviceConnectionActivity.this)) {
            Utils.showRequestDialog(DeviceConnectionActivity.this);
            JSONArray dtTableArray = new JSONArray();
            if (getIntent().getStringExtra("status1") != null) {
                onBackPressed();
            } else {
                try {
                    if ((!txtSys.getText().toString().trim().equalsIgnoreCase("No data")) && (!txtDias.getText().toString().trim().equalsIgnoreCase("No data")) && (!txtPulse.getText().toString().trim().equalsIgnoreCase("No data"))) {
                        try {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("vmID", 3);
                            jsonObject1.put("vmValue", txtPulse.getText().toString().trim());
                            dtTableArray.put(jsonObject1);
                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put("vmID", 6);
                            jsonObject2.put("vmValue", txtDias.getText().toString().trim());
                            dtTableArray.put(jsonObject2);
                            JSONObject jsonObject3 = new JSONObject();
                            jsonObject3.put("vmID", 4);
                            jsonObject3.put("vmValue", txtSys.getText().toString().trim());
                            dtTableArray.put(jsonObject3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(DeviceConnectionActivity.this, "Error in data.\nPlease retest", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("PID", txtPid.getText().toString().trim());
                jsonObject.put("headID", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getHeadID());
                jsonObject.put("entryDate", format.format(today));
                jsonObject.put("subDeptID", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getSubdeptID());
                jsonObject.put("isFinalDiagnosis", false);
                jsonObject.put("isMachine", "1");
                jsonObject.put("ipNo", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getIpNo());
                jsonObject.put("userID", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getUser().getUserid());
                jsonObject.put("consultantName", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getUser().getUserid());
                jsonObject.put("vitals", dtTableArray);
                Log.v("hitApiArr", String.valueOf(jsonObject));
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/saveVitals")
                    .addHeaders("accessToken", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getUser().getAccessToken())
                    .addHeaders("userID", SharedPrefManager.getInstance(DeviceConnectionActivity.this).getUser().getUserid().toString())
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(DeviceConnectionActivity.this, "Vitals saved successfully", Toast.LENGTH_SHORT).show();
//                        bind();
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
                            Toast.makeText(DeviceConnectionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                            Utils.hideDialog();
                        }
                    });
        } else {
            Toast.makeText(DeviceConnectionActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDeviceScanResult(no.nordicsemi.android.support.v18.scanner.ScanResult scanResult) {
        super.onDeviceScanResult(scanResult);
    }


    private void connectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            MedCheck.getInstance().connect(this, mBleDevice.getMacAddress());
        }
    }

    private void checkDeviceOnline() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            connectDevice();
        }
    }

    private void readData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().writeCommand(this, mBleDevice.getMacAddress());
    }

    private void clearData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().clearDevice(this, mBleDevice.getMacAddress());
    }

    private void timeSync() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().timeSyncDevice(this, mBleDevice.getMacAddress());
    }

    private void disconnectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().disconnectDevice(this);
    }
}
