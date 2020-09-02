package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientTest;

import java.util.List;

public class BillDetailsResp {
    @SerializedName("patientTest")
    @Expose
    public List<PatientTest> patientTest = null;

    public void setPatientTest(List<PatientTest> patientTest) {
        this.patientTest = patientTest;
    }

    public List<PatientTest> getPatientTest() {
        return patientTest;
    }
}
