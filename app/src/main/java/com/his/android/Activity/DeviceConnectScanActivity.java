package com.his.android.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.getmedcheck.lib.model.BleDevice;
import com.google.zxing.Result;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.his.android.Activity.BP.Initial;
import com.his.android.Activity.BP.Medcheck.DeviceConnectionActivity;
import com.his.android.Activity.ViaOximeter.DataActivity;
import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;
import com.vphealthy.oximetersdk.OxiOprateManager;
import com.vphealthy.oximetersdk.listener.base.IABleConnectStatusListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DeviceConnectScanActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        int currentapiVersion = Build.VERSION.SDK_INT;
//        if (currentapiVersion >= Build.VERSION_CODES.M)
        {
            if (!checkPermission()) {
                //Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                requestPermission();
            } else {
//                requestPermission();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        try {
            Intent intent = null;
            String[] split = rawResult.getText().trim().split("\\*");

            String macAddress = split[0];
            String name = split[1];

            if (getIntent().getStringExtra("type") != null) {
                if (getIntent().getStringExtra("type").equals("BP")) {
                    if (SharedPrefManager.getInstance(mActivity).getBpMachine() == 0) {
                        Intent intent1 = new Intent(this, DeviceConnectionActivity.class);
                        intent1.putExtra("DATA", (BleDevice) getIntent().getParcelableExtra("DATA"));
                        BleDevice bleDevice = new BleDevice();
                        bleDevice.setMacAddress(macAddress);
                        bleDevice.setDeviceName(name);
                        intent1.putExtra("DATA", bleDevice);
                        startActivity(intent1);
                    } else if (SharedPrefManager.getInstance(mActivity).getBpMachine() == 1) {
                        Intent intent1 = new Intent(this, Initial.class);
                        /*intent1.putExtra("DEVICE_NAME", name);
                        intent1.putExtra("DEVICE_ADDRESS", macAddress);*/
                        intent1.putExtra("view", "1").putExtra("mac", macAddress);
                        startActivity(intent1);
                    } else if (SharedPrefManager.getInstance(mActivity).getBpMachine() == 2) {
                        startActivity(new Intent(mActivity, com.his.android.Activity.BP.BLE.DeviceControlActivity.class).putExtra("DEVICE_NAME", name).putExtra("DEVICE_ADDRESS", macAddress));
                    }
                } else {
                    if (SharedPrefManager.getInstance(mActivity).getOximeter() == 0) {
                        intent = new Intent(this, DeviceControlActivity.class);
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, name);
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, macAddress);
                        intent.putExtra("show", getIntent().getStringExtra("show"));
                        startActivity(intent);
                    } else {
                        connect(macAddress, name);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void connect(String macAddress, String deviceName) {
        Log.d(TAG, "connect:address: "+macAddress);
        Log.d(TAG, "connect:name: "+deviceName);

        OxiOprateManager.getMangerInstance(getApplicationContext()).registerConnectStatusListener(macAddress, new IABleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int code) {
                if (code == Constants.STATUS_CONNECTED) {
                    String connectStr = getString(R.string.connected);
                    Log.i(TAG, connectStr);
//                    binding.connectionState.setText(connectStr);
                } else {
                    String unConnectStr = getString(R.string.disconnected);
                    Log.i(TAG, unConnectStr);
                }
            }
        });

        OxiOprateManager.getMangerInstance(getApplicationContext()).connectDevice(macAddress, deviceName, (code, bleGattProfile, isUpdateModel) -> {
            if (code == Code.REQUEST_SUCCESS) {
                //蓝牙与设备的连接状态
                Log.i(TAG, "Connection Success");
            } else {
                Toast.makeText(mActivity, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(DeviceConnectScanActivity.this);
                Log.i(TAG, "Connection Failure");
            }
        }, state -> {
            if (state == Code.REQUEST_SUCCESS) {
                Log.i(TAG, "Notify Success");
                Intent intent = new Intent(DeviceConnectScanActivity.this, DataActivity.class);
                intent.putExtra("mac", macAddress);
                intent.putExtra("show", getIntent().getStringExtra("show"));
                startActivity(intent);
            } else {
                Toast.makeText(mActivity, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(DeviceConnectScanActivity.this);
                Log.i(TAG, "Notify Fail");
            }
        });


    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            showMessageOKCancel((dialog, which) -> requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA));
                        }
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }
}
