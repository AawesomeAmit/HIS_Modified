package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientDetailAngio;
import com.trueform.era.his.Model.TestListAngio;

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
