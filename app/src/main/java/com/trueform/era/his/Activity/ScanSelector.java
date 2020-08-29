package com.trueform.era.his.Activity;

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

import com.trueform.era.his.Activity.UploadMultipleImg.Api;
import com.trueform.era.his.Activity.UploadMultipleImg.ApiUtilsLocalUrl;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.BedMaster;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.BedResp;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.ConsultantList;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.ConsultantRes;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.GetDepartmentList;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.GetDepartmentRes;
import com.trueform.era.his.Activity.UploadMultipleImg.TransferPatient.WardList;
import com.trueform.era.his.Activity.UploadMultipleImg.Universalres;
import com.trueform.era.his.Activity.UploadMultipleImg.UploadImg;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanSelector extends BaseActivity {
    TextView txtPrescription, txtTransferIn, txtTransferOut, txtDischarge, txtVital, txtUpload,
            tvPtName, tvPID, txtIntake, txtUpdateVital, txtProgressNote;
    Intent intent;
    Spinner popupspnDepartment,popUpspnConsultant,popUpspnWard, spnBed;
    EditText popUpEtReason;
    Dialog dialog;
    List<GetDepartmentList> departmentList = new ArrayList<>();
    List<ConsultantList> consultantLists = new ArrayList<>();
    List<BedMaster> bedList = new ArrayList<>();
    List<WardList> wardLists = new ArrayList<>();
    private String departmentID="";
    private String consultantID="";
    private String bedID="";
    private String wardID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_selector);
        txtPrescription=findViewById(R.id.txtPrescription);
        txtUpload=findViewById(R.id.txtUpload);
        txtUpdateVital=findViewById(R.id.txtUpdateVital);
        txtTransferIn =findViewById(R.id.txtTransferIn);
        txtTransferOut =findViewById(R.id.txtTransferOut);
        txtDischarge=findViewById(R.id.txtDischarge);
        txtProgressNote=findViewById(R.id.txtProgressNote);
        txtVital=findViewById(R.id.txtVital);
        txtIntake=findViewById(R.id.txtIntake);
        intent=new Intent(ScanSelector.this, Dashboard.class);
        txtPrescription.setOnClickListener(view -> {
            intent.putExtra("status1", "0");
            startActivity(intent);
        });
        txtVital.setOnClickListener(view -> {
            intent.putExtra("status1", "1");
            startActivity(intent);
        });
        txtIntake.setOnClickListener(view -> {
            intent.putExtra("status1", "3");
            startActivity(intent);
        });
        txtTransferIn.setOnClickListener(view -> alertTransferPatient("Transfer-In"));
        txtTransferOut.setOnClickListener(view -> alertTransferPatient("Transfer-Out"));
        txtDischarge.setOnClickListener(view -> {
            intent.putExtra("status1", "2");
            startActivity(intent);});
        txtUpload.setOnClickListener(view -> startActivity(new Intent(ScanSelector.this, UploadImg.class)));
        txtUpdateVital.setOnClickListener(view -> startActivity(new Intent(ScanSelector.this, UpdateVital.class)));
        txtProgressNote.setOnClickListener(view -> startActivity(new Intent(ScanSelector.this, ProgressNoteScan.class)));
        if(SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID()==1){
            txtProgressNote.setVisibility(View.VISIBLE);
            txtPrescription.setVisibility(View.VISIBLE);
            txtIntake.setVisibility(View.GONE);
        } else if(SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID()==4){
            txtProgressNote.setVisibility(View.GONE);
            txtPrescription.setVisibility(View.GONE);
            txtIntake.setVisibility(View.VISIBLE);
        }
    }

    //Dialog transfer patient
    private void alertTransferPatient(String head) {

        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_patient_transfer);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RelativeLayout relativelyBed=dialog.findViewById(R.id.relativelyBed);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        TextView tvHeading = dialog.findViewById(R.id.tvHeading);
        tvPID = dialog.findViewById(R.id.tvPID);
        tvPtName = dialog.findViewById(R.id.tvPtName);
        tvPID.setText(String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        tvPtName.setText(SharedPrefManager.getInstance(mActivity).getPtName());
        popupspnDepartment = dialog.findViewById(R.id.spnDepartment);
        popUpspnConsultant = dialog.findViewById(R.id.spnConsultant);
        spnBed = dialog.findViewById(R.id.spnBed);
        popUpspnWard = dialog.findViewById(R.id.spnWard);
        popUpEtReason = dialog.findViewById(R.id.etReason);
        if(head.equalsIgnoreCase("transfer-out")) {
            relativelyBed.setVisibility(View.GONE);
            tvHeading.setText(R.string.where_transfer_out);
        }
        else {
            relativelyBed.setVisibility(View.VISIBLE);
            tvHeading.setText(R.string.where_transfer_in);
        }
        ivClose.setOnClickListener(view -> dialog.dismiss());

        bedList.add(new BedMaster());
        bedList.get(0).setId(0);
        bedList.get(0).setBedName("Select Bed");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, bedList);
        spnBed.setAdapter(arrayAdapter1);
        if (ConnectivityChecker.checker(mActivity)) {
            // hitTransferPatient();
            hitGetDepartment();

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        tvSubmit.setOnClickListener(view -> {
            try {

                if (departmentID.equals("0") || departmentID.equals("")) {
                    Toast.makeText(mActivity, "Please select department", Toast.LENGTH_SHORT).show();
                } else if (consultantID.equals("0") || consultantID.equals("")) {
                    Toast.makeText(mActivity, "Please select consultant", Toast.LENGTH_SHORT).show();
                } else if (wardID.equals("0") || wardID.equals("")) {
                    Toast.makeText(mActivity, "Please select ward", Toast.LENGTH_SHORT).show();
                } else if (popUpEtReason.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter reason", Toast.LENGTH_SHORT).show();
                } else {
                    if (ConnectivityChecker.checker(mActivity)) {
                        /*if(head.equalsIgnoreCase("transfer-out")) {
                            hitTransferPatient(true);
                        } else hitTransferPatient(false);*/
                        hitTransferPatient();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    //Hit Get Department
    private void hitGetDepartment() {
        departmentList.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<GetDepartmentRes> call = iRestInterfaces.getDepartmentList(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()
        );

        call.enqueue(new Callback<GetDepartmentRes>() {
            @Override
            public void onResponse(Call<GetDepartmentRes> call, Response<GetDepartmentRes> response) {

                if (response.isSuccessful()) {

                    departmentList.add(new GetDepartmentList());
                    departmentList.get(0).setId(0);
                    departmentList.get(0).setSubDepartmentName("Select Department");

                    departmentList.addAll(response.body().getSubDept());

                    if(departmentList.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, departmentList);
                        popupspnDepartment.setAdapter(arrayAdapter);

                        popupspnDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                departmentID = String.valueOf(departmentList.get(popupspnDepartment.getSelectedItemPosition()).getId());
                                if (ConnectivityChecker.checker(mActivity)) {
                                    hitGetConsultant();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<GetDepartmentRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //Hit Transfer Patient
    private void hitTransferPatient() {
        Utils.showRequestDialog(mActivity);
        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<Universalres> call = iRestInterfaces.hitTransferPatient(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                consultantID,
                SharedPrefManager.getInstance(mActivity).getCr(),
                SharedPrefManager.getInstance(mActivity).getIpNo(),
                popUpEtReason.getText().toString(),
                departmentID,
                "transfer",
                wardID,
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()

        );

        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "Transfer Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    //if(!out)
                    hitAccept();
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "Data not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<Universalres> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //hit Get Consultant
    private void hitGetConsultant() {

        consultantLists.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<ConsultantRes> call = iRestInterfaces.getConsultantAndDepartmentList(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                departmentID
        );

        call.enqueue(new Callback<ConsultantRes>() {
            @Override
            public void onResponse(Call<ConsultantRes> call, Response<ConsultantRes> response) {

                if (response.isSuccessful()) {

                    consultantLists.add(new ConsultantList());
                    consultantLists.get(0).setUserID(0);
                    consultantLists.get(0).setDisplayName("Select Consultant");

                    consultantLists.addAll(response.body().getDoctors());


                    if (consultantLists.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, consultantLists);
                        popUpspnConsultant.setAdapter(arrayAdapter);

                        popUpspnConsultant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                consultantID = String.valueOf(consultantLists.get(popUpspnConsultant.getSelectedItemPosition()).getUserID());

                                Log.v("asfasgtrhasb", String.valueOf(consultantLists.get(popUpspnConsultant.getSelectedItemPosition()).getUserID()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Utils.hideDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }

                    //For ward List
                    wardLists.add(new WardList());
                    wardLists.get(0).setId(0);
                    wardLists.get(0).setName("Select Ward");

                    wardLists.addAll(response.body().getWards());

                    if (wardLists.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, wardLists);
                        popUpspnWard.setAdapter(arrayAdapter);

                        popUpspnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                wardID = String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId());
                                if(position!=0) hitGetBed();
                                Log.v("asfasgtrhasb", String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Utils.hideDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }


                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                //Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<ConsultantRes> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    private void hitGetBed() {

        bedList.clear();

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<BedResp> call = iRestInterfaces.GetWardBedNo(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getHeadID()),
                wardID,
                String.valueOf(SharedPrefManager.getInstance(mActivity).getSubdeptID())
        );

        call.enqueue(new Callback<BedResp>() {
            @Override
            public void onResponse(Call<BedResp> call, Response<BedResp> response) {

                if (response.isSuccessful()) {


                    bedList.add(new BedMaster());
                    bedList.get(0).setId(0);
                    bedList.get(0).setBedName("Select Bed");
                    ArrayAdapter arrayAdapter1 = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, bedList);
                    spnBed.setAdapter(arrayAdapter1);
                    bedList.addAll(response.body().getBedMaster());


                    if (bedList.size()>0) {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, bedList);
                        spnBed.setAdapter(arrayAdapter);

                        spnBed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                bedID = String.valueOf(bedList.get(spnBed.getSelectedItemPosition()).getId());

                                Log.v("asfasgtrhasb", String.valueOf(bedList.get(spnBed.getSelectedItemPosition()).getId()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Utils.hideDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    // error case
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                //Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<BedResp> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private void hitAccept() {

        Utils.showRequestDialog(mActivity);

        Api iRestInterfaces = ApiUtilsLocalUrl.getAPIService();
        Call<Universalres> call = iRestInterfaces.AcceptPatient(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(mActivity).getSubdeptID().toString(),
                "",
                SharedPrefManager.getInstance(mActivity).getIpNo(),
                consultantID,
                wardID,
                String.valueOf(bedID)
        );
        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Transfer Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(mActivity, "Unauthorized User", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(mActivity, "Data not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mActivity, "Internal Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<Universalres> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}