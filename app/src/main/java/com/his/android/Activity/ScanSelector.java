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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanSelector extends BaseActivity {
    TextView txtPrescription, txtTransferIn, txtTransferOut, txtDischarge, txtVital, txtUpload, txtUpdateIntake,
            tvPtName, tvPID, txtIntake, txtUpdateVital, txtProgressNote,txtScanIntake, txtChecklist, txtUpdateVitals, txtO2, txtVentilator;
    Intent intent;
    Spinner popupspnDepartment,popUpspnConsultant,popUpspnWard, spnBed;
    EditText popUpEtReason;
    Dialog dialog;
    ArrayAdapter arrayAdapter;
    List<GetDepartmentList> departmentList = new ArrayList<>();
    List<ConsultantList> consultantLists = new ArrayList<>();
    List<BedMaster> bedList = new ArrayList<>();
    static List<Ward> wardLists = new ArrayList<>();
    private String departmentID="";
    private String consultantID="";
    private String bedID="";
    private String wardID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_selector);
        txtPrescription = findViewById(R.id.txtPrescription);
        txtUpload = findViewById(R.id.txtUpload);
        txtUpdateVital = findViewById(R.id.txtUpdateVital);
        txtScanIntake = findViewById(R.id.txtScanIntake);
        txtTransferIn = findViewById(R.id.txtTransferIn);
        tvPtName = findViewById(R.id.tvPtName);
        tvPID = findViewById(R.id.tvPID);
        txtO2 = findViewById(R.id.txtO2);
        txtVentilator = findViewById(R.id.txtVentilator);
        txtChecklist = findViewById(R.id.txtChecklist);
        txtUpdateVitals = findViewById(R.id.txtUpdateVitals);
        txtUpdateIntake = findViewById(R.id.txtUpdateIntake);
        //txtTransferOut = findViewById(R.id.txtTransferOut);
        txtDischarge = findViewById(R.id.txtDischarge);
        txtProgressNote = findViewById(R.id.txtProgressNote);
        txtVital = findViewById(R.id.txtVital);
        txtIntake = findViewById(R.id.txtIntake);
        tvPID.setText(String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        tvPtName.setText(SharedPrefManager.getInstance(mActivity).getPtName());
        intent = new Intent(mActivity, Dashboard.class);
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
        txtChecklist.setOnClickListener(view -> {
            intent.putExtra("status1", "4");
            startActivity(intent);
        });
        txtUpdateVitals.setOnClickListener(view -> {
            intent.putExtra("status1", "5");
            startActivity(intent);
        });
        txtTransferIn.setOnClickListener(view -> alertPatientTransfer());//alertTransferPatient("Transfer-In")
//        txtTransferOut.setOnClickListener(view -> alertTransferPatient("Transfer-Out"));
        txtDischarge.setOnClickListener(view -> {
            intent.putExtra("status1", "2");
            startActivity(intent);
        });
        txtScanIntake.setOnClickListener(view -> startActivity(new Intent(mActivity, MealScanner.class)));
        txtUpload.setOnClickListener(view -> startActivity(new Intent(mActivity, UploadImg.class)));
        txtUpdateVital.setOnClickListener(view -> startActivity(new Intent(mActivity, UpdateVital.class)));
        txtProgressNote.setOnClickListener(view -> startActivity(new Intent(mActivity, ProgressNoteScan.class)));
        txtUpdateIntake.setOnClickListener(view -> startActivity(new Intent(mActivity, DietIntakeSequence.class)));
        if (SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID() == 1) {
            txtProgressNote.setVisibility(View.VISIBLE);
            txtPrescription.setVisibility(View.VISIBLE);
            txtIntake.setVisibility(View.GONE);
        } else if (SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID() == 4) {
            txtProgressNote.setVisibility(View.GONE);
            txtPrescription.setVisibility(View.GONE);
            txtIntake.setVisibility(View.VISIBLE);
        }
//        hitGetUserProfileByPID();
        hitGetWard();
        if(SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID()==1){
            txtChecklist.setVisibility(View.GONE);
            txtUpdateVitals.setVisibility(View.GONE);
            txtIntake.setVisibility(View.GONE);
            txtScanIntake.setVisibility(View.GONE);
            txtO2.setVisibility(View.GONE);
            txtVentilator.setVisibility(View.GONE);
        } else if(SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID()==4){
            txtVital.setVisibility(View.GONE);
            txtProgressNote.setVisibility(View.GONE);
            txtPrescription.setVisibility(View.GONE);
            txtDischarge.setVisibility(View.GONE);
            txtO2.setVisibility(View.GONE);
            txtVentilator.setVisibility(View.GONE);
        } else if(SharedPrefManager.getInstance(mActivity).getUser().getUserTypeID()==5){
            txtChecklist.setVisibility(View.GONE);
            txtUpdateVitals.setVisibility(View.GONE);
            txtIntake.setVisibility(View.GONE);
            txtScanIntake.setVisibility(View.GONE);
            txtVital.setVisibility(View.GONE);
            txtProgressNote.setVisibility(View.GONE);
            txtPrescription.setVisibility(View.GONE);
            txtDischarge.setVisibility(View.GONE);
            txtTransferIn.setVisibility(View.GONE);
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
                        hitPatientTransfer();

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
    private void alertPatientTransfer() {
        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_transfer_patient);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        RelativeLayout relativelyBed=dialog.findViewById(R.id.relativelyBed);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        tvPID = dialog.findViewById(R.id.tvPID);
        tvPtName = dialog.findViewById(R.id.tvPtName);
        tvPID.setText(String.valueOf(SharedPrefManager.getInstance(mActivity).getPid()));
        tvPtName.setText(SharedPrefManager.getInstance(mActivity).getPtName());
        popUpspnWard = dialog.findViewById(R.id.spnWard);
        arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, wardLists);
        popUpspnWard.setAdapter(arrayAdapter);
        popUpEtReason = dialog.findViewById(R.id.etReason);
        ivClose.setOnClickListener(view -> dialog.dismiss());
        tvSubmit.setOnClickListener(view -> {
            try {
                if (popUpspnWard.getSelectedItemPosition()==0) {
                    Toast.makeText(mActivity, "Please select ward", Toast.LENGTH_SHORT).show();
                }/* else if (popUpEtReason.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter reason", Toast.LENGTH_SHORT).show();
                } */else {
                    if (ConnectivityChecker.checker(mActivity)) {
                        /*if(head.equalsIgnoreCase("transfer-out")) {
                            hitTransferPatient(true);
                        } else hitTransferPatient(false);*/
                        hitPatientTransfer();
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
    /*private void hitGetUserProfileByPID(){
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
    }*/

    private void hitGetWard() {
        departmentList.clear();
        Utils.showRequestDialog(mActivity);
        Call<WardResp> call = RetrofitClient.getInstance().getApi().getWardTransferList(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString()
        );

        call.enqueue(new Callback<WardResp>() {
            @Override
            public void onResponse(Call<WardResp> call, Response<WardResp> response) {
                if (response.isSuccessful()) {
                    wardLists.add(new Ward(0, "Select Ward", 0));
                    wardLists.addAll(response.body().getWardTransferList());
                    if (wardLists.size() > 0) {

                        arrayAdapter = new ArrayAdapter(mActivity, R.layout.inflate_spinner_item, wardLists);
//                        popUpspnWard.setAdapter(arrayAdapter);

                        /*popUpspnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                wardID = String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId());
                                if (position != 0) hitGetBed();
                                Log.v("asfasgtrhasb", String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId()));


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });*/
                        Utils.hideDialog();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_data_available), Toast.LENGTH_SHORT).show();
                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<WardResp> call, Throwable t) {

                Utils.hideDialog();
                // progressDialog.dismiss();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

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

    private void hitPatientTransfer() {
        Utils.showRequestDialog(mActivity);
        Call<Universalres> call = RetrofitClient.getInstance().getApi().patientIPDTransferToWard(
                SharedPrefManager.getInstance(mActivity).getUser().getAccessToken(),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(SharedPrefManager.getInstance(mActivity).getPmId()),
                SharedPrefManager.getInstance(mActivity).getUser().getUserid().toString(),
                String.valueOf(wardLists.get(popUpspnWard.getSelectedItemPosition()).getId())
        );

        call.enqueue(new Callback<Universalres>() {
            @Override
            public void onResponse(Call<Universalres> call, Response<Universalres> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Transferred Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    //if(!out)
//                    hitAccept();
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
                    wardLists.add(new Ward(0, "Select Ward", 0));

//                    wardLists.addAll(response.body());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
        startActivity(new Intent(mActivity, PreDashboard.class));
    }
}