package com.trueform.era.his.Activity;

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

import com.google.zxing.Result;
import com.trueform.era.his.Model.PatientInfoBarcode;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.PatientBarcodeResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    public static PatientInfoBarcode patientInfo=null;
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
                            SharedPrefManager.getInstance(ScannerActivity.this).setPid(patientInfo.getPid());
                            SharedPrefManager.getInstance(ScannerActivity.this).setIpNo(patientInfo.getIpNo());
                            SharedPrefManager.getInstance(ScannerActivity.this).setPmId(patientInfo.getPmID());
                            SharedPrefManager.getInstance(ScannerActivity.this).setHeadID(patientInfo.getHeadId(), "", "");
                            Intent intent;
                            if(getIntent().getStringExtra("redi").equals(1))
                            intent = new Intent(ScannerActivity.this, Dashboard.class);
                            else {
                                intent = new Intent(ScannerActivity.this, DeviceControlActivity.class);
                                intent.putExtra("status", "");
                            }
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                            finish();
                            startActivity(intent);
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