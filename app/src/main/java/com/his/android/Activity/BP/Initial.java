package com.his.android.Activity.BP;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.his.android.Activity.CasualtyRegistration;
import com.his.android.Activity.PreDashboard;
import com.his.android.Activity.ScannerActivity;
import com.his.android.R;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.his.android.Activity.BP.TerminalFragment.dias;
import static com.his.android.Activity.BP.TerminalFragment.sys;
import static com.his.android.Activity.BP.TerminalFragment.pulse;

public class Initial extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    static TextView btnScan;
    EditText txtPid;
    TextView btnSaveData;
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
        if (getIntent().getStringExtra("view") != null){
            if (getIntent().getStringExtra("view").equals("1")){
                Bundle args = new Bundle();
                Log.v("viewsdcdc", "1");
                args.putString("device", getIntent().getStringExtra("mac"));
                Fragment fragment = new TerminalFragment();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
                //fabBarcode.hide();
            }
        }else {
            if (savedInstanceState == null){
//            ll.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
            }
            else{
//            ll.setVisibility(View.VISIBLE);
                onBackStackChanged();
            }
        }
        if (getIntent().getStringExtra("status1") != null) {
            Initial.btnScan.setVisibility(View.GONE);
            btnScan.setVisibility(View.GONE);
            btnSaveData.setVisibility(View.VISIBLE);
            btnSaveData.setText("OK");
            txtPid.setVisibility(View.GONE);
            txtPid.setText(String.valueOf(SharedPrefManager.getInstance(Initial.this).getPid()));
        } else {
            Initial.btnScan.setVisibility(View.VISIBLE);
            btnScan.setVisibility(View.VISIBLE);
            btnSaveData.setVisibility(View.VISIBLE);
            txtPid.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra("status") != null) {
            txtPid.setText(String.valueOf(SharedPrefManager.getInstance(Initial.this).getPid()));
        }
        btnScan.setOnClickListener(view1 -> {
            Intent intent1 = new Intent(Initial.this, ScannerActivity.class);
            intent1.putExtra("redi", "3");
            intent1.putExtra("view", "1");
            intent1.putExtra("type", "BP");
            startActivity(intent1);
        });
        btnSaveData.setOnClickListener(view1 -> {
            if (getIntent().getStringExtra("status1") != null) {
                onBackPressed();
            } else {
//            displayData(spo2, pulse);
                JSONArray dtTableArray = new JSONArray();
                try {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("vmID", "4");
                    jsonObject1.put("vmValue", sys);
                    dtTableArray.put(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("vmID", "6");
                    jsonObject2.put("vmValue", dias);
                    dtTableArray.put(jsonObject2);
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("vmID", "206");
                    jsonObject3.put("vmValue", pulse);
                    dtTableArray.put(jsonObject3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dtTableArray.length() != 0) {
                    if (ConnectivityChecker.checker(Initial.this)) {
                        if (sys != null) {
                            if ((!sys.equals("0")) && !dias.equals("0")) {
                                saveBluetoothVital(dtTableArray);
                            } else
                                Toast.makeText(this, "Error in data.\nPlease retest", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Initial.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void saveBluetoothVital(JSONArray dt) {
        Utils.showRequestDialog(Initial.this);
        Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");


        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PID", SharedPrefManager.getInstance(Initial.this).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(Initial.this).getHeadID());
            jsonObject.put("entryDate", format.format(today));
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(Initial.this).getSubdeptID());
            jsonObject.put("isFinalDiagnosis", false);
            jsonObject.put("isMachine", "1");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("status1")==null) {

            Intent intent = new Intent(Initial.this, PreDashboard.class);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(Initial.this, CasualtyRegistration.class);
            intent.putExtra("status2", "1");
            finish();
            startActivity(intent);
        }
    }
}
