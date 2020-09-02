package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.Activity.BP.Initial;
import com.his.android.R;

public class ScanDashboard extends AppCompatActivity {
    TextView txtBp, txtSpo2, txtIpd, txtIcu;
    private static final int REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_dashboard);
        txtBp=findViewById(R.id.txtBp);
        txtSpo2=findViewById(R.id.txtSpo2);
        txtIpd=findViewById(R.id.txtIpd);
        txtIcu=findViewById(R.id.txtIcu);
        txtBp.setOnClickListener(view -> {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    startActivity(new Intent(this, Initial.class));
                }
            }
        });
        txtSpo2.setOnClickListener(view -> {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    startActivity(new Intent(this, DeviceScanActivity.class));
                }
            }
        });
        txtBp.setOnClickListener(view -> startActivity(new Intent(this, Initial.class)));
        txtBp.setOnClickListener(view -> startActivity(new Intent(this, Initial.class)));
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScanDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ScanDashboard.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted) {
                    startActivity(new Intent(ScanDashboard.this, DeviceScanActivity.class));
                    Toast.makeText(ScanDashboard.this, "Permission Granted, Now you can access location", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ScanDashboard.this, "Permission Denied, You cannot access location", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel((dialog, which) -> requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION));
                        }
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(ScanDashboard.this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}