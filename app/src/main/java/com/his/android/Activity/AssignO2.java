package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.Model.LifeSupportMasterList;
import com.his.android.R;
import com.his.android.Response.LifeSupportMasterResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignO2 extends BaseActivity {
    Spinner spnSupport;
    TextView btnAssign, btnSaveO2, btnRemoveO2;
    EditText edtO2, edtFio2;
    ArrayList<LifeSupportMasterList> lifeSupportList;
    ArrayAdapter<LifeSupportMasterList> lifeSupportAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_o2);
        spnSupport = findViewById(R.id.spnSupport);
        btnAssign = findViewById(R.id.btnAssign);
        btnSaveO2 = findViewById(R.id.btnSaveO2);
        btnRemoveO2 = findViewById(R.id.btnRemoveO2);
        edtO2 = findViewById(R.id.edtO2);
        edtFio2 = findViewById(R.id.edtFio2);
        lifeSupportList = new ArrayList<>();
        lifeSupportList.add(new LifeSupportMasterList(0, "Select Life Support"));
        btnAssign.setOnClickListener(view -> {
            if (spnSupport.getSelectedItemPosition() != 0) assignLifeSupport();
            else
                Toast.makeText(mActivity, "Please select Life Support!", Toast.LENGTH_SHORT).show();
        });
        btnRemoveO2.setOnClickListener(view -> removeO2());
        btnSaveO2.setOnClickListener(view -> {
            if (edtO2.getText().toString().isEmpty()) Toast.makeText(mActivity, "Enter O2 value!", Toast.LENGTH_SHORT).show();
            else saveO2();
        });
        bindSupport();
    }

    private void assignLifeSupport() {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().savePatientLifeSupportAssign(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), lifeSupportList.get(spnSupport.getSelectedItemPosition()).getId().toString(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Assigned Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void saveO2() {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().saveO2(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid(), SharedPrefManager.getInstance(mActivity).getHeadID(), edtO2.getText().toString().trim(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Saved Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void removeO2() {
        Utils.showRequestDialog(mActivity);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().removeOxygen(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(), SharedPrefManager.getInstance(mActivity).getPid());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mActivity, "Removed Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void bindSupport() {
        Utils.showRequestDialog(mActivity);
        Call<LifeSupportMasterResp> call = RetrofitClient.getInstance().getApi().getLifeSupportMasterList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<LifeSupportMasterResp>() {
            @Override
            public void onResponse(Call<LifeSupportMasterResp> call, Response<LifeSupportMasterResp> response) {
                if (response.isSuccessful()) lifeSupportList.addAll(1, response.body().getLifeSupportMasterList());
                lifeSupportAdp = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, lifeSupportList);
                spnSupport.setAdapter(lifeSupportAdp);
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<LifeSupportMasterResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
}