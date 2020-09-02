package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientDetailAngio;
import com.his.android.Model.TestListAngio;

import java.util.List;

public class AngioPatientDetailResp {
    @SerializedName("patientDetails")
    @Expose
    public List<PatientDetailAngio> patientDetails = null;
    @SerializedName("testList")
    @Expose
    public List<TestListAngio> testList = null;

    public List<PatientDetailAngio> getPatientDetails() {
        return patientDetails;
    }

    public List<TestListAngio> getTestList() {
        return testList;
    }
}
