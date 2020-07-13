package com.trueform.era.his.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trueform.era.his.Model.AdmittedPatient;
import com.trueform.era.his.Model.AdmittedPatientICU;
import com.trueform.era.his.Model.ConsultantName;
import com.trueform.era.his.Model.DieteticsPatientList;
import com.trueform.era.his.Model.GetMemberId;
import com.trueform.era.his.Model.HeadAssign;
import com.trueform.era.his.Model.PatientDetail;
import com.trueform.era.his.Model.PatientHistory;
import com.trueform.era.his.Model.PatientList;
import com.trueform.era.his.Model.PhysioPatientList;
import com.trueform.era.his.Model.SubDept;
import com.trueform.era.his.Model.UserDetail;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "shared_pref";

    private SharedPreferences sharedprefs;
    private SharedPreferences.Editor editor;
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager appSharedprefs;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    private SharedPrefManager(Context context) {
        this.context = context;
        this.sharedprefs = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        this.editor = sharedprefs.edit();
    }

    public static SharedPrefManager getInstance(Context context) {
        if (appSharedprefs == null)
            appSharedprefs = new SharedPrefManager(context);
        return appSharedprefs;
    }

    public UserDetail getUser() {
        return new UserDetail(
                sharedprefs.getInt("userid", 0),
                sharedprefs.getString("username", null),
                sharedprefs.getString("displayName", null),
                sharedprefs.getString("accessToken", null),
                sharedprefs.getInt("desigid", 0)
        );
    }

    public void setUser(UserDetail user) {
        this.editor = sharedprefs.edit();
        editor.putInt("userid", user.getUserid());
        editor.putString("username", user.getUsername());
        editor.putString("accessToken", user.getAccessToken());
        editor.putString("displayName", user.getDisplayName());
        editor.putInt("desigid", user.getDesigid());
        editor.apply();
    }

    public void setSubHead(SubDept subDept) {
        this.editor = sharedprefs.edit();
        editor.putInt("id", subDept.getId());
        editor.putString("subHeadName", subDept.getSubDepartmentName());
        editor.apply();
    }

    public void setFCMToken(String FCMToken) {
        this.editor = sharedprefs.edit();
        editor.putString("FCMToken", FCMToken);
        editor.apply();
    }

    public void setIsCovid(Boolean isCovid) {
        this.editor = sharedprefs.edit();
        editor.putBoolean("covid", isCovid);
        editor.apply();
    }
    public Boolean isCovid() {
        return sharedprefs.getBoolean("covid", false);
    }
    public void setIntakeDate(String intakeDate) {
        this.editor = sharedprefs.edit();
        editor.putString("intakeDate", intakeDate);
        editor.apply();
    }

    public String getIntakeDate() {
        return sharedprefs.getString("intakeDate", null);
    }

    public void setMemberId(GetMemberId getMemberId) {
        this.editor = sharedprefs.edit();
        editor.putInt("memberId", getMemberId.getMemberId());
        editor.putInt("userLoginId", getMemberId.getUserLoginId());
        editor.apply();
    }

    public String getFCMToken() {
        return sharedprefs.getString("FCMToken", null);
    }

    public GetMemberId getMemberId() {
        return new GetMemberId(
                sharedprefs.getInt("memberId", 0),
                sharedprefs.getInt("userLoginId", 0)
        );
    }

    public void setHeadID(Integer headID, String headName, String color) {
        this.editor = sharedprefs.edit();
        editor.putInt("headID", headID);
        editor.putString("head", headName);
        editor.putString("color", color);
        editor.apply();
    }
    public void setSubdeptID(Integer subDeptId) {
        this.editor = sharedprefs.edit();
        editor.putInt("subDepartmentID", subDeptId);
        editor.apply();
    }
    public Integer getSubdeptID() {
        return sharedprefs.getInt("subDepartmentID", 0);
    }

    public Integer getHeadID() {
        return sharedprefs.getInt("headID", 0);
    }

    public HeadAssign getHead() {
        return new HeadAssign(
                sharedprefs.getInt("headID", 0),
                sharedprefs.getString("head", null),
                sharedprefs.getString("color", null)
        );
    }

    public void setAdmitPatient(AdmittedPatient admitPatient) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", admitPatient.getPid());
        editor.putInt("subDeptID", admitPatient.getSubDeptID());
        editor.putString("pname", admitPatient.getPname());
        editor.putString("age", admitPatient.getAge());
        editor.putString("ageUnit", admitPatient.getAgeUnit());
        editor.putString("sex", admitPatient.getSex());
        editor.putString("gender", admitPatient.getGender());
        editor.apply();
    }
    public void setCovidPatient(PatientList admitPatient) {
        this.editor = sharedprefs.edit();
        editor.putInt("appointmentId", admitPatient.getAppointmentId());
        editor.putInt("memberID", admitPatient.getMemberID());
        editor.putInt("pid", admitPatient.getPid());
        //editor.putInt("subDeptID", admitPatient.getSubDeptID());
        editor.putString("pname", admitPatient.getName());
        editor.putString("age", admitPatient.getAge());
        //editor.putString("ageUnit", admitPatient.getAgeUnit());
        //editor.putString("sex", admitPatient.getGender());
        editor.putString("gender", admitPatient.getGender());
        editor.putString("mobileNo", admitPatient.getMobileNo());
        editor.apply();
    }

    public void setIcuAdmitPatient(AdmittedPatientICU admitPatient) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", admitPatient.getPid());
        editor.putInt("subDeptID", admitPatient.getSubDeptID());
        editor.putString("pname", admitPatient.getPname());
        editor.putString("age", admitPatient.getAge());
        editor.putString("ageUnit", admitPatient.getAgeUnit());
        editor.putString("sex", admitPatient.getGender().substring(0, 0));
        editor.putString("gender", admitPatient.getGender());
        editor.apply();
    }

    public void setPhysioPatient(PhysioPatientList physioPatientList) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", physioPatientList.getPid());
        editor.putString("patientName", physioPatientList.getPatientName());
        editor.putString("age", physioPatientList.getAge());
        editor.putString("gender", physioPatientList.getGender());
        editor.apply();
    }

    public void setDieteticsPatient(DieteticsPatientList dieteticsPatientList) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", dieteticsPatientList.getpID());
        editor.putString("name", dieteticsPatientList.getName());
        editor.putInt("memberId", dieteticsPatientList.getMemberId());
        editor.putInt("userLoginId", dieteticsPatientList.getUserLoginId());
        editor.apply();
    }

    public DieteticsPatientList getDieteticsPatient() {
        return new DieteticsPatientList(
                sharedprefs.getInt("pid", 0),
                sharedprefs.getString("name", null),
                sharedprefs.getInt("memberId", 0),
                sharedprefs.getInt("userLoginId", 0)
        );
    }

    public void setPid(int pid) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", pid);
        editor.apply();
    }
    public void setfor(int pid) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", pid);
        editor.apply();
    }

    public int getPid() {
        return sharedprefs.getInt("pid", 0);
    }

    public void setPmId(int pid) {
        this.editor = sharedprefs.edit();
        editor.putInt("pmid", pid);
        editor.apply();
    }

    public int getPmId() {
        return sharedprefs.getInt("pmid", 0);
    }

    public void setIpNo(String ipNo) {
        this.editor = sharedprefs.edit();
        editor.putString("ipNo", ipNo);
        editor.apply();
    }

    public String getIpNo() {
        return sharedprefs.getString("ipNo", null);
    }

    public PhysioPatientList getPhysioPatient() {
        return new PhysioPatientList(
                sharedprefs.getInt("pid", 0),
                sharedprefs.getString("patientName", null),
                sharedprefs.getString("age", null),
                sharedprefs.getString("gender", null)
        );
    }

    public AdmittedPatientICU getIcuAdmitPatient() {
        return new AdmittedPatientICU(
                sharedprefs.getInt("pid", 0),
                sharedprefs.getString("pname", null),
                sharedprefs.getString("gender", null),
                sharedprefs.getString("age", null),
                sharedprefs.getString("ageUnit", null),
                sharedprefs.getInt("subDeptID", 0)
        );
    }

    public void setOpdPatient(PatientDetail patientDetail) {
        this.editor = sharedprefs.edit();
        editor.putInt("pid", patientDetail.getPid());
        editor.putString("pname", patientDetail.getPname());
        editor.putString("sex", patientDetail.getSex());
        editor.putString("gender", patientDetail.getGender());
        editor.putString("age", patientDetail.getAge());
        editor.putString("ageUnit", patientDetail.getAgeUnit());
        editor.apply();
    }

    public PatientDetail getOpdPatient() {
        return new PatientDetail(
                sharedprefs.getInt("pid", 0),
                sharedprefs.getString("pname", null),
                sharedprefs.getString("sex", null),
                sharedprefs.getString("gender", null),
                sharedprefs.getString("age", null),
                sharedprefs.getString("ageUnit", null)
        );
    }

    public AdmittedPatient getAdmitPatient() {
        return new AdmittedPatient(
                sharedprefs.getInt("pid", 0),
                sharedprefs.getInt("subDeptID", 0),
                sharedprefs.getString("pname", null),
                sharedprefs.getString("age", null),
                sharedprefs.getString("ageUnit", null),
                sharedprefs.getString("sex", null),
                sharedprefs.getString("gender", null)
        );
    }
    public PatientList getCovidPatient() {
        return new PatientList(
                sharedprefs.getInt("appointmentId", 0),
                sharedprefs.getInt("memberID", 0),
                sharedprefs.getInt("pid", 0),
                sharedprefs.getString("pname", null),
                sharedprefs.getString("gender", null),
                sharedprefs.getString("age", null),
                sharedprefs.getString("mobileNo", null)
        );
    }

    public void setHead(List<HeadAssign> head) {
        this.editor = sharedprefs.edit();
        for (int i = 0; i < head.size(); i++) {
            editor.putString("headID", String.valueOf(head.get(i).getHeadID()));
            editor.putString("headName", head.get(i).getHeadName());
            editor.putString("headDiscription", head.get(i).getHeadDiscription());
            editor.putString("color", head.get(i).getColor());
        }
        editor.apply();
    }

    public SubDept getSubDept() {
        return new SubDept(
                sharedprefs.getInt("id", 0),
                sharedprefs.getString("subHeadName", null)
        );
    }

    public void savePatientHistoryList(List<PatientHistory> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<PatientHistory> getPatientHistoryList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<PatientHistory>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void setConsultantList(List<ConsultantName> list) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("consList", json);
        editor.apply();
    }

    public ArrayList<ConsultantName> getConsultantList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("consList", null);
        Type type = new TypeToken<ArrayList<ConsultantName>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void saveHeadList(List<HeadAssign> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<HeadAssign> getHeadList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<HeadAssign>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userid", 0) != 0;
    }
}
