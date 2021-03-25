package com.his.android.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.getmedcheck.lib.model.BleDevice;
import com.google.zxing.Result;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.his.android.Activity.BP.Initial;
import com.his.android.Activity.BP.Medcheck.DeviceConnectionActivity;
import com.his.android.Activity.ViaOximeter.DataActivity;
import com.his.android.Model.PatientInfoBarcode;
import com.his.android.R;
import com.his.android.Response.PatientBarcodeResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.vphealthy.oximetersdk.OxiOprateManager;
import com.vphealthy.oximetersdk.listener.base.IABleConnectStatusListener;

import java.io.IOException;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.his.android.Activity.BP.BLE.DeviceControlActivity.EXTRAS_DEVICE_ADDRESS;
import static com.his.android.Activity.BP.BLE.DeviceControlActivity.EXTRAS_DEVICE_NAME;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private String TAG = "FragmentActivity";
    public static PatientInfoBarcode patientInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                //Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                requestPermission();
            }/* else {
                requestPermission();
            }*/
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        if (ConnectivityChecker.checker(ScannerActivity.this)) {
            Utils.showRequestDialog(ScannerActivity.this);
            Call<PatientBarcodeResp> call = RetrofitClient.getInstance().getApi().getPatientDetailByBarcode(SharedPrefManager.getInstance(ScannerActivity.this).getUser().getAccessToken(), SharedPrefManager.getInstance(ScannerActivity.this).getUser().getUserid().toString(), result);
            call.enqueue(new Callback<PatientBarcodeResp>() {
                @Override
                public void onResponse(Call<PatientBarcodeResp> call, Response<PatientBarcodeResp> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && !response.body().getPatientInfo().isEmpty()) {
                            patientInfo = response.body().getPatientInfo().get(0);
                            SharedPrefManager.getInstance(ScannerActivity.this).setScanned(true);
                            SharedPrefManager.getInstance(ScannerActivity.this).setPid(patientInfo.getPid());
                            SharedPrefManager.getInstance(ScannerActivity.this).setIpNo(patientInfo.getIpNo());
                            SharedPrefManager.getInstance(ScannerActivity.this).setPmId(patientInfo.getPmID());
                            SharedPrefManager.getInstance(ScannerActivity.this).setPtName(patientInfo.getPatientName());
                            SharedPrefManager.getInstance(ScannerActivity.this).setCr(patientInfo.getCrNo());
                            SharedPrefManager.getInstance(ScannerActivity.this).setSubdeptID(patientInfo.getAdmitSubDepartmentID());
                            SharedPrefManager.getInstance(ScannerActivity.this).setHeadID(patientInfo.getHeadId(), "", "");
                            Intent intent = null;
                            if (Objects.equals(getIntent().getStringExtra("redi"), "1")) {
                                intent = new Intent(ScannerActivity.this, Dashboard.class);
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "2")) {
                                intent = new Intent(ScannerActivity.this, DeviceControlActivity.class);
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "3")) {
                                intent = new Intent(ScannerActivity.this, Initial.class);
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "4")) {
                                intent = new Intent(ScannerActivity.this, ScanSelector.class);
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "5")) {
                                intent = new Intent(ScannerActivity.this, com.his.android.Activity.BP.BLE.DeviceControlActivity.class);
                                intent.putExtra(EXTRAS_DEVICE_NAME, getIntent().getStringExtra(EXTRAS_DEVICE_NAME));
                                intent.putExtra(EXTRAS_DEVICE_ADDRESS, getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS));
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "6")) {
                                intent = new Intent(ScannerActivity.this, DeviceConnectionActivity.class);
                                intent.putExtra("DATA", (BleDevice) getIntent().getParcelableExtra("DATA"));
                                intent.putExtra("status", "");
                            } else if (Objects.equals(getIntent().getStringExtra("redi"), "7")) {
//                                    connect(getIntent().getStringExtra("mac"), getIntent().getStringExtra("mac"));
                                intent = new Intent(ScannerActivity.this, DataActivity.class);
                                intent.putExtra("mac", getIntent().getStringExtra("mac"));
                                intent.putExtra("mac", getIntent().getStringExtra("mac"));
                                intent.putExtra("show", getIntent().getStringExtra("show"));
                                intent.putExtra("status", "");
                            }
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ScannerActivity.this, "Patient not admitted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ScannerActivity.this, PreDashboard.class));
                        }
                    } else {
                        try {
                            Toast.makeText(ScannerActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<PatientBarcodeResp> call, Throwable t) {
                    Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
        } else {
            Toast.makeText(ScannerActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScannerActivity.this, PreDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", (dialog, which) -> {
            mScannerView.resumeCameraPreview(PreDashboard.this);
        });
        builder.setNeutralButton("Visit", (dialog, which) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
            startActivity(browserIntent);
        });
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
    }

    private void connect(String macAddress, String deviceName) {
        Log.d(TAG, "connect:address: " + macAddress);
        Log.d(TAG, "connect:name: " + deviceName);

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
                Toast.makeText(ScannerActivity.this, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(ScannerActivity.this);
                Log.i(TAG, "Connection Failure");
            }
        }, state -> {
            if (state == Code.REQUEST_SUCCESS) {
                Log.i(TAG, "Notify Success");
                Intent intent = new Intent(ScannerActivity.this, DataActivity.class);
                intent.putExtra("mac", macAddress);
                intent.putExtra("show", getIntent().getStringExtra("show"));
                startActivity(intent);
            } else {
                Toast.makeText(ScannerActivity.this, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(ScannerActivity.this);
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
        new AlertDialog.Builder(ScannerActivity.this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
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