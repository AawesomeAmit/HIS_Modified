package com.his.android.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.his.android.Activity.UploadMultipleImg.Api;
import com.his.android.Activity.UploadMultipleImg.ApiUtilsLocalUrl;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.BedMaster;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.BedResp;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.ConsultantList;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.ConsultantRes;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.GetDepartmentList;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.GetDepartmentRes;
import com.his.android.Activity.UploadMultipleImg.TransferPatient.WardList;
import com.his.android.Activity.UploadMultipleImg.Universalres;
import com.his.android.Activity.UploadMultipleImg.UploadImg;
import com.his.android.Model.GetMemberId;
import com.his.android.Model.Ward;
import com.his.android.R;
import com.his.android.Response.CheckO2AssignResp;
import com.his.android.Response.MemberIdResp;
import com.his.android.Response.WardResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanSelector extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_selector);
    }
}