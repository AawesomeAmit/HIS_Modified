package com.his.android.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.his.android.Activity.BP.Initial;
import com.his.android.Activity.ViaOximeter.ViaOximeterScanActivity;
import com.his.android.Adapter.HeadAdp;
import com.his.android.Adapter.RecyclerTouchListener;
import com.his.android.Adapter.SubHeadAdp;
import com.his.android.Fragment.NutriAnalyserFragment;
import com.his.android.Model.GetMemberId;
import com.his.android.Model.HeadAssign;
import com.his.android.Model.PatientInfoBarcode;
import com.his.android.Model.SubDept;
import com.his.android.R;
import com.his.android.Response.GetAllMedicineByAlphabetRes;
import com.his.android.Response.MemberIdResp;
import com.his.android.Response.NotificationCountResp;
import com.his.android.Response.PatientBarcodeResp;
import com.his.android.Response.SubHeadIDResp;
import com.his.android.Utils.ClickListener;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClient1;
import com.his.android.Utils.RetrofitClientOrgan;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;
import com.his.android.database.DatabaseController;
import com.his.android.database.TableMedicineList;
import com.his.android.database.TableSubDeptList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreDashboard extends AppCompatActivity {
    TextView txtDrName, txtDept, img, count, txtScan, btnOxi, btnBp, txtBp, txtSpo2, txtScans, txtCovidRegistration;
    ImageView imgNotification;
    SubHeadIDResp subHeadIDResp;
    ArrayList<HeadAssign> headAssigns;
    ProgressDialog progressDialog;
    RecyclerView rvGrid;
    EditText edtPid;
    ImageView btnGo;
    private static final int REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_dashboard);
        rvGrid = findViewById(R.id.rvGrid);
        txtScan = findViewById(R.id.txtScan);
        txtBp = findViewById(R.id.txtBp);
        txtSpo2 = findViewById(R.id.txtSpo2);
        txtScans = findViewById(R.id.txtScans);
        edtPid = findViewById(R.id.edtPid);
        btnGo = findViewById(R.id.btnGo);
        txtCovidRegistration = findViewById(R.id.txtCovidRegistration);
        count = findViewById(R.id.count);
        img = findViewById(R.id.img);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        imgNotification = findViewById(R.id.imgNotification);
        SharedPrefManager.getInstance(PreDashboard.this).setScanned(false);
        if (SharedPrefManager.getInstance(this).getUser().getDesigid()==3 || SharedPrefManager.getInstance(this).getUser().getDesigid()==4 ||
                SharedPrefManager.getInstance(this).getUser().getDesigid()==5 || SharedPrefManager.getInstance(this).getUser().getDesigid()==21 ||
                SharedPrefManager.getInstance(this).getUser().getDesigid()==22 || SharedPrefManager.getInstance(this).getUser().getDesigid()==11)
            txtCovidRegistration.setVisibility(View.VISIBLE);
        else txtCovidRegistration.setVisibility(View.GONE);

        btnGo.setOnClickListener(view -> {
            if (!edtPid.getText().toString().isEmpty()) {
                Utils.showRequestDialog(PreDashboard.this);
                Call<PatientBarcodeResp> call = RetrofitClient.getInstance().getApi().getPatientDetailByBarcode(SharedPrefManager.getInstance(PreDashboard.this).getUser().getAccessToken(), SharedPrefManager.getInstance(PreDashboard.this).getUser().getUserid().toString(), edtPid.getText().toString().trim());
                call.enqueue(new Callback<PatientBarcodeResp>() {
                    @Override
                    public void onResponse(Call<PatientBarcodeResp> call, Response<PatientBarcodeResp> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getPatientInfo().size() > 0) {
                                PatientInfoBarcode patientInfo = response.body().getPatientInfo().get(0);
                                ScannerActivity.patientInfo = patientInfo;
                                SharedPrefManager.getInstance(PreDashboard.this).setScanned(true);
                                SharedPrefManager.getInstance(PreDashboard.this).setPid(patientInfo.getPid());
                                SharedPrefManager.getInstance(PreDashboard.this).setIpNo(patientInfo.getIpNo());
                                SharedPrefManager.getInstance(PreDashboard.this).setPmId(patientInfo.getPmID());
                                SharedPrefManager.getInstance(PreDashboard.this).setPtName(patientInfo.getPatientName());
                                SharedPrefManager.getInstance(PreDashboard.this).setCr(patientInfo.getCrNo());
                                SharedPrefManager.getInstance(PreDashboard.this).setSubdeptID(patientInfo.getAdmitSubDepartmentID());
                                SharedPrefManager.getInstance(PreDashboard.this).setHeadID(patientInfo.getHeadId(), "", "");
                                Intent intent = new Intent(PreDashboard.this, ScanSelector.class);
                                intent.putExtra("status", "");
                                startActivity(intent);
                            }
                        } else {
                            try {
                                Toast.makeText(PreDashboard.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<PatientBarcodeResp> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            } else
                Toast.makeText(PreDashboard.this, "Please enter a valid PID", Toast.LENGTH_SHORT).show();
        });
        txtBp.setOnClickListener(view -> {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    if(SharedPrefManager.getInstance(PreDashboard.this).getBpMachine()==0){
                        startActivity(new Intent(PreDashboard.this, com.his.android.Activity.BP.Medcheck.MainActivity.class));
                    } else if(SharedPrefManager.getInstance(PreDashboard.this).getBpMachine()==1){
                        startActivity(new Intent(PreDashboard.this, Initial.class));
                    } else if(SharedPrefManager.getInstance(PreDashboard.this).getBpMachine()==2){
                        startActivity(new Intent(PreDashboard.this, com.his.android.Activity.BP.BLE.DeviceScanActivity.class));
                    }
                }
            }
        });
        txtCovidRegistration.setOnClickListener(view -> startActivity(new Intent(PreDashboard.this, ChecklistCovidPatient.class)));
        txtSpo2.setOnClickListener(view -> {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    if(SharedPrefManager.getInstance(PreDashboard.this).getOximeter()==0){
                        startActivity(new Intent(PreDashboard.this, DeviceScanActivity.class));
                    } else if(SharedPrefManager.getInstance(PreDashboard.this).getOximeter()==1){
                        startActivity(new Intent(PreDashboard.this, ViaOximeterScanActivity.class));
                    }
                }
            }
        });
        String head = "headList";
        GridLayoutManager mGridLayoutManager;
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            mGridLayoutManager = new GridLayoutManager(PreDashboard.this, 4);
        else mGridLayoutManager = new GridLayoutManager(PreDashboard.this, 3);
        rvGrid.setLayoutManager(mGridLayoutManager);
        txtDrName = findViewById(R.id.txtDrName);
        txtDept = findViewById(R.id.txtDept);
        btnOxi = findViewById(R.id.btnOxi);
        btnBp = findViewById(R.id.btnBp);
        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());
        headAssigns = SharedPrefManager.getInstance(PreDashboard.this).getHeadList(head);
        rvGrid.setAdapter(new HeadAdp(PreDashboard.this, headAssigns));
        SharedPrefManager.getInstance(PreDashboard.this).setIsCovid(false);
        Call<List<NotificationCountResp>> call1 = RetrofitClient.getInstance().getApi().getUnReadNotificationCount(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getUser().getUserid());
        call1.enqueue(new Callback<List<NotificationCountResp>>() {
            @Override
            public void onResponse(Call<List<NotificationCountResp>> call, Response<List<NotificationCountResp>> response) {
                progressDialog.show();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        count.setText(String.valueOf(response.body().get(0).getUnReadCount()));
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<NotificationCountResp>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        btnBp.setOnClickListener(view -> {
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        startActivity(new Intent(PreDashboard.this, Initial.class));
                    }
                }
        });
        btnOxi.setOnClickListener(view -> {
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        startActivity(new Intent(PreDashboard.this, DeviceScanActivity.class));
                    }
                }
        });
        img.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(PreDashboard.this, img);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
            menu.getMenu().findItem(R.id.setting).setOnMenuItemClickListener(menuItem -> {
                startActivity(new Intent(PreDashboard.this, DevicesSelection.class));
                return true;
            });


            menu.getMenu().findItem(R.id.one).setOnMenuItemClickListener(item -> {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().logOut(SharedPrefManager.getInstance(PreDashboard.this).getUser().getAccessToken(), SharedPrefManager.getInstance(PreDashboard.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(PreDashboard.this).getFCMToken(), String.valueOf(SharedPrefManager.getInstance(PreDashboard.this).getUser().getUserid()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.show();
                        if (response.isSuccessful()) {
                            Toast.makeText(PreDashboard.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(PreDashboard.this).clear();
                            Intent intent = new Intent(PreDashboard.this, MainActivity.class);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PreDashboard.this, "Network problem!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                return true;
            });
            menu.show();
        });


        rvGrid.addOnItemTouchListener(new RecyclerTouchListener(this, rvGrid, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //if((headAssigns.get(position).getHeadID()==2) || (headAssigns.get(position).getHeadID()==3)||(headAssigns.get(position).getHeadID()==4)) {
                //Intent intent = new Intent(PreDashboard.this, SubHeadList.class);
                SharedPrefManager.getInstance(PreDashboard.this).setHeadID(headAssigns.get(position).getHeadID(), headAssigns.get(position).getHeadName(), headAssigns.get(position).getColor());
                if (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 31)
                    startActivity(new Intent(PreDashboard.this, CasualtyRegistration.class));
                //startActivity(intent);
                try {
                    showPopup(view);
                } catch (Exception ex) {
                    Log.v("error", Objects.requireNonNull(ex.getMessage()));
                    ex.printStackTrace();
                }
                /*} else {
                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setHeadID(headAssigns.get(position).getHeadID(), headAssigns.get(position).getHeadName());
                    startActivity(intent);
                }*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        txtScan.setOnClickListener(view -> {
            Intent intent=new Intent(PreDashboard.this, ScannerActivity.class);
            intent.putExtra("redi", "1");
            startActivity(intent);
        });
        txtScans.setOnClickListener(view -> {
//            Intent intent=new Intent(PreDashboard.this, ScannerActivity.class);
            Intent intent=new Intent(PreDashboard.this, SendMessage.class);
            intent.putExtra("redi", "4");
            startActivity(intent);
        });
        imgNotification.setOnClickListener(view -> {
            Intent intent = new Intent(PreDashboard.this, NotificationList.class);
            startActivity(intent);
        });
        count.setOnClickListener(view -> {
            Intent intent = new Intent(PreDashboard.this, NotificationList.class);
            startActivity(intent);
        });

        hitGetUserProfileByPID();
    }

    public void showPopup(final View anchorView) {
        progressDialog.show();
        final RecyclerView recycler;
        ImageView close;
        TextView txtDept;
        RelativeLayout relLayout;
        View popupView = getLayoutInflater().inflate(R.layout.activity_sub_head_list, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        txtDept = popupView.findViewById(R.id.txtDept);
        recycler = popupView.findViewById(R.id.recycler);
        close = popupView.findViewById(R.id.close);
        relLayout = popupView.findViewById(R.id.relLayout);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        txtDept.setText(SharedPrefManager.getInstance(this).getHead().getHeadName());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        relLayout.setBackgroundColor(Color.parseColor(SharedPrefManager.getInstance(PreDashboard.this).getHead().getColor()));

        if (ConnectivityChecker.checker(PreDashboard.this)){

            progressDialog.show();
            Call<SubHeadIDResp> call = RetrofitClient.getInstance().getApi().getsubDepertmentByHID(
                    SharedPrefManager.getInstance(PreDashboard.this).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(PreDashboard.this).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(PreDashboard.this).getHeadID().toString(),
                    SharedPrefManager.getInstance(PreDashboard.this).getUser().getUserid());
            call.enqueue(new Callback<SubHeadIDResp>() {
                @Override
                public void onResponse(Call<SubHeadIDResp> call, Response<SubHeadIDResp> response) {
                    if (response.isSuccessful()) {
                        subHeadIDResp = response.body();
                        if (subHeadIDResp.getSubDept().size() > 1) {
                            recycler.setAdapter(new SubHeadAdp(PreDashboard.this, subHeadIDResp));
                            popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

                            try {
                                DatabaseController.myDataBase.beginTransaction();

//                                DatabaseController.myDataBase.delete(TableSubDeptList.sub_dept_list, null, null);

                                for (int i = 0; i < subHeadIDResp.getSubDept().size(); i++) {

                                    SubDept subDept = response.body().getSubDept().get(i);

                                    ContentValues contentValues = new ContentValues();

                                    contentValues.put(TableSubDeptList.subDeptListColumn.id.toString(), subDept.getId());
                                    contentValues.put(TableSubDeptList.subDeptListColumn.headID.toString(), subDept.getHeadID());
                                    contentValues.put(TableSubDeptList.subDeptListColumn.subDepartmentName.toString(), subDept.getSubDepartmentName());
                                    contentValues.put(TableSubDeptList.subDeptListColumn.bgColor.toString(), subDept.getBgColor());

                                    if (!DatabaseController.checkRecordExistWhere(TableSubDeptList.sub_dept_list,
                                            TableSubDeptList.subDeptListColumn.headID +" = '"+ subDept.getHeadID().toString() +"'" +
                                                    " and "+ TableSubDeptList.subDeptListColumn.id +" = '"+ subDept.getId().toString() +"'")) {
                                        DatabaseController.insertData(contentValues, TableSubDeptList.sub_dept_list);
                                    }
//                                    DatabaseController.insertUpdateData(contentValues, TableSubDeptList.sub_dept_list, "id", subDept.getId().toString());
                                }

                                DatabaseController.myDataBase.setTransactionSuccessful();

                            } finally {
                                DatabaseController.myDataBase.endTransaction();

                                Utils.hideDialog();
                            }

                        } else {
                            try {
                                if ((SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 2) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 3) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 4) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 9 || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 7) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 2029) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 2030) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 34) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 35) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 36) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 14))) {//
                                    Intent intent = new Intent(PreDashboard.this, PatientList.class);
                                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                    startActivity(intent);
                                } else if (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 10) {
                                    Intent intent = new Intent(PreDashboard.this, Dashboard.class);
                                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                    startActivity(intent);
                                } else if (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 26) {
                                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                                    //Intent intent = new Intent(PreDashboard.this, MedicineSidePathway.class);
//                                SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                    //SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                    startActivity(intent);
                                } /*else if (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 7) {
                                Intent intent = new Intent(PreDashboard.this, NutriAnalyserFragment.class);
                                //Intent intent = new Intent(PreDashboard.this, MedicineSidePathway.class);
//                                SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                //SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                startActivity(intent);
                            }*/ else {
                                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                                    SharedPrefManager.getInstance(PreDashboard.this).setHeadID(SharedPrefManager.getInstance(PreDashboard.this).getHeadID(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getHeadName(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getColor());
                                    startActivity(intent);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else
                        Toast.makeText(PreDashboard.this, response.message(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<SubHeadIDResp> call, Throwable t) {
                    Toast.makeText(PreDashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }else {
            subHeadIDResp = new SubHeadIDResp();
            subHeadIDResp.setSubDept(DatabaseController.getSubDeptList(SharedPrefManager.getInstance(PreDashboard.this).getHeadID().toString()));

            if (subHeadIDResp.getSubDept().size() > 1) {
                recycler.setAdapter(new SubHeadAdp(PreDashboard.this, subHeadIDResp));
                popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
            }else {
                if ((SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 2) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 3) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 4) || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 9 || (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 7))) {
                    Intent intent = new Intent(PreDashboard.this, PatientList.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                    startActivity(intent);
                } else if (SharedPrefManager.getInstance(PreDashboard.this).getHeadID() == 10) {
                    Intent intent = new Intent(PreDashboard.this, PatientDashboard.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(0));
                    SharedPrefManager.getInstance(PreDashboard.this).setHeadID(SharedPrefManager.getInstance(PreDashboard.this).getHeadID(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getHeadName(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getColor());
                    startActivity(intent);
                }
            }
        }

        recycler.addOnItemTouchListener(new RecyclerTouchListener(this, recycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (subHeadIDResp.getSubDept().get(position).getHeadID() == 1) {
                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(position));
                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setHeadID(SharedPrefManager.getInstance(PreDashboard.this).getHeadID(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getHeadName(), SharedPrefManager.getInstance(PreDashboard.this).getHead().getColor());
                    startActivity(intent);
                }  else if (subHeadIDResp.getSubDept().get(position).getHeadID() == 26) {
                    Intent intent = new Intent(PreDashboard.this, EnterPID.class);
                    startActivity(intent);
                } else if (subHeadIDResp.getSubDept().get(position).getHeadID() == 7) {
                    Intent intent = new Intent(PreDashboard.this, NutriAnalyserFragment.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(PreDashboard.this, PatientList.class);
                    SharedPrefManager.getInstance(PreDashboard.this).setSubHead(subHeadIDResp.getSubDept().get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            moveTaskToBack(true);
                            finish();
                            System.exit(0);
                        })

                .setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel())
                .show();
    }

    private void getAllMedicine() {

        Utils.showRequestDialog(this);

        Call<GetAllMedicineByAlphabetRes> call = RetrofitClientOrgan.getInstance().getApi().getAllMedicineByAlphabet("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoic2FkZGFtIiwibGFzdE5hbWUiOm51bGwsImVtYWlsSWQiOm51bGwsIm1vYmlsZU5vIjoiODk2MDI1MzEzMyIsImNvdW50cnkiOiJJTkRJQSIsInppcENvZGUiOiIyMjYwMjAiLCJvY2N1cGF0aW9uSWQiOjEsImFnZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJoZWlnaHRJbkZlZXQiOm51bGwsImhlaWdodEluSW5jaCI6bnVsbCwid2VpZ2h0IjpudWxsLCJwYWNrYWdlTmFtZSI6IkZyZWUiLCJpYXQiOjE1NjMwMTM4MDUsImV4cCI6MTU5NDU0OTgwNX0.l220lljQyTXmDPD-gyU53H4vV-I1GDPociKcp2qrWe8", "2");
        call.enqueue(new Callback<GetAllMedicineByAlphabetRes>() {
            @Override
            public void onResponse(Call<GetAllMedicineByAlphabetRes> call, Response<GetAllMedicineByAlphabetRes> response) {
                if (response != null && response.body().getResponseCode() == 1) {

                    try{
                        DatabaseController.myDataBase.beginTransaction();

                        DatabaseController.myDataBase.delete(TableMedicineList.icd_list, null, null);

                        for (int i = 0; i < response.body().getResponseValue().size(); i++) {

                            ContentValues contentValues = new ContentValues();

                            contentValues.put(TableMedicineList.icdListColumn.icdId.toString(), response.body().getResponseValue().get(i).getId());
                            contentValues.put(TableMedicineList.icdListColumn.icdCode.toString(), response.body().getResponseValue().get(i).getMedicineName());

                            DatabaseController.insertData(contentValues, TableMedicineList.icd_list);

                        }

                        DatabaseController.myDataBase.setTransactionSuccessful();

                    } finally {
                        DatabaseController.myDataBase.endTransaction();

                        Utils.hideDialog();

                        Log.d("checkcount", String.valueOf(DatabaseController.getAllICDListCount()));

                        Log.d("#checkList", String.valueOf(DatabaseController.getICDList()));
                    }

                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetAllMedicineByAlphabetRes> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }

    private void hitGetUserProfileByPID(){
        Call<MemberIdResp> call= RetrofitClient1.getInstance().getApi().getUserProfileByPID("AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getPid()));
        call.enqueue(new Callback<MemberIdResp>() {
            @Override
            public void onResponse(Call<MemberIdResp> call, Response<MemberIdResp> response) {
                if (response.body() != null) {
                    MemberIdResp memberIdResp = response.body();
                    if (memberIdResp.getResponseCode() == 1) {
                        SharedPrefManager.getInstance(getApplicationContext()).setMemberId(memberIdResp.getResponseValue().get(0));
                    } else {
                        SharedPrefManager.getInstance(getApplicationContext()).setMemberId(new GetMemberId(0,0));
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberIdResp> call, Throwable t) {
                Log.v("showError", t.getMessage());
            }
        });
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(PreDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(PreDashboard.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted) {
                    startActivity(new Intent(PreDashboard.this, DeviceScanActivity.class));
                    Toast.makeText(PreDashboard.this, "Permission Granted, Now you can access location", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PreDashboard.this, "Permission Denied, You cannot access location", Toast.LENGTH_LONG).show();
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
        new androidx.appcompat.app.AlertDialog.Builder(PreDashboard.this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}