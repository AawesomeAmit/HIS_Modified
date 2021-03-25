package com.his.android.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.his.android.Activity.UploadMultipleImg.DischargePatient.DischargePatient;
import com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty.EmployeesOnDuty;
import com.his.android.Fragment.ActivityProblemInput;
import com.his.android.Fragment.CalciumPatientReport;
import com.his.android.Fragment.CalciumReportDynamic;
import com.his.android.Fragment.Calculator;
import com.his.android.Fragment.CombinedGraph;
import com.his.android.Fragment.EmployeeDetail;
import com.his.android.Fragment.InputVital;
import com.his.android.Fragment.Intake;
import com.his.android.Fragment.IntakeInput;
import com.his.android.Fragment.IntakeOutputVitalRange;
import com.his.android.Fragment.NutritiAnalyzer;
import com.his.android.Fragment.ObservationGraph;
import com.his.android.Fragment.Output;
import com.his.android.Fragment.PatientDetailGraph;
import com.his.android.Fragment.PatientInput;
import com.his.android.Fragment.ProgressNote;
import com.his.android.Fragment.Questionnaire;
import com.his.android.Fragment.VentilatorSetting;
import com.his.android.Fragment.ViewInvestigation;
import com.his.android.Fragment.ViewMedication;
import com.his.android.Fragment.VitalGraph;
import com.his.android.Model.ConsultantName;
import com.his.android.R;
import com.his.android.Response.ControlBySubDeptResp;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtPName, txtPId;   //txtPAge, txtGender
    public static Spinner spnConsultant;
    List<ConsultantName> consultantNameList;
    LinearLayout llHeader;
    Fragment fragment;
    int subdept = 0;
    public ArrayAdapter<ConsultantName> consltantAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtPName = findViewById(R.id.txtPName);
        llHeader = findViewById(R.id.llHeader);
        txtPId = findViewById(R.id.txtPId);
        spnConsultant = findViewById(R.id.spnConsultant);
        consultantNameList = new ArrayList<>();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        /*Menu menu=navigationView.getMenu();
        if(SharedPrefManager.getInstance(Dashboard.this).getHeadID()==7) {
            menu.findItem(R.id.nav_vital_graph).setVisible(false);
            menu.findItem(R.id.nav_detail_graph).setVisible(false);
            menu.findItem(R.id.nav_prescription).setVisible(false);
            menu.findItem(R.id.nav_view_medication).setVisible(true);
            menu.findItem(R.id.nav_investigation).setVisible(true);
            menu.findItem(R.id.nav_input_vital).setVisible(false);
            menu.findItem(R.id.nav_input_intake).setVisible(false);
            menu.findItem(R.id.nav_output).setVisible(false);
            menu.findItem(R.id.nav_range).setVisible(false);
            menu.findItem(R.id.nav_activity_problem).setVisible(false);
            menu.findItem(R.id.nav_activity_movement).setVisible(false);
            menu.findItem(R.id.nav_progress_note).setVisible(false);
        }*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (SharedPrefManager.getInstance(this).getHeadID() != 10) {
            if (getIntent().getStringExtra("statuss") != null) {
                switch (Objects.requireNonNull(getIntent().getStringExtra("statuss"))) {
                    case "1":
                        if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                            spnConsultant.setVisibility(View.VISIBLE);
                        else spnConsultant.setVisibility(View.GONE);
                        fragment = new Prescription();
                        break;
                    case "3":
                        spnConsultant.setVisibility(View.GONE);
                        fragment = new Questionnaire();
                        break;
                    case "4":
                        spnConsultant.setVisibility(View.GONE);
                        fragment = new ViewMedication();
                        break;
                    case "5":
                        spnConsultant.setVisibility(View.GONE);
                        fragment = new ViewInvestigation();
                        break;
                    default:
                        fragment = new ObservationGraph();
                        spnConsultant.setVisibility(View.GONE);
                        break;
                }
            } else {
                fragment = new ObservationGraph();
                spnConsultant.setVisibility(View.GONE);
            }
        } else {
            fragment = new PatientDashboard();
            llHeader.setVisibility(View.GONE);
        }
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        if (SharedPrefManager.getInstance(this).getHeadID() == 10) {
            menu.findItem(R.id.nav_cal_patient).setVisible(true);
            menu.findItem(R.id.nav_patient_dashboard).setVisible(true);
            menu.findItem(R.id.nav_employee).setVisible(true);
            menu.findItem(R.id.nav_cal_dynamic).setVisible(true);
            menu.findItem(R.id.nav_ventilator).setVisible(true);
            menu.findItem(R.id.nav_vital_graph).setVisible(false);
            menu.findItem(R.id.nav_detail_graph).setVisible(false);
            menu.findItem(R.id.nav_prescription).setVisible(false);
            menu.findItem(R.id.nav_view_medication).setVisible(false);
            menu.findItem(R.id.nav_investigation).setVisible(false);
            menu.findItem(R.id.nav_input_vital).setVisible(false);
            menu.findItem(R.id.nav_input_intake).setVisible(false);
            menu.findItem(R.id.nav_output).setVisible(false);
            menu.findItem(R.id.nav_range).setVisible(false);
            menu.findItem(R.id.nav_activity_problem).setVisible(false);
            menu.findItem(R.id.nav_activity_movement).setVisible(false);
            menu.findItem(R.id.nav_progress_note).setVisible(false);
            menu.findItem(R.id.nav_observation).setVisible(false);
            menu.findItem(R.id.nav_calculator).setVisible(false);
            menu.findItem(R.id.nav_nutrition).setVisible(false);
            menu.findItem(R.id.nav_questionnaire).setVisible(false);
            menu.findItem(R.id.nav_patient_list).setVisible(false);
        }
        if (SharedPrefManager.getInstance(this).getHeadID() == 2)
            txtPName.setText(SharedPrefManager.getInstance(Dashboard.this).getAdmitPatient().getPname());
        else if (SharedPrefManager.getInstance(this).getHeadID() == 3 || SharedPrefManager.getInstance(this).getHeadID() == 4 || SharedPrefManager.getInstance(this).getHeadID() == 2029 || SharedPrefManager.getInstance(this).getHeadID() == 2030)
            txtPName.setText(SharedPrefManager.getInstance(Dashboard.this).getIcuAdmitPatient().getPname());
        else if (SharedPrefManager.getInstance(this).getHeadID() == 9)
            txtPName.setText(SharedPrefManager.getInstance(Dashboard.this).getPhysioPatient().getPatientName());
        else if (SharedPrefManager.getInstance(Dashboard.this).getHeadID() == 7)
            txtPName.setText(SharedPrefManager.getInstance(Dashboard.this).getDieteticsPatient().getName());
        else
            txtPName.setText(SharedPrefManager.getInstance(Dashboard.this).getOpdPatient().getPname());
        try {
            if (SharedPrefManager.getInstance(Dashboard.this).isScanned()) {
                menu.findItem(R.id.nav_patient_list).setVisible(false);
                //   txtPName.setText(ScannerActivity.patientInfo.getPatientName());
            }
//        ScannerActivity.patientInfo = null;
            txtPId.setText(String.valueOf(SharedPrefManager.getInstance(this).getPid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getIntent().getStringExtra("status1") != null || getIntent().getStringExtra("status") != null)
            subdept = SharedPrefManager.getInstance(this).getSubdeptID();
        else subdept = SharedPrefManager.getInstance(this).getSubDept().getId();
        /*if (getIntent().getStringExtra("status") != null)
            subdept=SharedPrefManager.getInstance(this).getSubdeptID();
        else subdept=SharedPrefManager.getInstance(this).getSubDept().getId();*/
        consultantNameList.add(0, new ConsultantName(0, 0, "Select Consultant", 0));
        Call<ControlBySubDeptResp> call = RetrofitClient.getInstance().getApi().getControlsBySubDept(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(Dashboard.this).getUser().getUserid().toString(), subdept, SharedPrefManager.getInstance(this).getHeadID(), SharedPrefManager.getInstance(this).getUser().getUserid());
        call.enqueue(new Callback<ControlBySubDeptResp>() {
            @Override
            public void onResponse(Call<ControlBySubDeptResp> call, Response<ControlBySubDeptResp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        consultantNameList.addAll(1, response.body().getConsultantName());
                    }
                    SharedPrefManager.getInstance(Dashboard.this).setConsultantList(consultantNameList);
                }
                consltantAdp = new ArrayAdapter<>(Dashboard.this, R.layout.spinner_layout, consultantNameList);
                spnConsultant.setAdapter(consltantAdp);
                spnConsultant.setSelection(0);
                /*if (getIntent().getStringExtra("status1") != null) {
                    if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("0")) {
                        if (SharedPrefManager.getInstance(Dashboard.this).getUser().getDesigid() != 1)
                            spnConsultant.setVisibility(View.VISIBLE);
                        else spnConsultant.setVisibility(View.GONE);
                        fragment = new Prescription();
                    }
                    if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("1"))
                        fragment = new ObservationGraph();
                    if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("2"))
                        fragment = new DischargePatient();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }*/
            }

            @Override
            public void onFailure(Call<ControlBySubDeptResp> call, Throwable t) {

            }
        });
        if (getIntent().getStringExtra("status1") != null) {
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("0")) {
                if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                    spnConsultant.setVisibility(View.VISIBLE);
                else spnConsultant.setVisibility(View.GONE);
                fragment = new Prescription();
            }
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("1"))
                fragment = new ObservationGraph();
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("2"))
                fragment = new DischargePatient();
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("3"))
                fragment = new Intake();
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("4"))
                fragment = new ViewMedication();
            if (Objects.requireNonNull(getIntent().getExtras()).getString("status1").equalsIgnoreCase("5")) {
                if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                    spnConsultant.setVisibility(View.VISIBLE);
                else spnConsultant.setVisibility(View.GONE);
                fragment = new InputVital();
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        /*if(SharedPrefManager.getInstance(Dashboard.this).getHeadID()==7){
            menu.findItem(R.id.nav_patient_list).setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_patient_list) {
            if (SharedPrefManager.getInstance(Dashboard.this).getHeadID() != 1) {
                Intent intent = new Intent(this, PatientList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } else if (id == R.id.nav_dashboard) {
            Intent intent = new Intent(this, PreDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_observation) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new ObservationGraph();
        } else if (id == R.id.nav_analysing) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new CombinedGraph();
            //fragment = new ObservationGraph();
        } else if (id == R.id.nav_prescription) {
            //if (SharedPrefManager.getInstance(Dashboard.this).getHeadID() != 1)
            {
                if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                    spnConsultant.setVisibility(View.VISIBLE);
                else spnConsultant.setVisibility(View.GONE);
                fragment = new Prescription();
            }
        } else if (id == R.id.nav_investigation) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new ViewInvestigation();
        } else if (id == R.id.nav_cal_patient) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new CalciumPatientReport();
        } else if (id == R.id.nav_patient_dashboard) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new PatientDashboard();
        } else if (id == R.id.nav_intake) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new IntakeInput();
        } else if (id == R.id.nav_cal_dynamic) {
            fragment = new CalciumReportDynamic();
        } else if (id == R.id.nav_ventilator) {
            fragment = new VentilatorSetting();
        } else if (id == R.id.nav_questionnaire) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new Questionnaire();
        } else if (id == R.id.nav_employee) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new EmployeeDetail();
        } else if (id == R.id.nav_detail_graph) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new PatientDetailGraph();
        } else if (id == R.id.nav_view_medication) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new ViewMedication();
        } else if (id == R.id.nav_activity_problem) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new ActivityProblemInput();
        } else if (id == R.id.nav_nutrition) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new NutritiAnalyzer();
        } else if (id == R.id.nav_progress_note) {
            if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                spnConsultant.setVisibility(View.VISIBLE);
            else spnConsultant.setVisibility(View.GONE);
            fragment = new ProgressNote();
        } else if (id == R.id.nav_calculator) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new Calculator();
        } else if (id == R.id.uploaddocument) {

            Intent intent = new Intent(Dashboard.this, UploadDocument.class);
            startActivity(intent);

        } else if (id == R.id.nav_input_vital) {
            if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                spnConsultant.setVisibility(View.VISIBLE);
            else spnConsultant.setVisibility(View.GONE);
            fragment = new InputVital();
        } else if (id == R.id.nav_input_intake) {
            spnConsultant.setVisibility(View.GONE);
//            fragment = new IntakeInput();
            fragment = new Intake();
        } else if (id == R.id.nav_output) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new Output();
        } else if (id == R.id.nav_vital_graph) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new VitalGraph();
        } else if (id == R.id.nav_activity_movement) {
            spnConsultant.setVisibility(View.GONE);
            //fragment = new PatientMovementInsert();
            fragment = new PatientInput();
        } else if (id == R.id.nav_range) {
            if (SharedPrefManager.getInstance(this).getUser().getDesigid() != 1)
                spnConsultant.setVisibility(View.VISIBLE);
            else spnConsultant.setVisibility(View.GONE);
            fragment = new IntakeOutputVitalRange();
        } else if (id == R.id.nav_emp_on_duty) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new EmployeesOnDuty();
        } else if (id == R.id.nav_discharge_patient) {
            spnConsultant.setVisibility(View.GONE);
            fragment = new DischargePatient();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
