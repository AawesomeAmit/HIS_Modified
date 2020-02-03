package com.trueform.era.his.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trueform.era.his.BuildConfig;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.VersionCheckResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    VersionCheckResp version;
    TextView txt;
    Intent httpIntent;
    Handler handler;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressDialog = new ProgressDialog(StartActivity.this);
        linearLayout=findViewById(R.id.lLayout);
        progressDialog.setMessage("Please wait...");
        txt = findViewById(R.id.txt);
        txt.setEnabled(false);
        if (ConnectivityChecker.checker(StartActivity.this)) {
            /*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w("getInstanceId failed", task.getException());
                        return;
                    }
                    String token = Objects.requireNonNull(task.getResult()).getToken();
                    SharedPrefManager.getInstance(StartActivity.this).setFCMToken(token);
                    Log.d("token", token);
                }
            });*/
            Log.v("status", BuildConfig.VERSION_NAME);
            Call<VersionCheckResp> call = RetrofitClient.getInstance().getApi().checkVersion(BuildConfig.VERSION_NAME);
            call.enqueue(new Callback<VersionCheckResp>() {
                @Override
                public void onResponse(Call<VersionCheckResp> call, Response<VersionCheckResp> response) {
                    progressDialog.show();
                    if (response.isSuccessful()) {
                        //Log.v("status", "entered");
                        version = response.body();
                        //Log.v("version1", String.valueOf(version));
                        if (!Objects.requireNonNull(version).getIsVersionUpdated().get(0).getStatus()) {
                            txt.setEnabled(true);
                            Toast.makeText(StartActivity.this, R.string.please_update_to_latest_version, Toast.LENGTH_LONG).show();
                            txt.setText(R.string.please_update_to_latest_version);
                            httpIntent = new Intent(Intent.ACTION_VIEW);
                            httpIntent.setData(Uri.parse(version.getIsVersionUpdated().get(0).getAppURL()));
                            startActivity(httpIntent);
                        } else {
                            try {
                                if (SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()) {
                                    handler=new Handler();
                                    //Log.v("ver", String.valueOf(SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()));
                                    handler.postDelayed(() -> {
                                        Intent intent = new Intent(StartActivity.this, PreDashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    },3000);
                                } else{
                                    handler=new Handler();
                                    handler.postDelayed(() -> {
                                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                                        finish();
                                    },3000);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<VersionCheckResp> call, Throwable t) {
                    Log.v("status", t.getMessage());
                    Toast.makeText(StartActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                }
            });
            txt.setOnClickListener(view -> startActivity(httpIntent));
        }
        else{
            //Toast.makeText(this, "Network connection not found!", Toast.LENGTH_SHORT).show();

            try {
                if (SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()) {
                    handler=new Handler();
                    //Log.v("ver", String.valueOf(SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()));
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(StartActivity.this, PreDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    },3000);
                } else{
                    handler=new Handler();
                    handler.postDelayed(() -> {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
                    },3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}