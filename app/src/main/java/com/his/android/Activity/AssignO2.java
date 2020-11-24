package com.his.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.his.android.Model.LifeSupportMasterList;
import com.his.android.R;
import com.his.android.Response.LifeSupportMasterResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.ArrayList;

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
        spnSupport=findViewById(R.id.spnSupport);
        btnAssign=findViewById(R.id.btnAssign);
        btnSaveO2=findViewById(R.id.btnSaveO2);
        btnRemoveO2=findViewById(R.id.btnRemoveO2);
        edtO2=findViewById(R.id.edtO2);
        edtFio2=findViewById(R.id.edtFio2);
        lifeSupportList.add(new LifeSupportMasterList(0, "Select Life Support"));
        btnAssign.setOnClickListener(view -> {
            
        });
        btnRemoveO2.setOnClickListener(view -> {

        });
        bindSupport();
    }
    private void bindSupport(){
        Utils.showRequestDialog(mActivity);
        Call<LifeSupportMasterResp> call= RetrofitClient.getInstance().getApi().getLifeSupportMasterList(SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(), SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString());
        call.enqueue(new Callback<LifeSupportMasterResp>() {
            @Override
            public void onResponse(Call<LifeSupportMasterResp> call, Response<LifeSupportMasterResp> response) {
                if(response.isSuccessful()) lifeSupportAdp = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, lifeSupportList);
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