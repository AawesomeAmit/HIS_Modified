package com.his.android.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.his.android.Model.UserDetail;
import com.his.android.Model.sendotpres;
import com.his.android.R;
import com.his.android.Response.LoginResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import in.aabhasjindal.otptextview.OtpTextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser, edtPwd;
    TextView btnLogin, txtForget, resend, mobilenumbertv;
    ProgressDialog progressDialog;
    ProgressBar pb;
    TextView button;
    OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUser = findViewById(R.id.edtUser);
        edtPwd = findViewById(R.id.edtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        txtForget = findViewById(R.id.txtForget);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please");
        Log.v("ver", "Entered");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        if (!ConnectivityChecker.checker(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
        } else {
            btnLogin.setOnClickListener(this);
            txtForget.setOnClickListener(this);
        }
    }

    private boolean validate() {
        if (!TextUtils.isEmpty(edtUser.getText().toString())) {
            if (!TextUtils.isEmpty(edtPwd.getText().toString())) {
                return true;
            } else {
                edtPwd.setError("Empty password");
                edtPwd.requestFocus();
                return false;
            }
        } else {
            edtUser.setError("Empty username");
            edtUser.requestFocus();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            if (ConnectivityChecker.checker(MainActivity.this)) {
                if (validate()) {
                    try {
                        Utils.showRequestDialog(MainActivity.this);
                        byte[] data;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
                            data = edtPwd.getText().toString().getBytes(StandardCharsets.UTF_8);
                        else data = edtPwd.getText().toString().getBytes("UTF-8");
                        Call<sendotpres> call = RetrofitClient.getInstance().getApi().sendotp(SharedPrefManager.getInstance(MainActivity.this).getUser().getAccessToken(), String.valueOf(SharedPrefManager.getInstance(MainActivity.this).getUser().getUserid()), edtUser.getText().toString().trim(), Base64.encodeToString(data, Base64.DEFAULT).trim());

                        call.enqueue(new Callback<sendotpres>() {
                            @Override
                            public void onResponse(Call<sendotpres> call, Response<sendotpres> response) {

                                if (response.isSuccessful()) {
                                    progressDialog.show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.otpdialoglayout, viewGroup, false);
                                    builder.setView(dialogView);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();

                                    if (response == null)
                                        return;

                                    String detail = response.body().getOtp().get(0).getCurrentOTP();
                                    ;

                                    button = dialogView.findViewById(R.id.button);
                                    otpTextView = dialogView.findViewById(R.id.otptextview);
                                    mobilenumbertv = dialogView.findViewById(R.id.textView45);
                                    mobilenumbertv.setText("Enter the verification code we just sent on your registered mobile number " + response.body().getTable1().get(0).getMobileNo());


                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String otpenter = otpTextView.getOTP();

                                            Call<LoginResp> call = RetrofitClient.getInstance().getApi().loginAuthontication(SharedPrefManager.getInstance(MainActivity.this).getUser().getAccessToken(), "1234567", edtUser.getText().toString().trim(), Base64.encodeToString(data, Base64.DEFAULT).trim(), otpenter);

                                            call.enqueue(new Callback<LoginResp>() {
                                                @Override
                                                public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {

                                                    if (response.isSuccessful()) {

                                                        progressDialog.show();
                                                        UserDetail detail = response.body().getUserDetails().get(0);
                                                        SharedPrefManager.getInstance(MainActivity.this).setUser(detail);
                                                        SharedPrefManager.getInstance(MainActivity.this).setHead(response.body().getHeadAssign());
                                                        SharedPrefManager.getInstance(MainActivity.this).saveHeadList(response.body().getHeadAssign(), "headList");
                                                        insertFcmToken();

                                                        Intent intent = new Intent(MainActivity.this, PreDashboard.class);
                                                        intent.putExtra("oldpassword", Base64.encodeToString(data, Base64.DEFAULT).trim());
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        try {
                                                            Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }


                                                    Utils.hideDialog();
//

                                                }

                                                @Override
                                                public void onFailure(Call<LoginResp> call, Throwable t) {
                                                    Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                                                    Utils.hideDialog();
                                                }
                                            });


                                        }
                                    });

                                } else {
                                    try {
                                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }

                            @Override
                            public void onFailure(Call<sendotpres> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                                Utils.hideDialog();
                            }
                        });


//                        call.enqueue(new Callback<LoginResp>() {
//                            @Override
//                            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
//                                try {
//                                    if (response.isSuccessful()) {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                        ViewGroup viewGroup = findViewById(android.R.id.content);
//                                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.otpdialoglayout, viewGroup, false);
//                                        builder.setView(dialogView);
//                                        AlertDialog alertDialog = builder.create();
//                                        alertDialog.show();
//                                        pb = dialogView.findViewById(R.id.progressbar);
//                                        button = dialogView.findViewById(R.id.button);
//                                        button.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//
//                                                button.setText("Verifying");
//                                                pb.setVisibility(View.VISIBLE);
////                                                UserDetail detail = response.body().getUserDetails().get(0);
////                                                SharedPrefManager.getInstance(MainActivity.this).setUser(detail);
////                                                SharedPrefManager.getInstance(MainActivity.this).setHead(response.body().getHeadAssign());
////                                                SharedPrefManager.getInstance(MainActivity.this).saveHeadList(response.body().getHeadAssign(), "headList");
////                                                insertFcmToken();
////                                                Intent intent = new Intent(MainActivity.this, PreDashboard.class);
////                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                                                startActivity(intent);
//                                            }
//                                        });
////
//                                    } else {
//
//                                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
//                                        //Snackbar.make(btnLogin, response.errorBody().string(), Snackbar.LENGTH_LONG).show();
//                                    }
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
//                                Utils.hideDialog();
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<LoginResp> call, Throwable t) {
//                                Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
//                                Utils.hideDialog();
//                            }
//                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Utils.hideDialog();
                    }
                }
            } else
                Toast.makeText(MainActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.txtForget) {
            if (ConnectivityChecker.checker(MainActivity.this)) {

            } else
                Toast.makeText(MainActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("instanceId", task.getException());
                return;
            }
            String token = Objects.requireNonNull(task.getResult()).getToken();
            SharedPrefManager.getInstance(MainActivity.this).setFCMToken(token);
            Log.d("token", token);
            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().insertFCMDeviceToken(SharedPrefManager.getInstance(MainActivity.this).getUser().getAccessToken(), SharedPrefManager.getInstance(MainActivity.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(MainActivity.this).getFCMToken(), String.valueOf(SharedPrefManager.getInstance(MainActivity.this).getUser().getUserid()));

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        //Toast.makeText(MainActivity.this, "Token sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network problem!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {

    }

}