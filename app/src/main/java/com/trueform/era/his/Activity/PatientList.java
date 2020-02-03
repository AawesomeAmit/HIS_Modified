package com.trueform.era.his.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Adapter.DieteticsPatientListAdp;
import com.trueform.era.his.Adapter.IcuPatientListAdp;
import com.trueform.era.his.Adapter.PatientListAdp;
import com.trueform.era.his.Adapter.PhysioPatientListAdp;
import com.trueform.era.his.Model.AdmittedPatient;
import com.trueform.era.his.Model.AdmittedPatientICU;
import com.trueform.era.his.Model.DieteticsPatientList;
import com.trueform.era.his.Model.PhysioPatientList;
import com.trueform.era.his.R;
import com.trueform.era.his.Response.DieteticsPatientResp;
import com.trueform.era.his.Response.IcuPatientListResp;
import com.trueform.era.his.Response.IpdPatientListResp;
import com.trueform.era.his.Response.PhysioPatientListResp;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.RetrofitClient1;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.database.DatabaseController;
import com.trueform.era.his.database.TableICUAdmittedPatientList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trueform.era.his.Fragment.NutriAnalyserFragment.NUTRI_TOKEN;

public class PatientList extends AppCompatActivity implements View.OnClickListener {

    TextView txtDept, txtDeptT, txtDrName, txtSubDept, img, btnGo;
    static RecyclerView rView;
    static Spinner spnSearch;
    EditText edtPid;
    DieteticsPatientResp dieteticsPatientResp;
    static Context context;
    RelativeLayout spnLayout;
    static RelativeLayout pidLayout;
    ArrayAdapter<AdmittedPatient> ipdArrayAdapter;
    ArrayAdapter<AdmittedPatientICU> icuArrayAdapter;
    List<AdmittedPatient> admittedPatientList;
    List<AdmittedPatientICU> icuPatientList;
    List<AdmittedPatient> admittedPatientList1;
    static List<PhysioPatientList> physioPatientLists;
    static List<DieteticsPatientList> dieteticsPatientLists;
    List<AdmittedPatientICU> icuPatientList1;
    IpdPatientListResp ipdPatientListResp;
    IcuPatientListResp icuPatientListResp;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        txtDept=findViewById(R.id.txtDept);
        txtSubDept=findViewById(R.id.txtSubDept);
        txtDeptT=findViewById(R.id.txtDeptT);
        txtDrName=findViewById(R.id.txtDrName);
        edtPid=findViewById(R.id.edtPid);
        btnGo=findViewById(R.id.btnGo);
        spnSearch=findViewById(R.id.spnSearch);
        img = findViewById(R.id.img);
        spnLayout=findViewById(R.id.spnLayout);
        pidLayout=findViewById(R.id.pidLayout);
        admittedPatientList=new ArrayList<>();
        admittedPatientList1=new ArrayList<>();
        physioPatientLists=new ArrayList<>();
        context= PatientList.this;
        icuPatientList=new ArrayList<>();
        icuPatientList1=new ArrayList<>();
        rView=findViewById(R.id.rView);
        btnGo.setOnClickListener(this);
        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        String dept=SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName()+" Department";
        String subDept=SharedPrefManager.getInstance(this).getHead().getHeadName()+" Patient List";
        txtSubDept.setText(subDept);
        txtDept.setText(dept);
        txtDeptT.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());
        rView.setLayoutManager(new LinearLayoutManager(this));

        if(SharedPrefManager.getInstance(this).getHeadID() == 2) {

            if (ConnectivityChecker.checker(context)){
                Utils.showRequestDialog(context);
                Call<IpdPatientListResp> call = RetrofitClient.getInstance().getApi().getIPDPatientList(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
                call.enqueue(new Callback<IpdPatientListResp>() {
                    @Override
                    public void onResponse(Call<IpdPatientListResp> call, Response<IpdPatientListResp> response) {
                        Utils.showRequestDialog(context);
                        if (response.isSuccessful()) {
                            ipdPatientListResp = response.body();
                            if (ipdPatientListResp != null) {
                                ipdArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, ipdPatientListResp.getAdmittedPatient());
                                ipdArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                                spnSearch.setAdapter(ipdArrayAdapter);
                                spnSearch.setSelection(-1);
                                admittedPatientList = ipdPatientListResp.getAdmittedPatient();
                                admittedPatientList1 = ipdPatientListResp.getAdmittedPatient();
                                rView.setAdapter(new PatientListAdp(PatientList.this, admittedPatientList));
                            }
                        } else{
                            Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<IpdPatientListResp> call, Throwable t) {
                        Toast.makeText(PatientList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
            }else {
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

        }else if(SharedPrefManager.getInstance(this).getHeadID()==7){
            bindDieteticsPatient();
        }else if(SharedPrefManager.getInstance(this).getHeadID()==9){
            bindPhysioList();
        } else if(SharedPrefManager.getInstance(this).getHeadID()==3 || SharedPrefManager.getInstance(this).getHeadID()==4){
            int wardId;
            if(SharedPrefManager.getInstance(this).getHeadID()==3)
                wardId=39;
            else wardId=36;
            hitGetICUPatientList(wardId);
        }
        spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    try {
                        if(admittedPatientList.size()>0) {
                            admittedPatientList1=Collections.singletonList(admittedPatientList.get(i));
                            rView.setAdapter(new PatientListAdp(PatientList.this, admittedPatientList1));
                        }
                        else if(icuPatientList.size()>0) {
                            icuPatientList1=Collections.singletonList(icuPatientList.get(i));
                            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList1));
                        }
                        else if(physioPatientLists.size()>0) {
                            physioPatientLists=Collections.singletonList(physioPatientLists.get(i));
                            rView.setAdapter(new PhysioPatientListAdp(PatientList.this, physioPatientLists));
                        }
                        else if(dieteticsPatientLists.size()>0) {
                            dieteticsPatientLists=Collections.singletonList(dieteticsPatientLists.get(i));
                            rView.setAdapter(new DieteticsPatientListAdp(PatientList.this, dieteticsPatientLists));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(PatientList.this, img);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().logOut(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getFCMToken(), String.valueOf(SharedPrefManager.getInstance(PatientList.this).getUser().getUserid()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.showRequestDialog(PatientList.this);
                        if (response.isSuccessful()) {
                            Toast.makeText(PatientList.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(PatientList.this).clear();
                            Intent intent = new Intent(PatientList.this, MainActivity.class);
                            startActivity(intent);
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PatientList.this, "Network problem!", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
                return true;
            });
            menu.show();
        });
        /*rView.addOnItemTouchListener(new RecyclerTouchListener(this, rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 2) {
                    SharedPrefManager.getInstance(PatientList.this).setAdmitPatient(admittedPatientList1.get(position));
                    SharedPrefManager.getInstance(PatientList.this).setPid(admittedPatientList1.get(position).getPid());
                    Intent intent = new Intent(PatientList.this, Dashboard.class);
                    startActivity(intent);
                }
                else if (SharedPrefManager.getInstance(PatientList.this).getHeadID() == 3 || SharedPrefManager.getInstance(PatientList.this).getHeadID() == 4) {
                    SharedPrefManager.getInstance(PatientList.this).setIcuAdmitPatient(icuPatientList1.get(position));
                    SharedPrefManager.getInstance(PatientList.this).setPid(icuPatientList1.get(position).getPid());
                    Intent intent = new Intent(PatientList.this, Dashboard.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }
    public void bindDieteticsPatient(){
        Utils.showRequestDialog(context);
        Call<DieteticsPatientResp> call= RetrofitClient1.getInstance().getApi().getNutritionalPanelPatientList(
                NUTRI_TOKEN,
                SharedPrefManager.getInstance(context).getUser().getUserid().toString());
        call.enqueue(new Callback<DieteticsPatientResp>() {
            @Override
            public void onResponse(Call<DieteticsPatientResp> call, Response<DieteticsPatientResp> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null && response.body().getResponseCode() == 1) {
                        dieteticsPatientResp = response.body();
                        if (dieteticsPatientResp != null && dieteticsPatientResp.getResponseValue().size() > 0) {
                            ArrayAdapter<DieteticsPatientList> dieteticsAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_text_size, dieteticsPatientResp.getResponseValue());
                            dieteticsAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                            spnSearch.setAdapter(dieteticsAdapter);
                            spnSearch.setSelection(-1);
                            dieteticsPatientLists = dieteticsPatientResp.getResponseValue();
                            rView.setAdapter(new DieteticsPatientListAdp(context, dieteticsPatientLists));
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<DieteticsPatientResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    public void bindPhysioList(){
        Utils.showRequestDialog(context);
        pidLayout.setVisibility(View.VISIBLE);
        Call<PhysioPatientListResp> call = RetrofitClient.getInstance().getApi().getPhysiotherapyPatientList(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
        call.enqueue(new Callback<PhysioPatientListResp>() {
            @Override
            public void onResponse(Call<PhysioPatientListResp> call, Response<PhysioPatientListResp> response) {
                if (response.isSuccessful()) {
                    PhysioPatientListResp physioPatientListResp = response.body();
                    if (physioPatientListResp != null && physioPatientListResp.getPhysioPatientList().size() > 0) {
                        ArrayAdapter<PhysioPatientList> physioAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_text_size, physioPatientListResp.getPhysioPatientList());
                        physioAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                        spnSearch.setAdapter(physioAdapter);
                        spnSearch.setSelection(-1);
                        physioPatientLists = physioPatientListResp.getPhysioPatientList();
                        rView.setAdapter(new PhysioPatientListAdp(context, physioPatientListResp.getPhysioPatientList()));
                    }
                } else
                    Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<PhysioPatientListResp> call, Throwable t) {
                Utils.hideDialog();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PatientList.this, PreDashboard.class));
    }

    @Override
    public void onClick(View view) {
        Utils.showRequestDialog(context);
        if(view.getId()==R.id.btnGo){
            if(!edtPid.getText().toString().isEmpty()) {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().savePatientPhysiotherapyPanel(SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(context).getUser().getUserid().toString(), SharedPrefManager.getInstance(PatientList.this).getHeadID(), edtPid.getText().toString().trim(), SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(), SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(PatientList.this, "Patient Added Successfully!", Toast.LENGTH_LONG).show();
                            bindPhysioList();
                        }else {
                            try {
                                Toast.makeText(PatientList.this, response.errorBody() != null ? response.errorBody().string() : null, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Utils.hideDialog();
                    }
                });
            } else Toast.makeText(this, "Please enter PID!", Toast.LENGTH_SHORT).show();
        }
    }

    private void hitGetICUPatientList(Integer wardId){

        if (ConnectivityChecker.checker(context)){

            Utils.showRequestDialog(context);
            Call<IcuPatientListResp> call= RetrofitClient.getInstance().getApi().getICUPatientList(
                    SharedPrefManager.getInstance(PatientList.this).getUser().getAccessToken(),
                    SharedPrefManager.getInstance(context).getUser().getUserid().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getSubDept().getId(),
                    wardId,
                    SharedPrefManager.getInstance(PatientList.this).getUser().getUserid());
            call.enqueue(new Callback<IcuPatientListResp>() {
                @Override
                public void onResponse(Call<IcuPatientListResp> call, Response<IcuPatientListResp> response) {
                    if (response.isSuccessful()) {
                        icuPatientListResp = response.body();
                        if (icuPatientListResp != null) {
                            icuArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, icuPatientListResp.getAdmittedPatientICU());
                            icuArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
                            spnSearch.setAdapter(icuArrayAdapter);
                            spnSearch.setSelection(-1);
                            icuPatientList=icuPatientListResp.getAdmittedPatientICU();
                            icuPatientList1=icuPatientListResp.getAdmittedPatientICU();
                            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList));

                            try{
                                DatabaseController.myDataBase.beginTransaction();

                                DatabaseController.deleteRow(TableICUAdmittedPatientList.icu_admitted_patient_list,
                                        TableICUAdmittedPatientList.icuPatientColumn.headId_subDeptId.toString(),
                                        SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString());

                                for (int i = 0; i < response.body().getAdmittedPatientICU().size(); i++) {

                                    AdmittedPatientICU admittedPatientICU = response.body().getAdmittedPatientICU().get(i);

                                    ContentValues contentValues = new ContentValues();

                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.headId_subDeptId.toString(), SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pmid.toString(), admittedPatientICU.getPmid());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.wardID.toString(), admittedPatientICU.getWardID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.previousWardID.toString(), admittedPatientICU.getPreviousWardID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.bedName.toString(), admittedPatientICU.getBedName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pid.toString(), admittedPatientICU.getPid());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.crNo.toString(), admittedPatientICU.getCrNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ipNo.toString(), admittedPatientICU.getIpNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.userID.toString(), admittedPatientICU.getUserID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.subDeptID.toString(), admittedPatientICU.getSubDeptID());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.subDepartmentName.toString(), admittedPatientICU.getSubDepartmentName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.status.toString(), admittedPatientICU.getStatus());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.consultantName.toString(), admittedPatientICU.getConsultantName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.pname.toString(), admittedPatientICU.getPname());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.sex.toString(), admittedPatientICU.getSex());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.gender.toString(), admittedPatientICU.getGender());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.age.toString(), admittedPatientICU.getAge());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ageUnit.toString(), admittedPatientICU.getAgeUnit());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.address.toString(), admittedPatientICU.getAddress());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.fatherName.toString(), admittedPatientICU.getFatherName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.phoneNo.toString(), admittedPatientICU.getPhoneNo());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.relation.toString(), admittedPatientICU.getRelation());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.admitDate.toString(), admittedPatientICU.getAdmitDate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.dischargeDate.toString(), admittedPatientICU.getDischargeDate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.ddate.toString(), admittedPatientICU.getDdate());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.dischargeStatus.toString(), admittedPatientICU.getDischargeStatus());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.previousWardName.toString(), admittedPatientICU.getPreviousWardName());
                                    contentValues.put(TableICUAdmittedPatientList.icuPatientColumn.notificationCount.toString(), admittedPatientICU.getNotificationCount());

                                   // DatabaseController.insertUpdateData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list, "subDeptID", admittedPatientICU.getPmid().toString());
//                                    DatabaseController.insertUpdateData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list, "subDeptID", admittedPatientICU.getPid().toString());

                                    DatabaseController.insertData(contentValues, TableICUAdmittedPatientList.icu_admitted_patient_list);

                                }

                                DatabaseController.myDataBase.setTransactionSuccessful();

                            } finally {
                                DatabaseController.myDataBase.endTransaction();

                                Utils.hideDialog();


                            }

                        }
                    } else
                        Toast.makeText(PatientList.this, response.message(), Toast.LENGTH_SHORT).show();
                    Utils.hideDialog();
                }

                @Override
                public void onFailure(Call<IcuPatientListResp> call, Throwable t) {
                    Utils.hideDialog();
                }
            });
        }else {

            icuPatientListResp = new IcuPatientListResp();

            icuPatientList.addAll(DatabaseController.getICUAdmittedPatientList(
                    SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString()));

            icuPatientList1.addAll(DatabaseController.getICUAdmittedPatientList(SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString(),
                    SharedPrefManager.getInstance(PatientList.this).getHeadID() + SharedPrefManager.getInstance(PatientList.this).getSubDept().getId().toString()));

            icuPatientListResp.setAdmittedPatientICU(icuPatientList);

            icuArrayAdapter = new ArrayAdapter<>(PatientList.this, R.layout.spinner_item_text_size, icuPatientListResp.getAdmittedPatientICU());
            icuArrayAdapter.setDropDownViewResource(R.layout.spinner_item_text_size);
            spnSearch.setAdapter(icuArrayAdapter);
            spnSearch.setSelection(-1);

//            icuPatientList = icuPatientListResp.getAdmittedPatientICU();
//            icuPatientList1 = icuPatientListResp.getAdmittedPatientICU();
            rView.setAdapter(new IcuPatientListAdp(PatientList.this, icuPatientList));

        }


    }
}