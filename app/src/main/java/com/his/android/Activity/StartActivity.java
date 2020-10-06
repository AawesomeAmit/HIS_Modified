package com.his.android.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.his.android.BuildConfig;
import com.his.android.R;
import com.his.android.Response.VersionCheckResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
//    VersionCheckResp version;
    boolean update = false;
    VersionChecker versionChecker;
    double mLatestVersionName;
    TextView txt;
    Handler handler;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressDialog = new ProgressDialog(StartActivity.this);
        linearLayout = findViewById(R.id.lLayout);
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
            versionChecker = new VersionChecker();
            try {
                mLatestVersionName = Double.parseDouble(versionChecker.execute().get());
                if (Double.parseDouble(BuildConfig.VERSION_NAME) < mLatestVersionName) {
                    txt.setEnabled(true);
                    Toast.makeText(StartActivity.this, R.string.please_update_to_latest_version, Toast.LENGTH_LONG).show();
                    txt.setText(R.string.please_update_to_latest_version);
                    update = true;
                    Toast.makeText(this, "Please update to latest version", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                } else {
                    if (SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()) {
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent intent = new Intent(StartActivity.this, PreDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }, 3000);
                    } else {
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
                        }, 3000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                if (SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()) {
                    handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(StartActivity.this, PreDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }, 3000);
                } else {
                    handler = new Handler();
                    handler.postDelayed(() -> {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
                    }, 3000);
                }
            }
        }else {
            if (SharedPrefManager.getInstance(StartActivity.this).isLoggedIn()) {
                handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(StartActivity.this, PreDashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }, 3000);
            } else {
                handler = new Handler();
                handler.postDelayed(() -> {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }, 3000);
            }
        }
        txt.setOnClickListener(view -> {
            if(update) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        });
    }

    public static class VersionChecker extends AsyncTask<String, String, String> {

        private String newVersion;

        @Override
        protected String doInBackground(String... params) {
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
    }
}