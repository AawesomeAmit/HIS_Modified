package com.his.android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.his.android.R;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.view.BaseActivity;

public class DevicesSelection extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    RadioButton rbOxiControlD, rbOxiVia, rbBpOmron, rbBpMeditive, rbBpMedcheck;
    TextView tvSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_selection);
        tvSave=findViewById(R.id.tvSave);
        rbBpMedcheck=findViewById(R.id.rbBpMedcheck);
        rbBpMeditive=findViewById(R.id.rbBpMeditive);
        rbBpOmron=findViewById(R.id.rbBpOmron);
        rbOxiVia=findViewById(R.id.rbOxiVia);
        rbOxiControlD=findViewById(R.id.rbOxiControlD);
        rbOxiControlD.setOnCheckedChangeListener(this);
        rbOxiVia.setOnCheckedChangeListener(this);
        rbBpOmron.setOnCheckedChangeListener(this);
        rbBpMeditive.setOnCheckedChangeListener(this);
        rbBpMedcheck.setOnCheckedChangeListener(this);
        tvSave.setOnClickListener(view -> {
            startActivity(new Intent(mActivity, PreDashboard.class));
            finish();
        });
        if(SharedPrefManager.getInstance(mActivity).getBpMachine()==0){
            rbBpMedcheck.setChecked(true);
        } else if(SharedPrefManager.getInstance(mActivity).getBpMachine()==1){
            rbBpOmron.setChecked(true);
        } else if(SharedPrefManager.getInstance(mActivity).getBpMachine()==2){
            rbBpMeditive.setChecked(true);
        }
        if(SharedPrefManager.getInstance(mActivity).getOximeter()==0){
            rbOxiControlD.setChecked(true);
        } else if(SharedPrefManager.getInstance(mActivity).getOximeter()==1){
            rbOxiVia.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.rbBpMedcheck) {
            if(rbBpMedcheck.isChecked())
            SharedPrefManager.getInstance(mActivity).setBpMachine(0);
        } else if (compoundButton.getId() == R.id.rbBpOmron) {
            if(rbBpOmron.isChecked())
            SharedPrefManager.getInstance(mActivity).setBpMachine(1);
        } else if (compoundButton.getId() == R.id.rbBpMeditive) {
            if(rbBpMeditive.isChecked())
            SharedPrefManager.getInstance(mActivity).setBpMachine(2);
        }
        if (compoundButton.getId() == R.id.rbOxiControlD) {
            if(rbOxiControlD.isChecked())
            SharedPrefManager.getInstance(mActivity).setOximeter(0);
        } else if (compoundButton.getId() == R.id.rbOxiVia) {
            if(rbOxiVia.isChecked())
            SharedPrefManager.getInstance(mActivity).setOximeter(1);
        }
    }
}