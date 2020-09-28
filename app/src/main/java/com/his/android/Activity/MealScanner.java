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
import com.his.android.Activity.BP.Initial;
import com.his.android.Activity.BP.Medcheck.DeviceConnectionActivity;
import com.his.android.Activity.ViaOximeter.DataActivity;
import com.his.android.Model.PatientInfoBarcode;
import com.his.android.R;
import com.his.android.Response.PatientBarcodeResp;
import com.his.android.Response.ScanMealResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
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
import static com.his.android.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class MealScanner extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private String TAG = "FragmentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission();
            }
        }
    }
    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        if (ConnectivityChecker.checker(MealScanner.this)) {
            Utils.showRequestDialog(MealScanner.this);
            Call<ScanMealResp> call = RetrofitClient.getInstance().getApi().getIntakeNameByDetails(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(MealScanner.this).getUser().getUserid().toString(), rawResult.getText(), String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
            call.enqueue(new Callback<ScanMealResp>() {
                @Override
                public void onResponse(Call<ScanMealResp> call, Response<ScanMealResp> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                                moveTaskToBack(true);
                                finish();
                                Intent intent=new Intent(mActivity, MealScanEntry.class);
                                intent.putExtra("intake", response.body().getTable().get(0).getIntakeName());
                                intent.putExtra("intakeID", response.body().getTable().get(0).getIntakeID().toString());
                                intent.putExtra("qty", response.body().getTable().get(0).getIntakeQuantity().toString());
                                intent.putExtra("isSupplement", response.body().getTable().get(0).getIntakeType().toString());
                                intent.putExtra("isSynonym", response.body().getTable().get(0).getIsSynonym().toString());
                                intent.putExtra("textID", response.body().getTable().get(0).getTextID().toString());
                                intent.putExtra("doseForm", response.body().getTable().get(0).getDoseForm());
                                intent.putExtra("pmID", response.body().getTable().get(0).getPmID().toString());
                                intent.putExtra("prescriptionID", response.body().getTable().get(0).getPrescriptionID().toString());
                                intent.putExtra("unit", response.body().getTable().get(0).getUnitName());
                                intent.putExtra("unitID", response.body().getTable().get(0).getUnitID().toString());
                                startActivity(intent);
                        }
                    }else {
                        try {
                            Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<ScanMealResp> call, Throwable t) {
                    Toast.makeText(MealScanner.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }
            });
        } else {
            Toast.makeText(MealScanner.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MealScanner.this, PreDashboard.class);
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
                Toast.makeText(MealScanner.this, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(MealScanner.this);
                Log.i(TAG, "Connection Failure");
            }
        }, state -> {
            if (state == Code.REQUEST_SUCCESS) {
                Log.i(TAG, "Notify Success");
                Intent intent = new Intent(MealScanner.this, DataActivity.class);
                intent.putExtra("mac", macAddress);
                intent.putExtra("show", getIntent().getStringExtra("show"));
                startActivity(intent);
            } else {
                Toast.makeText(MealScanner.this, "Connection Failure", Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(MealScanner.this);
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
        new AlertDialog.Builder(MealScanner.this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mActivity, PreDashboard.class));
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