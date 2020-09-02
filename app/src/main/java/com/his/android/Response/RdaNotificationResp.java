package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ActivityList;
import com.his.android.Model.InvestigationList;
import com.his.android.Model.PatientDetailRDA;
import com.his.android.Model.ProblemList;
import com.his.android.Model.SideEffectList;
import com.his.android.Model.SymptomList;

import java.util.List;

public class RdaNotificationResp {
    @SerializedName("patientDetails")
    @Expose
    private List<PatientDetailRDA> patientDetails = null;
    @SerializedName("sideEffectList")
    @Expose
    private List<SideEffectList> sideEffectList = null;
    @SerializedName("symptomList")
    @Expose
    private List<SymptomList> symptomList = null;
    @SerializedName("problemList")
    @Expose
    private List<ProblemList> problemList = null;
    @SerializedName("investigationList")
    @Expose
    private List<InvestigationList> investigationList = null;
    @SerializedName("vitalList")
    @Expose
    private List<VitalList> vitalLists = null;
    @SerializedName("activityList")
    @Expose
    private List<ActivityList> activityLists = null;

    public List<PatientDetailRDA> getPatientDetails() {
        return patientDetails;
    }

    public List<SideEffectList> getSideEffectList() {
        return sideEffectList;
    }

    public List<SymptomList> getSymptomList() {
        return symptomList;
    }

    public List<ProblemList> getProblemList() {
        return problemList;
    }

    public List<InvestigationList> getInvestigationList() {
        return investigationList;
    }

    public List<VitalList> getVitalLists() {
        return vitalLists;
    }

    public List<ActivityList> getActivityLists() {
        return activityLists;
    }
}
