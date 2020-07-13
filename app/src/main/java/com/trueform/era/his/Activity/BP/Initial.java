package com.trueform.era.his.Activity.BP;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trueform.era.his.Activity.DeviceControlActivity;
import com.trueform.era.his.Activity.ScannerActivity;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.trueform.era.his.Activity.BP.TerminalFragment.dias;
import static com.trueform.era.his.Activity.BP.TerminalFragment.sys;
import static com.trueform.era.his.Activity.BP.TerminalFragment.pulse;

public class Initial extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    TextView btnScan, txtPid, btnSaveData;
    private Date today = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        txtPid = findViewById(R.id.txtPid);
        btnScan = findViewById(R.id.btnScan);
        btnSaveData = findViewById(R.id.btnSaveData);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if(getIntent().getStringExtra("status")!=null) {
            txtPid.setText(String.valueOf(SharedPrefManager.getInstance(Initial.this).getPid()));
        }
        btnScan.setOnClickListener(view1 -> {
            Intent intent1=new Intent(Initial.this, ScannerActivity.class);
            intent1.putExtra("redi", "3");
            startActivity(intent1);
        });
        btnSaveData.setOnClickListener(view1 -> {
//            displayData(spo2, pulse);
            JSONArray dtTableArray = new JSONArray();
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("vitalId", "4");
                jsonObject1.put("vitalValue", sys);
                dtTableArray.put(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("vitalId", "6");
                jsonObject2.put("vitalValue", dias);
                dtTableArray.put(jsonObject2);
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("vitalId", "3");
                jsonObject3.put("vitalValue", pulse);
                dtTableArray.put(jsonObject3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (ConnectivityChecker.checker(Initial.this)) {
                    saveBluetoothVital(dtTableArray.toString());
                } else {
                    Toast.makeText(Initial.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
        else
            onBackStackChanged();
    }
    public void saveBluetoothVital(String dt) {
        Utils.showRequestDialog(Initial.this);
        Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PID", SharedPrefManager.getInstance(Initial.this).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(Initial.this).getHeadID());
            jsonObject.put("entryDate", format.format(today));
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(Initial.this).getSubdeptID());
            jsonObject.put("isFinalDiagnosis", false);
            jsonObject.put("ipNo", SharedPrefManager.getInstance(Initial.this).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(Initial.this).getUser().getUserid());
            jsonObject.put("consultantName", SharedPrefManager.getInstance(Initial.this).getUser().getUserid());
            jsonObject.put("vitals", dt);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/saveVitals")
                .addHeaders("accessToken", SharedPrefManager.getInstance(Initial.this).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(Initial.this).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Initial.this, "Vitals saved successfully", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Initial.this, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }
    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
